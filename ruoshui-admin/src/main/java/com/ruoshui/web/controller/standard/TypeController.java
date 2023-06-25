package com.ruoshui.web.controller.standard;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.metadata.validate.ValidationGroups;
import com.ruoshui.standard.dto.TypeDto;
import com.ruoshui.standard.entity.TypeEntity;
import com.ruoshui.standard.mapstruct.TypeMapper;
import com.ruoshui.standard.query.TypeQuery;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.standard.service.TypeService;
import com.ruoshui.standard.vo.TypeVo;
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
 * 数据标准类别表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Api(tags = {"标准类别信息表"})
@RestController
@RequestMapping("/standard/types")
public class TypeController extends BaseController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getTypeById(@PathVariable String id) {
        TypeEntity typeEntity = typeService.getTypeById(id);
        return AjaxResult.success(typeMapper.toVO(typeEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public AjaxResult getTypeList() {
        QueryWrapper<TypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", DataConstant.EnableState.ENABLE.getKey());
        List<TypeEntity> list = typeService.list(queryWrapper);
        List<TypeVo> collect = list.stream().map(typeMapper::toVO).collect(Collectors.toList());
        return AjaxResult.success(collect);
    }

    /**
     * 分页查询信息
     *
     * @param typeQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeQuery", value = "查询实体typeQuery", required = true, dataTypeClass = TypeQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getTypePage(TypeQuery typeQuery) {
        QueryWrapper<TypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(typeQuery.getGbTypeCode()), "gb_type_code", typeQuery.getGbTypeCode());
        queryWrapper.like(StrUtil.isNotBlank(typeQuery.getGbTypeName()), "gb_type_name", typeQuery.getGbTypeName());
        IPage<TypeEntity> page = typeService.page(new Page<>(typeQuery.getPageNum(), typeQuery.getPageSize()), queryWrapper);
        List<TypeVo> collect = page.getRecords().stream().map(typeMapper::toVO).collect(Collectors.toList());
        JsonPage<TypeVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }

    /**
     * 添加
     * @param type
     * @return
     */
    @ApiOperation(value = "添加信息", notes = "根据type对象添加信息")
    @ApiImplicitParam(name = "type", value = "详细实体type", required = true, dataType = "TypeDto")
    @PostMapping()
    public AjaxResult saveType(@RequestBody @Validated({ValidationGroups.Insert.class}) TypeDto type) {
        TypeEntity typeEntity = typeService.saveType(type);
        return AjaxResult.success(typeMapper.toVO(typeEntity));
    }

    /**
     * 修改
     * @param type
     * @return
     */
    @ApiOperation(value = "修改信息", notes = "根据url的id来指定修改对象，并根据传过来的信息来修改详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "详细实体type", required = true, dataType = "TypeDto")
    })
    @PutMapping("/{id}")
    public AjaxResult updateType(@PathVariable String id, @RequestBody @Validated({ValidationGroups.Update.class}) TypeDto type) {
        TypeEntity typeEntity = typeService.updateType(type);
        return AjaxResult.success(typeMapper.toVO(typeEntity));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public AjaxResult deleteTypeById(@PathVariable String id) {
        typeService.deleteTypeById(id);
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
    public AjaxResult deleteTypeBatch(@PathVariable List<String> ids) {
        typeService.deleteTypeBatch(ids);
        return AjaxResult.success();
    }
}
