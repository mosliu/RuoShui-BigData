package com.ruoshui.bigdata.mapper;

import com.ruoshui.bigdata.entity.InfoReport;
import com.ruoshui.bigdata.entity.JobLogReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 *
 * @author xuxueli 2019-11-22
 */
@Mapper
public interface JobLogReportMapper {

    int save(JobLogReport xxlJobLogReport);

    int update(JobLogReport xxlJobLogReport);

    List<JobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                      @Param("triggerDayTo") Date triggerDayTo);


	List<InfoReport> getInfoReportCount();

    JobLogReport queryLogReportTotal();
}
