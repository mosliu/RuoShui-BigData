package com.ruoshui.bigdata.service;


import com.ruoshui.bigdata.dto.DataXBatchJsonBuildDto;
import com.ruoshui.bigdata.dto.TaskScheduleDto;
import com.ruoshui.bigdata.entity.JobInfo;
import com.ruoshui.core.biz.model.ReturnT;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * core job action for ruoshui-ground
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface JobService {

    /**
     * page list
     *
     * @param start
     * @param length
     * @param jobGroup
     * @param jobDesc
     * @param glueType
     * @param userId
     * @return
     */
    Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String glueType, int userId,Integer[] projectIds);

    List<JobInfo> list();

    /**
     * add job
     *
     * @param jobInfo
     * @return
     */
    ReturnT<String> add(JobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo
     * @return
     */
    ReturnT<String> update(JobInfo jobInfo);

    /**
     * remove job
     * *
     *
     * @param id
     * @return
     */
    ReturnT<String> remove(int id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    ReturnT<String> start(int id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    ReturnT<String> stop(int id);

    /**
     * dashboard info
     *
     * @return
     */
    Map<String, Object> dashboardInfo();

    /**
     * chart info
     *
     * @return
     */
    ReturnT<Map<String, Object>> chartInfo();

    /**
     * batch add
     * @param dto
     * @return
     */
    ReturnT<String> batchAdd(DataXBatchJsonBuildDto dto) throws IOException;
}
