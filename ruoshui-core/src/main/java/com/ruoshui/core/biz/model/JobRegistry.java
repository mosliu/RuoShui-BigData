package com.ruoshui.core.biz.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.sql.Date;

/**
 * 执行器管理对象 job_registry
 * 
 * @author ruoshui
 * @date 2022-04-27
 */
public class JobRegistry
{
    /** id */
    private Long id;

    /** 执行器名称 */
    private String registryName;

    /** 执行器标识 */
    private String registryKey;

    /** 在线机器ip */
    private String registryValue;

    /** cpu使用率 */
    private Long cpuUsage;

    /** 内存使用 */
    private Long memoryUsage;

    /** 平均负荷 */
    private Long loadAverage;

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRegistryName(String registryName) 
    {
        this.registryName = registryName;
    }

    public String getRegistryName() 
    {
        return registryName;
    }
    public void setRegistryKey(String registryKey) 
    {
        this.registryKey = registryKey;
    }

    public String getRegistryKey() 
    {
        return registryKey;
    }
    public void setRegistryValue(String registryValue) 
    {
        this.registryValue = registryValue;
    }

    public String getRegistryValue() 
    {
        return registryValue;
    }
    public void setCpuUsage(Long cpuUsage) 
    {
        this.cpuUsage = cpuUsage;
    }

    public Long getCpuUsage() 
    {
        return cpuUsage;
    }
    public void setMemoryUsage(Long memoryUsage) 
    {
        this.memoryUsage = memoryUsage;
    }

    public Long getMemoryUsage() 
    {
        return memoryUsage;
    }
    public void setLoadAverage(Long loadAverage) 
    {
        this.loadAverage = loadAverage;
    }

    public Long getLoadAverage() 
    {
        return loadAverage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("registryName", getRegistryName())
            .append("registryKey", getRegistryKey())
            .append("registryValue", getRegistryValue())
            .append("cpuUsage", getCpuUsage())
            .append("memoryUsage", getMemoryUsage())
            .append("loadAverage", getLoadAverage())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
