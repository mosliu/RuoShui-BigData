package com.ruoshui.web.controller.test;

import com.ruoshui.bigdata.core.conf.JobAdminConfig;
import com.ruoshui.common.utils.http.HttpUtils;
import com.ruoshui.flink.streaming.web.common.util.IpUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {

    public static void main(String[] arg){
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("主机名："+localHost.getHostName());
            System.out.println("本地ip地址："+localHost.getHostAddress());
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
    }

