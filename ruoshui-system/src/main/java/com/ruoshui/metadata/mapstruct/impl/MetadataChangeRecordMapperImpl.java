package com.ruoshui.metadata.mapstruct.impl;


import com.ruoshui.metadata.dto.MetadataChangeRecordDto;
import com.ruoshui.metadata.entity.MetadataChangeRecordEntity;
import com.ruoshui.metadata.mapstruct.MetadataChangeRecordMapper;
import com.ruoshui.metadata.vo.MetadataChangeRecordVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MetadataChangeRecordMapperImpl implements MetadataChangeRecordMapper {

    @Override
    public MetadataChangeRecordDto toDTO(MetadataChangeRecordEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataChangeRecordDto metadataChangeRecordDto = new MetadataChangeRecordDto();

        metadataChangeRecordDto.setId( e.getId() );
        metadataChangeRecordDto.setVersion( e.getVersion() );
        metadataChangeRecordDto.setObjectType( e.getObjectType() );
        metadataChangeRecordDto.setObjectId( e.getObjectId() );
        metadataChangeRecordDto.setFieldName( e.getFieldName() );
        metadataChangeRecordDto.setFieldOldValue( e.getFieldOldValue() );
        metadataChangeRecordDto.setFieldNewValue( e.getFieldNewValue() );
        metadataChangeRecordDto.setStatus( e.getStatus() );
        metadataChangeRecordDto.setRemark( e.getRemark() );

        return metadataChangeRecordDto;
    }

    @Override
    public List<MetadataChangeRecordDto> toDTO(List<MetadataChangeRecordEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataChangeRecordDto> list = new ArrayList<MetadataChangeRecordDto>( es.size() );
        for ( MetadataChangeRecordEntity metadataChangeRecordEntity : es ) {
            list.add( toDTO( metadataChangeRecordEntity ) );
        }

        return list;
    }

    @Override
    public MetadataChangeRecordVo toVO(MetadataChangeRecordEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataChangeRecordVo metadataChangeRecordVo = new MetadataChangeRecordVo();

        metadataChangeRecordVo.setId( e.getId() );
        metadataChangeRecordVo.setStatus( e.getStatus() );
        metadataChangeRecordVo.setCreateTime( e.getCreateTime() );
        metadataChangeRecordVo.setVersion( e.getVersion() );
        metadataChangeRecordVo.setObjectType( e.getObjectType() );
        metadataChangeRecordVo.setObjectId( e.getObjectId() );
        metadataChangeRecordVo.setFieldName( e.getFieldName() );
        metadataChangeRecordVo.setFieldOldValue( e.getFieldOldValue() );
        metadataChangeRecordVo.setFieldNewValue( e.getFieldNewValue() );
        metadataChangeRecordVo.setSourceId( e.getSourceId() );
        metadataChangeRecordVo.setSourceName( e.getSourceName() );
        metadataChangeRecordVo.setTableId( e.getTableId() );
        metadataChangeRecordVo.setTableName( e.getTableName() );

        return metadataChangeRecordVo;
    }

    @Override
    public List<MetadataChangeRecordVo> toVO(List<MetadataChangeRecordEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataChangeRecordVo> list = new ArrayList<MetadataChangeRecordVo>( es.size() );
        for ( MetadataChangeRecordEntity metadataChangeRecordEntity : es ) {
            list.add( toVO( metadataChangeRecordEntity ) );
        }

        return list;
    }

    @Override
    public MetadataChangeRecordEntity toEntity(MetadataChangeRecordDto d) {
        if ( d == null ) {
            return null;
        }

        MetadataChangeRecordEntity metadataChangeRecordEntity = new MetadataChangeRecordEntity();

        metadataChangeRecordEntity.setId( d.getId() );
        metadataChangeRecordEntity.setStatus( d.getStatus() );
        metadataChangeRecordEntity.setRemark( d.getRemark() );
        metadataChangeRecordEntity.setVersion( d.getVersion() );
        metadataChangeRecordEntity.setObjectType( d.getObjectType() );
        metadataChangeRecordEntity.setObjectId( d.getObjectId() );
        metadataChangeRecordEntity.setFieldName( d.getFieldName() );
        metadataChangeRecordEntity.setFieldOldValue( d.getFieldOldValue() );
        metadataChangeRecordEntity.setFieldNewValue( d.getFieldNewValue() );

        return metadataChangeRecordEntity;
    }

    @Override
    public List<MetadataChangeRecordEntity> toEntity(List<MetadataChangeRecordDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<MetadataChangeRecordEntity> list = new ArrayList<MetadataChangeRecordEntity>( ds.size() );
        for ( MetadataChangeRecordDto metadataChangeRecordDto : ds ) {
            list.add( toEntity( metadataChangeRecordDto ) );
        }

        return list;
    }
}
