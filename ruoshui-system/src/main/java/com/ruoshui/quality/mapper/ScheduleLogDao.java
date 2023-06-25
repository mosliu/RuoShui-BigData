package com.ruoshui.quality.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.quality.entity.ScheduleLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据质量监控任务日志信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-10-13
 */
@Mapper
public interface ScheduleLogDao extends BaseDao<ScheduleLogEntity> {

    @Override
    <E extends IPage<ScheduleLogEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<ScheduleLogEntity> queryWrapper);
}
