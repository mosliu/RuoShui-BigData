package com.ruoshui.quality.mapstruct.impl;

import com.ruoshui.quality.entity.RuleItemEntity;
import com.ruoshui.quality.mapstruct.RuleItemMapper;
import com.ruoshui.quality.vo.RuleItemVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;


@Component
public class RuleItemMapperImpl implements RuleItemMapper {

    @Override
    public RuleItemVo toVO(RuleItemEntity e) {
        if ( e == null ) {
            return null;
        }

        RuleItemVo ruleItemVo = new RuleItemVo();

        ruleItemVo.setId( e.getId() );
        ruleItemVo.setRuleTypeId( e.getRuleTypeId() );
        ruleItemVo.setItemCode( e.getItemCode() );
        ruleItemVo.setItemExplain( e.getItemExplain() );

        return ruleItemVo;
    }

    @Override
    public List<RuleItemVo> toVO(List<RuleItemEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<RuleItemVo> list = new ArrayList<RuleItemVo>( es.size() );
        for ( RuleItemEntity ruleItemEntity : es ) {
            list.add( toVO( ruleItemEntity ) );
        }

        return list;
    }
}
