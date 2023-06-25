package com.ruoshui.common.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用http工具封装
 * 
 * @author ruoshui
 */
public class HttpHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    public static String getBodyString(ServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream())
        {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            LOGGER.warn("getBodyString出现问题！");
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    LOGGER.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return sb.toString();
    }


    /**

     获取请求ip
     @param request
     @return
     */
    public static String getIP(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        String headerIP = request.getHeader("x-real-ip");
        if(headerIP == null || "".equals(headerIP) || "null".equals(headerIP)){
            headerIP = request.getHeader("x-forwarded-for");
        }
        System.out.println("headerIP:"+headerIP);
        if(headerIP !=null && !"".equals(headerIP) && !"null".equals(headerIP)){
            ip = headerIP;
        }
        return ip;
    }
    /**

     获取真实的ip
     @param request
     @return
     @throws
     */
    public static String getRealIp(HttpServletRequest request){
        String ip;
         // 有的user可能使用代理，为处理用户使用代理的情况，使用x-forwarded-for
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        if ("127.0.0.1".equals(ip)) {
            try {
            // 获取本机真正的ip地址
                ip = InetAddress.getLocalHost().getHostAddress();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ip;
    }

    
}
