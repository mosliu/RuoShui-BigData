package com.ruoshui.quality.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.quality.dto.RuleConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 核查规则信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
public class CheckRuleVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String ruleName;
    private String ruleTypeId;
    private String ruleItemId;
    private String ruleType;
    private String ruleLevelId;
    private String ruleLevel;
    private String ruleDbType;
    private String ruleSourceId;
    private String ruleSource;
    private String ruleTableId;
    private String ruleTable;
    private String ruleTableComment;
    private String ruleColumnId;
    private String ruleColumn;
    private String ruleColumnComment;
    private RuleConfig ruleConfig;
}
