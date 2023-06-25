package com.ruoshui.quality.mapstruct;


import com.ruoshui.quality.entity.RuleLevelEntity;
import com.ruoshui.quality.vo.RuleLevelVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 规则级别信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-10-14
 */
@Mapper(componentModel = "spring")
public interface RuleLevelMapper {

    /**
     * 将源对象转换为VO对象
     * @param e
     * @return D
     */
    RuleLevelVo toVO(RuleLevelEntity e);

    /**
     * 将源对象集合转换为VO对象集合
     * @param es
     * @return List<D>
     */
    List<RuleLevelVo> toVO(List<RuleLevelEntity> es);
}
