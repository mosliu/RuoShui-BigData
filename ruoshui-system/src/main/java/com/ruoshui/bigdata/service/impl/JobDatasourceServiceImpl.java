package com.ruoshui.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoshui.bigdata.mapper.JobDatasourceMapper;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.service.JobDatasourceService;
import com.ruoshui.bigdata.tool.query.BaseQueryTool;
import com.ruoshui.bigdata.tool.query.HBaseQueryTool;
import com.ruoshui.bigdata.tool.query.MongoDBQueryTool;
import com.ruoshui.bigdata.tool.query.QueryToolFactory;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.bigdata.util.JdbcConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.List;

/**
 * Created by jingwk on 2022/01/30
 */
@Service
@Transactional(readOnly = true)
public class JobDatasourceServiceImpl extends ServiceImpl<JobDatasourceMapper, JobDatasource> implements JobDatasourceService {

    @Autowired
    private JobDatasourceMapper datasourceMapper;

    @Override
    public Boolean  dataSourceTest(JobDatasource jobDatasource) throws IOException {
        if (JdbcConstants.HBASE.equals(jobDatasource.getDatasource())) {
            return new HBaseQueryTool(jobDatasource).dataSourceTest();
        }
        String userName = AESUtil.decrypt(jobDatasource.getJdbcUsername());
        //  判断账密是否为密文
        if (userName == null) {
            jobDatasource.setJdbcUsername(AESUtil.encrypt(jobDatasource.getJdbcUsername()));
        }
        String pwd = AESUtil.decrypt(jobDatasource.getJdbcPassword());
        if (pwd == null) {
            jobDatasource.setJdbcPassword(AESUtil.encrypt(jobDatasource.getJdbcPassword()));
        }
        if (JdbcConstants.MONGODB.equals(jobDatasource.getDatasource())) {
            return new MongoDBQueryTool(jobDatasource).dataSourceTest(jobDatasource.getDatabaseName());
        }
        BaseQueryTool queryTool = QueryToolFactory.getByDbType(jobDatasource);
        return queryTool.dataSourceTest();
    }

    @Override
    public int update(JobDatasource datasource) {
        return datasourceMapper.update(datasource);
    }

    @Override
    public List<JobDatasource> selectAllDatasource() {
        return datasourceMapper.selectList(null);
    }

    @Override
    public List<JobDatasource> findDataSourceName() {
        return datasourceMapper.findDataSourceName();
    }

    @Override
    public JobDatasource getDataSourceById(Long datasourceId) {
        return datasourceMapper.getDataSourceById(datasourceId);
    }
}