package com.ruoshui.dataassets.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoshui.common.annotation.Excel;
import com.ruoshui.common.core.domain.BaseEntity;

/**
 * 数仓规则管理对象 sys_data_rule
 * 
 * @author ruoshui
 * @date 2022-06-15
 */
public class SysDataRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 规则类型编码 */
    @Excel(name = "数据源id")
    private Long dataSourceId;

    /** 规则类型编码 */
    @Excel(name = "数据源编码")
    private String dataSourceCode;

    /** 规则类型编码 */
    @Excel(name = "数据源名称")
    private String dataSourceName;


    /** 数据库层级编码 */
    @Excel(name = "数据库层级编码")
    private String dataLayerCode;

    /** 数据库层级名称 */
    @Excel(name = "数据库层级名称")
    private String dataLayerName;


    /** 规则类型编码 */
    @Excel(name = "规则类型编码")
    private String ruleTypeCode;

    /** 规则类型名称 */
    @Excel(name = "规则类型名称")
    private String ruleTypeName;

    /** 规则表达式 */
    @Excel(name = "规则表达式")
    private String ruleText;


    public String getDataLayerCode() {
        return dataLayerCode;
    }

    public void setDataLayerCode(String dataLayerCode) {
        this.dataLayerCode = dataLayerCode;
    }

    public String getDataLayerName() {
        return dataLayerName;
    }

    public void setDataLayerName(String dataLayerName) {
        this.dataLayerName = dataLayerName;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRuleTypeCode(String ruleTypeCode)
    {
        this.ruleTypeCode = ruleTypeCode;
    }

    public String getRuleTypeCode()
    {
        return ruleTypeCode;
    }
    public void setRuleTypeName(String ruleTypeName) 
    {
        this.ruleTypeName = ruleTypeName;
    }

    public String getRuleTypeName() 
    {
        return ruleTypeName;
    }
    public void setRuleText(String ruleText) 
    {
        this.ruleText = ruleText;
    }

    public String getRuleText() 
    {
        return ruleText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ruleTypeCode", getRuleTypeCode())
            .append("ruleTypeName", getRuleTypeName())
            .append("ruleText", getRuleText())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
