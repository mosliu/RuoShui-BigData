package com.ruoshui.bigdata.service.impl;


import com.ruoshui.bigdata.service.RpcService;



public class RpcServiceImpl implements RpcService {
    @Override
    public String getMonitor() {
        return "json";
    }

    @Override
    public String runjob(String jonifo) {
        return "";
    }

    @Override
    public String getLog(String executorAddress) {
        return "";
    }
}
