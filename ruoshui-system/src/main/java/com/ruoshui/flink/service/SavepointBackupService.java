package com.ruoshui.flink.service;

import com.ruoshui.flink.streaming.web.model.dto.SavepointBackupDTO;

import java.util.Date;
import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-17
 * @time 20:26
 */
public interface SavepointBackupService {

    /**
     * 新增
     *
     * @author xinjingruoshui
     * @date 2022-09-17
     * @time 20:34
     */
    void insertSavepoint(Long jobConfigId, String savepointPath, Date backupTime);


    /**
     * 最近5条
     *
     * @author xinjingruoshui
     * @date 2022-09-17
     * @time 20:34
     */
    List<SavepointBackupDTO> lasterHistory10(Long jobConfigId);


    /**
     * 获取SavepointPath详细地址
     *
     * @author xinjingruoshui
     * @date 2022-09-21
     * @time 00:44
     */
    String getSavepointPathById(Long jobConfigId, Long id);

}
