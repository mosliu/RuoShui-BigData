package com.ruoshui.market.mapstruct;


import com.ruoshui.market.dto.DataApiDto;
import com.ruoshui.market.entity.DataApiEntity;
import com.ruoshui.market.vo.DataApiVo;
import com.ruoshui.metadata.mapstruct.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 数据API信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-03-31
 */
@Mapper(componentModel = "spring")
public interface DataApiMapper extends EntityMapper<DataApiDto, DataApiEntity, DataApiVo> {


}
