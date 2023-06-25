package com.ruoshui.bigdata.tool.pojo;

import com.ruoshui.bigdata.dto.UpsertInfo;
import com.ruoshui.bigdata.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用于传参，构建json
 *
 * @author jingwk
 * @ClassName FlinkxMongoDBPojo
 * @Version 2.0
 * @since 2022/03/14 11:15
 */
@Data
public class FlinkxMongoDBPojo {

    /**
     * hive列名
     */
    private List<Map<String, Object>> columns;

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    private String address;

    private String dbName;

    private String readerTable;

    private String writerTable;

    private UpsertInfo upsertInfo;

}
