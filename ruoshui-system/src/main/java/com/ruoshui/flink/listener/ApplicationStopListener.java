package com.ruoshui.flink.listener;

import com.ruoshui.flink.service.IpStatusService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-05-19
 * @time 23:16
 */
@Component
@Slf4j
public class ApplicationStopListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private IpStatusService ipStatusService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            log.warn("## sart the ApplicationStopListener start 。。。。。");

            ipStatusService.cancelIp();

            log.warn("## stop the ApplicationStopListener  end 。。。。。");

        } catch (Throwable e) {
            log.warn("##something goes wrong when stopping ApplicationStopListener:", e);

        } finally {
            log.info("## ApplicationStopListener client is down.");
        }
    }
}
