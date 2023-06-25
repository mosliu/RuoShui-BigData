package com.ruoshui.dataassets.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoshui.common.annotation.Excel;
import com.ruoshui.common.core.domain.BaseEntity;

/**
 * 数据表列表对象 sys_table
 *
 * @author ruoshui
 * @date 2022-07-04
 */
public class SysTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 父id */
    private Long parentId;

    /** 父名称 */
    @Excel(name = "父名称")
    private String parentName;

    /** 祖籍列表 */
    private String ancestors;

    /** 库/表/字段 */
    @Excel(name = "库/表/字段")
    private String name;

    /** 类型 */
    private String type;

    /** 长度 */
    private Long lenth;

    /** 库/表/字段名 */
    @Excel(name = "库/表/字段名")
    private String comment;

    /** 建表语句 */
    @Excel(name = "建表语句")
    private String createtablequery;

    /** TABLE:表  DATABASE:库  FIELD:字段 */
    @Excel(name = "TABLE:表  DATABASE:库  FIELD:字段")
    private String levelCode;

    /** 库id */
    private Long baseId;

    /** sql */
    private String myCode;

    private int pageSize;

    private int pageNum;

    /** 表主题（零售，会员，库存等） */
    private String theme;

    /**作用类型（档案,指标,数据）*/
    private String actionType;


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getParentName()
    {
        return parentName;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setLenth(Long lenth)
    {
        this.lenth = lenth;
    }

    public Long getLenth()
    {
        return lenth;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setCreatetablequery(String createtablequery)
    {
        this.createtablequery = createtablequery;
    }

    public String getCreatetablequery()
    {
        return createtablequery;
    }
    public void setLevelCode(String levelCode)
    {
        this.levelCode = levelCode;
    }

    public String getLevelCode()
    {
        return levelCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("parentId", getParentId())
                .append("parentName", getParentName())
                .append("ancestors", getAncestors())
                .append("name", getName())
                .append("type", getType())
                .append("lenth", getLenth())
                .append("comment", getComment())
                .append("createtablequery", getCreatetablequery())
                .append("levelCode", getLevelCode())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}