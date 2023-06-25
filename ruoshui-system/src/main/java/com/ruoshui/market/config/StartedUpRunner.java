package com.ruoshui.market.config;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.market.entity.DataApiEntity;
//import com.ruoshui.market.feign.DataApiServiceFeign;
import com.ruoshui.market.handler.MappingHandlerMapping;
import com.ruoshui.market.service.DataApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final Environment environment;


    @Autowired
    private MappingHandlerMapping mappingHandlerMapping;

    @Autowired
    private DataApiService dataApiService;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            String banner = "-----------------------------------------\n" +
                    "服务启动成功，时间：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "\n" +
                    "服务名称：" + environment.getProperty("ruoshui.name") + "\n" +
                    "端口号：" + environment.getProperty("server.port") + "\n" +
                    "-----------------------------------------";
            System.out.println(banner);

            // 项目启动时，初始化已发布的接口
            List<DataApiEntity> releaseDataApiList =  dataApiService.getDataApiEntityList(DataConstant.ApiState.RELEASE.getKey());
            if (CollUtil.isNotEmpty(releaseDataApiList)) {
                releaseDataApiList.stream().forEach(api -> mappingHandlerMapping.registerMapping(api));
            }
        }
    }
}
