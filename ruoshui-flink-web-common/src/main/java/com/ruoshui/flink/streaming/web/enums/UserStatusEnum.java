package com.ruoshui.flink.streaming.web.enums;

import lombok.Getter;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-10
 * @time 00:47
 */
@Getter
public enum UserStatusEnum {

    CLOSE(0, "停用"),

    OPEN(1, "启用"),

    UNKNOWN(-1, "未知"),
    ;

    private Integer code;

    private String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum getStatus(Integer code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode().equals(code)) {
                return userStatusEnum;
            }
        }
        return UserStatusEnum.UNKNOWN;
    }
}
