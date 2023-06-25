package com.ruoshui.flink.service;

import com.ruoshui.flink.streaming.web.enums.AlarmTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/2/27
 * @time 17:47
 */
public interface JobAlarmConfigService {

    /**
     * 批量新增/修改
     *
     * @author xinjingruoshui
     * @date 2021/2/27
     * @time 17:49
     */
    void upSertBatchJobAlarmConfig(List<AlarmTypeEnum> alarmTypeEnumList, Long jobId);


    /**
     * 按jobId查询
     *
     * @author xinjingruoshui
     * @date 2021/2/27
     * @time 17:53
     */
    List<AlarmTypeEnum>  findByJobId(Long jobId);


    /**
     *
     * @author xinjingruoshui
     */
    Map<Long ,List<AlarmTypeEnum>> findByJobIdList(List<Long> jobIdList);


}
