package com.ruoshui.common.database.datasource;


import com.ruoshui.common.database.service.DataSourceFactory;
import com.ruoshui.common.database.service.DbDialect;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.common.database.DialectFactory;
import com.ruoshui.common.database.constants.DbQueryProperty;
import com.ruoshui.common.database.constants.DbType;
import com.ruoshui.common.database.query.AbstractDbQueryFactory;
import com.ruoshui.common.database.query.CacheDbQueryFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Component
public  class AbstractDataSourceFactory implements DataSourceFactory {


    public DbQuery createDbQuery(DbQueryProperty property) {
        property.viald();
        DbType dbType = DbType.getDbType(property.getDbType());
        DataSource dataSource = createDataSource(property);
        DbQuery dbQuery = createDbQuery(dataSource, dbType);
        return dbQuery;
    }

    public static DbQuery createDbQuery(DataSource dataSource, DbType dbType) {
        DbDialect dbDialect = DialectFactory.getDialect(dbType);
        if(dbDialect == null){
            throw new RuntimeException("该数据库类型正在开发中");
        }
        AbstractDbQueryFactory dbQuery = new CacheDbQueryFactoryBean();
        dbQuery.setDataSource(dataSource);
        dbQuery.setJdbcTemplate(new JdbcTemplate(dataSource));
        dbQuery.setDbDialect(dbDialect);
        return dbQuery;
    }

    public  DataSource createDataSource(DbQueryProperty property) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(trainToJdbcUrl(property));
        dataSource.setUsername(property.getUsername());
        dataSource.setPassword(property.getPassword());
        return dataSource;
    }

    protected  String trainToJdbcUrl(DbQueryProperty property) {
        String url = DbType.getDbType(property.getDbType()).getUrl();
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("无效数据库类型!");
        }
        url = url.replace("${host}", property.getHost());
        url = url.replace("${port}", String.valueOf(property.getPort()));
        if (DbType.ORACLE.getDb().equals(property.getDbType()) || DbType.ORACLE_12C.getDb().equals(property.getDbType())) {
            url = url.replace("${sid}", property.getSid());
        } else {
            url = url.replace("${dbName}", property.getDbName());
        }
        return url;
    }

}
