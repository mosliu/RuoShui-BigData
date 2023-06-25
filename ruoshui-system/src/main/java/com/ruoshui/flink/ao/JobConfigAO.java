package com.ruoshui.flink.ao;

import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/2/28
 * @time 11:25
 */
public interface JobConfigAO {

    /**
     * 新增
     *
     * @author xinjingruoshui
     * @date 2021/2/28
     * @time 11:26
     */
    void addJobConfig(JobConfigDTO jobConfigDTO);


    /**
     * 修改参数
     *
     * @author xinjingruoshui
     * @date 2021/2/28
     * @time 11:26
     */
    void updateJobConfigById(JobConfigDTO jobConfigDTO);
}
