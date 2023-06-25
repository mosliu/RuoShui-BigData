package com.ruoshui.flink.rpc.model;

import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-18
 * @time 23:50
 */
@Data
public class JobStandaloneInfo {

    private String jid;

    private String state;

    private String errors;


}
