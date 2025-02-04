package com.ruoshui.flink.streaming.web.common.util;

import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.model.dto.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-12
 * @time 21:25
 */
@Slf4j
public class UserSessionUtil {

    /**
     * 根据cookie获取登陆信息
     *
     * @author xinjingruoshui
     * @date 2022-08-12
     * @time 19:06
     */
    public static UserSession userSession(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SystemConstants.COOKIE_NAME_SESSION_ID)) {
                try {
                    if (StringUtils.isEmpty(cookie.getValue())) {
                        log.warn("登陆信息失效 请重新登陆");
                        return null;
                    }
                    UserSession userSession = UserSession.toUserSession(Base64Coded.decode(cookie.getValue().getBytes()));
                    return userSession;
                } catch (Exception e) {
                    log.error("解析登陆信息 请重新登陆 {}", cookie.getValue(), e);
                }
            }
        }

        return null;
    }
}
