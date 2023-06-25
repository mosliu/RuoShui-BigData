package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.entity.ScheduleLogEntity;
import com.ruoshui.quality.mapstruct.ScheduleLogMapper;
import com.ruoshui.quality.vo.ScheduleLogVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;


@Component
public class ScheduleLogMapperImpl implements ScheduleLogMapper {

    @Override
    public ScheduleLogVo toVO(ScheduleLogEntity e) {
        if ( e == null ) {
            return null;
        }

        ScheduleLogVo scheduleLogVo = new ScheduleLogVo();

        scheduleLogVo.setId( e.getId() );
        scheduleLogVo.setStatus( e.getStatus() );
        scheduleLogVo.setExecuteJobId( e.getExecuteJobId() );
        scheduleLogVo.setExecuteRuleId( e.getExecuteRuleId() );
        scheduleLogVo.setExecuteDate( e.getExecuteDate() );
        scheduleLogVo.setExecuteResult( e.getExecuteResult() );
        scheduleLogVo.setExecuteBatch( e.getExecuteBatch() );
        scheduleLogVo.setExecuteJobName( e.getExecuteJobName() );
        scheduleLogVo.setExecuteRuleName( e.getExecuteRuleName() );
        scheduleLogVo.setExecuteRuleTypeName( e.getExecuteRuleTypeName() );

        return scheduleLogVo;
    }

    @Override
    public List<ScheduleLogVo> toVO(List<ScheduleLogEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ScheduleLogVo> list = new ArrayList<ScheduleLogVo>( es.size() );
        for ( ScheduleLogEntity scheduleLogEntity : es ) {
            list.add( toVO( scheduleLogEntity ) );
        }

        return list;
    }
}
