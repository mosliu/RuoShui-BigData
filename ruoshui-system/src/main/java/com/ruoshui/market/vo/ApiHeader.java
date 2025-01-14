package com.ruoshui.market.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiHeader implements Serializable {

    private static final long serialVersionUID=1L;

    private String apiKey;
    private String secretKey;
}
