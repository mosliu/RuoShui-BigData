package com.ruoshui.standard.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.standard.entity.ContrastEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 * 对照表信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper
public interface ContrastDao extends BaseDao<ContrastEntity> {

    @Override
    ContrastEntity selectById(Serializable id);

    IPage<ContrastEntity> statistic(IPage<ContrastEntity> page, @Param(Constants.WRAPPER) Wrapper<ContrastEntity> queryWrapper);
}
