package com.ruoshui.flink.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.*;

public class JavaExecLinuxCommandRemote {



    /**
     * 远程执行 linux 命令
     * @return
     */
    public static Session RemoteSubmitCommand(String command,String serverIp,String serverUser, String serverPassword) throws UnsupportedEncodingException {
        // 设置主机IP地址
        Connection c = new Connection(serverIp);
        StringBuilder buffer = new StringBuilder();
        Session session = null;
        try {
            c.connect();
            // 设置登录名称和登录密码
            boolean flag = c.authenticateWithPassword(serverUser, serverPassword);
            if(!flag){
                throw new RuntimeException("flink服务器连接失败！");
            }
            session = c.openSession();
            session.execCommand(command);
        } catch (IOException e) {
            c.close();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return session;
    }
}
