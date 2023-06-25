package com.ruoshui.bigdata.entity;

import lombok.Data;

@Data
public class SystemMonitor {

    //系统名称
    private String osName;

    //程序启动时间
    private String startTime;

    //pid
    private String pid;

    //cpu核数
    private Integer cpuCores;

    //cpu使用率
    private Double cpuUtilization;

    //cpu空闲率
    private Double cpuRate;

    //JVM初始内存
    private Double jvmInitialMemory;

    //JVM最大内存
    private Double jvmMaxMemory;

    //JVM已用内存
    private Double jvmUsedMemory;

    //总物理内存
    private Double physicalMemory;

    //剩余物理内存
    private Double surplusMemory;

    //以用物理内存
    private Double usedMemory;

    //磁盘状态
    private String diskStatus;

}
