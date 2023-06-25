package com.ruoshui.flink.streaming.web.model.param;

import com.ruoshui.flink.streaming.web.model.page.PageParam;
import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-17
 * @time 00:18
 */
@Data
public class JobRunLogParam extends PageParam {

    private Long jobConfigId;

    /**
     * 运行后的任务id
     */
    private String jobId;

    private String jobName;
}
