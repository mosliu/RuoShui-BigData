package com.ruoshui.market.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.market.entity.DataApiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据API信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-03-31
 */
@Mapper
public interface DataApiDao extends BaseDao<DataApiEntity> {

    @Override
    DataApiEntity selectById(Serializable id);

    @Override
    List<DataApiEntity> selectList(@Param(Constants.WRAPPER) Wrapper<DataApiEntity> queryWrapper);

    @Override
    <E extends IPage<DataApiEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<DataApiEntity> queryWrapper);

    List<DataApiEntity> getDataApiEntityList(String status);

}
