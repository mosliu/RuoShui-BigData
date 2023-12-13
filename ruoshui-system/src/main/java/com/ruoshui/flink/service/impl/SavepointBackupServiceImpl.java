package com.ruoshui.flink.service.impl;

import com.ruoshui.flink.mapper.SavepointBackupMapper;
import com.ruoshui.flink.streaming.web.model.dto.SavepointBackupDTO;
import com.ruoshui.flink.streaming.web.model.entity.SavepointBackup;
import com.ruoshui.flink.service.SavepointBackupService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-17
 * @time 20:26
 */
@Service
public class SavepointBackupServiceImpl implements SavepointBackupService {

    @Resource
    private SavepointBackupMapper savepointBackupMapper;

    @Override
    public void insertSavepoint(Long jobConfigId, String savepointPath, Date backupTime) {
        SavepointBackup savepointBackup = new SavepointBackup();
        savepointBackup.setBackupTime(backupTime);
        savepointBackup.setSavepointPath(savepointPath);
        savepointBackup.setJobConfigId(jobConfigId);
        savepointBackupMapper.insert(savepointBackup);
    }

    @Override
    public List<SavepointBackupDTO> lasterHistory10(Long jobConfigId) {
        return SavepointBackupDTO.toDTOList(savepointBackupMapper.selectByLimt10(jobConfigId));
    }

    @Override
    public String getSavepointPathById(Long jobConfigId, Long id) {
        SavepointBackup savepointBackup = savepointBackupMapper.getSavepointBackupById(jobConfigId, id);
        if (savepointBackup != null) {
            return savepointBackup.getSavepointPath();
        }
        return null;
    }
}
