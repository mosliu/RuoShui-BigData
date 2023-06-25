package com.ruoshui.common.flink.model;


import com.ruoshui.common.flink.enums.StateBackendEnum;
import lombok.Data;


/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-21
 * @time 23:16
 */
@Data
public class CheckPointParam {

    /**
     * 默认60S
     */
    private long checkpointInterval = 1000 * 60L;

    /**
     * 默认CheckpointingMode.EXACTLY_ONCE
     */
    private String checkpointingMode = "EXACTLY_ONCE";

    /**
     * 默认超时10 minutes.
     */
    private long checkpointTimeout = 10 * 60 * 1000;

    /**
     * 目录
     */
    private String checkpointDir;

    /**
     * 设置失败次数 默认一次
     */

    private int tolerableCheckpointFailureNumber = 1;

    /**
     * 是否异步
     */
    private Boolean asynchronousSnapshots;

    /**
     * 检查点在作业取消后的保留策略，DELETE_ON_CANCELLATION代表删除，RETAIN_ON_CANCELLATION代表保留
     */
    private String externalizedCheckpointCleanup;

    /**
     * 后端状态类型
     */
    private StateBackendEnum stateBackendEnum;

    /**
     * 支持增量
     */
    private Boolean enableIncremental;


    public long getCheckpointInterval() {
        return checkpointInterval;
    }

    public void setCheckpointInterval(long checkpointInterval) {
        this.checkpointInterval = checkpointInterval;
    }

    public String getCheckpointingMode() {
        return checkpointingMode;
    }

    public void setCheckpointingMode(String checkpointingMode) {
        this.checkpointingMode = checkpointingMode;
    }

    public long getCheckpointTimeout() {
        return checkpointTimeout;
    }

    public void setCheckpointTimeout(long checkpointTimeout) {
        this.checkpointTimeout = checkpointTimeout;
    }

    public String getCheckpointDir() {
        return checkpointDir;
    }

    public void setCheckpointDir(String checkpointDir) {
        this.checkpointDir = checkpointDir;
    }

    public int getTolerableCheckpointFailureNumber() {
        return tolerableCheckpointFailureNumber;
    }

    public void setTolerableCheckpointFailureNumber(int tolerableCheckpointFailureNumber) {
        this.tolerableCheckpointFailureNumber = tolerableCheckpointFailureNumber;
    }

    public Boolean getAsynchronousSnapshots() {
        return asynchronousSnapshots;
    }

    public void setAsynchronousSnapshots(Boolean asynchronousSnapshots) {
        this.asynchronousSnapshots = asynchronousSnapshots;
    }

    public String getExternalizedCheckpointCleanup() {
        return externalizedCheckpointCleanup;
    }

    public void setExternalizedCheckpointCleanup(String externalizedCheckpointCleanup) {
        this.externalizedCheckpointCleanup = externalizedCheckpointCleanup;
    }

    public StateBackendEnum getStateBackendEnum() {
        return stateBackendEnum;
    }

    public void setStateBackendEnum(StateBackendEnum stateBackendEnum) {
        this.stateBackendEnum = stateBackendEnum;
    }

    public Boolean getEnableIncremental() {
        return enableIncremental;
    }

    public void setEnableIncremental(Boolean enableIncremental) {
        this.enableIncremental = enableIncremental;
    }
}
