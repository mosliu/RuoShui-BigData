package com.ruoshui.test;

import ch.ethz.ssh2.*;
import cn.hutool.extra.ftp.Ftp;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Controller;;

public class FtpU {

    public static void main(String[] args) {
        //连接服务器 服务器名称和端口号
        //xx.xx.xx.xx 就是目标服务器的ip 端口是22
        Connection connection = new Connection("cluster02.toroidal.com",22);
        //你要上传文件所在地址，linux和window路径不一样看你自己的系统
        String filePath1 ="C:\\Users\\admin\\Desktop\\e3plus-sdk-java-1.0.1.jar" ;
        File f = new File(filePath1);
        try(FileInputStream fis = new FileInputStream(f)) {
            connection.connect();
            //yuan服务器用户名和密码
            boolean isAuthenticated = connection.authenticateWithPassword("root","mendale$5790");
            if(!isAuthenticated){
                System.out.println("连接建立失败");
                return ;
            }

            SCPClient scpClient = new SCPClient(connection);
            //这个是你要上传文件的目标服务器的文件路径
            String remoteTargetDirectory = "/lsx/bigdata/upload_jars/";
            SFTPv3Client sftpv3Client = new SFTPv3Client(connection);

//            //判断是否有这个文件夹 如果没有就创建一个
//            Boolean isdir = f.isDirectory(sftpv3Client, remoteTargetDirectory);
//            if(!isdir){
//                sftpv3Client.mkdir(remoteTargetDirectory,0600);
//            }
            SCPOutputStream os = scpClient.put(f.getName(),f.length(),remoteTargetDirectory,null);
            byte[] b = new byte[4096];
            int i;
            while ((i = fis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            Session session= connection.openSession();//打开一个会话
            //远程执行linux命令 因为上传的文件没有读的文件 需要加上才能下载 （如果你上传的文件有）
            String cmd = "chmod +r "+remoteTargetDirectory+f.getName();
            System.out.println("linux命令=="+cmd);
            session.execCommand(cmd);//执行命令
            os.flush();
            fis.close();
            os.close();
            connection.close();
            System.out.println("upload ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
