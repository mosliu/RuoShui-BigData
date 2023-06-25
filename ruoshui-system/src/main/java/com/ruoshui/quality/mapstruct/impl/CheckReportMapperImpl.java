package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.mapstruct.CheckReportMapper;
import com.ruoshui.quality.vo.CheckReportVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckReportMapperImpl implements CheckReportMapper {

    @Override
    public CheckReportVo toVO(CheckReportEntity e) {
        if ( e == null ) {
            return null;
        }

        CheckReportVo checkReportVo = new CheckReportVo();

        checkReportVo.setId( e.getId() );
        checkReportVo.setCheckRuleId( e.getCheckRuleId() );
        checkReportVo.setCheckDate( e.getCheckDate() );
        checkReportVo.setCheckResult( e.getCheckResult() );
        checkReportVo.setCheckTotalCount( e.getCheckTotalCount() );
        checkReportVo.setCheckErrorCount( e.getCheckErrorCount() );
        checkReportVo.setRuleName( e.getRuleName() );
        checkReportVo.setRuleType( e.getRuleType() );
        checkReportVo.setRuleSource( e.getRuleSource() );
        checkReportVo.setRuleTable( e.getRuleTable() );
        checkReportVo.setRuleColumn( e.getRuleColumn() );

        return checkReportVo;
    }

    @Override
    public List<CheckReportVo> toVO(List<CheckReportEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<CheckReportVo> list = new ArrayList<CheckReportVo>( es.size() );
        for ( CheckReportEntity checkReportEntity : es ) {
            list.add( toVO( checkReportEntity ) );
        }

        return list;
    }
}
