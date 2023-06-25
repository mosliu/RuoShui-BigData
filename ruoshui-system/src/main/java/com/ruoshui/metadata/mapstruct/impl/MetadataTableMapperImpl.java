package com.ruoshui.metadata.mapstruct.impl;


import com.ruoshui.metadata.dto.MetadataTableDto;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.mapstruct.MetadataTableMapper;
import com.ruoshui.metadata.vo.MetadataTableVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MetadataTableMapperImpl implements MetadataTableMapper {

    @Override
    public MetadataTableDto toDTO(MetadataTableEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataTableDto metadataTableDto = new MetadataTableDto();

        metadataTableDto.setId( e.getId() );
        metadataTableDto.setSourceId( e.getSourceId() );
        metadataTableDto.setTableName( e.getTableName() );
        metadataTableDto.setTableComment( e.getTableComment() );

        return metadataTableDto;
    }

    @Override
    public List<MetadataTableDto> toDTO(List<MetadataTableEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataTableDto> list = new ArrayList<MetadataTableDto>( es.size() );
        for ( MetadataTableEntity metadataTableEntity : es ) {
            list.add( toDTO( metadataTableEntity ) );
        }

        return list;
    }

    @Override
    public MetadataTableVo toVO(MetadataTableEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataTableVo metadataTableVo = new MetadataTableVo();

        metadataTableVo.setId( e.getId() );
        metadataTableVo.setSourceId( e.getSourceId() );
        metadataTableVo.setTableName( e.getTableName() );
        metadataTableVo.setTableComment( e.getTableComment() );
        metadataTableVo.setSourceName( e.getSourceName() );

        return metadataTableVo;
    }

    @Override
    public List<MetadataTableVo> toVO(List<MetadataTableEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataTableVo> list = new ArrayList<MetadataTableVo>( es.size() );
        for ( MetadataTableEntity metadataTableEntity : es ) {
            list.add( toVO( metadataTableEntity ) );
        }

        return list;
    }

    @Override
    public MetadataTableEntity toEntity(MetadataTableDto d) {
        if ( d == null ) {
            return null;
        }

        MetadataTableEntity metadataTableEntity = new MetadataTableEntity();

        metadataTableEntity.setId( d.getId() );
        metadataTableEntity.setSourceId( d.getSourceId() );
        metadataTableEntity.setTableName( d.getTableName() );
        metadataTableEntity.setTableComment( d.getTableComment() );

        return metadataTableEntity;
    }

    @Override
    public List<MetadataTableEntity> toEntity(List<MetadataTableDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<MetadataTableEntity> list = new ArrayList<MetadataTableEntity>( ds.size() );
        for ( MetadataTableDto metadataTableDto : ds ) {
            list.add( toEntity( metadataTableDto ) );
        }

        return list;
    }
}
