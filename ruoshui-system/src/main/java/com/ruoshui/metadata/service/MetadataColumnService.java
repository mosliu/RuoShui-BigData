package com.ruoshui.metadata.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.metadata.dto.MetadataColumnDto;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.query.MetadataColumnQuery;
import com.ruoshui.metadata.vo.MetadataTreeVo;

import java.util.List;

/**
 * <p>
 * 元数据信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
public interface MetadataColumnService extends BaseService<MetadataColumnEntity> {

    MetadataColumnEntity saveMetadataColumn(MetadataColumnDto metadataColumn);

    MetadataColumnEntity updateMetadataColumn(MetadataColumnDto metadataColumn);

    MetadataColumnEntity getMetadataColumnById(String id);

    void deleteMetadataColumnById(String id);

    void deleteMetadataColumnBatch(List<String> ids);

    List<MetadataTreeVo> getDataMetadataTree(String level, MetadataColumnQuery metadataColumnQuery);

    List<MetadataColumnEntity> getDataMetadataColumnList(MetadataColumnQuery metadataColumnQuery);

    <E extends IPage<MetadataColumnEntity>> E pageWithAuth(E page, Wrapper<MetadataColumnEntity> queryWrapper);
}
