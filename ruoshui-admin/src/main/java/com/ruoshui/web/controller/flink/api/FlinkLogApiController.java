package com.ruoshui.web.controller.flink.api;

import com.ruoshui.bigdata.core.conf.JobAdminConfig;
import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.flink.service.JobConfigService;
import com.ruoshui.flink.streaming.web.common.util.IpUtil;
import com.ruoshui.flink.streaming.web.common.util.LinuxInfoUtil;
import com.ruoshui.flink.streaming.web.enums.SysConfigEnum;
import com.ruoshui.flink.service.SystemConfigService;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.web.controller.flink.utils.JavaExecLinuxCommandRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/5/5
 * @time 10:23
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class FlinkLogApiController {

    @Autowired
    public SystemConfigService systemConfigService;

    @Autowired
    private JobConfigService jobConfigService;

    @Autowired
    private BaseResourceMapper baseResourceMapper;

    @RequestMapping(value = "/getFlinkLocalJobLog")
    public String  getFlinkLocalJobLog(@RequestParam Long jobid, HttpServletResponse response) {
        String logstr;
        try {
            JobConfigDTO jobConfigById = jobConfigService.getJobConfigById(jobid);
            BaseResource resource = baseResourceMapper.getById(Math.toIntExact(jobConfigById.getFlinkCluster()));
            String password = AESUtil.decrypt(resource.getServerPassword());
            String str = JavaExecLinuxCommandRemote.RemoteSubmitCommand("hostname", resource.getServerIp(), resource.getServerUser(), password);
            String fileName = String.format("flink-%s-client-%s.log", resource.getServerUser(), str.replaceAll("\r|\n", ""));
            String flinkName = systemConfigService.getSystemConfigByKey(SysConfigEnum.FLINK_HOME.getKey());
            //String logPath = "cat " + flinkName + "log/" + fileName;
            String logPath = "cat /data/var/log/udp/2.0.0.0/flink/" + fileName;


            log.info("日志文件地址 logPath={}", "cat" + logPath);
            logstr = JavaExecLinuxCommandRemote.RemoteSubmitCommand(logPath, resource.getServerIp(), resource.getServerUser(), password);
            System.out.println(logstr);
            response.reset();
            response.addHeader("Content-Length", "" + logstr.length());
            response.setContentType("text/plain; charset=utf-8");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream(),2048);
            toClient.write(logstr.getBytes());
            toClient.flush();
            toClient.close();
        } catch (Exception ex) {
            log.error("[getFlinkLocalJobLog is error]", ex);
            return ex.getMessage();
        }
        return "ok";
    }


}
