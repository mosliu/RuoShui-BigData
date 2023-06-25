package com.ruoshui.quality.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.quality.entity.CheckRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 核查规则信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper
public interface CheckRuleDao extends BaseDao<CheckRuleEntity> {

    @Override
    CheckRuleEntity selectById(Serializable id);

    @Override
    List<CheckRuleEntity> selectList(@Param(Constants.WRAPPER) Wrapper<CheckRuleEntity> queryWrapper);

    @Override
    <E extends IPage<CheckRuleEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<CheckRuleEntity> queryWrapper);
}
