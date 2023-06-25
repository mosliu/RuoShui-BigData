package com.ruoshui.standard.mapstruct;


import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.standard.dto.TypeDto;
import com.ruoshui.standard.entity.TypeEntity;
import com.ruoshui.standard.vo.TypeVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据标准类别表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Mapper(componentModel = "spring")
public interface TypeMapper extends EntityMapper<TypeDto, TypeEntity, TypeVo> {

}
