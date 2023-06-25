package com.ruoshui.web.controller.metadata;


import cn.hutool.core.util.StrUtil;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.core.database.core.DbColumn;
import com.ruoshui.core.database.core.DbTable;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.core.database.core.PageResult;
import com.ruoshui.metadata.dto.MetadataSourceDto;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.service.MetadataSourceMapper;
import com.ruoshui.metadata.query.DbDataQuery;
import com.ruoshui.metadata.query.MetadataSourceQuery;
import com.ruoshui.metadata.service.MetadataSourceService;
import com.ruoshui.metadata.validate.ValidationGroups;
import com.ruoshui.metadata.vo.MetadataSourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据源信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Api(tags = {"数据源信息表"})
@RestController
@RequestMapping("/sources")
public class MetadataSourceController extends BaseController {

    @Autowired
    private MetadataSourceService metadataSourceService;

    @Autowired
    private MetadataSourceMapper metadataSourceMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getMetadataSourceById(@PathVariable String id) {
        MetadataSourceEntity metadataSourceEntity = metadataSourceService.getMetadataSourceById(id);
        return AjaxResult.success(metadataSourceMapper.toVO(metadataSourceEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public AjaxResult getMetadataSourceList() {
        List<MetadataSourceEntity> list = metadataSourceService.getMetadataSourceList();
        List<MetadataSourceVo> collect = list.stream().map(metadataSourceMapper::toVO).collect(Collectors.toList());
        return AjaxResult.success(collect);
    }

    /**
     * 分页查询信息
     *
     * @param metadataSourceQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "metadataSourceQuery", value = "查询实体metadataSourceQuery", required = true, dataTypeClass = MetadataSourceQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getMetadataSourcePage(MetadataSourceQuery metadataSourceQuery) {
        QueryWrapper<MetadataSourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(metadataSourceQuery.getSourceName()), "s.source_name", metadataSourceQuery.getSourceName());
        IPage<MetadataSourceEntity> page = metadataSourceService.pageWithAuth(new Page<>(metadataSourceQuery.getPageNum(), metadataSourceQuery.getPageSize()), queryWrapper);
        List<MetadataSourceVo> collect = page.getRecords().stream().map(metadataSourceMapper::toVO).collect(Collectors.toList());
        JsonPage<MetadataSourceVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }

    /**
     * 添加
     * @param metadataSourceDto
     * @return
     */
    @ApiOperation(value = "添加信息", notes = "根据metadataSourceDto对象添加信息")
    @ApiImplicitParam(name = "metadataSourceDto", value = "详细实体metadataSourceDto", required = true, dataType = "MetadataSourceDto")
    @PostMapping()
    public AjaxResult saveMetadataSource(@RequestBody @Validated({ValidationGroups.Insert.class}) MetadataSourceDto metadataSourceDto) {
        metadataSourceDto.setUser(getUsername());
        metadataSourceService.saveMetadataSource(metadataSourceDto);
        return AjaxResult.success();
    }

    /**
     * 修改
     * @param metadataSourceDto
     * @return
     */
    @ApiOperation(value = "修改信息", notes = "根据url的id来指定修改对象，并根据传过来的信息来修改详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "metadataSourceDto", value = "详细实体metadataSourceDto", required = true, dataType = "MetadataSourceDto")
    })
    @PutMapping("/{id}")
    public AjaxResult updateMetadataSource(@PathVariable String id, @RequestBody @Validated({ValidationGroups.Update.class}) MetadataSourceDto metadataSourceDto) {
        metadataSourceDto.setUser(getUsername());
        metadataSourceService.updateMetadataSource(metadataSourceDto);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public AjaxResult deleteMetadataSourceById(@PathVariable String id) {
        metadataSourceService.deleteMetadataSourceById(id);
        return AjaxResult.success();
    }

    @ApiOperation(value = "批量删除", notes = "根据url的ids来批量删除对象")
    @ApiImplicitParam(name = "ids", value = "ID集合", required = true, dataType = "List", paramType = "path")
    @DeleteMapping("/batch/{ids}")
    public AjaxResult deleteMetadataSourceBatch(@PathVariable List<String> ids) {
        metadataSourceService.deleteMetadataSourceBatch(ids);
        return AjaxResult.success();
    }

    /**
     * 检测数据库连通性
     * @param metadataSourceDto
     * @return
     */
    @ApiOperation(value = "数据库连通性", notes = "根据数据库配置信息检测数据库连通性")
    @ApiImplicitParam(name = "dataSource", value = "详细实体dataSource", required = true, dataType = "DataSourceDto")
    @PostMapping("/checkConnection")
    public AjaxResult checkConnection(@RequestBody @Validated({ValidationGroups.Insert.class}) MetadataSourceDto metadataSourceDto) {
        DbQuery dbQuery = metadataSourceService.checkConnection(metadataSourceDto);
        Boolean valid = dbQuery.valid();
        return valid ? AjaxResult.success("数据库连接成功") : AjaxResult.error("数据库连接有误，请检查数据库配置是否正确");
    }

    /**
     * 数据库表
     * @param id
     * @return
     */
    @ApiOperation(value = "数据库表", notes = "根据数据源的id来获取指定数据库表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/{id}/tables")
    public AjaxResult getDbTables(@PathVariable String id) {
        List<DbTable> tables = metadataSourceService.getDbTables(id);
        return AjaxResult.success(tables);
    }

    /**
     * 数据库表结构
     * @param id
     * @return
     */
    @ApiOperation(value = "数据库表结构", notes = "根据数据源的id来获取指定数据库表的表结构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "tableName", value = "数据库表", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/{id}/{tableName}/columns")
    public AjaxResult getDbTableColumns(@PathVariable String id, @PathVariable String tableName) {
        List<DbColumn> columns = metadataSourceService.getDbTableColumns(id, tableName);
        return AjaxResult.success(columns);
    }

    @ApiOperation(value = "获取SQL结果", notes = "根据数据源的id来获取SQL结果")
    @ApiImplicitParam(name = "dbDataQuery", value = "详细实体dbDataQuery", required = true, dataType = "DbDataQuery")
    @PostMapping("/queryList")
    public AjaxResult queryList(@RequestBody @Validated DbDataQuery dbDataQuery) {
        DbQuery dbQuery = metadataSourceService.getDbQuery(dbDataQuery.getDataSourceId());
        List<Map<String, Object>> list = dbQuery.queryList(dbDataQuery.getSql());
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "分页获取SQL结果", notes = "根据数据源的id来分页获取SQL结果")
    @ApiImplicitParam(name = "dbDataQuery", value = "详细实体dbDataQuery", required = true, dataType = "DbDataQuery")
    @PostMapping("/queryByPage")
    public AjaxResult queryByPage(@RequestBody @Validated DbDataQuery dbDataQuery) {
        DbQuery dbQuery = metadataSourceService.getDbQuery(dbDataQuery.getDataSourceId());
        PageResult<Map<String, Object>> page = dbQuery.queryByPage(dbDataQuery.getSql(), dbDataQuery.getOffset(), dbDataQuery.getPageSize());
        page.setPageNum(dbDataQuery.getPageNum()).setPageSize(dbDataQuery.getPageSize());
        return AjaxResult.success(page);
    }

    @ApiOperation(value = "同步", notes = "根据url的id来指定同步对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @PostMapping("/sync/{id}")
    public AjaxResult syncMetadata(@PathVariable String id) {
        metadataSourceService.syncMetadata(id);
        return AjaxResult.success();
    }

    @ApiOperation(value = "数据库设计文档", notes = "根据url的id来指定生成数据库设计文档对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @PostMapping("/word/{id}")
    public void wordMetadata(@PathVariable String id, HttpServletResponse response) throws Exception {
        // 清空response
        response.reset();
        // 设置response的Header
        response.setContentType("application/octet-stream;charset=utf-8");
        // 设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("数据库设计文档.doc".getBytes()));
        Document doc = metadataSourceService.wordMetadata(id);
        OutputStream out = response.getOutputStream();
        doc.save(out, SaveOptions.createSaveOptions(SaveFormat.DOC));
        out.flush();
        out.close();
    }

    /**
     * 刷新参数缓存
     *
     * @return
     */
    @GetMapping("/refresh")
    public AjaxResult refreshMetadata() {
        metadataSourceService.refreshMetadata();
        return AjaxResult.success();
    }


}
