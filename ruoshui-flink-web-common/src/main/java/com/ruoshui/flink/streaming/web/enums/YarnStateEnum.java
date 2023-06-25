package com.ruoshui.flink.streaming.web.enums;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-07
 * @time 21:42
 */
public enum YarnStateEnum {
    NEW, NEW_SAVING, SUBMITTED, ACCEPTED, RUNNING, FINISHED, FAILED, KILLED, UNKNOWN;

    public static YarnStateEnum getYarnStateEnum(String state) {
        for (YarnStateEnum stateEnum : YarnStateEnum.values()) {
            if (stateEnum.name().equals(state)) {
                return stateEnum;
            }

        }

        return UNKNOWN;
    }
}
