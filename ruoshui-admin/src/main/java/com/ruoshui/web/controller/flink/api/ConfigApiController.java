package com.ruoshui.web.controller.flink.api;

import com.ruoshui.flink.streaming.web.common.RestResult;
import com.ruoshui.flink.streaming.web.enums.SysConfigEnumType;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.vo.SystemConfigVO;
import com.ruoshui.flink.service.SystemConfigService;
import com.ruoshui.web.controller.flink.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-07
 * @time 22:00
 */
@RestController
@RequestMapping("/flink")
@Slf4j
public class ConfigApiController extends BaseController {


    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping(value = "/upsertSynConfig", method = RequestMethod.POST)
    public RestResult<?> upsertSynConfig(String key, String val) {
        try {
            systemConfigService.addOrUpdateConfigByKey(key, val.trim());
        } catch (BizException biz) {
            log.warn("upsertSynConfig is error ", biz);
            return RestResult.error(biz.getMessage());
        } catch (Exception e) {
            log.error("upsertSynConfig is error", e);
            return RestResult.error(e.getMessage());
        }
        return RestResult.success();
    }


    @RequestMapping(value = "/deleteConfig", method = RequestMethod.POST)
    public RestResult<?> deleteConfig(String key) {
        try {
            systemConfigService.deleteConfigByKey(key);
        } catch (BizException biz) {
            log.warn("upsertSynConfig is error ", biz);
            return RestResult.error(biz.getMessage());
        } catch (Exception e) {
            log.error("upsertSynConfig is error", e);
            return RestResult.error(e.getMessage());
        }
        return RestResult.success();
    }
    
    /**
     * 查询系统配置列表
     * 
     * @param modelMap
     * @return
     * @author wxj
     * @date 2021年12月16日 下午5:19:53 
     * @version V1.0
     */
    @RequestMapping(value = "/sysConfig")
    public RestResult<?> sysConfig(ModelMap modelMap) {
        List<SystemConfigVO> list = SystemConfigVO.toListVO(systemConfigService.getSystemConfig(SysConfigEnumType.SYS));
        return RestResult.success(list);
    }

}
