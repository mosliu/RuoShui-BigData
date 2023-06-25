package com.ruoshui.dataassets.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoshui.common.annotation.Excel;
import com.ruoshui.common.core.domain.BaseEntity;

/**
 * 数据库管理对象 sys_data_base
 * 
 * @author ruoshui
 * @date 2022-06-18
 */
public class SysDataBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 数据库库名 */
    @Excel(name = "数据库库名")
    private String baseName;

    /** 数据源id */
    private Long dataSourceId;

    /** 数据源编码 */
    @Excel(name = "数据源编码")
    private String dataSourceCode;

    /** 数据源名称 */
    @Excel(name = "数据源名称")
    private String dataSourceName;

    /** 是否是集群 */
    @Excel(name = "是否是集群")
    private String colonyState;

    /** 集群名称 */
    @Excel(name = "集群名称")
    private String colonyName;

    private String passWorde;

    public String getPassWorde() {
        return passWorde;
    }

    public void setPassWorde(String passWorde) {
        this.passWorde = passWorde;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBaseName(String baseName) 
    {
        this.baseName = baseName;
    }

    public String getBaseName() 
    {
        return baseName;
    }
    public void setDataSourceId(Long dataSourceId) 
    {
        this.dataSourceId = dataSourceId;
    }

    public Long getDataSourceId() 
    {
        return dataSourceId;
    }
    public void setDataSourceCode(String dataSourceCode) 
    {
        this.dataSourceCode = dataSourceCode;
    }

    public String getDataSourceCode() 
    {
        return dataSourceCode;
    }
    public void setDataSourceName(String dataSourceName) 
    {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceName() 
    {
        return dataSourceName;
    }
    public void setColonyState(String colonyState) 
    {
        this.colonyState = colonyState;
    }

    public String getColonyState() 
    {
        return colonyState;
    }
    public void setColonyName(String colonyName) 
    {
        this.colonyName = colonyName;
    }

    public String getColonyName() 
    {
        return colonyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("baseName", getBaseName())
            .append("dataSourceId", getDataSourceId())
            .append("dataSourceCode", getDataSourceCode())
            .append("dataSourceName", getDataSourceName())
            .append("colonyState", getColonyState())
            .append("colonyName", getColonyName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
