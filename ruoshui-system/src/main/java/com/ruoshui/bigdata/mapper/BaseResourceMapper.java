package com.ruoshui.bigdata.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoshui.bigdata.entity.BaseResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseResourceMapper extends BaseMapper<BaseResource>{

	int delete(@Param("id") int id);

	List<BaseResource> findList(@Param("offset") int offset,
			@Param("pagesize") int pagesize,
			@Param("name") String name);

	int save(BaseResource apiAuth);

	int update(BaseResource entity);

	BaseResource getById(int id);

	List<BaseResource> getResource();


	List<BaseResource> getFileResource();
}
