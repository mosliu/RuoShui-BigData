package com.ruoshui.flink.service;


import com.ruoshui.flink.streaming.web.model.dto.PageModel;
import com.ruoshui.flink.streaming.web.model.dto.UploadFileDTO;
import com.ruoshui.flink.streaming.web.model.entity.UploadFile;
import com.ruoshui.flink.streaming.web.model.entity.UploadFileVo;
import com.ruoshui.flink.streaming.web.model.param.UploadFileParam;

import java.util.List;

public interface UploadFileService {

  void addFile(UploadFileDTO uploadFileDTO);

  void deleteFile(Long id);

  PageModel<UploadFileDTO> queryUploadFile(UploadFileParam uploadFileParam);

  UploadFileDTO getUploadFileByFileName(String fileName);

  UploadFileDTO getUploadFileById(Long id);

  List<UploadFileVo> getUploadFileByJar();
}
