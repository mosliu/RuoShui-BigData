package com.ruoshui.metadata.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.databse.base.BaseDao;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据源信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Mapper
public interface MetadataSourceDao extends BaseDao<MetadataSourceEntity> {

    @Override
    MetadataSourceEntity selectById(Serializable id);

    @Override
    List<MetadataSourceEntity> selectList(@Param(Constants.WRAPPER) Wrapper<MetadataSourceEntity> queryWrapper);

    <E extends IPage<MetadataSourceEntity>> E selectPageWithAuth(E page, @Param(Constants.WRAPPER) Wrapper<MetadataSourceEntity> queryWrapper, @Param("roles") List<String> roles);
}
