package com.ruoshui.market.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.market.dto.HttpService;
import com.ruoshui.market.dto.WebService;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务集成表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-08-20
 */
@Data
public class ServiceIntegrationVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private String serviceNo;
    private String serviceName;
    private String serviceType;
    private HttpService httpService;
    private WebService webService;
}
