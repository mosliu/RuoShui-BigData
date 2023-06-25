package com.ruoshui.web.controller.standard;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.metadata.validate.ValidationGroups;
import com.ruoshui.standard.dto.ContrastDto;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.mapstruct.ContrastMapper;
import com.ruoshui.standard.query.ContrastQuery;
import com.ruoshui.standard.service.ContrastService;
import com.ruoshui.standard.vo.ContrastStatisticVo;
import com.ruoshui.standard.vo.ContrastTreeVo;
import com.ruoshui.standard.vo.ContrastVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 对照表信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Api(tags = {"对照表信息表"})
@RestController
@RequestMapping("/standard/contrasts")
public class ContrastController extends BaseController {

    @Autowired
    private ContrastService contrastService;

    @Autowired
    private ContrastMapper contrastMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getContrastById(@PathVariable String id) {
        ContrastEntity contrastEntity = contrastService.getContrastById(id);
        return AjaxResult.success(contrastMapper.toVO(contrastEntity));
    }

    /**
     * 分页查询信息
     *
     * @param contrastQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contrastQuery", value = "查询实体contrastQuery", required = true, dataTypeClass = ContrastQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getContrastPage(ContrastQuery contrastQuery) {
        QueryWrapper<ContrastEntity> queryWrapper = new QueryWrapper<>();
        IPage<ContrastEntity> page = contrastService.page(new Page<>(contrastQuery.getPageNum(), contrastQuery.getPageSize()), queryWrapper);
        List<ContrastVo> collect = page.getRecords().stream().map(contrastMapper::toVO).collect(Collectors.toList());
        JsonPage<ContrastVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }

    /**
     * 添加
     * @param contrast
     * @return
     */
    @ApiOperation(value = "添加信息", notes = "根据contrast对象添加信息")
    @ApiImplicitParam(name = "contrast", value = "详细实体contrast", required = true, dataType = "ContrastDto")
    @PostMapping()
    public AjaxResult saveContrast(@RequestBody @Validated({ValidationGroups.Insert.class}) ContrastDto contrast) {
        ContrastEntity contrastEntity = contrastService.saveContrast(contrast);
        return AjaxResult.success(contrastMapper.toVO(contrastEntity));
    }

    /**
     * 修改
     * @param contrast
     * @return
     */
    @ApiOperation(value = "修改信息", notes = "根据url的id来指定修改对象，并根据传过来的信息来修改详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "contrast", value = "详细实体contrast", required = true, dataType = "ContrastDto")
    })
    @PutMapping("/{id}")
    public AjaxResult updateContrast(@PathVariable String id, @RequestBody @Validated({ValidationGroups.Update.class}) ContrastDto contrast) {
        ContrastEntity contrastEntity = contrastService.updateContrast(contrast);
        return AjaxResult.success(contrastMapper.toVO(contrastEntity));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public AjaxResult deleteContrastById(@PathVariable String id) {
        contrastService.deleteContrastById(id);
        return AjaxResult.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除角色", notes = "根据url的ids来批量删除对象")
    @ApiImplicitParam(name = "ids", value = "ID集合", required = true, dataType = "List", paramType = "path")
    @DeleteMapping("/batch/{ids}")
    public AjaxResult deleteContrastBatch(@PathVariable List<String> ids) {
        contrastService.deleteContrastBatch(ids);
        return AjaxResult.success();
    }

    @GetMapping("/tree")
    public AjaxResult getContrastTree() {
        List<ContrastTreeVo> list = contrastService.getContrastTree();
        return AjaxResult.success(list);
    }

    /**
     * 分页查询统计信息
     *
     * @param contrastQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contrastQuery", value = "查询实体contrastQuery", required = true, dataTypeClass = ContrastQuery.class)
    })
    @GetMapping("/stat")
    public AjaxResult contrastStat(ContrastQuery contrastQuery) {
        QueryWrapper<ContrastEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(contrastQuery.getSourceName()), "c.source_name", contrastQuery.getSourceName());
        queryWrapper.like(StrUtil.isNotBlank(contrastQuery.getTableName()), "c.table_name", contrastQuery.getTableName());
        queryWrapper.like(StrUtil.isNotBlank(contrastQuery.getColumnName()), "c.column_name", contrastQuery.getColumnName());
        IPage<ContrastEntity> page = contrastService.statistic(new Page<>(contrastQuery.getPageNum(), contrastQuery.getPageSize()), queryWrapper);
        List<ContrastStatisticVo> collect = page.getRecords().stream().map(contrastMapper::toSVO).collect(Collectors.toList());
        JsonPage<ContrastStatisticVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }
}
