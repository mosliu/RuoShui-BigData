package com.ruoshui.bigdata.tool.datax;

import com.ruoshui.bigdata.tool.pojo.DataxHbasePojo;
import com.ruoshui.bigdata.tool.pojo.DataxHivePojo;
import com.ruoshui.bigdata.tool.pojo.DataxMongoDBPojo;
import com.ruoshui.bigdata.tool.pojo.DataxRdbmsPojo;


import java.util.Map;

/**
 * 插件基础接口
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName DataxPluginInterface
 * @Version 1.0
 * @since 2019/7/30 22:59
 */
public interface DataxPluginInterface {
    /**
     * 获取reader插件名称
     *
     * @return
     */
    String getName();

    /**
     * 构建
     *
     * @return dataxPluginPojo
     */
    Map<String, Object> build(DataxRdbmsPojo dataxPluginPojo);


    /**
     * hive json构建
     * @param dataxHivePojo
     * @return
     */
    Map<String, Object> buildHive(DataxHivePojo dataxHivePojo);

    /**
     * hbase json构建
     * @param dataxHbasePojo
     * @return
     */
    Map<String, Object> buildHbase(DataxHbasePojo dataxHbasePojo);

    /**
     * mongodb json构建
     * @param dataxMongoDBPojo
     * @return
     */
    Map<String,Object> buildMongoDB(DataxMongoDBPojo dataxMongoDBPojo);

    /**
     * 获取示例
     *
     * @return
     */
    Map<String, Object> sample();
}
