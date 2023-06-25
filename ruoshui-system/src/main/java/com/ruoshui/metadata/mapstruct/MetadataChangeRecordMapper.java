package com.ruoshui.metadata.mapstruct;


import com.ruoshui.metadata.dto.MetadataChangeRecordDto;
import com.ruoshui.metadata.entity.MetadataChangeRecordEntity;
import com.ruoshui.metadata.vo.MetadataChangeRecordVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 元数据变更记录表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
@Mapper(componentModel = "spring")
public interface MetadataChangeRecordMapper extends EntityMapper<MetadataChangeRecordDto, MetadataChangeRecordEntity, MetadataChangeRecordVo> {

}
