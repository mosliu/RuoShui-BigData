package com.ruoshui.standard.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.standard.entity.ContrastDictEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典对照信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper
public interface ContrastDictDao extends BaseDao<ContrastDictEntity> {

    @Override
    List<ContrastDictEntity> selectList(@Param(Constants.WRAPPER) Wrapper<ContrastDictEntity> queryWrapper);

    @Override
    <E extends IPage<ContrastDictEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<ContrastDictEntity> queryWrapper);
}
