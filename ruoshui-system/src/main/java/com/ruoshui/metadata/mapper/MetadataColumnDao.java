package com.ruoshui.metadata.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.databse.base.BaseDao;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 元数据信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper
public interface MetadataColumnDao extends BaseDao<MetadataColumnEntity>, com.ruoshui.core.database.base.BaseDao<MetadataColumnEntity> {

    <E extends IPage<MetadataColumnEntity>> E selectPageWithAuth(E page, @Param(Constants.WRAPPER) Wrapper<MetadataColumnEntity> queryWrapper, @Param("roles") List<String> roles);
}