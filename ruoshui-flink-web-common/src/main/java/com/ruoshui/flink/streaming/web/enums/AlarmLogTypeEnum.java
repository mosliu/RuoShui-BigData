package com.ruoshui.flink.streaming.web.enums;

import lombok.Getter;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-25
 * @time 21:45
 */
@Getter
public enum AlarmLogTypeEnum {

    DINGDING(1, "钉钉"),
    CALLBACK_URL(2, "自定义回调http"),
    OTHER(3, "其他");

    private int code;

    private String desc;

    AlarmLogTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlarmLogTypeEnum getAlarmLogTypeEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (AlarmLogTypeEnum alarMLogTypeEnum : AlarmLogTypeEnum.values()) {
            if (alarMLogTypeEnum.getCode() == code) {
                return alarMLogTypeEnum;
            }

        }
        return null;
    }


}
