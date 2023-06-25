package com.ruoshui.bigdata.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoshui.bigdata.entity.DevEnvSetting;
import com.ruoshui.bigdata.entity.DevTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DevTaskMapper extends BaseMapper<DevTask>{

	int delete(@Param("id") int id);

	int save(DevTask devJar);

	int update(DevTask entity);

	DevTask getById(int id);

	List<DevTask> findList(@Param("offset") int offset,
			@Param("pagesize") int pagesize,
			@Param("type") String type);

	String findPath(@Param("tasktype")  String tasktype);
}
