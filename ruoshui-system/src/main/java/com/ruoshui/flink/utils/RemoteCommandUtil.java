package com.ruoshui.flink.utils;

import ch.ethz.ssh2.*;
import com.ruoshui.common.utils.StringUtils;
import com.ruoshui.common.utils.uuid.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zfwy
 */
@Slf4j
public class RemoteCommandUtil {

    //目标服务器端口,默认
    private static int port = 22;
    private static Connection conn = null;

    /**
     * 登录服务器主机
     *
     * @param ip       主机IP
     * @param userName 用户名
     * @param userPwd  密码
     * @return 登录对象
     */
    public static Connection login(String ip, String userName, String userPwd) {
        boolean flg = false;
        Connection conn = null;
        try {
            conn = new Connection(ip,port);
            //连接
            conn.connect();
            //认证
            flg = conn.authenticateWithPassword(userName, userPwd);
            if (flg) {
                log.info("连接成功");
                return conn;
            }
        } catch (IOException e) {
            log.error("连接失败" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 实现下载服务器上的文件到本地指定目录
     *
     * @param conn      SSH连接信息
     * @param basePath  服务器上的文件地址/home/img
     * @param localPath 本地路径：D:\
     * @param newName	文件重命名
     * @throws IOException
     */
    public void downloadFile(Connection conn, String fileName, String basePath, String localPath,String newName) throws IOException {
        SCPClient scpClient = conn.createSCPClient();
        try {
            SCPInputStream sis = scpClient.get(basePath + "/" + fileName);
            File f = new File(localPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            File newFile = null;
            if (StringUtils.isBlank(newName)) {
                newFile = new File(localPath + fileName);
            } else {
                newFile = new File(localPath + newName);
            }
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] b = new byte[4096];
            int i;
            while ((i = sis.read(b)) != -1) {
                fos.write(b, 0, i);
            }
            fos.flush();
            fos.close();
            sis.close();
            log.info("下载完成");
        } catch (IOException e) {
            log.info("文件不存在或连接失败");
            e.printStackTrace();
        } finally {
            log.info("服务关闭");
            closeConn();
        }
    }


    /**
     * 上传文件到服务器
     *
     * @param conn                  SSH连接信息
     * @param f                     文件对象
     * @param remoteTargetDirectory 上传路径
     * @param mode                  默认为null
     */
    public Boolean uploadFile(Connection conn, File f, String remoteTargetDirectory, String mode) {
        try {
            SCPClient scpClient = new SCPClient(conn);
            SCPOutputStream os = scpClient.put(f.getName(), f.length(), remoteTargetDirectory, mode);
            byte[] b = new byte[4096];
            FileInputStream fis = new FileInputStream(f);
            int i;
            while ((i = fis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
            fis.close();
            os.close();
            log.info("上传成功");
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        } finally {
            closeConn();
        }
    }


    /**
     * 关闭服务
     */
    public static void closeConn() {
        if (null == conn) {
            return;
        }
        conn.close();
    }

    /**
     * 批量下载目录所有文件
     *
     * @param root   SSH连接信息
     * @param fwPath 服务器上的文件地址/home/img
     * @param bdPath 本地路径：D:\
     */
    public void downloadDirFile(Connection root, String fwPath, String bdPath) throws IOException {
        SCPClient scpClient = root.createSCPClient();
        try {
            List<String> allFilePath = getAllFilePath(root, fwPath);
            File f = new File(bdPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            FileOutputStream fos = null;
            SCPInputStream sis = null;
            for (String path : allFilePath) {
                String[] split = path.split("/");
                String filename = split[split.length-1];
                sis = scpClient.get(path);
                File newFile = new File(bdPath);
                fos = new FileOutputStream(newFile+"\\"+filename);
                byte[] b = new byte[4096];
                int i;
                while ((i = sis.read(b)) != -1) {
                    fos.write(b, 0, i);
                }
            }
            fos.flush();
            fos.close();
            sis.close();
            log.info("下载完成");
        } catch (IOException e) {
            log.info("文件不存在或连接失败");
            e.printStackTrace();
        } finally {
            log.info("服务关闭");
            closeConn();
        }
    }

    /**
     * 获取目录下所有文件路径
     * @param conn
     * @param fwPath
     * @return
     */
    public List<String> getAllFilePath(Connection conn, String fwPath) {
        List<String> folderNameList = new ArrayList<String>();
        try {
            // 创建一个会话
            Session sess = conn.openSession();
            sess.requestPTY("bash");
            sess.startShell();
            StreamGobbler stdout = new StreamGobbler(sess.getStdout());
            StreamGobbler stderr = new StreamGobbler(sess.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stdout));

            PrintWriter out = new PrintWriter(sess.getStdin());
            out.println("cd " + fwPath);
            out.println("ll");
            out.println("exit");
            out.close();
            sess.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 30000);

            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                //获取文件名称
                if (line.contains("-rw-r--r--")) {
                    //取出正确的文件名称
                    StringBuffer sb =
                            new StringBuffer(line.substring(line.lastIndexOf(":") + 3, line.length()));

                    line = sb.toString().replace(" ", fwPath + "/");
                    folderNameList.add(line);
                }
            }

            //关闭连接
            sess.close();
            stderrReader.close();
            stdoutReader.close();
        } catch (IOException e) {
            log.info("文件不存在或连接失败");
            System.exit(2);
            e.printStackTrace();
        } finally {
            log.info("服务关闭");
            closeConn();
        }
        return folderNameList;
    }

    public String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\uplodFile";
    }

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空！";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath();
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName; // 返回重命名后的文件名
    }

}
