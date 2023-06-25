package com.ruoshui.web.controller.flink.web;

import com.ruoshui.flink.streaming.web.common.util.UserSessionUtil;
import com.ruoshui.flink.streaming.web.model.dto.UserSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-12
 * @time 21:20
 */
public class BaseController {

    /**
     * 取得HttpServletRequest对象.
     *
     * @return HttpServletRequest对象.
     */
    protected HttpServletRequest getServletRequest() {

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        return request;
    }


    public String getUserName() {
        UserSession userSession = UserSessionUtil.userSession(getServletRequest());
        if (userSession == null) {
            return "";
        }
        return userSession.getName();
    }

}
