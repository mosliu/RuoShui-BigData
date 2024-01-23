package com.ruoshui.flink.ao.impl;

import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.flink.ao.JobBaseServiceAO;
import com.ruoshui.flink.ao.JobServerAO;
import com.ruoshui.flink.streaming.web.common.MessageConstants;
import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.enums.*;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.streaming.web.model.dto.JobRunParamDTO;
import com.ruoshui.flink.rpc.CommandRpcClinetAdapter;
import com.ruoshui.flink.rpc.FlinkRestRpcAdapter;
import com.ruoshui.flink.rpc.model.JobStandaloneInfo;
import com.ruoshui.flink.service.JobConfigService;
import com.ruoshui.flink.service.SavepointBackupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-20
 * @time 23:11
 */
@Component("jobStandaloneServerAO")
@Slf4j
public class JobStandaloneServerAOImpl implements JobServerAO {


    @Autowired
    private JobConfigService jobConfigService;

    @Autowired
    private SavepointBackupService savepointBackupService;

    @Autowired
    private CommandRpcClinetAdapter commandRpcClinetAdapter;

    @Autowired
    private FlinkRestRpcAdapter flinkRestRpcAdapter;

    @Autowired
    private JobBaseServiceAO jobBaseServiceAO;

    @Autowired
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

        if (StringUtils.isNotBlank(jobConfigDTO.getJobId())) {
            JobStandaloneInfo jobstatus = flinkRestRpcAdapter.getJobInfoForStandaloneByAppId(jobConfigDTO.getJobId(), jobConfigDTO.getDeployModeEnum());
            if (!("CANCELED".equals(jobstatus.getState()) || "FAILED".equals(jobstatus.getState()) || "FINISHED".equals(jobstatus.getState()))
                    && StringUtils.isNotBlank(jobstatus.getState())) {
                throw new BizException("请检查Flink任务列表，任务ID=[" + jobConfigDTO.getJobId() + "]处于[ "+jobstatus.getState()+"]状态，不能重复启动任务！") ; 
            }
        }

        //1、检查jobConfigDTO 状态等参数
        jobBaseServiceAO.checkStart(jobConfigDTO);

        // TODO 要不要检查集群上任务是否存在

        //2、将配置的sql 写入本地文件并且返回运行所需参数
        JobRunParamDTO jobRunParamDTO = jobBaseServiceAO.writeSqlToFile(jobConfigDTO,baseResource.getServerIp(), baseResource.getServerUser(), AESUtil.decrypt(baseResource.getServerPassword()));

        //3、插一条运行日志数据
        Long jobRunLogId = jobBaseServiceAO.insertJobRunLog(jobConfigDTO, userName);

        //4、变更任务状态（变更为：启动中） 有乐观锁 防止重复提交
        jobConfigService.updateStatusByStart(jobConfigDTO.getId(), userName, jobRunLogId, jobConfigDTO.getVersion());

        String savepointPath = savepointBackupService.getSavepointPathById(id, savepointId);

        //异步提交任务
        jobBaseServiceAO.aSyncExecJob(jobRunParamDTO, jobConfigDTO, jobRunLogId, savepointPath, baseResource.getServerIp(), baseResource.getServerUser(), AESUtil.decrypt(baseResource.getServerPassword()));

    }


    @Override
    public void stop(Long id, String userName) {
        log.info("[{}]开始停止任务[{}]", userName, id);
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(id);
        if (jobConfigDTO == null) {
            throw new BizException(SysErrorEnum.JOB_CONFIG_JOB_IS_NOT_EXIST);
        }
        JobStandaloneInfo jobStandaloneInfo = flinkRestRpcAdapter.getJobInfoForStandaloneByAppId(jobConfigDTO.getJobId(), jobConfigDTO.getDeployModeEnum());
        log.info("任务[{}]当前状态为：{}", id, jobStandaloneInfo);
        if (jobStandaloneInfo == null || StringUtils.isNotEmpty(jobStandaloneInfo.getErrors())) {
            log.warn("开始停止任务[{}]，getJobInfoForStandaloneByAppId is error jobStandaloneInfo={}", id, jobStandaloneInfo);
        } else {
            // 停止前先savepoint
            if (StringUtils.isNotBlank(jobConfigDTO.getFlinkCheckpointConfig())
                    && jobConfigDTO.getJobTypeEnum() != JobTypeEnum.SQL_BATCH
                    && SystemConstants.STATUS_RUNNING.equals(jobStandaloneInfo.getState())) {
                log.info("开始保存任务[{}]的状态-savepoint", id);
                this.savepoint(id);
            }
            //停止任务
            if (SystemConstants.STATUS_RUNNING.equals(jobStandaloneInfo.getState()) || SystemConstants.STATUS_RESTARTING.equals(jobStandaloneInfo.getState())) {
                flinkRestRpcAdapter.cancelJobForFlinkByAppId(jobConfigDTO.getJobId(), jobConfigDTO.getDeployModeEnum());
            }
        }
        JobConfigDTO jobConfig = new JobConfigDTO();
        jobConfig.setStatus(JobConfigStatus.STOP);
        jobConfig.setEditor(userName);
        jobConfig.setId(id);
        jobConfig.setJobId("");
        //变更状态
        jobConfigService.updateJobConfigById(jobConfig);
    }

    @Override
    public void savepoint(Long id) {
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(id);

        jobBaseServiceAO.checkSavepoint(jobConfigDTO);

        JobStandaloneInfo jobStandaloneInfo = flinkRestRpcAdapter.getJobInfoForStandaloneByAppId(jobConfigDTO.getJobId()
                , jobConfigDTO.getDeployModeEnum());
        if (jobStandaloneInfo == null || StringUtils.isNotEmpty(jobStandaloneInfo.getErrors())
                || !SystemConstants.STATUS_RUNNING.equals(jobStandaloneInfo.getState())) {
            log.warn(MessageConstants.MESSAGE_007, jobConfigDTO.getJobName());
            throw new BizException(MessageConstants.MESSAGE_007);
        }

        //1、 执行savepoint
        try {
            //yarn模式下和集群模式下统一目录是hdfs:///flink/savepoint/ruoshui-flink-savepoint/
            //LOCAL模式本地模式下保存在flink根目录下
            String targetDirectory = SystemConstants.DEFAULT_SAVEPOINT_ROOT_PATH + id;
            if (DeployModeEnum.LOCAL.equals(jobConfigDTO.getDeployModeEnum())) {
                targetDirectory = "savepoint/" + id;
            }

            commandRpcClinetAdapter.savepointForPerCluster(jobConfigDTO.getJobId(), targetDirectory);
        } catch (Exception e) {
            log.error(MessageConstants.MESSAGE_008, e);
            throw new BizException(MessageConstants.MESSAGE_008);
        }

        String savepointPath = flinkRestRpcAdapter.savepointPath(jobConfigDTO.getJobId(),
                jobConfigDTO.getDeployModeEnum());
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


    private void checkSysConfig(Map<String, String> systemConfigMap, DeployModeEnum deployModeEnum) {
        if (systemConfigMap == null) {
            throw new BizException(SysErrorEnum.SYSTEM_CONFIG_IS_NULL);
        }
        if (!systemConfigMap.containsKey(SysConfigEnum.FLINK_HOME.getKey())) {
            throw new BizException(SysErrorEnum.SYSTEM_CONFIG_IS_NULL_FLINK_HOME);
        }

        if (DeployModeEnum.LOCAL == deployModeEnum
                && !systemConfigMap.containsKey(SysConfigEnum.FLINK_REST_HTTP_ADDRESS.getKey())) {
            throw new BizException(SysErrorEnum.SYSTEM_CONFIG_IS_NULL_FLINK_REST_HTTP_ADDRESS);
        }

        if (DeployModeEnum.STANDALONE == deployModeEnum
                && !systemConfigMap.containsKey(SysConfigEnum.FLINK_REST_HA_HTTP_ADDRESS.getKey())) {
            throw new BizException(SysErrorEnum.SYSTEM_CONFIG_IS_NULL_FLINK_REST_HA_HTTP_ADDRESS);
        }
    }
}
