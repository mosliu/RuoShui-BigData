package com.ruoshui.metadata.mapstruct.impl;


import com.ruoshui.metadata.dto.MetadataColumnDto;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.service.MetadataColumnMapper;
import com.ruoshui.metadata.vo.MetadataColumnVo;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class MetadataColumnMapperImpl implements MetadataColumnMapper {

    @Override
    public MetadataColumnDto toDTO(MetadataColumnEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataColumnDto metadataColumnDto = new MetadataColumnDto();

        metadataColumnDto.setId( e.getId() );
        metadataColumnDto.setSourceId( e.getSourceId() );
        metadataColumnDto.setTableId( e.getTableId() );
        metadataColumnDto.setColumnName( e.getColumnName() );
        metadataColumnDto.setColumnComment( e.getColumnComment() );
        metadataColumnDto.setColumnKey( e.getColumnKey() );
        metadataColumnDto.setColumnNullable( e.getColumnNullable() );
        metadataColumnDto.setColumnPosition( e.getColumnPosition() );
        metadataColumnDto.setDataType( e.getDataType() );
        metadataColumnDto.setDataLength( e.getDataLength() );
        metadataColumnDto.setDataPrecision( e.getDataPrecision() );
        metadataColumnDto.setDataScale( e.getDataScale() );
        metadataColumnDto.setDataDefault( e.getDataDefault() );

        return metadataColumnDto;
    }

    @Override
    public List<MetadataColumnDto> toDTO(List<MetadataColumnEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataColumnDto> list = new ArrayList<MetadataColumnDto>( es.size() );
        for ( MetadataColumnEntity metadataColumnEntity : es ) {
            list.add( toDTO( metadataColumnEntity ) );
        }

        return list;
    }

    @Override
    public MetadataColumnVo toVO(MetadataColumnEntity e) {
        if ( e == null ) {
            return null;
        }

        MetadataColumnVo metadataColumnVo = new MetadataColumnVo();

        metadataColumnVo.setId( e.getId() );
        metadataColumnVo.setSourceId( e.getSourceId() );
        metadataColumnVo.setTableId( e.getTableId() );
        metadataColumnVo.setColumnName( e.getColumnName() );
        metadataColumnVo.setColumnComment( e.getColumnComment() );
        metadataColumnVo.setColumnKey( e.getColumnKey() );
        metadataColumnVo.setColumnNullable( e.getColumnNullable() );
        metadataColumnVo.setColumnPosition( e.getColumnPosition() );
        metadataColumnVo.setDataType( e.getDataType() );
        metadataColumnVo.setDataLength( e.getDataLength() );
        metadataColumnVo.setDataPrecision( e.getDataPrecision() );
        metadataColumnVo.setDataScale( e.getDataScale() );
        metadataColumnVo.setDataDefault( e.getDataDefault() );
        metadataColumnVo.setSourceName( e.getSourceName() );
        metadataColumnVo.setTableName( e.getTableName() );
        metadataColumnVo.setTableComment( e.getTableComment() );

        return metadataColumnVo;
    }

    @Override
    public List<MetadataColumnVo> toVO(List<MetadataColumnEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<MetadataColumnVo> list = new ArrayList<MetadataColumnVo>( es.size() );
        for ( MetadataColumnEntity metadataColumnEntity : es ) {
            list.add( toVO( metadataColumnEntity ) );
        }

        return list;
    }

    @Override
    public MetadataColumnEntity toEntity(MetadataColumnDto d) {
        if ( d == null ) {
            return null;
        }

        MetadataColumnEntity metadataColumnEntity = new MetadataColumnEntity();

        metadataColumnEntity.setId( d.getId() );
        metadataColumnEntity.setSourceId( d.getSourceId() );
        metadataColumnEntity.setTableId( d.getTableId() );
        metadataColumnEntity.setColumnName( d.getColumnName() );
        metadataColumnEntity.setColumnComment( d.getColumnComment() );
        metadataColumnEntity.setColumnKey( d.getColumnKey() );
        metadataColumnEntity.setColumnNullable( d.getColumnNullable() );
        metadataColumnEntity.setColumnPosition( d.getColumnPosition() );
        metadataColumnEntity.setDataType( d.getDataType() );
        metadataColumnEntity.setDataLength( d.getDataLength() );
        metadataColumnEntity.setDataPrecision( d.getDataPrecision() );
        metadataColumnEntity.setDataScale( d.getDataScale() );
        metadataColumnEntity.setDataDefault( d.getDataDefault() );

        return metadataColumnEntity;
    }

    @Override
    public List<MetadataColumnEntity> toEntity(List<MetadataColumnDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<MetadataColumnEntity> list = new ArrayList<MetadataColumnEntity>( ds.size() );
        for ( MetadataColumnDto metadataColumnDto : ds ) {
            list.add( toEntity( metadataColumnDto ) );
        }

        return list;
    }
}
