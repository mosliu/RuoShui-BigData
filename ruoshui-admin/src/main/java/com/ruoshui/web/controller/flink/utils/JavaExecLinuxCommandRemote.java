package com.ruoshui.web.controller.flink.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaExecLinuxCommandRemote {

    /**
     * 远程执行 linux 命令
     */
    public static String RemoteSubmitCommand(String command, String hostname, String user, String password) {
        // 设置主机IP地址
        Connection c = new Connection(hostname);
        StringBuilder buffer = new StringBuilder();
        try {
            c.connect();
            // 设置登录名称和登录密码
            boolean flag = c.authenticateWithPassword(user, password);
            if(!flag){
                throw new RuntimeException("flink服务器连接失败！");
            }
            Session session = c.openSession();
            session.execCommand(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(session.getStdout(), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            c.close();
        } catch (IOException e) {
            c.close();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
