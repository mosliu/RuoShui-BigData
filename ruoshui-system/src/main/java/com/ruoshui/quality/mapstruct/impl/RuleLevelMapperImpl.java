package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.entity.RuleLevelEntity;
import com.ruoshui.quality.mapstruct.RuleLevelMapper;
import com.ruoshui.quality.vo.RuleLevelVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleLevelMapperImpl implements RuleLevelMapper {

    @Override
    public RuleLevelVo toVO(RuleLevelEntity e) {
        if ( e == null ) {
            return null;
        }

        RuleLevelVo ruleLevelVo = new RuleLevelVo();

        ruleLevelVo.setId( e.getId() );
        ruleLevelVo.setCode( e.getCode() );
        ruleLevelVo.setName( e.getName() );

        return ruleLevelVo;
    }

    @Override
    public List<RuleLevelVo> toVO(List<RuleLevelEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<RuleLevelVo> list = new ArrayList<RuleLevelVo>( es.size() );
        for ( RuleLevelEntity ruleLevelEntity : es ) {
            list.add( toVO( ruleLevelEntity ) );
        }

        return list;
    }
}
