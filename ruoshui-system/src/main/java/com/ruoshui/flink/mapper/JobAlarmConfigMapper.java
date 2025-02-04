package com.ruoshui.flink.mapper;

import com.ruoshui.flink.streaming.web.model.entity.JobAlarmConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAlarmConfigMapper {

    int insertBatch(@Param("list") List<JobAlarmConfig> list);

    List<JobAlarmConfig> selectByJobId(@Param("jobId")Long jobId);

    List<JobAlarmConfig> selectByJobIdList(@Param("jobIdList")List<Long> jobIdList);

    int deleteByJobId(@Param("jobId")Long jobId);


}
