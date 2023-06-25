package com.ruoshui.flink.streaming.web.model.param;

import com.ruoshui.flink.streaming.web.model.page.PageParam;
import com.ruoshui.flink.streaming.web.enums.JobConfigStatus;
import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-15
 * @time 02:04
 */
@Data
public class JobConfigParam extends PageParam {

    /**
     * @author xinjingruoshui
     * @date 2022-07-15
     * @time 02:07
     * @see JobConfigStatus
     */
    private Integer status;


    /**
     * 任务名称
     */
    private String jobName;


    /**
     * 任务类型 0:sql 1:自定义jar'
     */
    private Integer jobType;


    private Integer open;

    private String jobId;

    private String deployMode;
}
