package com.ruoshui.framework.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import com.ruoshui.common.utils.ServletUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务相关配置
 * 
 * @author ruoshui
 */
@Component
public class ServerConfig
{
    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     * 
     * @return 服务地址
     */
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    public  Map<String,String> getIpAndPort(String url) {
        Map<String,String> map = new HashMap<>();
        Pattern p = compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)?");
        Matcher m = p.matcher(url);
        String ip = "";
        String port = "";
        while (m.find()) {
            ip = m.group(1);
            port = m.group(2);
        }
        map.put("ip",ip);
        map.put("port",port);
        return map;
    }


    public static Map<String,String> getIpAndPort1(String url) {
        Map<String,String> map = new HashMap<>();
        URI uri = URI.create(url);
        String host = uri.getHost();
        int port = uri.getPort();
        map.put("ip",host);
        map.put("port", String.valueOf(port));
        return map;
    }

    public static void main(String[] arg){

    }



}
