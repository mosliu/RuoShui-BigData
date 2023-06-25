package com.ruoshui.market.mapstruct;


import com.ruoshui.market.dto.ApiMaskDto;
import com.ruoshui.market.entity.ApiMaskEntity;
import com.ruoshui.market.vo.ApiMaskVo;
import com.ruoshui.metadata.mapstruct.EntityMapper;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据API脱敏信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
@Mapper(componentModel = "spring")
public interface ApiMaskMapper extends EntityMapper<ApiMaskDto, ApiMaskEntity, ApiMaskVo> {

}
