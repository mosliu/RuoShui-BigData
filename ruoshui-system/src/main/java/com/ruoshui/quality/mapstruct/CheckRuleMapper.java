package com.ruoshui.quality.mapstruct;


import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.quality.dto.CheckRuleDto;
import com.ruoshui.quality.entity.CheckRuleEntity;
import com.ruoshui.quality.vo.CheckRuleVo;
import org.mapstruct.Mapper;

/**
 * <p>
 * 核查规则信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper(componentModel = "spring")
public interface CheckRuleMapper extends EntityMapper<CheckRuleDto, CheckRuleEntity, CheckRuleVo> {

}
