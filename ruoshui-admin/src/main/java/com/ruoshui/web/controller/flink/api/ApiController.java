package com.ruoshui.web.controller.flink.api;

import com.ruoshui.flink.ao.JobServerAO;
import com.ruoshui.flink.streaming.web.common.RestResult;
import com.ruoshui.web.controller.flink.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-07
 * @time 22:00
 */
@RestController
@Slf4j
public class ApiController extends BaseController {

    @Resource
    private JobServerAO jobYarnServerAO;

    @RequestMapping("/ok")
    public RestResult ok() {
        return RestResult.success();
    }

    @RequestMapping("/alarmCallback")
    public RestResult alarmCallback(String appId, String jobName, String deployMode) {
        log.info("测试回调 appId={} jobName={} deployMode={}", appId, jobName, deployMode);
        return RestResult.success();
    }

}
