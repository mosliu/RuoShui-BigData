package com.ruoshui.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.market.dto.FieldRule;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 数据API脱敏信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
@Data
public class ApiMaskVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String apiId;
    private String maskName;
    private List<FieldRule> rules;
}
