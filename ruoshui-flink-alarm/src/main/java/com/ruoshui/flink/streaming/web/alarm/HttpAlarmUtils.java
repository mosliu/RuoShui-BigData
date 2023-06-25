package com.ruoshui.flink.streaming.web.alarm;

import com.ruoshui.flink.streaming.web.common.util.HttpUtil;
import com.ruoshui.flink.streaming.web.enums.SysErrorEnum;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.vo.CallbackDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/2/21
 * @time 11:38
 */
@Slf4j
public class HttpAlarmUtils {
    public static boolean send(String url, CallbackDTO callbackDTO) {
        if (StringUtils.isEmpty(url) || callbackDTO == null) {
            log.error("url={} is null or callbackDTO={} is null", url, callbackDTO);
            throw new BizException(SysErrorEnum.PARAM_IS_NULL);
        }
        RestTemplate restTemplate = HttpUtil.buildRestTemplate(HttpUtil.TIME_OUT_15_S);
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("appId", callbackDTO.getAppId());
            map.add("deployMode", callbackDTO.getDeployMode());
            map.add("jobName", callbackDTO.getJobName());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity(map, headers);
            //发起请求,服务地址，请求参数，返回消息体的数据类型
            ResponseEntity<String> response = restTemplate.postForEntity(url, param, String.class);
            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                return true;
            }
            log.error("http请求失败 response={}", response);
            throw new BizException("http请求失败  status=" + response.getStatusCode().value());
        } catch (BizException bizException) {
            throw bizException;
        } catch (Exception e) {
            log.error("请求失败 url={} callbackDTO={}", url, callbackDTO, e);
            throw new BizException("http请求失败");
        }
    }
}
