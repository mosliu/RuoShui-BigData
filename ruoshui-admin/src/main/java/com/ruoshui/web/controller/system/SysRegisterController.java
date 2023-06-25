package com.ruoshui.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.common.core.domain.model.RegisterBody;
import com.ruoshui.common.utils.StringUtils;
import com.ruoshui.framework.web.service.SysRegisterService;
import com.ruoshui.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author ruoshui
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
