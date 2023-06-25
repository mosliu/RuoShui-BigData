package com.ruoshui.quality.service;



import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.entity.DataReportEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核查报告信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
public interface CheckReportService extends BaseService<CheckReportEntity> {

    CheckReportEntity getCheckReportById(String id);

    /**
     * 按数据源统计
     * @return
     */
    List<DataReportEntity> getReportBySource(String checkDate);

    /**
     * 按规则类型统计
     * @return
     */
    List<DataReportEntity> getReportByType(String checkDate);

    Map<String, Object> getReportDetail(String checkDate);
}
