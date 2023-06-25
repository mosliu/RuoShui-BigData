package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.dto.CheckRuleDto;
import com.ruoshui.quality.entity.CheckRuleEntity;
import com.ruoshui.quality.mapstruct.CheckRuleMapper;
import com.ruoshui.quality.vo.CheckRuleVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;


@Component
public class CheckRuleMapperImpl implements CheckRuleMapper {

    @Override
    public CheckRuleDto toDTO(CheckRuleEntity e) {
        if ( e == null ) {
            return null;
        }

        CheckRuleDto checkRuleDto = new CheckRuleDto();

        checkRuleDto.setId( e.getId() );
        checkRuleDto.setRuleName( e.getRuleName() );
        checkRuleDto.setRuleTypeId( e.getRuleTypeId() );
        checkRuleDto.setRuleItemId( e.getRuleItemId() );
        checkRuleDto.setRuleLevelId( e.getRuleLevelId() );
        checkRuleDto.setRuleDbType( e.getRuleDbType() );
        checkRuleDto.setRuleSourceId( e.getRuleSourceId() );
        checkRuleDto.setRuleSource( e.getRuleSource() );
        checkRuleDto.setRuleTableId( e.getRuleTableId() );
        checkRuleDto.setRuleTable( e.getRuleTable() );
        checkRuleDto.setRuleTableComment( e.getRuleTableComment() );
        checkRuleDto.setRuleColumnId( e.getRuleColumnId() );
        checkRuleDto.setRuleColumn( e.getRuleColumn() );
        checkRuleDto.setRuleColumnComment( e.getRuleColumnComment() );
        checkRuleDto.setRuleConfig( e.getRuleConfig() );
        checkRuleDto.setStatus( e.getStatus() );
        checkRuleDto.setRemark( e.getRemark() );

        return checkRuleDto;
    }

    @Override
    public List<CheckRuleDto> toDTO(List<CheckRuleEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<CheckRuleDto> list = new ArrayList<CheckRuleDto>( es.size() );
        for ( CheckRuleEntity checkRuleEntity : es ) {
            list.add( toDTO( checkRuleEntity ) );
        }

        return list;
    }

    @Override
    public CheckRuleVo toVO(CheckRuleEntity e) {
        if ( e == null ) {
            return null;
        }

        CheckRuleVo checkRuleVo = new CheckRuleVo();

        checkRuleVo.setId( e.getId() );
        checkRuleVo.setStatus( e.getStatus() );
        checkRuleVo.setCreateTime( e.getCreateTime() );
        checkRuleVo.setRemark( e.getRemark() );
        checkRuleVo.setRuleName( e.getRuleName() );
        checkRuleVo.setRuleTypeId( e.getRuleTypeId() );
        checkRuleVo.setRuleItemId( e.getRuleItemId() );
        checkRuleVo.setRuleType( e.getRuleType() );
        checkRuleVo.setRuleLevelId( e.getRuleLevelId() );
        checkRuleVo.setRuleLevel( e.getRuleLevel() );
        checkRuleVo.setRuleDbType( e.getRuleDbType() );
        checkRuleVo.setRuleSourceId( e.getRuleSourceId() );
        checkRuleVo.setRuleSource( e.getRuleSource() );
        checkRuleVo.setRuleTableId( e.getRuleTableId() );
        checkRuleVo.setRuleTable( e.getRuleTable() );
        checkRuleVo.setRuleTableComment( e.getRuleTableComment() );
        checkRuleVo.setRuleColumnId( e.getRuleColumnId() );
        checkRuleVo.setRuleColumn( e.getRuleColumn() );
        checkRuleVo.setRuleColumnComment( e.getRuleColumnComment() );
        checkRuleVo.setRuleConfig( e.getRuleConfig() );

        return checkRuleVo;
    }

    @Override
    public List<CheckRuleVo> toVO(List<CheckRuleEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<CheckRuleVo> list = new ArrayList<CheckRuleVo>( es.size() );
        for ( CheckRuleEntity checkRuleEntity : es ) {
            list.add( toVO( checkRuleEntity ) );
        }

        return list;
    }

    @Override
    public CheckRuleEntity toEntity(CheckRuleDto d) {
        if ( d == null ) {
            return null;
        }

        CheckRuleEntity checkRuleEntity = new CheckRuleEntity();

        checkRuleEntity.setId( d.getId() );
        checkRuleEntity.setStatus( d.getStatus() );
        checkRuleEntity.setRemark( d.getRemark() );
        checkRuleEntity.setRuleName( d.getRuleName() );
        checkRuleEntity.setRuleTypeId( d.getRuleTypeId() );
        checkRuleEntity.setRuleItemId( d.getRuleItemId() );
        checkRuleEntity.setRuleLevelId( d.getRuleLevelId() );
        checkRuleEntity.setRuleDbType( d.getRuleDbType() );
        checkRuleEntity.setRuleSourceId( d.getRuleSourceId() );
        checkRuleEntity.setRuleSource( d.getRuleSource() );
        checkRuleEntity.setRuleTableId( d.getRuleTableId() );
        checkRuleEntity.setRuleTable( d.getRuleTable() );
        checkRuleEntity.setRuleTableComment( d.getRuleTableComment() );
        checkRuleEntity.setRuleColumnId( d.getRuleColumnId() );
        checkRuleEntity.setRuleColumn( d.getRuleColumn() );
        checkRuleEntity.setRuleColumnComment( d.getRuleColumnComment() );
        checkRuleEntity.setRuleConfig( d.getRuleConfig() );

        return checkRuleEntity;
    }

    @Override
    public List<CheckRuleEntity> toEntity(List<CheckRuleDto> ds) {
        if ( ds == null ) {
            return null;
        }

        List<CheckRuleEntity> list = new ArrayList<CheckRuleEntity>( ds.size() );
        for ( CheckRuleDto checkRuleDto : ds ) {
            list.add( toEntity( checkRuleDto ) );
        }

        return list;
    }
}
