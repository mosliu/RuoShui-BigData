package com.ruoshui.quality.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 规则级别信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-10-14
 */
@Data
public class RuleLevelVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String code;
    private String name;
}
