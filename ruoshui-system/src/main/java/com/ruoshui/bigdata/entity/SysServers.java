package com.ruoshui.bigdata.entity;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoshui.common.annotation.Excel;
import com.ruoshui.common.core.domain.BaseEntity;

/**
 * 执行器管理对象 sys_servers
 * 
 * @author ruoshui
 * @date 2022-04-28
 */
public class SysServers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分组名 */
    @Excel(name = "分组名")
    private String groupname;

    /** 分组编码 */
    @Excel(name = "分组编码")
    private String groupcode;

    /** 服务器地址 */
    @Excel(name = "服务器地址")
    private String serveraddress;

    /** 系统名 */
    @Excel(name = "系统名")
    private String osname;

    /** 程序启动时间 */
    @Excel(name = "程序启动时间")
    private String starttime;

    /** pid */
    @Excel(name = "pid")
    private String pid;

    /** cpu核心数 */
    @Excel(name = "cpu核心数")
    private Long cpucores;

    /** cpu使用率 */
    @Excel(name = "cpu使用率")
    private BigDecimal cpuutilization;

    /** cpu空闲率 */
    @Excel(name = "cpu空闲率")
    private BigDecimal cpurate;

    /** JVM初始内存 */
    @Excel(name = "JVM初始内存")
    private BigDecimal jvminitialmemory;

    /** JVM最大内存 */
    @Excel(name = "JVM最大内存")
    private BigDecimal jvmmaxmemory;

    /** JVM已用内存 */
    @Excel(name = "JVM已用内存")
    private BigDecimal jvmusedmemory;

    /** 总物理内存 */
    @Excel(name = "总物理内存")
    private BigDecimal physicalmemory;

    /** 剩余物理内存 */
    @Excel(name = "剩余物理内存")
    private BigDecimal surplusmemory;

    /** 已用物理内存 */
    @Excel(name = "已用物理内存")
    private BigDecimal usedmemory;

    /** 磁盘状态 */
    @Excel(name = "磁盘状态")
    private String diskstatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupname(String groupname) 
    {
        this.groupname = groupname;
    }

    public String getGroupname() 
    {
        return groupname;
    }
    public void setGroupcode(String groupcode) 
    {
        this.groupcode = groupcode;
    }

    public String getGroupcode() 
    {
        return groupcode;
    }
    public void setServeraddress(String serveraddress) 
    {
        this.serveraddress = serveraddress;
    }

    public String getServeraddress() 
    {
        return serveraddress;
    }
    public void setOsname(String osname) 
    {
        this.osname = osname;
    }

    public String getOsname() 
    {
        return osname;
    }
    public void setStarttime(String starttime) 
    {
        this.starttime = starttime;
    }

    public String getStarttime() 
    {
        return starttime;
    }
    public void setPid(String pid) 
    {
        this.pid = pid;
    }

    public String getPid() 
    {
        return pid;
    }
    public void setCpucores(Long cpucores) 
    {
        this.cpucores = cpucores;
    }

    public Long getCpucores() 
    {
        return cpucores;
    }
    public void setCpuutilization(BigDecimal cpuutilization) 
    {
        this.cpuutilization = cpuutilization;
    }

    public BigDecimal getCpuutilization() 
    {
        return cpuutilization;
    }
    public void setCpurate(BigDecimal cpurate) 
    {
        this.cpurate = cpurate;
    }

    public BigDecimal getCpurate() 
    {
        return cpurate;
    }
    public void setJvminitialmemory(BigDecimal jvminitialmemory) 
    {
        this.jvminitialmemory = jvminitialmemory;
    }

    public BigDecimal getJvminitialmemory() 
    {
        return jvminitialmemory;
    }
    public void setJvmmaxmemory(BigDecimal jvmmaxmemory) 
    {
        this.jvmmaxmemory = jvmmaxmemory;
    }

    public BigDecimal getJvmmaxmemory() 
    {
        return jvmmaxmemory;
    }
    public void setJvmusedmemory(BigDecimal jvmusedmemory) 
    {
        this.jvmusedmemory = jvmusedmemory;
    }

    public BigDecimal getJvmusedmemory() 
    {
        return jvmusedmemory;
    }
    public void setPhysicalmemory(BigDecimal physicalmemory) 
    {
        this.physicalmemory = physicalmemory;
    }

    public BigDecimal getPhysicalmemory() 
    {
        return physicalmemory;
    }
    public void setSurplusmemory(BigDecimal surplusmemory) 
    {
        this.surplusmemory = surplusmemory;
    }

    public BigDecimal getSurplusmemory() 
    {
        return surplusmemory;
    }
    public void setUsedmemory(BigDecimal usedmemory) 
    {
        this.usedmemory = usedmemory;
    }

    public BigDecimal getUsedmemory() 
    {
        return usedmemory;
    }
    public void setDiskstatus(String diskstatus) 
    {
        this.diskstatus = diskstatus;
    }

    public String getDiskstatus() 
    {
        return diskstatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupname", getGroupname())
            .append("groupcode", getGroupcode())
            .append("serveraddress", getServeraddress())
            .append("osname", getOsname())
            .append("starttime", getStarttime())
            .append("pid", getPid())
            .append("cpucores", getCpucores())
            .append("cpuutilization", getCpuutilization())
            .append("cpurate", getCpurate())
            .append("jvminitialmemory", getJvminitialmemory())
            .append("jvmmaxmemory", getJvmmaxmemory())
            .append("jvmusedmemory", getJvmusedmemory())
            .append("physicalmemory", getPhysicalmemory())
            .append("surplusmemory", getSurplusmemory())
            .append("usedmemory", getUsedmemory())
            .append("diskstatus", getDiskstatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
