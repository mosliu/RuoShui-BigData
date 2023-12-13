package com.ruoshui.web.controller.flink.api;

import com.ruoshui.flink.streaming.web.common.RestResult;
import com.ruoshui.flink.streaming.web.enums.JobConfigStatus;
import com.ruoshui.flink.streaming.web.enums.SysErrorEnum;
import com.ruoshui.flink.streaming.web.enums.YN;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.streaming.web.model.dto.SavepointBackupDTO;
import com.ruoshui.flink.streaming.web.model.vo.SavepointBackupVO;
import com.ruoshui.flink.service.JobConfigService;
import com.ruoshui.flink.service.SavepointBackupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-21
 * @time 01:52
 */
@RestController
@RequestMapping("/flink")
@Slf4j
public class SavepointApiController {

    @Resource
    private SavepointBackupService savepointBackupService;
    
    @Resource
    private JobConfigService jobConfigService;

    @RequestMapping(value = "/addSavepoint")
    public RestResult<?> addSavepoint(Long jobConfigId, String savepointPath) {
        try {
            if (StringUtils.isBlank(savepointPath)) {
                throw new BizException("SavePoint地址不能为空！");
            }
            JobConfigDTO jobdto = jobConfigService.getJobConfigById(jobConfigId);
            if (jobdto == null) {
                throw new BizException("查找不到编号为[" + jobConfigId + "]的任务！");
            }
            savepointPath = savepointPath.trim();
            List<SavepointBackupDTO> savepointBackupDTOList = savepointBackupService.lasterHistory10(jobConfigId);
            for (SavepointBackupDTO savepointBackupDTO : savepointBackupDTOList) {
                if (savepointPath.equals(savepointBackupDTO.getSavepointPath())) {
                    throw new BizException("SavePoint地址[" + savepointPath + "]已经存在！");
                }
            }
            savepointBackupService.insertSavepoint(jobConfigId, savepointPath, new Date());
        } catch (BizException e) {
            log.error("addSavepoint is error jobConfigId={},savepointPath={}", jobConfigId, savepointPath, e);
            return RestResult.error(e.getCode(), e.getErrorMsg());
        } catch (Exception e) {
            log.error("addSavepoint  error jobConfigId={},savepointPath={}", jobConfigId, savepointPath, e);
            return RestResult.error(SysErrorEnum.ADD_SAVEPOINT_ERROR);
        }
        return RestResult.success();
    }

    /**
     * 获取SavePoint保存历史信息
     *
     * @param taskid
     * @return
     * @author wxj
     * @date 2021年12月3日 上午10:01:42 
     * @version V1.0
     */
    @RequestMapping(value = "/querySavePointList10")
    public RestResult<?> querySavePointList10(Long taskid) {
        List<SavepointBackupDTO> savepointBackupDTOList = savepointBackupService.lasterHistory10(taskid);
        ModelMap modelMap = new ModelMap();
        modelMap.put("data", SavepointBackupVO.toDTOList(savepointBackupDTOList));
        JobConfigDTO jobConfigDTO = jobConfigService.getJobConfigById(taskid);
        if (jobConfigDTO != null && JobConfigStatus.RUN.getCode().intValue() != jobConfigDTO.getStatus().getCode().intValue()
                && YN.getYNByValue(jobConfigDTO.getIsOpen()).getCode()) {
            modelMap.put("enable", true);
        } else {
            modelMap.put("enable", false);
        }
        modelMap.put("taskId", taskid);
        return RestResult.success(modelMap);
    }
}
