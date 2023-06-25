package com.ruoshui.standard.mapstruct.impl;


import com.ruoshui.standard.dto.DictDto;
import com.ruoshui.standard.entity.DictEntity;
import com.ruoshui.standard.mapstruct.DictMapper;
import com.ruoshui.standard.vo.DictVo;
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
public class DictMapperImpl implements DictMapper {

    @Override
    public DictDto toDTO(DictEntity e) {
        if ( e == null ) {
            return null;
        }

        DictDto dictDto = new DictDto();

        dictDto.setId( e.getId() );
        dictDto.setTypeId( e.getTypeId() );
        dictDto.setGbCode( e.getGbCode() );
        dictDto.setGbName( e.getGbName() );
        dictDto.setStatus( e.getStatus() );
        dictDto.setRemark( e.getRemark() );

        return dictDto;
    }

    @Override
    public List<DictDto> toDTO(List<DictEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<DictDto> list = new ArrayList<DictDto>( es.size() );
        for ( DictEntity dictEntity : es ) {
            list.add( toDTO( dictEntity ) );
        }

        return list;
    }

    @Override
    public DictVo toVO(DictEntity e) {
        if ( e == null ) {
            return null;
        }

        DictVo dictVo = new DictVo();

        dictVo.setId( e.getId() );
        dictVo.setStatus( e.getStatus() );
        dictVo.setCreateTime( e.getCreateTime() );
        dictVo.setRemark( e.getRemark() );
        dictVo.setTypeId( e.getTypeId() );
        dictVo.setGbTypeCode( e.getGbTypeCode() );
        dictVo.setGbTypeName( e.getGbTypeName() );
        dictVo.setGbCode( e.getGbCode() );
        dictVo.setGbName( e.getGbName() );

        return dictVo;
    }

    @Override
    public List<DictVo> toVO(List<DictEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<DictVo> list = new ArrayList<DictVo>( es.size() );
        for ( DictEntity dictEntity : es ) {
            list.add( toVO( dictEntity ) );
        }

        return list;
    }

    @Override
    public DictEntity toEntity(DictDto d) {
        if ( d == null ) {
            return null;
        }

        DictEntity dictEntity = new DictEntity();

        dictEntity.setId( d.getId() );
        dictEntity.setStatus( d.getStatus() );
        dictEntity.setRemark( d.getRemark() );
        dictEntity.setTypeId( d.getTypeId() );
        dictEntity.setGbCode( d.getGbCode() );
        dictEntity.setGbName( d.getGbName() );

        return dictEntity;
    }

    @Override
    public List<DictEntity> toEntity(List<DictDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<DictEntity> list = new ArrayList<DictEntity>( ds.size() );
        for ( DictDto dictDto : ds ) {
            list.add( toEntity( dictDto ) );
        }

        return list;
    }
}
