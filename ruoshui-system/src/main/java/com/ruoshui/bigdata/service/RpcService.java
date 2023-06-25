package com.ruoshui.bigdata.service;

public interface RpcService {

    /**获取当前机器的服务器状态*/
    String getMonitor();


    String runjob(String jonifo);

    String getLog(String executorAddress);
}
