package com.ruoshui.standard.mapstruct;


import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.standard.dto.ContrastDictDto;
import com.ruoshui.standard.entity.ContrastDictEntity;
import com.ruoshui.standard.vo.ContrastDictVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 字典对照信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper(componentModel = "spring")
public interface ContrastDictMapper extends EntityMapper<ContrastDictDto, ContrastDictEntity, ContrastDictVo> {

}
