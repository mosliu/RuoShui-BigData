package com.ruoshui.market.mapstruct.impl;


import com.ruoshui.market.dto.ApiMaskDto;
import com.ruoshui.market.dto.FieldRule;
import com.ruoshui.market.entity.ApiMaskEntity;
import com.ruoshui.market.mapstruct.ApiMaskMapper;
import com.ruoshui.market.vo.ApiMaskVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;


@Component
public class ApiMaskMapperImpl implements ApiMaskMapper {

    @Override
    public ApiMaskDto toDTO(ApiMaskEntity e) {
        if ( e == null ) {
            return null;
        }

        ApiMaskDto apiMaskDto = new ApiMaskDto();

        apiMaskDto.setId( e.getId() );
        apiMaskDto.setApiId( e.getApiId() );
        apiMaskDto.setMaskName( e.getMaskName() );
        List<FieldRule> list = e.getRules();
        if ( list != null ) {
            apiMaskDto.setRules( new ArrayList<FieldRule>( list ) );
        }
        apiMaskDto.setStatus( e.getStatus() );
        apiMaskDto.setRemark( e.getRemark() );

        return apiMaskDto;
    }

    @Override
    public List<ApiMaskDto> toDTO(List<ApiMaskEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ApiMaskDto> list = new ArrayList<ApiMaskDto>( es.size() );
        for ( ApiMaskEntity apiMaskEntity : es ) {
            list.add( toDTO( apiMaskEntity ) );
        }

        return list;
    }

    @Override
    public ApiMaskVo toVO(ApiMaskEntity e) {
        if ( e == null ) {
            return null;
        }

        ApiMaskVo apiMaskVo = new ApiMaskVo();

        apiMaskVo.setId( e.getId() );
        apiMaskVo.setStatus( e.getStatus() );
        apiMaskVo.setCreateTime( e.getCreateTime() );
        apiMaskVo.setRemark( e.getRemark() );
        apiMaskVo.setApiId( e.getApiId() );
        apiMaskVo.setMaskName( e.getMaskName() );
        List<FieldRule> list = e.getRules();
        if ( list != null ) {
            apiMaskVo.setRules( new ArrayList<FieldRule>( list ) );
        }

        return apiMaskVo;
    }

    @Override
    public List<ApiMaskVo> toVO(List<ApiMaskEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ApiMaskVo> list = new ArrayList<ApiMaskVo>( es.size() );
        for ( ApiMaskEntity apiMaskEntity : es ) {
            list.add( toVO( apiMaskEntity ) );
        }

        return list;
    }

    @Override
    public ApiMaskEntity toEntity(ApiMaskDto d) {
        if ( d == null ) {
            return null;
        }

        ApiMaskEntity apiMaskEntity = new ApiMaskEntity();

        apiMaskEntity.setId( d.getId() );
        apiMaskEntity.setStatus( d.getStatus() );
        apiMaskEntity.setRemark( d.getRemark() );
        apiMaskEntity.setApiId( d.getApiId() );
        apiMaskEntity.setMaskName( d.getMaskName() );
        List<FieldRule> list = d.getRules();
        if ( list != null ) {
            apiMaskEntity.setRules( new ArrayList<FieldRule>( list ) );
        }

        return apiMaskEntity;
    }

    @Override
    public List<ApiMaskEntity> toEntity(List<ApiMaskDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<ApiMaskEntity> list = new ArrayList<ApiMaskEntity>( ds.size() );
        for ( ApiMaskDto apiMaskDto : ds ) {
            list.add( toEntity( apiMaskDto ) );
        }

        return list;
    }
}
