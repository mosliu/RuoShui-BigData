package com.ruoshui.flink.streaming.web.common.util;

import com.ruoshui.flink.streaming.web.model.dto.UserSession;
import org.apache.commons.codec.binary.Base64;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-13
 * @time 23:13
 */
public class Base64Coded {

    /**
     * base64 解码
     *
     * @author xinjingruoshui
     * @date 2022-07-13
     * @time 23:16
     */
    public static String decode(byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

    /**
     * base64 编码
     *
     * @author xinjingruoshui
     * @date 2022-07-13
     * @time 23:16
     */
    public static String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }


    public static void main(String[] args) {
        String string = UserSession.toJsonString(1, "xinjingruoshui", "123456");
        //编码
        String encode = encode(string.getBytes());
        System.out.println(string + "\t编码后的字符串为：" + encode);
        //解码
        String decode = decode(encode.getBytes());
        System.out.println(encode + "\t字符串解码后为：" + decode + " " + UserSession.toUserSession(decode));

    }

}
