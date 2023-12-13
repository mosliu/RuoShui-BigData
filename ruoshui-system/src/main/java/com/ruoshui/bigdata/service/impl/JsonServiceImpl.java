package com.ruoshui.bigdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoshui.bigdata.dto.JsonBuildDto;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.service.JsonService;
import com.ruoshui.bigdata.service.JobDatasourceService;
import com.ruoshui.bigdata.tool.flinkx.DataxJsonHelper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @Author: RuoShui
 * @Date: 2022/9/16 11:14
 * @Description:  JSON构建实现类
 **/
@Service
public class JsonServiceImpl implements JsonService {

    @Resource
    private JobDatasourceService jobJdbcDatasourceService;

    @Override
    public String buildJobDataxJson(JsonBuildDto dataXJsonBuildDto) {
        DataxJsonHelper dataxJsonHelper = new DataxJsonHelper();
        // reader
        JobDatasource readerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getReaderDatasourceId());
        dataxJsonHelper.initReader(dataXJsonBuildDto, readerDatasource);
        // writer
        JobDatasource writerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getWriterDatasourceId());
        dataxJsonHelper.initWriter(dataXJsonBuildDto, writerDatasource);

        return JSON.toJSONString(dataxJsonHelper.buildJob());
    }
}
