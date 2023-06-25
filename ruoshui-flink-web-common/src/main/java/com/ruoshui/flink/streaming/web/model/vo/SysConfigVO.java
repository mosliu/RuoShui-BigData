package com.ruoshui.flink.streaming.web.model.vo;

import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-23
 * @time 00:07
 */
@Data
public class SysConfigVO {

    private String key;

    private String desc;

    public SysConfigVO(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
