package com.ruoshui.market.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoshui.market.service.impl.ApiMappingEngine;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RequestHandler {

    private RequestInterceptor requestInterceptor;

    private ApiMappingEngine apiMappingEngine;

    private ObjectMapper objectMapper;

    public void setRequestInterceptor(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    public void setApiMappingEngine(ApiMappingEngine apiMappingEngine) {
        this.apiMappingEngine = apiMappingEngine;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RequestInterceptor getRequestInterceptor() {
        return requestInterceptor;
    }

    public ApiMappingEngine getApiMappingEngine() {
        return apiMappingEngine;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
