package com.ruoshui.market.service.impl;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.aspose.words.Document;
import com.aspose.words.MailMerge;
import com.aspose.words.net.System.Data.DataException;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.common.core.redis.RedisCache;
import com.ruoshui.common.database.utils.SecurityUtil;
import com.ruoshui.common.database.utils.WordUtil;
import com.ruoshui.common.utils.ThrowableUtil;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.core.database.core.RedisConstant;
import com.ruoshui.market.dto.DataApiDto;
import com.ruoshui.market.dto.ReqParam;
import com.ruoshui.market.dto.ResParam;
import com.ruoshui.market.dto.SqlParseDto;
import com.ruoshui.market.entity.DataApiEntity;
import com.ruoshui.market.enums.ConfigType;
import com.ruoshui.market.handler.MappingHandlerMapping;
import com.ruoshui.market.mapper.DataApiDao;
import com.ruoshui.market.mapstruct.DataApiMapper;
import com.ruoshui.market.service.DataApiService;
import com.ruoshui.market.utils.MD5Util;
import com.ruoshui.market.utils.SqlBuilderUtil;
import com.ruoshui.market.vo.ApiHeader;
import com.ruoshui.market.vo.SqlParseVo;
import com.ruoshui.metadata.dto.SqlConsoleDto;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.service.SqlConsoleService;
import com.ruoshui.metadata.vo.SqlConsoleVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.SelectUtils;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ruoshui.common.utils.uuid.UUID;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 数据API信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-03-31
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DataApiServiceImpl extends BaseServiceImpl<DataApiDao, DataApiEntity> implements DataApiService {

    @Autowired
    private DataApiDao dataApiDao;

    @Autowired
    private DataApiMapper dataApiMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisCache redisService;

    @Autowired
    private SqlConsoleService sqlConsoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDataApi(DataApiDto dataApiDto) {
        String uuid = UUID.randomUUID(true).toString();
        dataApiDto.setApiCode(uuid);
        DataApiEntity dataApi = shareCode(dataApiDto);
        dataApiDao.insert(dataApi);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDataApi(DataApiDto dataApiDto) {
        DataApiEntity dataApi = shareCode(dataApiDto);
        dataApiDao.updateById(dataApi);
    }

    private DataApiEntity shareCode(DataApiDto dataApiDto) {
        DataApiEntity dataApi = dataApiMapper.toEntity(dataApiDto);
        String configType = dataApi.getExecuteConfig().getConfigType();
        if (ConfigType.FORM.getKey().equals(configType)) {
            try {
                dataApi.getExecuteConfig().setSqlText(sqlJdbcNamedParameterBuild(dataApi));
            } catch (JSQLParserException e) {
                log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
                throw new DataException("SQL语法有问题，解析出错");
            }
        } else if (ConfigType.SCRIPT.getKey().equals(configType)) {}
        return dataApi;
    }

    @Override
    public DataApiEntity getDataApiById(String id) {
        DataApiEntity dataApiEntity = super.getById(id);
        return dataApiEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataApiById(String id) {
        dataApiDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataApiBatch(List<String> ids) {
        dataApiDao.deleteBatchIds(ids);
    }

    @Override
    public SqlParseVo sqlParse(SqlParseDto sqlParseDto) throws SQLException, JSQLParserException {
        String sourceId = sqlParseDto.getSourceId();
        String sql = sqlParseDto.getSqlText();
        sql = sql.replace(SqlBuilderUtil.getInstance().MARK_KEY_START, "");
        sql = sql.replace(SqlBuilderUtil.getInstance().MARK_KEY_END, "");
        Statement stmt;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
            throw new DataException("SQL语法有问题，解析出错");
        }
        // 维护元数据缓存数据
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tables = tablesNamesFinder.getTableList(stmt);
        // 查询字段
        final List<Map<String,String>> cols = new ArrayList<>();
        // 查询参数
        final List<String> vars = new ArrayList<>();
        if (tables.size() == 1) {
            // 单表解析
            singleSqlParse(stmt, cols, vars, tables.get(0));
        } else if (tables.size() > 1) {
            // 多表解析
            multipleSqlParse(stmt, cols, vars);
        }
        SqlParseVo sqlParseVo = new SqlParseVo();
        List<ReqParam> reqParams = vars.stream().map(s -> {
            ReqParam reqParam = new ReqParam();
            reqParam.setParamName(s);
            reqParam.setNullable(DataConstant.TrueOrFalse.FALSE.getKey());
            return reqParam;
        }).collect(Collectors.toList());
        sqlParseVo.setReqParams(reqParams);


        final List<ResParam> resParams = new ArrayList<>();
        parseFields(sqlParseDto.getSqlText()).forEach((column, alias) -> {
            ResParam resParam = new ResParam();
            if(alias.isEmpty()){
                resParam.setFieldName(column);
            }else {
                resParam.setFieldName(alias);
            }
            resParams.add(resParam);
        });

//        List<MetadataSourceEntity> sourceEntityList = (List<MetadataSourceEntity>) redisService.get(RedisConstant.METADATA_SOURCE_KEY);
//        MetadataSourceEntity sourceEntity = sourceEntityList.stream().filter(s -> sourceId.equals(s.getId())).findFirst().orElse(null);
//        if (sourceEntity != null) {
//            List<MetadataTableEntity> tableEntityList = (List<MetadataTableEntity>) redisService.hget(RedisConstant.METADATA_TABLE_KEY, sourceEntity.getId());
//            Map<String, List<Map<String, String>>> map = cols.stream().collect(Collectors.groupingBy(e -> e.get("tableName").toString()));
//            for (Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()) {
//                String entryKey = entry.getKey().toLowerCase();
//                List<Map<String, String>> entryValue = entry.getValue();
//                MetadataTableEntity tableEntity = tableEntityList.stream().filter(t -> entryKey.equals(t.getTableName().toLowerCase())).findFirst().orElse(null);
//                if (tableEntity != null) {
//                    List<MetadataColumnEntity> columnEntityList = (List<MetadataColumnEntity>) redisService.hget(RedisConstant.METADATA_COLUMN_KEY, tableEntity.getId());
//                    entryValue.stream().forEach(m -> {
//                        String columnName = m.get("columnName").toLowerCase();
//                        String columnAliasName = m.get("columnAliasName");
//                        Stream<MetadataColumnEntity> stream = columnEntityList.stream().filter(c -> columnName.equals(c.getColumnName().toLowerCase()));
//                        MetadataColumnEntity columnEntity = stream.findFirst().orElse(null);
//                        ResParam resParam = new ResParam();
//                        if (columnEntity != null) {
//                            resParam.setFieldName(columnEntity.getColumnName());
//                            resParam.setFieldComment(StrUtil.isNotBlank(columnEntity.getColumnComment()) ? columnEntity.getColumnComment() : "");
//                            resParam.setDataType(StrUtil.isNotBlank(columnEntity.getDataType()) ? columnEntity.getDataType() : "");
//                            resParam.setFieldAliasName(StrUtil.isNotBlank(columnAliasName) ? columnAliasName : "");
//                        }else{
//                            resParam.setFieldName(columnName);
//                        }
//                        resParams.add(resParam);
//                    });
//                }
//            }
//        }
        sqlParseVo.setResParams(resParams);
        return sqlParseVo;
    }

    private void singleSqlParse(Statement stmt, List<Map<String, String>> cols, List<String> vars, String tableName) {
        stmt.accept(new StatementVisitorAdapter() {
            @Override
            public void visit(Select select) {
                select.getSelectBody().accept(new SelectVisitorAdapter() {
                    @Override
                    public void visit(PlainSelect plainSelect) {
                        plainSelect.getSelectItems().forEach(selectItem -> {
                            selectItem.accept(new SelectItemVisitorAdapter() {
                                @Override
                                public void visit(SelectExpressionItem item) {
                                    Map<String, String> map = new HashMap<>();
                                    String columnName;
                                    Object expression = item.getExpression();

                                    if (expression instanceof Column) {
                                        Column column = (Column) expression;
                                        columnName = column.getColumnName();
                                        if (item.getAlias() != null) {
                                            map.put("columnAliasName", item.getAlias().getName());
                                        }
                                    } else if (expression instanceof Function) {
                                        columnName = expression.toString();
                                    } else {
                                        // 增加对select 'aaa' from table; 的支持
                                        columnName = String.valueOf(expression);
                                        columnName = columnName.replace("'", "").replace("\"", "").replace("`", "");
                                    }
                                    columnName = columnName.replace("'", "").replace("\"", "").replace("`", "");
                                    map.put("tableName", tableName);
                                    map.put("columnName", columnName);
                                    cols.add(map);
                                }
                            });
                        });
                        if (plainSelect.getWhere() != null) {
                            plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                                @Override
                                public void visit(JdbcNamedParameter jdbcNamedParameter) {
                                    vars.add(jdbcNamedParameter.getName());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void multipleSqlParse(Statement stmt, List<Map<String, String>> cols, List<String> vars) {
        stmt.accept(new StatementVisitorAdapter() {
            @Override
            public void visit(Select select) {
                select.getSelectBody().accept(new SelectVisitorAdapter() {
                    @Override
                    public void visit(PlainSelect plainSelect) {
                        // 存储表名
                        Map<String, String> map = new HashMap<>();
                        Table table = (Table) plainSelect.getFromItem();
                        if (table.getAlias() != null) {
                            map.put(table.getName(), table.getAlias().getName());
                        }
                        for (Join join : plainSelect.getJoins()) {
                            Table table1 = (Table) join.getRightItem();
                            if (table1.getAlias() != null) {
                                map.put(table1.getName(), table1.getAlias().getName());
                            }
                        }
                        plainSelect.getSelectItems().forEach(selectItem -> {
                            selectItem.accept(new SelectItemVisitorAdapter() {
                                @Override
                                public void visit(SelectExpressionItem item) {
                                    Map<String, String> m = new HashMap<>();
                                    String tableName = "", columnName;
                                    Object expression = item.getExpression();
                                    if (expression instanceof Column) {
                                        Column column = (Column) expression;
                                        Table table = column.getTable();
                                        if (table != null) {
                                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                                if (table.getName().equals(entry.getValue())) {
                                                    tableName = entry.getKey();
                                                    break;
                                                }
                                            }
                                        }
                                        columnName = column.getColumnName();
                                        if (item.getAlias() != null) {
                                            m.put("columnAliasName", item.getAlias().getName());
                                        }
                                    } else if (expression instanceof Function) {
                                        columnName = expression.toString();
                                    } else {
                                        // 增加对select 'aaa' from table; 的支持
                                        columnName = String.valueOf(expression);
                                        columnName = columnName.replace("'", "").replace("\"", "").replace("`", "");
                                    }
                                    columnName = columnName.replace("'", "").replace("\"", "").replace("`", "");
                                    m.put("tableName", tableName);
                                    m.put("columnName", columnName);
                                    cols.add(m);
                                }
                            });
                        });
                        if (plainSelect.getWhere() != null) {
                            plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                                @Override
                                public void visit(JdbcNamedParameter jdbcNamedParameter) {
                                    vars.add(jdbcNamedParameter.getName());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private String sqlJdbcNamedParameterBuild(DataApiEntity dataApi) throws JSQLParserException {
        Table table = new Table(dataApi.getExecuteConfig().getTableName());
        String[] resParams = dataApi.getResParams().stream().map(s -> s.getFieldName()).toArray(String[]::new);
        Select select = SelectUtils.buildSelectFromTableAndExpressions(table, resParams);
        return SqlBuilderUtil.getInstance().buildHql(select.toString(), dataApi.getReqParams());
    }

    private String sqlJdbcNamedParameterParse(String sqlText) throws JSQLParserException {
        final StringBuilder buffer = new StringBuilder();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser() {
            @Override
            public void visit(JdbcNamedParameter jdbcNamedParameter) {
                this.getBuffer().append("?");
            }
        };
        SelectDeParser deparser = new SelectDeParser(expressionDeParser, buffer);
        expressionDeParser.setSelectVisitor(deparser);
        expressionDeParser.setBuffer(buffer);
        Statement stmt = CCJSqlParserUtil.parse(sqlText);
        stmt.accept(new StatementVisitorAdapter() {
            @Override
            public void visit(Select select) {
                select.getSelectBody().accept(deparser);
            }
        });
        return buffer.toString();
    }

    @Override
    public void copyDataApi(String id) {
        DataApiEntity dataApiEntity = Optional.ofNullable(super.getById(id)).orElseThrow(() -> new DataException("获取失败"));
        DataApiEntity copy = new DataApiEntity();
        copy.setApiName(dataApiEntity.getApiName() + "_副本" + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        copy.setApiVersion(dataApiEntity.getApiVersion());
        copy.setApiUrl(dataApiEntity.getApiUrl() + "/copy" + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        copy.setReqMethod(dataApiEntity.getReqMethod());
        copy.setResType(dataApiEntity.getResType());
        copy.setDeny(dataApiEntity.getDeny());
        copy.setRateLimit(dataApiEntity.getRateLimit());
        copy.setExecuteConfig(dataApiEntity.getExecuteConfig());
        copy.setReqParams(dataApiEntity.getReqParams());
        copy.setResParams(dataApiEntity.getResParams());
        copy.setStatus(DataConstant.ApiState.WAIT.getKey());
        dataApiDao.insert(copy);
    }

    @Override
    public void releaseDataApi(String id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("type", "1");
        LambdaUpdateWrapper<DataApiEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataApiEntity::getStatus, DataConstant.ApiState.RELEASE.getKey());
        updateWrapper.eq(DataApiEntity::getId, id);
        dataApiDao.update(null, updateWrapper);
        DataApiEntity dataApiEntity = dataApiDao.selectById(id);
        MappingHandlerMapping.registerMapping(dataApiEntity);
    }

    @Override
    public void cancelDataApi(String id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("type", "2");
        LambdaUpdateWrapper<DataApiEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataApiEntity::getStatus, DataConstant.ApiState.CANCEL.getKey());
        updateWrapper.eq(DataApiEntity::getId, id);
        dataApiDao.update(null, updateWrapper);
        DataApiEntity dataApiEntity = dataApiDao.selectById(id);
        MappingHandlerMapping.unregisterMapping(dataApiEntity);
    }

    @Override
    public Document wordDataApi(String id) throws Exception {
        //注册编码提供程序
        DataApiEntity dataApiEntity = super.getById(id);
        // 合并模版
        ClassPathResource classPathResource = new ClassPathResource("templates/api_1.0.0.docx");
        InputStream inputStream = classPathResource.getInputStream();
        Document doc = WordUtil.getInstance().getDocument(inputStream);
        // 提供字段
        MD5Util mt = MD5Util.getInstance();
        String apiKey = mt.encode(id);
        String secretkey = mt.encode(SecurityUtil.getUserId());
        String[] fieldNames = new String[] {"apiName", "apiVersion", "reqMethod", "resType", "apiUrl", "remark", "apiKey", "secretkey","apiCode"};
        Object[] fieldValues = new Object[] {dataApiEntity.getApiName(), dataApiEntity.getApiVersion(), dataApiEntity.getReqMethod(), dataApiEntity.getResType(), "/services/"+dataApiEntity.getApiVersion()+dataApiEntity.getApiUrl(), dataApiEntity.getRemark(), apiKey, secretkey,dataApiEntity.getApiCode()};
        MailMerge mailMerge = doc.getMailMerge();
        mailMerge.execute(fieldNames, fieldValues);
        // 请求参数 TableStart:ReqParamList TableEnd:ReqParamList
        DataTable reqParamTable = new DataTable("ReqParamList");
        reqParamTable.getColumns().add("paramName");
        reqParamTable.getColumns().add("paramComment");
        reqParamTable.getColumns().add("paramType");
        reqParamTable.getColumns().add("nullable");
        reqParamTable.getColumns().add("exampleValue");
        List<ReqParam> reqParamList = objectMapper.convertValue(dataApiEntity.getReqParams(), new TypeReference<List<ReqParam>>() {});
        ReqParam reqparam = new ReqParam();
        reqparam.setParamName("apiCode");
        reqparam.setParamComment("接口key");
        reqparam.setParamType("String");
        reqparam.setNullable("N");
        reqparam.setExampleValue(dataApiEntity.getApiCode());
        reqParamList.add(reqparam);
        for (int i = 0; i < reqParamList.size(); i++) {
            DataRow row = reqParamTable.newRow();
            ReqParam param = reqParamList.get(i);
            row.set(0, param.getParamName());
            row.set(1, param.getParamComment());
            row.set(2, param.getParamType());
            row.set(3, "1".equals(param.getNullable()) ? "Y" : "N");
            row.set(4, param.getExampleValue());
            reqParamTable.getRows().add(row);
        }
        mailMerge.executeWithRegions(reqParamTable);
        // 返回字段 TableStart:ResParamList TableEnd:ResParamList
        DataTable resParamTable = new DataTable("ResParamList");
        resParamTable.getColumns().add("fieldName");
        resParamTable.getColumns().add("dataType");
        resParamTable.getColumns().add("fieldComment");
        resParamTable.getColumns().add("exampleValue");
        List<ResParam> resParamList = objectMapper.convertValue(dataApiEntity.getResParams(), new TypeReference<List<ResParam>>() {});
        for (int i = 0; i < resParamList.size(); i++) {
            DataRow row = resParamTable.newRow();
            ResParam param = resParamList.get(i);
            row.set(0, param.getFieldName());
            row.set(1, param.getDataType());
            row.set(2, param.getFieldComment());
            row.set(3, param.getExampleValue());
            resParamTable.getRows().add(row);
        }
        mailMerge.executeWithRegions(resParamTable);
        // 返回示例 reqExample resExample
        ObjectNode objectNode = objectMapper.createObjectNode();
        for (int i = 0; i < resParamList.size(); i++) {
            ResParam param = resParamList.get(i);
            objectNode.put(param.getFieldName(), param.getExampleValue());
        }
        mailMerge.execute(new String[] {"reqExample", "resExample"}, new Object[] {objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(AjaxResult.success(new LinkedList<ObjectNode>(){{add(objectNode);}})), objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(AjaxResult.error("返回失败"))});
        WordUtil.getInstance().insertWatermarkText(doc, SecurityUtil.getUserName());
        return doc;
    }

    @Override
    public Map<String, Object> getDataApiDetailById(String id) {
        DataApiEntity dataApiEntity = super.getById(id);
        ApiHeader apiHeader = new ApiHeader();
        MD5Util mt = null;
        try {
            mt = MD5Util.getInstance();
            apiHeader.setApiKey(mt.encode(id));
            apiHeader.setSecretKey(mt.encode(SecurityUtil.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", dataApiMapper.toVO(dataApiEntity));
        map.put("header", apiHeader);
        return map;
    }

    @Override
    public List<DataApiEntity> getDataApiEntityList(String status) {
        return dataApiDao.getDataApiEntityList(status);
    }

    public static Map<String, String> parseFields(String sql) throws JSQLParserException {
        // Remove placeholders
        sql = sql.replaceAll("\\$\\{.*?\\}", "");

        Map<String, String> fields = new HashMap<>();
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plainSelect.getSelectItems();

        for (SelectItem selectItem : selectItems) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    String columnName = item.getExpression().toString().replaceAll("^[a-zA-Z]+\\.", "");
                    String alias = item.getAlias() != null ? item.getAlias().getName().replaceAll("^[a-zA-Z]+\\.", "") : columnName;
                    fields.put(columnName, alias);
                }
            });
        }
        return fields;
    }
}
