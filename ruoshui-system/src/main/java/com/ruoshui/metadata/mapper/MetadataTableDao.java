package com.ruoshui.metadata.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据库表信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper
public interface MetadataTableDao extends BaseDao<MetadataTableEntity> {

    <E extends IPage<MetadataTableEntity>> E selectPageWithAuth(E page, @Param(Constants.WRAPPER) Wrapper<MetadataTableEntity> queryWrapper, @Param("roles") List<String> roles);


    void deleteBysourceId(@Param("sourceId") String sourceId,@Param("tableName") String tableName);

    List<String> selectTableBySourceId(@Param("sourceId") String sourceId);

    Integer countByTableName(@Param("tableName") String tableName,@Param("sourceId") String sourceId);

    String selectIdByTableName(@Param("tableName") String tableName,@Param("sourceId") String sourceId);
}
