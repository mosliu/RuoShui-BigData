package com.ruoshui.flink.scheduler;

import com.ruoshui.flink.ao.TaskServiceAO;
import com.ruoshui.flink.ao.TaskServiceAO;
import com.ruoshui.flink.quartz.BatchJobManagerScheduler;
import com.ruoshui.flink.service.IpStatusService;
import com.ruoshui.flink.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2015/9/6
 * @time 下午5:01
 */
@Slf4j
@Component
@Configurable
@EnableScheduling
@EnableAsync
public class SchedulerTask {


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
    @Async("taskExecutor")
    @Scheduled(cron = "0 */1 * * * ?")
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
    @Async("taskExecutor")
    @Scheduled(cron = "0 */5 * * * ?")
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


//    /**
//     * 每隔20分钟进行一次对停止任务进行是否在yarn上运行的检查
//     *
//     * @author xinjingruoshui
//     * @date 2022-10-25
//     * @time 18:34
//     */
//    @Async("taskExecutor")
//    @Scheduled(cron = "0 */20 * * * ?")
//    public void checkYarnJobByStop() {
//        if (!ipStatusService.isLeader()) {
//            return;
//        }
//        log.info("#####checkYarnJobByStop#######");
//        try {
//            taskServiceAO.checkYarnJobByStop();
//        } catch (Exception e) {
//            log.error("checkYarnJobByStop is error", e);
//        }
//    }


    /**
     * 每隔1小时进行一次自动savePoint
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 23:45
     */
    @Async("taskExecutor")
    @Scheduled(cron = "0 0 */1 * * ?")
  //@Scheduled(cron = "0 */1 * * * ?")
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
    @Async("taskExecutor")
    //@Scheduled(cron = "0 */30 * * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
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
