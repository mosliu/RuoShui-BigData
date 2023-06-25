package com.ruoshui.flink.ao;

import com.ruoshui.flink.streaming.web.model.vo.CallbackDTO;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-25
 * @time 19:50
 */
public interface AlarmServiceAO {

    /**
     * 发送钉钉告警消息
     *
     * @author xinjingruoshui
     * @date 2022-09-25
     * @time 19:53
     */
    boolean sendForDingding(String url, String content, Long jobConfigId);


    /**
     * 发送http请求回调
     *
     * @author xinjingruoshui
     * @date 2021/2/21
     * @time 11:31
     */
    boolean sendForHttp(String url,CallbackDTO callbackDTO);
}
