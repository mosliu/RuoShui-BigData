package com.ruoshui.market.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.market.dto.ExecuteConfig;
import com.ruoshui.market.dto.RateLimit;
import com.ruoshui.market.dto.ReqParam;
import com.ruoshui.market.dto.ResParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 数据API信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-03-31
 */
@Data
public class DataApiVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String apiName;
    private String apiVersion;
    private String apiUrl;
    private String reqMethod;
    private String deny;
    private String resType;
    private String apiCode;
    private RateLimit rateLimit;
    private ExecuteConfig executeConfig;
    private List<ReqParam> reqParams;
    private List<ResParam> resParams;
}
