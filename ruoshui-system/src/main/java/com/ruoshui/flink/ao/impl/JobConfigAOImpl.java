package com.ruoshui.flink.ao.impl;

import com.ruoshui.flink.ao.JobConfigAO;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.service.JobAlarmConfigService;
import com.ruoshui.flink.service.JobConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/2/28
 * @time 11:25
 */
@Component
@Slf4j
public class JobConfigAOImpl implements JobConfigAO {

    @Autowired
    private JobConfigService jobConfigService;

    @Autowired
    private JobAlarmConfigService jobAlarmConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJobConfig(JobConfigDTO jobConfigDTO) {

        jobConfigDTO.setCreator(getUsername());
        Long jobConfigId = jobConfigService.addJobConfig(jobConfigDTO);
        jobAlarmConfigService.upSertBatchJobAlarmConfig(jobConfigDTO.getAlarmTypeEnumList(), jobConfigId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobConfigById(JobConfigDTO jobConfigDTO) {
        jobConfigDTO.setEditor(getUsername());
        jobConfigService.updateJobConfigByIdWithWriteHistory(jobConfigDTO);
        jobAlarmConfigService.upSertBatchJobAlarmConfig(jobConfigDTO.getAlarmTypeEnumList(), jobConfigDTO.getId());
    }
}
