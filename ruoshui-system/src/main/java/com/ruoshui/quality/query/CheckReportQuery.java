package com.ruoshui.quality.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * <p>
 * 核查报告信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckReportQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String ruleTypeId;
    private String ruleName;
    private String ruleSource;
    private String ruleTable;
    private String ruleColumn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkDate;
}
