package com.ruoshui.flink.streaming.web.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UploadFile implements Serializable {

  private static final long serialVersionUID = 1L;
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

  private Integer isDeleted;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 修改时间
   */
  private Date editTime;
  private String creator;
  private String editor;
  private String url;
  private String resourceType;
  private String resourceName;
  private Integer resourceId;
}