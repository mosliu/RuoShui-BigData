package com.ruoshui.market.mapstruct;


import com.ruoshui.market.dto.ApiLogDto;
import com.ruoshui.market.entity.ApiLogEntity;
import com.ruoshui.market.vo.ApiLogVo;
import com.ruoshui.metadata.mapstruct.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiLogMapper extends EntityMapper<ApiLogDto, ApiLogEntity, ApiLogVo> {
}
