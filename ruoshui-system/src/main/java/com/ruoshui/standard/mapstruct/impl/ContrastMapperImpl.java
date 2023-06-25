package com.ruoshui.standard.mapstruct.impl;


import com.ruoshui.standard.dto.ContrastDto;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.mapstruct.ContrastMapper;
import com.ruoshui.standard.vo.ContrastStatisticVo;
import com.ruoshui.standard.vo.ContrastVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-27T15:15:10+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class ContrastMapperImpl implements ContrastMapper {

    @Override
    public ContrastDto toDTO(ContrastEntity e) {
        if ( e == null ) {
            return null;
        }

        ContrastDto contrastDto = new ContrastDto();

        contrastDto.setId( e.getId() );
        contrastDto.setSourceId( e.getSourceId() );
        contrastDto.setSourceName( e.getSourceName() );
        contrastDto.setTableId( e.getTableId() );
        contrastDto.setTableName( e.getTableName() );
        contrastDto.setTableComment( e.getTableComment() );
        contrastDto.setColumnId( e.getColumnId() );
        contrastDto.setColumnName( e.getColumnName() );
        contrastDto.setColumnComment( e.getColumnComment() );
        contrastDto.setGbTypeId( e.getGbTypeId() );
        contrastDto.setBindGbColumn( e.getBindGbColumn() );

        return contrastDto;
    }

    @Override
    public List<ContrastDto> toDTO(List<ContrastEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ContrastDto> list = new ArrayList<ContrastDto>( es.size() );
        for ( ContrastEntity contrastEntity : es ) {
            list.add( toDTO( contrastEntity ) );
        }

        return list;
    }

    @Override
    public ContrastVo toVO(ContrastEntity e) {
        if ( e == null ) {
            return null;
        }

        ContrastVo contrastVo = new ContrastVo();

        contrastVo.setId( e.getId() );
        contrastVo.setStatus( e.getStatus() );
        contrastVo.setCreateTime( e.getCreateTime() );
        contrastVo.setRemark( e.getRemark() );
        contrastVo.setSourceId( e.getSourceId() );
        contrastVo.setSourceName( e.getSourceName() );
        contrastVo.setTableId( e.getTableId() );
        contrastVo.setTableName( e.getTableName() );
        contrastVo.setTableComment( e.getTableComment() );
        contrastVo.setColumnId( e.getColumnId() );
        contrastVo.setColumnName( e.getColumnName() );
        contrastVo.setColumnComment( e.getColumnComment() );
        contrastVo.setGbTypeId( e.getGbTypeId() );
        contrastVo.setGbTypeCode( e.getGbTypeCode() );
        contrastVo.setGbTypeName( e.getGbTypeName() );
        contrastVo.setBindGbColumn( e.getBindGbColumn() );

        return contrastVo;
    }

    @Override
    public List<ContrastVo> toVO(List<ContrastEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ContrastVo> list = new ArrayList<ContrastVo>( es.size() );
        for ( ContrastEntity contrastEntity : es ) {
            list.add( toVO( contrastEntity ) );
        }

        return list;
    }

    @Override
    public ContrastEntity toEntity(ContrastDto d) {
        if ( d == null ) {
            return null;
        }

        ContrastEntity contrastEntity = new ContrastEntity();

        contrastEntity.setId( d.getId() );
        contrastEntity.setSourceId( d.getSourceId() );
        contrastEntity.setSourceName( d.getSourceName() );
        contrastEntity.setTableId( d.getTableId() );
        contrastEntity.setTableName( d.getTableName() );
        contrastEntity.setTableComment( d.getTableComment() );
        contrastEntity.setColumnId( d.getColumnId() );
        contrastEntity.setColumnName( d.getColumnName() );
        contrastEntity.setColumnComment( d.getColumnComment() );
        contrastEntity.setGbTypeId( d.getGbTypeId() );
        contrastEntity.setBindGbColumn( d.getBindGbColumn() );

        return contrastEntity;
    }

    @Override
    public List<ContrastEntity> toEntity(List<ContrastDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<ContrastEntity> list = new ArrayList<ContrastEntity>( ds.size() );
        for ( ContrastDto contrastDto : ds ) {
            list.add( toEntity( contrastDto ) );
        }

        return list;
    }

    @Override
    public ContrastStatisticVo toSVO(ContrastEntity e) {
        if ( e == null ) {
            return null;
        }

        ContrastStatisticVo contrastStatisticVo = new ContrastStatisticVo();

        contrastStatisticVo.setId( e.getId() );
        contrastStatisticVo.setStatus( e.getStatus() );
        contrastStatisticVo.setCreateTime( e.getCreateTime() );
        contrastStatisticVo.setRemark( e.getRemark() );
        contrastStatisticVo.setSourceId( e.getSourceId() );
        contrastStatisticVo.setSourceName( e.getSourceName() );
        contrastStatisticVo.setTableId( e.getTableId() );
        contrastStatisticVo.setTableName( e.getTableName() );
        contrastStatisticVo.setTableComment( e.getTableComment() );
        contrastStatisticVo.setColumnId( e.getColumnId() );
        contrastStatisticVo.setColumnName( e.getColumnName() );
        contrastStatisticVo.setColumnComment( e.getColumnComment() );
        contrastStatisticVo.setGbTypeId( e.getGbTypeId() );
        contrastStatisticVo.setGbTypeCode( e.getGbTypeCode() );
        contrastStatisticVo.setGbTypeName( e.getGbTypeName() );
        contrastStatisticVo.setBindGbColumn( e.getBindGbColumn() );
        contrastStatisticVo.setMappingCount( e.getMappingCount() );
        contrastStatisticVo.setUnMappingCount( e.getUnMappingCount() );

        return contrastStatisticVo;
    }

    @Override
    public List<ContrastStatisticVo> toSVO(List<ContrastEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ContrastStatisticVo> list = new ArrayList<ContrastStatisticVo>( es.size() );
        for ( ContrastEntity contrastEntity : es ) {
            list.add( toSVO( contrastEntity ) );
        }

        return list;
    }
}
