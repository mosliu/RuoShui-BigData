package com.ruoshui.metadata.mapstruct;

import com.ruoshui.metadata.dto.MetadataTableDto;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.vo.MetadataTableVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据库表信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper(componentModel = "spring")
public interface MetadataTableMapper extends EntityMapper<MetadataTableDto, MetadataTableEntity, MetadataTableVo> {

}
