package com.ruoshui.web.controller.market;

import cn.hutool.core.map.MapUtil;
import com.aspose.words.net.System.Data.DataException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.core.database.core.PageResult;
import com.ruoshui.market.entity.ApiMaskEntity;
import com.ruoshui.market.entity.DataApiEntity;
import com.ruoshui.market.handler.MappingHandlerMapping;
import com.ruoshui.market.handler.RequestHandler;
import com.ruoshui.market.handler.RequestInterceptor;
import com.ruoshui.market.service.ApiMaskService;
import com.ruoshui.market.service.DataApiService;
import com.ruoshui.market.service.impl.ApiMappingEngine;
import lombok.SneakyThrows;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inner")
public class marketInnerController extends BaseController {

    @Resource
    private DataApiService dataApiService;

    @Resource
    private ApiMaskService apiMaskService;

    @Resource
    private RequestHandler requestHandler;


    @GetMapping("/apis/{id}")
    public DataApiEntity getDataApiById(@PathVariable("id") String id) {
        DataApiEntity dataApiEntity = dataApiService.getDataApiById(id);
        return dataApiEntity;
    }

    @GetMapping("/apis/release/list")
    public List<DataApiEntity> getReleaseDataApiList() {
        QueryWrapper<DataApiEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", DataConstant.ApiState.RELEASE.getKey());
        List<DataApiEntity> dataApiEntityList = dataApiService.list(queryWrapper);
        return dataApiEntityList;
    }

    @GetMapping("/apiMasks/api/{id}")
    public ApiMaskEntity getApiMaskByApiId(@PathVariable("id") String id) {
        ApiMaskEntity apiMaskEntity = apiMaskService.getApiMaskByApiId(id);
        return apiMaskEntity;
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/data/api")
    public Object getApiMaskByApiId(HttpServletRequest request, HttpServletResponse response,
                                           @PathVariable(required = false) Map<String, Object> pathVariables,
                                           @RequestParam(required = false) Map<String, Object> requestParams,
                                           @RequestBody(required = false) Map<String, Object> requestBodys) {

        DataApiEntity api;
        Map<String, Object> params = new HashMap<>();
        if (MapUtil.isNotEmpty(pathVariables)) {
            params.putAll(pathVariables);
        }
        if (MapUtil.isNotEmpty(requestParams)) {
            params.putAll(requestParams);
        }
        if (MapUtil.isNotEmpty(requestBodys)) {
            params.putAll(requestBodys);
        }
        api = MappingHandlerMapping.getMappingApiInfoA(params.get("apiCode").toString());
        params.remove("apiCode");
        if(null == api){
            throw new DataException("api接口未发布或不存在!");
        }
        // 序列化
        api = requestHandler.getObjectMapper().readValue(requestHandler.getObjectMapper().writeValueAsString(api), DataApiEntity.class);
        // 执行前置拦截器
        requestHandler.getRequestInterceptor().preHandle(request, response, api, params);
        PageResult<Map<String, Object>> value = requestHandler.getApiMappingEngine().execute(api, params);
        // 执行后置拦截器
        requestHandler.getRequestInterceptor().postHandle(request, response, api, params, value);
        return AjaxResult.success(value);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/data/api")
    public Object postApiMaskByApiId(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable(required = false) Map<String, Object> pathVariables,
                                    @RequestParam(required = false) Map<String, Object> requestParams,
                                    @RequestBody(required = false) Map<String, Object> requestBodys) {

        DataApiEntity api;
        Map<String, Object> params = new HashMap<>();
        if (MapUtil.isNotEmpty(pathVariables)) {
            params.putAll(pathVariables);
        }
        if (MapUtil.isNotEmpty(requestParams)) {
            params.putAll(requestParams);
        }
        if (MapUtil.isNotEmpty(requestBodys)) {
            params.putAll(requestBodys);
        }
        api = MappingHandlerMapping.getMappingApiInfoA(params.get("apiCode").toString());
        params.remove("apiCode");
        if(null == api){
            throw new DataException("api接口未发布或不存在!");
        }
        // 序列化
        api = requestHandler.getObjectMapper().readValue(requestHandler.getObjectMapper().writeValueAsString(api), DataApiEntity.class);
        // 执行前置拦截器
        requestHandler.getRequestInterceptor().preHandle(request, response, api, params);
        PageResult<Map<String, Object>> value = requestHandler.getApiMappingEngine().execute(api, params);
        // 执行后置拦截器
        requestHandler.getRequestInterceptor().postHandle(request, response, api, params, value);
        return AjaxResult.success(value);
    }

}
