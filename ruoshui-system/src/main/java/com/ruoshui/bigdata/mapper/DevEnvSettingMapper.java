package com.ruoshui.bigdata.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.bigdata.entity.DevEnvSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface DevEnvSettingMapper extends BaseMapper<DevEnvSetting>{
    IPage<DevEnvSetting> getDevEnvSettingListPaging(IPage<DevEnvSetting> page,
                                          @Param("searchName") String searchName);
}
