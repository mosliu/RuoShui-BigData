package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.mapstruct.RuleTypeMapper;
import com.ruoshui.quality.vo.RuleTypeVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleTypeMapperImpl implements RuleTypeMapper {

    @Override
    public RuleTypeVo toVO(RuleTypeEntity e) {
        if ( e == null ) {
            return null;
        }

        RuleTypeVo ruleTypeVo = new RuleTypeVo();

        ruleTypeVo.setId( e.getId() );
        ruleTypeVo.setName( e.getName() );
        ruleTypeVo.setCode( e.getCode() );

        return ruleTypeVo;
    }

    @Override
    public List<RuleTypeVo> toVO(List<RuleTypeEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<RuleTypeVo> list = new ArrayList<RuleTypeVo>( es.size() );
        for ( RuleTypeEntity ruleTypeEntity : es ) {
            list.add( toVO( ruleTypeEntity ) );
        }

        return list;
    }
}
