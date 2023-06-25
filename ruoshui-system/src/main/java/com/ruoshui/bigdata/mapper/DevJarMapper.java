package com.ruoshui.bigdata.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoshui.bigdata.entity.DevTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DevJarMapper extends BaseMapper<DevTask>{


	int save(DevTask devJar);

	int update(DevTask entity);

	DevTask getById(int id);
}
