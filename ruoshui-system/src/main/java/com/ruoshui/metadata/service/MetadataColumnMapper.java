package com.ruoshui.metadata.service;


import com.ruoshui.metadata.dto.MetadataColumnDto;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.metadata.vo.MetadataColumnVo;


/**
 * <p>
 * 元数据信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
public interface MetadataColumnMapper extends EntityMapper<MetadataColumnDto, MetadataColumnEntity, MetadataColumnVo> {

}
