package com.ruoshui.core.database.base;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.core.database.core.DataConstant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> extends BaseMapper<T> {

    List<T> selectListDataScope(@Param("ew") Wrapper<T> queryWrapper, @Param("dataScope") DataConstant.DataScope dataScope);

    IPage<T> selectPageDataScope(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, @Param("dataScope") DataConstant.DataScope dataScope);
}
