package com.flink.streaming.core.model;


import com.ruoshui.common.flink.enums.JobTypeEnum;
import com.ruoshui.common.flink.model.CheckPointParam;
import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-21
 * @time 02:10
 */
@Data
public class JobRunParam {
    /**
     * sql语句目录
     */
    private String sqlPath;

    /**
     * 任务类型
     */
    private JobTypeEnum jobTypeEnum;

    /**
     * CheckPoint 参数
     */
    private CheckPointParam checkPointParam;



}
