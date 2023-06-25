package com.ruoshui.quality.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.entity.DataReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 核查报告信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper
public interface CheckReportDao extends BaseDao<CheckReportEntity> {

    @Override
    <E extends IPage<CheckReportEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<CheckReportEntity> queryWrapper);

    List<DataReportEntity> getReportBySource(@Param("checkDate") String checkDate);

    List<DataReportEntity> getReportByType(@Param("checkDate") String checkDate);

    List<DataReportEntity> getReportDetail(@Param("checkDate") String checkDate);
}
