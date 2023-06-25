package com.ruoshui.flink.service;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-20
 * @time 02:26
 */
public interface IpStatusService {

    /**
     * 服务启动修改状态
     *
     * @author xinjingruoshui
     * @date 2022-07-20
     * @time 02:27
     */
    void registerIp();


    /**
     * 服务关闭修改状态
     *
     * @author xinjingruoshui
     * @date 2022-07-20
     * @time 02:27
     */
    void cancelIp();


    /**
     * 更新最后心跳时间
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 19:01
     */
    void updateHeartbeatBylocalIp();


    /**
     * 检查本机是不是leader
     *
     * @author xinjingruoshui
     * @date 2022-09-22
     * @time 19:53
     */
    boolean isLeader();
}
