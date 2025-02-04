package com.ruoshui.flink.mapper;

import com.ruoshui.flink.streaming.web.model.entity.AlartLog;
import com.ruoshui.flink.streaming.web.model.param.AlartLogParam;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmLogMapper {


    int insert(AlartLog alartLog);


    AlartLog selectByPrimaryKey(@Param("id") Long id);


    Page<AlartLog> selectByParam(AlartLogParam alartLogParam);


}
