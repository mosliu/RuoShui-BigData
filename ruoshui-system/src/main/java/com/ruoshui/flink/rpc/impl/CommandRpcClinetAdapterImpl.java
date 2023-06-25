package com.ruoshui.flink.rpc.impl;

import ch.ethz.ssh2.Session;
import cn.hutool.core.date.DateUtil;
import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.common.flink.constant.SystemConstant;
import com.ruoshui.flink.ao.impl.JobBaseServiceAOImpl;
import com.ruoshui.flink.service.JobConfigService;
import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.common.TipsConstants;
import com.ruoshui.flink.streaming.web.common.util.CommandUtil;
import com.ruoshui.flink.streaming.web.config.WaitForPoolConfig;
import com.ruoshui.flink.streaming.web.enums.DeployModeEnum;
import com.ruoshui.flink.streaming.web.enums.SysConfigEnum;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.rpc.CommandRpcClinetAdapter;
import com.ruoshui.flink.service.JobRunLogService;
import com.ruoshui.flink.service.SystemConfigService;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.streaming.web.model.entity.JobConfig;
import com.ruoshui.flink.utils.JavaExecLinuxCommandRemote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-18
 * @time 20:13
 */
@Slf4j
@Service
public class CommandRpcClinetAdapterImpl implements CommandRpcClinetAdapter {


    private static long INTERVAL_TIME_TWO = 1000 * 2;

    @Autowired
    private JobRunLogService jobRunLogService;


    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private JobConfigService jobConfigService;

    @Resource
    private BaseResourceMapper baseResourceMapper;


    @Override
    public String submitJob(String command, StringBuilder localLog, Long jobRunLogId,
        DeployModeEnum deployModeEnum) throws Exception {
        log.info(" command ={} ", command);
        localLog.append("启动命令：").append(command).append(SystemConstant.LINE_FEED);
        Process pcs = Runtime.getRuntime().exec(command);

        //清理错误日志
        this.clearLogStream(pcs.getErrorStream(), String.format("%s#startForLocal-error#%s", DateUtil.now(),
            deployModeEnum.name()));
        String appId = this.clearInfoLogStream(pcs.getInputStream(), localLog, jobRunLogId);
        int rs = pcs.waitFor();
        localLog.append("rs=").append(rs).append(SystemConstant.LINE_FEED);
        jobRunLogService.updateLogById(localLog.toString(), jobRunLogId);
        if (rs != 0) {
            localLog.append(" 执行异常 rs=").append(rs).append("   appId=").append(appId);
            throw new RuntimeException("执行异常 is error  rs=" + rs);
        }
        if (StringUtils.isEmpty(appId)) {
            localLog.append("appId无法获 ").append(TipsConstants.TIPS_1);
            throw new RuntimeException("appId无法获取");
        }
        return appId;
    }



    @Override
    public String submitJobTwo(String command, StringBuilder localLog, Long jobRunLogId,
                               DeployModeEnum deployModeEnum, String serverIp,String serverUser, String serverPassword) throws Exception {
        log.info(" command ={} ", command);
        localLog.append("启动命令：").append(command).append(SystemConstant.LINE_FEED);
        JavaExecLinuxCommandRemote s = new JavaExecLinuxCommandRemote();
        Session session = s.RemoteSubmitCommand(command,serverIp,serverUser,serverPassword);
        String appId = this.clearInfoLogStream(session.getStdout(), localLog, jobRunLogId);
        session.close();
        localLog.append(SystemConstant.LINE_FEED);
        jobRunLogService.updateLogById(localLog.toString(), jobRunLogId);
        if (StringUtils.isEmpty(appId)) {
            localLog.append("appId无法获 ").append(TipsConstants.TIPS_1);
            throw new RuntimeException("appId无法获取");
        }
        return appId;
    }


    @Override
    public void savepointForPerYarn(String jobId, String targetDirectory, String yarnAppId) throws Exception {

        String command = CommandUtil.buildSavepointCommandForYarn(jobId, targetDirectory, yarnAppId,
            systemConfigService.getSystemConfigByKey(SysConfigEnum.FLINK_HOME.getKey()));
        log.info("[savepointForPerYarn] command={}", command);
        this.execSavepoint(command,yarnAppId);

    }

    @Override
    public void savepointForPerCluster(String jobId, String targetDirectory) throws Exception {
        String command = CommandUtil.buildSavepointCommandForCluster(jobId, targetDirectory,
            systemConfigService.getSystemConfigByKey(SysConfigEnum.FLINK_HOME.getKey()));
        log.info("[savepointForPerCluster] command={}", command);
        this.execSavepoint(command,jobId);
    }


    private void execSavepoint(String command,String jobId) throws Exception {
        JobConfig jobConfigByJobId = jobConfigService.getJobConfigByJobId(jobId);
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(jobConfigByJobId.getId());
        //通过flink集群id查询集群信息
        BaseResource baseResource = baseResourceMapper.getById(Math.toIntExact(jobConfigDTO.getFlinkCluster()));
        JavaExecLinuxCommandRemote s = new JavaExecLinuxCommandRemote();
        log.info("execSavepoint 命令", command);
        Session session = s.RemoteSubmitCommand(command, baseResource.getServerIp(), baseResource.getServerUser(), AESUtil.decrypt(baseResource.getServerPassword()));
        // Process pcs = Runtime.getRuntime().exec(command);
        //消费正常日志
        this.clearLogStream(session.getStdout(), String.format("%s-savepoint-success", DateUtil.now()));
        //消费错误日志
        this.clearLogStream(session.getStderr(), String.format("%s-savepoint-error", DateUtil.now()));
//        int rs = session.waitFor();
//        if (rs != 0) {
//            throw new Exception("[savepointForPerYarn]执行savepoint失败 is error  rs=" + rs);
//        }
    }

    /**
     * 清理pcs.waitFor()日志防止死锁
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 11:15
     */
    private void clearLogStream(InputStream stream, final String threadName) {
        WaitForPoolConfig.getInstance().getThreadPoolExecutor().execute(() -> {
                BufferedReader reader = null;
                try {
                    Thread.currentThread().setName(threadName);
                    String result = null;
                    reader = new BufferedReader(new InputStreamReader(stream, SystemConstants.CODE_UTF_8));
                    //按行读取
                    while ((result = reader.readLine()) != null) {
                        log.info(result);
                    }
                } catch (Exception e) {
                    log.error("threadName={}", threadName);
                } finally {
                    this.close(reader, stream, "clearLogStream");
                }
            }
        );
    }

    /**
     * 启动日志输出并且从日志中获取成功后的jobId
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 11:15
     */
    private String clearInfoLogStream(InputStream stream, StringBuilder localLog, Long jobRunLogId) {

        String appId = null;
        BufferedReader reader = null;
        try {
            long lastTime = System.currentTimeMillis();
            String result = null;
            reader = new BufferedReader(new InputStreamReader(stream, SystemConstants.CODE_UTF_8));
            //按行读取
            while ((result = reader.readLine()) != null) {
                log.info("read={}", result);
                if (StringUtils.isEmpty(appId) && result.contains(SystemConstant.QUERY_JOBID_KEY_WORD)) {
                    appId = result.replace(SystemConstant.QUERY_JOBID_KEY_WORD, SystemConstant.SPACE).trim();
                    log.info("[job-submitted-success] 解析得到的appId是 {}  原始数据 :{}", appId, result);
                    localLog.append("[job-submitted-success] 解析得到的appId是:")
                        .append(appId).append(SystemConstant.LINE_FEED);
                }
                if (StringUtils.isEmpty(appId) && result.contains(SystemConstant.QUERY_JOBID_KEY_WORD_BACKUP)) {
                    appId = result.replace(SystemConstant.QUERY_JOBID_KEY_WORD_BACKUP, SystemConstant.SPACE).trim();
                    log.info("[Job has been submitted with JobID] 解析得到的appId是 {}  原始数据 :{}", appId, result);
                    localLog.append("[Job has been submitted with JobID] 解析得到的appId是:")
                        .append(appId).append(SystemConstant.LINE_FEED);
                }
                localLog.append(result).append(SystemConstant.LINE_FEED);
                //每隔2s更新日志
                if (System.currentTimeMillis() >= lastTime + INTERVAL_TIME_TWO) {
                    jobRunLogService.updateLogById(localLog.toString(), jobRunLogId);
                    lastTime = System.currentTimeMillis();
                }
            }
            if (appId == null || appId.length() != 32) {
                log.error("解析appID异常 appId:{}", appId);
                throw new BizException("解析appId异常");
            }
            JobBaseServiceAOImpl.threadAppId.set(appId);
            log.info("获取到的appId是 {}", appId);
            return appId;
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("[clearInfoLogStream] is error", e);
            throw new RuntimeException("clearInfoLogStream is error");
        } finally {
            this.close(reader, stream, "clearInfoLogStream");

        }
    }


    /**
     * 关闭流
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 12:53
     */
    private void close(BufferedReader reader, InputStream stream, String typeName) {
        if (reader != null) {
            try {
                reader.close();
                log.info("[{}]关闭reader ", typeName);
            } catch (IOException e) {
                log.error("[{}] 关闭reader流失败 ", typeName, e);
            }
        }
        if (stream != null) {
            try {
                log.info("[{}]关闭stream ", typeName);
                stream.close();
            } catch (IOException e) {
                log.error("[{}] 关闭stream流失败 ", typeName, e);
            }
        }
        log.info("线程池状态: {}", WaitForPoolConfig.getInstance().getThreadPoolExecutor());
    }

}
