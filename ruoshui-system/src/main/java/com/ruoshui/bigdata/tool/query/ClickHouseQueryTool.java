package com.ruoshui.bigdata.tool.query;

import com.ruoshui.bigdata.entity.JobDatasource;

import java.sql.SQLException;

/**
 * ClickHouse
 */

public class ClickHouseQueryTool extends BaseQueryTool implements QueryToolInterface {
    /**
     * 构造方法
     *
     * @param jobJdbcDatasource
     */
  public ClickHouseQueryTool(JobDatasource jobJdbcDatasource) throws SQLException {
        super(jobJdbcDatasource);
    }
}
