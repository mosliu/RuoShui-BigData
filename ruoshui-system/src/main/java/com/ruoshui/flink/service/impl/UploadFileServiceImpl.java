package com.ruoshui.flink.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoshui.flink.mapper.UploadFileMapper;
import com.ruoshui.flink.service.UploadFileService;
import com.ruoshui.flink.streaming.web.config.CustomConfig;
import com.ruoshui.flink.streaming.web.enums.YN;
import com.ruoshui.flink.streaming.web.exceptions.BizException;
import com.ruoshui.flink.streaming.web.model.dto.PageModel;
import com.ruoshui.flink.streaming.web.model.dto.UploadFileDTO;
import com.ruoshui.flink.streaming.web.model.entity.UploadFile;
import com.ruoshui.flink.streaming.web.model.entity.UploadFileVo;
import com.ruoshui.flink.streaming.web.model.param.UploadFileParam;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static com.ruoshui.flink.streaming.web.common.MessageConstants.MESSAGE_012;


/**
 * @author zhuhuipei
 * @Description:
 * @date 2022/09/19
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

  @Resource
  private UploadFileMapper uploadFileMapper;

  @Resource
  private CustomConfig customConfig;

  @Override
  public void addFile(UploadFileDTO uploadFileDTO) {
    UploadFile uploadFile = uploadFileMapper.getFileByName(uploadFileDTO.getFileName());
    if (uploadFile != null) {
      throw new BizException(MESSAGE_012);
    }
    uploadFileMapper.insert(UploadFileDTO.toEntity(uploadFileDTO));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteFile(Long id) {
    UploadFile uploadFile = uploadFileMapper.getFileById(id);
    if (uploadFile == null) {
      log.warn("fileName = {}  id={} is no ", id);
      return;
    }
    uploadFileMapper.deleteById(id);
    Boolean res = new File(uploadFile.getFilePath()).delete();
    log.info("文件 {} 清理 res={}", uploadFile.getFilePath(), res);
  }

  @Override
  public PageModel<UploadFileDTO> queryUploadFile(UploadFileParam uploadFileParam) {
    if (uploadFileParam == null) {
      uploadFileParam = new UploadFileParam();
    }
    PageHelper
        .startPage(uploadFileParam.getPageNum(), uploadFileParam.getPageSize(), YN.Y.getCode());

    Page<UploadFile> page = uploadFileMapper.findFilesByPage(uploadFileParam);
    if (page == null) {
      return null;
    }
    PageModel<UploadFileDTO> pageModel = new PageModel<UploadFileDTO>();
    pageModel.setPageNum(page.getPageNum());
    pageModel.setPages(page.getPages());
    pageModel.setPageSize(page.getPageSize());
    pageModel.setTotal(page.getTotal());
    pageModel.addAll(UploadFileDTO.toDTOList(page.getResult(), customConfig.getUrlForDown()));
    return pageModel;
  }

  @Override
  public UploadFileDTO getUploadFileByFileName(String fileName) {
    return UploadFileDTO
        .toDTO(uploadFileMapper.getFileByName(fileName), customConfig.getUrlForDown());
  }

  @Override
  public UploadFileDTO getUploadFileById(Long id) {
    return UploadFileDTO
            .toDTO(uploadFileMapper.getFileById(id), customConfig.getUrlForDown());
  }

  @Override
  public List<UploadFileVo> getUploadFileByJar() {

    return uploadFileMapper.getUploadFileByJar();
  }
}
