package com.ruoshui.bigdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoshui.bigdata.dto.DataXBatchJsonBuildDto;
import com.ruoshui.bigdata.dto.DataXJsonBuildDto;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.entity.JobInfo;
import com.ruoshui.bigdata.entity.JobTemplate;
import com.ruoshui.bigdata.mapper.JobInfoMapper;
import com.ruoshui.bigdata.mapper.JobTemplateMapper;
import com.ruoshui.bigdata.service.DatasourceQueryService;
import com.ruoshui.bigdata.service.DataxJsonService;
import com.ruoshui.bigdata.service.JobDatasourceService;
import com.ruoshui.bigdata.tool.datax.DataxJsonHelper;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.wugui.datax json构建实现类
 *
 * @author jingwk
 * @ClassName DataxJsonServiceImpl
 * @Version 2.0
 * @since 2022/01/11 17:15
 */
@Service
public class DataxJsonServiceImpl implements DataxJsonService {

    @Resource
    private JobDatasourceService jobJdbcDatasourceService;

    @Override
    public String buildJobJson(DataXJsonBuildDto dataXJsonBuildDto) {
        DataxJsonHelper dataxJsonHelper = new DataxJsonHelper();
        // reader
        JobDatasource readerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getReaderDatasourceId());
        // reader plugin init
        dataxJsonHelper.initReader(dataXJsonBuildDto, readerDatasource);
        JobDatasource writerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getWriterDatasourceId());
        dataxJsonHelper.initWriter(dataXJsonBuildDto, writerDatasource);

        return JSON.toJSONString(dataxJsonHelper.buildJob());
    }
}