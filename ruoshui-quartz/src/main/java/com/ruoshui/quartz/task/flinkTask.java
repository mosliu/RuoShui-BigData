package com.ruoshui.quartz.task;

import com.ruoshui.flink.ao.TaskServiceAO;
import com.ruoshui.flink.quartz.BatchJobManagerScheduler;
import com.ruoshui.flink.service.IpStatusService;
import com.ruoshui.flink.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("flinkTask")
public class flinkTask {
    @Autowired
    private IpStatusService ipStatusService;

    @Autowired
    private TaskServiceAO taskServiceAO;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private BatchJobManagerScheduler batchJobManagerScheduler;


    /**
     * 每个1分钟更新一次心跳时间
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 19:52
     */
    public void checkHeartbeat() {
        log.debug("#####心跳检查checkHeartbeat#######");
        try {
            ipStatusService.updateHeartbeatBylocalIp();
        } catch (Exception e) {
            log.error("心跳检查失败", e);
        }
    }


    /**
     * 每隔5分钟进行一次一致性校验检查(如果校验失败会进行告警)
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 23:45
     */
    public void checkJobStatus() {
        if (!ipStatusService.isLeader()) {
            return;
        }
        log.info("#####[task]一致性校验检查#######");
        try {
            taskServiceAO.checkJobStatus();
        } catch (Exception e) {
            log.error("checkJobStatusByYarn is error", e);
        }
    }


    /**
     * 每隔1小时进行一次自动savePoint
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 23:45
     */
    public void autoSavePoint() {
        if (!systemConfigService.autoSavepoint()){
            log.info("#####没有开启自动savePoint#######");
            return;
        }

        if (!ipStatusService.isLeader()) {
            return;
        }
        log.info("#####[task]开始自动执行savePoint#######");
        try {
            taskServiceAO.autoSavePoint();
        } catch (Exception e) {
            log.error("autoSavePoint is error", e);
        }
    }

    /**
     * 定时检测离线任务调度注册情况（补偿）
     *
     * @Param:[]
     * @return: void
     * @Author: zhuhuipei
     * @date 2022/10/30
     */
    public void autoBatchRegisterJob() {
        if (!ipStatusService.isLeader()) {
            return;
        }
        log.info("#####定时检测离线任务调度注册情况（半个小时一次检测）#######");
        try {
            batchJobManagerScheduler.batchRegisterJob();
        } catch (Exception e) {
            log.error("autoSavePoint is error", e);
        }
    }

}
