package com.ruoshui.flink.rpc;

import com.ruoshui.flink.streaming.web.enums.DeployModeEnum;
import com.ruoshui.flink.rpc.model.JobStandaloneInfo;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-18
 * @time 23:43
 */
public interface FlinkRestRpcAdapter {


    /**
     * Standalone 模式下获取状态
     *
     * @author xinjingruoshui
     * @date 2022/11/3
     * @time 23:47
     */
    JobStandaloneInfo getJobInfoForStandaloneByAppId(String appId, DeployModeEnum deployModeEnum);


    /**
     * 基于flink rest API取消任务
     *
     * @author xinjingruoshui
     * @date 2022/11/3
     * @time 22:50
     */
    void cancelJobForFlinkByAppId(String jobId, DeployModeEnum deployModeEnum);


    /**
     * 获取savepoint路径
     *
     * @author xinjingruoshui
     * @date 2021/3/31
     * @time 22:01
     */
    String savepointPath(String jobId, DeployModeEnum deployModeEnum);


}
