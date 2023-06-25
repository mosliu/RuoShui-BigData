package com.ruoshui.standard.mapstruct;


import com.ruoshui.metadata.mapstruct.EntityMapper;
import com.ruoshui.standard.dto.ContrastDto;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.vo.ContrastStatisticVo;
import com.ruoshui.standard.vo.ContrastVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 对照表信息表 Mapper 实体映射
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper(componentModel = "spring")
public interface ContrastMapper extends EntityMapper<ContrastDto, ContrastEntity, ContrastVo> {

    /**
     * 将源对象转换为VO对象
     * @param e
     * @return D
     */
    ContrastStatisticVo toSVO(ContrastEntity e);

    /**
     * 将源对象集合转换为VO对象集合
     * @param es
     * @return List<D>
     */
    List<ContrastStatisticVo> toSVO(List<ContrastEntity> es);
}
