package com.ruoshui.flink.runner;

import com.ruoshui.flink.service.IpStatusService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2018/5/9
 * @time 下午4:12
 */
@Component
@Order(0)
@Slf4j
@Configuration
@Getter
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {


    @Resource
    private IpStatusService ipStatusService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ipStatusService.registerIp();
    }


}
