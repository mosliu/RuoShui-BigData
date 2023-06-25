package com.ruoshui.standard.mapstruct;

import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.standard.dto.DictDto;
import com.ruoshui.standard.entity.DictEntity;
import com.ruoshui.standard.vo.DictVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 数据标准字典表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Mapper(componentModel = "spring")
public interface DictMapper extends EntityMapper<DictDto, DictEntity, DictVo> {

}
