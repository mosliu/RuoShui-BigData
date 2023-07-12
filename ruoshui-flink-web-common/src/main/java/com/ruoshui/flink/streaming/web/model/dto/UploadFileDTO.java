package com.ruoshui.flink.streaming.web.model.dto;


import com.ruoshui.flink.streaming.web.common.util.DateFormatUtils;
import com.ruoshui.flink.streaming.web.model.entity.UploadFile;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author zhuhuipei
 * @Description:
 * @date 2022/09/19
 */
@Data
public class UploadFileDTO {

  private Long id;
  /**
   * 文件名字
   */
  private String fileName;

  /**
   * 文件路径
   */
  private String filePath;

  /**
   * 1:jar
   */
  private Integer type;

  /**
   * 创建时间
   */
  private Date createTime;

  private String createTimeStr;

  private String creator;

  private String editor;

  private String downloadJarHttp;

  private String resourceType;

  private String resourceName;

  private Integer resourceId;

  private String remarks;




  public static UploadFile toEntity(UploadFileDTO uploadFileDTO) {
    if (uploadFileDTO == null) {
      return null;
    }
    UploadFile uploadFile = new UploadFile();
    uploadFile.setId(uploadFileDTO.getId());
    uploadFile.setFileName(uploadFileDTO.getFileName());
    uploadFile.setFilePath(uploadFileDTO.getFilePath());
    uploadFile.setType(uploadFileDTO.getType());
    uploadFile.setCreateTime(uploadFileDTO.getCreateTime());
    uploadFile.setCreator(uploadFileDTO.getCreator());
    uploadFile.setEditor(uploadFileDTO.getEditor());
    uploadFile.setUrl(uploadFileDTO.downloadJarHttp);
    uploadFile.setResourceName(uploadFileDTO.getResourceName());
    uploadFile.setResourceType(uploadFileDTO.getResourceType());
    uploadFile.setResourceId(uploadFileDTO.getResourceId());
    uploadFile.setRemarks(uploadFileDTO.remarks);
    return uploadFile;
  }


  public static UploadFileDTO toDTO(UploadFile uploadFile, String downloadUrl) {
    if (uploadFile == null) {
      return null;
    }
    UploadFileDTO uploadFileDTO = new UploadFileDTO();
    uploadFileDTO.setId(uploadFile.getId());
    uploadFileDTO.setFileName(uploadFile.getFileName());
    uploadFileDTO.setFilePath(uploadFile.getFilePath());
    uploadFileDTO.setType(uploadFile.getType());
    uploadFileDTO.setDownloadJarHttp(uploadFile.getUrl());
    uploadFileDTO.setResourceName(uploadFile.getResourceName());
    uploadFileDTO.setResourceType(uploadFile.getResourceType());
    uploadFileDTO.setResourceId(uploadFile.getResourceId());
    uploadFileDTO.setRemarks(uploadFile.getRemarks());
    if (uploadFile.getCreateTime() != null) {
      uploadFileDTO.setCreateTime(uploadFile.getCreateTime());
      uploadFileDTO.setCreateTimeStr(DateFormatUtils.toFormatString(uploadFile.getCreateTime()));
    }
    return uploadFileDTO;
  }

  public static List<UploadFileDTO> toDTOList(List<UploadFile> uploadFileList, String downloadUrl) {
    if (CollectionUtils.isEmpty(uploadFileList)) {
      return Collections.emptyList();
    }
    List<UploadFileDTO> list = new ArrayList();
    for (UploadFile uploadFile : uploadFileList) {
      list.add(toDTO(uploadFile, downloadUrl));
    }
    return list;
  }


}
