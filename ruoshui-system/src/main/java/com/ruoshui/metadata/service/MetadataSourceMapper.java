package com.ruoshui.metadata.service;


import com.ruoshui.metadata.dto.MetadataSourceDto;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.metadata.vo.MetadataSourceVo;

/**
 * <p>
 * 数据源信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
public interface MetadataSourceMapper extends EntityMapper<MetadataSourceDto, MetadataSourceEntity, MetadataSourceVo> {

}
