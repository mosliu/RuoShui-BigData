package com.ruoshui.flink.factory;


import com.google.common.collect.Maps;
import com.ruoshui.flink.ao.JobServerAO;
import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.enums.DeployModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022/10/29
 */
@Component
@Slf4j
public class JobServerAOFactory implements ApplicationContextAware {


  private static Map<DeployModeEnum, JobServerAO> beanMap;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    Map<String, JobServerAO> map = applicationContext.getBeansOfType(JobServerAO.class);
    beanMap = Maps.newHashMap();
    for (Map.Entry<String, JobServerAO> entry : map.entrySet()) {
      switch (entry.getKey()) {
        case SystemConstants.BEANNAME_JOBSTANDALONESERVERAO:
          beanMap.put(DeployModeEnum.LOCAL, entry.getValue());
          beanMap.put(DeployModeEnum.STANDALONE, entry.getValue());
          break;
        case SystemConstants.BEANNAME_JOBYARNSERVERAO:
          beanMap.put(DeployModeEnum.YARN_APPLICATION, entry.getValue());
          beanMap.put(DeployModeEnum.YARN_PER, entry.getValue());
          break;
        default:
          log.error("不存在的bean类型 name={}", entry.getKey());
          throw new RuntimeException("不存在的bean类型");

      }
    }
  }

  public static JobServerAO getJobServerAO(DeployModeEnum deployModeEnum) {
    return beanMap.get(deployModeEnum);
  }
}
