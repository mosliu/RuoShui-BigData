package com.ruoshui.flink.streaming.web.enums;

import lombok.Getter;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-20
 * @time 02:15
 */
@Getter
public enum IpStatusEnum {

    START(1), STOP(-1);

    private int code;

    IpStatusEnum(int code) {
        this.code = code;
    }


}
