package com.ruoshui.web.controller.flink.api;


import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.common.config.RuoShuiConfig;
import com.ruoshui.common.utils.ReadYmlUtil;
import com.ruoshui.common.utils.file.FileUploadUtils;
import com.ruoshui.common.utils.file.FileUtils;
import com.ruoshui.core.biz.model.ReturnT;
import com.ruoshui.flink.service.SystemConfigService;
import com.ruoshui.flink.service.UploadFileService;
import com.ruoshui.flink.streaming.web.common.RestResult;
import com.ruoshui.flink.streaming.web.enums.FileTypeEnum;
import com.ruoshui.flink.streaming.web.model.dto.PageModel;
import com.ruoshui.flink.streaming.web.model.dto.UploadFileDTO;
import com.ruoshui.flink.streaming.web.model.entity.UploadFile;
import com.ruoshui.flink.streaming.web.model.entity.UploadFileVo;
import com.ruoshui.flink.streaming.web.model.param.UploadFileParam;
import com.ruoshui.flink.streaming.web.model.vo.PageVO;
import com.ruoshui.framework.config.ServerConfig;
import com.ruoshui.web.controller.flink.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhuhuipei
 * @Description:
 * @date 2022/09/19
 */

@RestController
@RequestMapping("/flink/uploadJar")
@Slf4j
public class UploadApiController extends BaseController {

  @Autowired
  private UploadFileService uploadFileService;

  @Autowired
  private SystemConfigService systemConfigService;

  @Resource
  private BaseResourceMapper baseResourceMapper;

  @Autowired
  private ServerConfig serverConfig;


  @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
  public RestResult<?> upload(HttpServletRequest request, @RequestParam(value="resourceId")  String resourceId,@RequestParam("file") MultipartFile file) {
    try {
       String  uploadPath = RuoShuiConfig.getUploadPath();
      //通过ID查资源服务器信息
      BaseResource baseResource = baseResourceMapper.getById(Integer.parseInt(resourceId));
      log.info("uploadPath={}", uploadPath);
      // 上传并返回新文件名称
      String fileName = FileUploadUtils.upload(uploadPath, file);
      String url = serverConfig.getUrl() + fileName;
      Map<String, String> ipAndPort = serverConfig.getIpAndPort1(url);
      if(!"".equals(ipAndPort.get("port")) && null!=ipAndPort.get("port")){
        url = url.replaceFirst(":"+ipAndPort.get("port"),":"+ String.valueOf(ReadYmlUtil.getValueToString("server.port")));
      }
      UploadFileDTO uploadFileDTO = new UploadFileDTO();
      uploadFileDTO.setFileName(file.getOriginalFilename());
      uploadFileDTO.setFilePath(FileUtils.getName(fileName));
      uploadFileDTO.setType(FileTypeEnum.JAR.getCode());
      uploadFileDTO.setDownloadJarHttp(url);
      uploadFileDTO.setEditor(getUserName());
      uploadFileDTO.setCreator(getUserName());
      uploadFileDTO.setResourceName(baseResource.getName());
      uploadFileDTO.setResourceType(baseResource.getType());
      uploadFileDTO.setResourceId(Integer.valueOf(resourceId));
      uploadFileService.addFile(uploadFileDTO);
      return RestResult.success();
    } catch (Exception e) {
      log.error("upload is error", e);
      return RestResult.error(e.getMessage());
    }
  }

  @RequestMapping("/deleteFile")
  public RestResult<?> deleteFile(Long id) {
    try {
      UploadFileDTO uploadFileById = uploadFileService.getUploadFileById(id);
      String  uploadPath = RuoShuiConfig.getUploadPath();
      String[] split = uploadFileById.getDownloadJarHttp().split("/");
      String filePath = uploadPath + "/" + split[split.length-4] + "/" + split[split.length-3] + "/" + split[split.length-2] + "/" + uploadFileById.getFilePath();
      FileUtils.deleteFile(filePath);
      uploadFileService.deleteFile(id);
      return RestResult.success();
    } catch (Exception e) {
      log.error("deleteFile is error", e);
      return RestResult.error("deleteFile is  error : " + e.getMessage());
    }
  }

  @RequestMapping(value = "/queryUploadFile")
  public RestResult<?> queryUploadFile(UploadFileParam uploadFileParam) {
    try {
      PageModel<UploadFileDTO> pageModel = uploadFileService.queryUploadFile(uploadFileParam);
      PageVO pageVO = new PageVO();
      pageVO.setPageNum(pageModel.getPageNum());
      pageVO.setPages(pageModel.getPages());
      pageVO.setPageSize(pageModel.getPageSize());
      pageVO.setTotal(pageModel.getTotal());
      pageVO.setData(pageModel);
      return RestResult.success(pageVO);
    } catch (Exception e) {
      log.error("queryUploadFile is error", e);
      return RestResult.error("queryUploadFile is  error : " + e.getMessage());
    }
  }

  @RequestMapping("/getFileJar")
  public RestResult<?> getFileJar() {
    List<UploadFileVo> uploadFileList = uploadFileService.getUploadFileByJar();
    return RestResult.success(uploadFileList);
  }

}
