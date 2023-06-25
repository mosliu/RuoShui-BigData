package com.ruoshui.quality.mapstruct.impl;


import com.ruoshui.quality.entity.ScheduleJobEntity;
import com.ruoshui.quality.mapstruct.ScheduleJobMapper;
import com.ruoshui.quality.vo.ScheduleJobVo;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;


@Component
public class ScheduleJobMapperImpl implements ScheduleJobMapper {

    @Override
    public ScheduleJobVo toVO(ScheduleJobEntity e) {
        if ( e == null ) {
            return null;
        }

        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();

        scheduleJobVo.setId( e.getId() );
        scheduleJobVo.setStatus( e.getStatus() );
        scheduleJobVo.setJobName( e.getJobName() );
        scheduleJobVo.setBeanName( e.getBeanName() );
        scheduleJobVo.setMethodName( e.getMethodName() );
        scheduleJobVo.setMethodParams( e.getMethodParams() );
        scheduleJobVo.setCronExpression( e.getCronExpression() );

        return scheduleJobVo;
    }

    @Override
    public List<ScheduleJobVo> toVO(List<ScheduleJobEntity> es) {
        if ( es == null ) {
            return null;
        }

        List<ScheduleJobVo> list = new ArrayList<ScheduleJobVo>( es.size() );
        for ( ScheduleJobEntity scheduleJobEntity : es ) {
            list.add( toVO( scheduleJobEntity ) );
        }

        return list;
    }
}
