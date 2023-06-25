package com.ruoshui.market.vo;

import com.ruoshui.market.dto.ReqParam;
import com.ruoshui.market.dto.ResParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SqlParseVo implements Serializable {

    private static final long serialVersionUID=1L;

    private List<ReqParam> reqParams;
    private List<ResParam> resParams;
}
