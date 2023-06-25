package com.ruoshui.flink.mapper;



import com.github.pagehelper.Page;
import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.flink.streaming.web.model.entity.UploadFile;
import com.ruoshui.flink.streaming.web.model.entity.UploadFileVo;
import com.ruoshui.flink.streaming.web.model.param.UploadFileParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileMapper {

  int deleteById(@Param("id") Long id);

  UploadFile getFileByName(@Param("fileName") String fileName);

  UploadFile getFileById(@Param("id") Long id);

  UploadFile getByUrl(String url);

  int insert(UploadFile uploadFile);

  Page<UploadFile> findFilesByPage(UploadFileParam uploadFileParam);

  List<UploadFileVo> getUploadFileByJar();
}