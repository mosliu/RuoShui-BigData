package com.ruoshui.bigdata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoshui.bigdata.entity.JobProject;
import com.ruoshui.bigdata.mapper.JobProjectMapper;
import com.ruoshui.bigdata.service.JobProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JobProjectServiceImpl
 * @author jingwk
 * @since 2019-05-30
 * @version v2.1.2
 */
@Service("jobProjectService")
public class JobProjectServiceImpl extends ServiceImpl<JobProjectMapper, JobProject> implements JobProjectService {

    @Autowired
    private JobProjectMapper jobProjectMapper;

    @Override
    public IPage<JobProject> getProjectListPaging(Integer pageSize, Integer pageNo, String searchName) {
        Page<JobProject> page = new Page(pageNo, pageSize);
        return jobProjectMapper.getProjectListPaging(page, searchName);
    }
}