package com.ruoshui.bigdata.util;


import com.ruoshui.bigdata.service.RpcService;
import com.ruoshui.common.config.RPCClient;
import java.io.IOException;
import java.net.InetSocketAddress;

public class TestOutput {

    public static void main(String[] args) throws IOException {
        for(int i=0;i<=5;i++) {
            RpcService service = RPCClient.getRemoteProxyObj(RpcService.class, new InetSocketAddress("192.168.172.235", 8088));
            System.out.println(service.getMonitor());
        }
    }



}
