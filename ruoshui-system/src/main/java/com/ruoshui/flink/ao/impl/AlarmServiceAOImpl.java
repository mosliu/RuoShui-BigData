package com.ruoshui.flink.ao.impl;

import com.alibaba.fastjson.JSON;
import com.ruoshui.flink.streaming.web.alarm.DingDingAlarmUtils;
import com.ruoshui.flink.streaming.web.alarm.HttpAlarmUtils;
import com.ruoshui.flink.streaming.web.enums.AlarmLogStatusEnum;
import com.ruoshui.flink.streaming.web.enums.AlarmLogTypeEnum;
import com.ruoshui.flink.streaming.web.model.dto.AlartLogDTO;
import com.ruoshui.flink.streaming.web.model.dto.JobRunLogDTO;
import com.ruoshui.flink.streaming.web.model.vo.CallbackDTO;
import com.ruoshui.flink.ao.AlarmServiceAO;
import com.ruoshui.flink.service.AlartLogService;
import com.ruoshui.flink.service.JobRunLogService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-25
 * @time 19:50
 */
@Component
@Slf4j
public class AlarmServiceAOImpl implements AlarmServiceAO {


    @Resource
    private AlartLogService alartLogService;

    @Resource
    private JobRunLogService jobRunLogService;

    @Override
    public boolean sendForDingding(String url, String content, Long jobConfigId) {

        boolean isSuccess = false;
        String failLog = "";
        try {
            isSuccess = DingDingAlarmUtils.send(url, content);
        } catch (Exception e) {
            log.error("dingDingAlarm.send is error", e);
            failLog = e.getMessage();
        }
        this.insertLog(isSuccess, jobConfigId, failLog, content, AlarmLogTypeEnum.DINGDING);

        return isSuccess;
    }

    @Override
    public boolean sendForHttp(String url, CallbackDTO callbackDTO) {

        boolean isSuccess = false;
        String failLog = "";
        try {
            isSuccess = HttpAlarmUtils.send(url, callbackDTO);
        } catch (Exception e) {
            log.error("dingDingAlarm.send is error", e);
            failLog = e.getMessage();
        }
        this.insertLog(isSuccess, callbackDTO.getJobConfigId(), failLog, JSON.toJSONString(callbackDTO),
                AlarmLogTypeEnum.CALLBACK_URL);

        return isSuccess;
    }


    private void insertLog(boolean isSuccess, Long jobConfigId, String failLog, String content,
                           AlarmLogTypeEnum alarMLogTypeEnum) {
        JobRunLogDTO jobRunLogDTO = jobRunLogService.getDetailLogById(jobConfigId);
        AlartLogDTO alartLogDTO = new AlartLogDTO();
        alartLogDTO.setJobConfigId(jobConfigId);
        alartLogDTO.setAlarMLogTypeEnum(alarMLogTypeEnum);
        alartLogDTO.setMessage(content);
        if (jobRunLogDTO != null) {
            alartLogDTO.setJobName(jobRunLogDTO.getJobName());
        } else {
            alartLogDTO.setJobName("测试");
        }
        if (isSuccess) {
            alartLogDTO.setAlarmLogStatusEnum(AlarmLogStatusEnum.SUCCESS);
        } else {
            alartLogDTO.setAlarmLogStatusEnum(AlarmLogStatusEnum.FAIL);
            alartLogDTO.setFailLog(failLog);
        }
        alartLogService.addAlartLog(alartLogDTO);

    }
}
