package com.ruoshui.market.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.market.entity.ApiMaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 * 数据API脱敏信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
@Mapper
public interface ApiMaskDao extends BaseDao<ApiMaskEntity> {

    @Override
    ApiMaskEntity selectById(Serializable id);

    @Override
    <E extends IPage<ApiMaskEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<ApiMaskEntity> queryWrapper);
}
