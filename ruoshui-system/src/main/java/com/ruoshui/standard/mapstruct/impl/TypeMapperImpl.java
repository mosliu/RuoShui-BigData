package com.ruoshui.standard.mapstruct.impl;

import com.ruoshui.standard.dto.TypeDto;
import com.ruoshui.standard.entity.TypeEntity;
import com.ruoshui.standard.mapstruct.TypeMapper;
import com.ruoshui.standard.vo.TypeVo;
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
public class TypeMapperImpl implements TypeMapper {

    @Override
    public TypeDto toDTO(TypeEntity e) {
        if ( e == null ) {
            return null;
        }

        TypeDto typeDto = new TypeDto();

        typeDto.setId( e.getId() );
        typeDto.setGbTypeCode( e.getGbTypeCode() );
        typeDto.setGbTypeName( e.getGbTypeName() );

        return typeDto;
    }

    @Override
    public List<TypeDto> toDTO(List<TypeEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<TypeDto> list = new ArrayList<TypeDto>( es.size() );
        for ( TypeEntity typeEntity : es ) {
            list.add( toDTO( typeEntity ) );
        }

        return list;
    }

    @Override
    public TypeVo toVO(TypeEntity e) {
        if ( e == null ) {
            return null;
        }

        TypeVo typeVo = new TypeVo();

        typeVo.setId( e.getId() );
        typeVo.setStatus( e.getStatus() );
        typeVo.setCreateTime( e.getCreateTime() );
        typeVo.setRemark( e.getRemark() );
        typeVo.setGbTypeCode( e.getGbTypeCode() );
        typeVo.setGbTypeName( e.getGbTypeName() );

        return typeVo;
    }

    @Override
    public List<TypeVo> toVO(List<TypeEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<TypeVo> list = new ArrayList<TypeVo>( es.size() );
        for ( TypeEntity typeEntity : es ) {
            list.add( toVO( typeEntity ) );
        }

        return list;
    }

    @Override
    public TypeEntity toEntity(TypeDto d) {
        if ( d == null ) {
            return null;
        }

        TypeEntity typeEntity = new TypeEntity();

        typeEntity.setId( d.getId() );
        typeEntity.setGbTypeCode( d.getGbTypeCode() );
        typeEntity.setGbTypeName( d.getGbTypeName() );

        return typeEntity;
    }

    @Override
    public List<TypeEntity> toEntity(List<TypeDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<TypeEntity> list = new ArrayList<TypeEntity>( ds.size() );
        for ( TypeDto typeDto : ds ) {
            list.add( toEntity( typeDto ) );
        }

        return list;
    }
}
