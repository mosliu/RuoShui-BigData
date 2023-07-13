package com.ruoshui.metadata.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.ruoshui.common.database.constants.DbQueryProperty;
import com.ruoshui.common.database.service.DataSourceFactory;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.common.utils.ThrowableUtil;
import com.ruoshui.metadata.concurrent.CallableTemplate;
import com.ruoshui.metadata.concurrent.DateHander;
import com.ruoshui.metadata.dto.DbSchema;
import com.ruoshui.metadata.dto.SqlConsoleDto;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.service.MetadataSourceService;
import com.ruoshui.metadata.service.SqlConsoleService;
import com.ruoshui.metadata.vo.SqlConsoleVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class SqlConsoleServiceImpl implements SqlConsoleService {

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private MetadataSourceService metadataSourceServiceFeign;

    private static Map<String, List<Connection>> connectionMap = new ConcurrentHashMap<>();

    @Override
    public List<SqlConsoleVo> sqlRun(SqlConsoleDto sqlConsoleDto) throws SQLException {
        String sqlKey = sqlConsoleDto.getSqlKey();
        Statements stmts;
        try {
            stmts = CCJSqlParserUtil.parseStatements(sqlConsoleDto.getSqlText());
        } catch (JSQLParserException e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
            throw new RuntimeException("SQL语法有问题，解析出错");
        }
        List<Statement> sqls = stmts.getStatements();
        if (CollUtil.isEmpty(sqls)) {
            throw new RuntimeException("未解析到SQL语句");
        }
        MetadataSourceEntity dataSource = metadataSourceServiceFeign.getMetadataSourceById(sqlConsoleDto.getSourceId());
        if(dataSource == null){
            throw new RuntimeException("SQL工作台查询数据源出错");
        }
        DbSchema dbSchema = dataSource.getDbSchema();
        DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
        DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
        // 定义计数器
        final CountDownLatch latch = new CountDownLatch(sqls.size());
        // 定义固定长度的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(sqls.size());
        // Callable用于产生结果
        List<CallableTemplate<SqlConsoleVo>> tasks = new ArrayList<>();
        List<Connection> conns = new ArrayList<>();
        for (int i = 0; i < sqls.size(); i++) {
            Connection conn = dbQuery.getConnection();
            conns.add(conn);
            DateHander dateHander = new DateHander(latch, conn, sqls.get(i).toString());
            tasks.add(dateHander);
        }
        connectionMap.put(sqlKey, conns);
        // Future用于获取结果
        List<SqlConsoleVo> result = new ArrayList<>();
        List<Future<SqlConsoleVo>> futures;
        try {
            futures = executorService.invokeAll(tasks);
            // 主线程阻塞，等待所有子线程执行完成
            latch.await();
            // 处理线程返回结果
            for (Future<SqlConsoleVo> future : futures) {
                result.add(future.get());
            }
        } catch (Exception e) {
            log.error("全局异常信息ex={}, StackTrace={}", e.getMessage(), ThrowableUtil.getStackTrace(e));
        }

        //关闭连接
        for(Connection conn : conns){
            if (null != conn && !conn.isClosed()) {
                conn.close();
            }
        }
        // 关闭线程池
        executorService.shutdown();
        // 执行完清除
        connectionMap.remove(sqlKey);
        return result;
    }

    @Override
    public void sqlStop(SqlConsoleDto sqlConsoleDto) {
        String sqlKey = sqlConsoleDto.getSqlKey();
        List<Connection> conns = connectionMap.get(sqlKey);
        if (CollUtil.isNotEmpty(conns)) {
            for (int i = 0; i < conns.size(); i++) {
                Connection conn = conns.get(i);
                try {
                    if (null != conn && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("SQL工作台停止出错");
                }
            }
        }
    }
}
