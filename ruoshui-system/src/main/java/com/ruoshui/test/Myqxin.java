package com.ruoshui.test;

import ch.ethz.ssh2.Connection;
import com.ruoshui.flink.utils.RemoteCommandUtil;

import java.io.File;

public class Myqxin {
    public static void main(String[] args)throws Exception {
        // 创建服务对象
        RemoteCommandUtil commandUtil = new RemoteCommandUtil();
        // 登录到LInux服务
        Connection root = commandUtil.login("cluster02.toroidal.com", "root", "mendale$5790");
        // 服务器文件路径
        String fwPath = "/lsx/bigdata/upload_jars";
        // 上传文件
        File file = new File("C:\\Users\\admin\\Desktop\\e3plus-sdk-java-1.0.1.jar");

        // windows本地存放目录，不需要加上具体文件
        String bdPath = "D:\\Music\\";

        // 如果存在
        //        if(file.exists()){
        // 文件重命名
        //            String newname = "新名称"+".jpeg";
        //            File newFile = new File(file.getParent() + File.separator + newname);
        //            if (file.renameTo(newFile))
        //                commandUtil.uploadFile(root,newFile,fwPath,null);
        //        }

        //上传文件
         commandUtil.uploadFile(root,file,fwPath,null);
        // 下载文件
         commandUtil.downloadFile(root,"e3plus-sdk-java-1.0.1.jar",fwPath,bdPath,null);
    }
}

