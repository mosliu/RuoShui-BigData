package com.ruoshui.quality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据质量监控任务日志信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-10-13
 */
@Data
public class ScheduleLogVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    private String executeJobId;
    private String executeRuleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime executeDate;
    private String executeResult;
    private String executeBatch;
    private String executeJobName;
    private String executeRuleName;
    private String executeRuleTypeName;
}
