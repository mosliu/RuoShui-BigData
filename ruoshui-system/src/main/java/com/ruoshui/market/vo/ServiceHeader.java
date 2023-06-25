package com.ruoshui.market.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceHeader implements Serializable {

    private static final long serialVersionUID=1L;

    private String serviceKey;
    private String secretKey;
}
