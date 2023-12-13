package com.ruoshui.flink.ao.impl;

import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.flink.ao.JobBaseServiceAO;
import com.ruoshui.flink.ao.JobServerAO;
import com.ruoshui.flink.streaming.web.common.MessageConstants;
import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.enums.JobConfigStatus;
import com.ruoshui.flink.streaming.web.enums.SysErrorEnum;
import com.ruoshui.flink.streaming.web.enums.YN;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.streaming.web.model.dto.JobRunParamDTO;
import com.ruoshui.flink.rpc.CommandRpcClinetAdapter;
import com.ruoshui.flink.rpc.YarnRestRpcAdapter;
import com.ruoshui.flink.rpc.model.JobInfo;
import com.ruoshui.flink.service.JobConfigService;
import com.ruoshui.flink.service.SavepointBackupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-20
 * @time 23:11
 */
@Component("jobYarnServerAO")
@Slf4j
public class JobYarnServerAOImpl implements JobServerAO {

    //最大重试次数
    private static final Integer TRY_TIMES = 2;

    @Resource
    private JobConfigService jobConfigService;


    @Resource
    private YarnRestRpcAdapter yarnRestRpcAdapter;

    @Resource
    private CommandRpcClinetAdapter commandRpcClinetAdapter;

    @Resource
    private SavepointBackupService savepointBackupService;

    @Resource
    private JobBaseServiceAO jobBaseServiceAO;

    @Resource
    private BaseResourceMapper baseResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start(Long id, Long savepointId, String userName) throws UnsupportedEncodingException {

        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(id);
        //通过flink集群id查询集群信息
        BaseResource baseResource = baseResourceMapper.getById(Math.toIntExact(jobConfigDTO.getFlinkCluster()));
        if(baseResource==null){
            throw new BizException("未找到flink服务，请前去配置");
        }

        //1、检查jobConfigDTO 状态等参数
        jobBaseServiceAO.checkStart(jobConfigDTO);

        if (StringUtils.isNotEmpty(jobConfigDTO.getJobId())) {
            this.stop(jobConfigDTO);
        }

        //2、将配置的sql 写入本地文件并且返回运行所需参数
        JobRunParamDTO jobRunParamDTO = jobBaseServiceAO.writeSqlToFile(jobConfigDTO,baseResource.getServerIp(), baseResource.getServerUser(), AESUtil.decrypt(baseResource.getServerPassword()));

        //3、插一条运行日志数据
        Long jobRunLogId = jobBaseServiceAO.insertJobRunLog(jobConfigDTO, userName);

        //4、变更任务状态（变更为：启动中） 有乐观锁 防止重复提交
        jobConfigService.updateStatusByStart(jobConfigDTO.getId(), userName, jobRunLogId, jobConfigDTO.getVersion());

        String savepointPath = savepointBackupService.getSavepointPathById(id, savepointId);

        //异步提交任务
        jobBaseServiceAO.aSyncExecJob(jobRunParamDTO, jobConfigDTO, jobRunLogId, savepointPath,baseResource.getServerIp(), baseResource.getServerUser(), AESUtil.decrypt(baseResource.getServerPassword()));

    }


    @Override
    public void stop(Long id, String userName) {
        log.info("[{}]开始停止任务[{}]", userName, id);
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(id);
        if (jobConfigDTO == null) {
            throw new BizException(SysErrorEnum.JOB_CONFIG_JOB_IS_NOT_EXIST);
        }
        //1、停止前做一次savepoint操作
        try {
            this.savepoint(id);
        } catch (Exception e) {
            log.error(MessageConstants.MESSAGE_008, e);
        }
        //2、停止任务
        this.stop(jobConfigDTO);
        JobConfigDTO jobConfig = new JobConfigDTO();
        jobConfig.setStatus(JobConfigStatus.STOP);
        jobConfig.setEditor(userName);
        jobConfig.setId(id);
        jobConfig.setJobId("");
        //3、变更状态
        jobConfigService.updateJobConfigById(jobConfig);
    }

    @Override
    public void savepoint(Long id) {
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(id);
        jobBaseServiceAO.checkSavepoint(jobConfigDTO);

        JobInfo jobInfo = yarnRestRpcAdapter.getJobInfoForPerYarnByAppId(jobConfigDTO.getJobId());
        if (jobInfo == null) {
            log.warn(MessageConstants.MESSAGE_007, jobConfigDTO.getJobName());
            throw new BizException(MessageConstants.MESSAGE_007);
        }
        //1、 执行savepoint
        try {
            commandRpcClinetAdapter.savepointForPerYarn(jobInfo.getId(),
                    SystemConstants.DEFAULT_SAVEPOINT_ROOT_PATH + id, jobConfigDTO.getJobId());
        } catch (Exception e) {
            log.error(MessageConstants.MESSAGE_008, e);
            throw new BizException(MessageConstants.MESSAGE_008);
        }

        String savepointPath = yarnRestRpcAdapter.getSavepointPath(jobConfigDTO.getJobId(), jobInfo.getId());
        if (StringUtils.isEmpty(savepointPath)) {
            log.warn(MessageConstants.MESSAGE_009, jobConfigDTO);
            throw new BizException(MessageConstants.MESSAGE_009);
        }
        //2、 执行保存Savepoint到本地数据库
        savepointBackupService.insertSavepoint(id, savepointPath, new Date());
    }


    @Override
    public void open(Long id, String userName) {
        jobConfigService.openOrClose(id, YN.Y, userName);
    }

    @Override
    public void close(Long id, String userName) {
        jobBaseServiceAO.checkClose(jobConfigService.getJobConfigById(id));

        jobConfigService.openOrClose(id, YN.N, userName);
    }


    private void stop(JobConfigDTO jobConfigDTO) {
        Integer retryNum = 1;
        while (retryNum <= TRY_TIMES) {
            JobInfo jobInfo = yarnRestRpcAdapter.getJobInfoForPerYarnByAppId(jobConfigDTO.getJobId());
            log.info("任务[{}]当前状态为：{}", jobConfigDTO.getId(), jobInfo);
            if (jobInfo != null && SystemConstants.STATUS_RUNNING.equals(jobInfo.getStatus())) {
                log.info("执行停止操作 jobYarnInfo={} retryNum={} id={}", jobInfo, retryNum, jobConfigDTO.getJobId());
                yarnRestRpcAdapter.cancelJobForYarnByAppId(jobConfigDTO.getJobId(), jobInfo.getId());
            } else {
                log.info("任务已经停止 jobYarnInfo={} id={}", jobInfo, jobConfigDTO.getJobId());
                break;
            }
            retryNum++;
        }
    }
}
