package com.ruoshui.flink.quartz;


import com.ruoshui.flink.streaming.web.common.SystemConstants;
import org.quartz.*;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022/10/29
 */
public class JobDetailAndTriggerBuild {

  public static JobDetail buildJobDetail(Long id, String jobName) {
    JobDetail jobDetail = JobBuilder.newJob(JobExecute.class).withIdentity(
        SystemConstants.buildQuartzJobKeyName(id)).build();
    jobDetail.getJobDataMap().put("id", id);
    jobDetail.getJobDataMap().put("jobName", jobName);
    return jobDetail;
  }

  public static Trigger buildTrigger(String jobName, String cron) {
    Trigger trigger = TriggerBuilder.newTrigger()
        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
        .withIdentity(jobName)
        .build();
    return trigger;
  }

}
