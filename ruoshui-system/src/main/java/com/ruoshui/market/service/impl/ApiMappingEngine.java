package com.ruoshui.market.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.net.System.Data.DataException;
import com.ruoshui.common.database.constants.DbQueryProperty;
import com.ruoshui.common.database.service.DataSourceFactory;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.common.utils.ThrowableUtil;
import com.ruoshui.common.utils.bean.BeanUtils;
import com.ruoshui.core.database.core.PageResult;
import com.ruoshui.core.util.PageUtil;
import com.ruoshui.market.dto.FieldRule;
import com.ruoshui.market.entity.ApiMaskEntity;
import com.ruoshui.market.entity.DataApiEntity;
import com.ruoshui.market.factory.AbstractFactory;
import com.ruoshui.market.factory.FactoryProducer;
import com.ruoshui.market.factory.crypto.Crypto;
import com.ruoshui.market.service.ApiMaskService;
import com.ruoshui.market.utils.SqlBuilderUtil;
import com.ruoshui.metadata.dto.DbSchema;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.service.MetadataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ApiMappingEngine {

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private MetadataSourceService metadataSourceService;

    @Autowired
    private ApiMaskService apiMaskService;

    public PageResult<Map<String, Object>> execute(DataApiEntity dataApi, Map<String, Object> params) {
        MetadataSourceEntity dataSource = Optional.ofNullable(metadataSourceService.getMetadataSourceById(dataApi.getExecuteConfig().getSourceId())).orElseThrow(() -> new DataException("API调用查询数据源出错"));
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = Optional.ofNullable(dataSourceFactory.createDbQuery(dbQueryProperty)).orElseThrow(() -> new DataException("创建数据查询接口出错"));
        // 参数
        Integer pageNum = Integer.parseInt(String.valueOf( params.getOrDefault("pageNum", 1)));
        Integer pageSize = Integer.parseInt(String.valueOf(params.getOrDefault("pageSize", 20)));
        PageUtil pageUtil = new PageUtil(pageNum, pageSize);
        Integer offset = pageUtil.getOffset();
        SqlBuilderUtil.SqlFilterResult sqlFilterResult;
        try {
            sqlFilterResult = SqlBuilderUtil.getInstance().applyFilters(dataApi.getExecuteConfig().getSqlText(), params);
        } catch (Exception e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
            throw new DataException("API调用动态构造SQL语句出错");
        }
        Map<String, Object> acceptedFilters = sqlFilterResult.getAcceptedFilters();
        // 数据脱敏
        List<FieldRule> rules = null;

        ApiMaskEntity apiMaskEntity = apiMaskService.getApiMaskByApiId(dataApi.getId());
        if (apiMaskEntity != null) {
            rules = apiMaskEntity.getRules();
        }
        PageResult<Map<String, Object>> pageResult;
        try {
            pageResult = dbQuery.queryByPage(sqlFilterResult.getSql(), acceptedFilters, offset, pageSize);
        } catch (Exception e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
            throw new DataException("API调用查询结果集出错");
        }
        try {
            if (CollUtil.isNotEmpty(rules)){
                System.out.println(rules.toString());
                // 并行流处理脱敏
                List<FieldRule> finalRules = new ArrayList<>();
                for(int i=0;i<rules.size();i++){
                    Map<String,Object> ruless = (Map<String,Object>)rules.get(i);
                    FieldRule fieldRule = BeanUtil.fillBeanWithMap(ruless, new FieldRule(), false);
                    finalRules.add(fieldRule);
                }

//                for(int i=0;i<pageResult.getData().size();i++){
//                    for(int x=0;x<rules.size();x++){
//                        Map<String,Object> ruless = (Map<String,Object>)rules.get(x);
//                        FieldRule fieldRule1 = BeanUtil.fillBeanWithMap(ruless, new FieldRule(), false);
//                        if (pageResult.getData().get(i).get(fieldRule1.getFieldName())!=null) {
//                            Object obj = pageResult.getData().get(i).get(fieldRule1.getFieldName());
//                            if (null != obj){
//                                AbstractFactory factory = FactoryProducer.getFactory(fieldRule1.getCipherType());
//                                Crypto crypto = factory.getCrypto(fieldRule1.getCryptType());
//                                String encrypt = crypto.encrypt(String.valueOf(obj));
//                                pageResult.getData().get(i).put(fieldRule1.getFieldName(), encrypt);
//                            }
//                        }
//                    }
//                }



                pageResult.getData().parallelStream().forEach(m -> {
                    finalRules.stream().forEach(r -> {
                        if (m.containsKey(r.getFieldName())) {
                            Object obj = m.get(r.getFieldName());
                            if (null != obj){
                                AbstractFactory factory = FactoryProducer.getFactory(r.getCipherType());
                                Crypto crypto = factory.getCrypto(r.getCryptType());
                                String encrypt = crypto.encrypt(String.valueOf(obj));
                                m.put(r.getFieldName(), encrypt);
                            }
                        }
                    });
                });
            }
        } catch (Exception e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
            throw new DataException("API调用数据脱敏出错");
        }
        pageResult.setPageNum(pageNum).setPageSize(pageSize);
        return pageResult;
    }
}
