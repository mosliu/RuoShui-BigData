package com.ruoshui.market.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.market.entity.ApiLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

@Mapper
public interface ApiLogDao extends BaseDao<ApiLogEntity> {

    @Override
    ApiLogEntity selectById(Serializable id);

    @Override
    <E extends IPage<ApiLogEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<ApiLogEntity> queryWrapper);
}
