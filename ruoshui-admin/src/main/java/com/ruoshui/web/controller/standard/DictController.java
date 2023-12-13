package com.ruoshui.web.controller.standard;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.metadata.validate.ValidationGroups;
import com.ruoshui.standard.dto.DictDto;
import com.ruoshui.standard.entity.DictEntity;
import com.ruoshui.standard.mapstruct.DictMapper;
import com.ruoshui.standard.query.DictQuery;
import com.ruoshui.standard.service.DictService;
import com.ruoshui.standard.vo.DictVo;
import com.ruoshui.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据标准字典表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Api(tags = {"数据标准字典表"})
@RestController
@RequestMapping("/standard/dicts")
public class DictController extends BaseController {

    @Resource
    private DictService dictService;

    @Resource
    private DictMapper dictMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getDictById(@PathVariable String id) {
        DictEntity dictEntity = dictService.getDictById(id);
        return AjaxResult.success(dictMapper.toVO(dictEntity));
    }

    /**
     * 分页查询信息
     *
     * @param dictQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictQuery", value = "查询实体dictQuery", required = true, dataTypeClass = DictQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getDictPage(DictQuery dictQuery) {
        QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(dictQuery.getTypeId()), "d.type_id", dictQuery.getTypeId());
        queryWrapper.like(StrUtil.isNotBlank(dictQuery.getGbCode()), "d.gb_code", dictQuery.getGbCode());
        queryWrapper.like(StrUtil.isNotBlank(dictQuery.getGbName()), "d.gb_name", dictQuery.getGbName());
        IPage<DictEntity> page = dictService.page(new Page<>(dictQuery.getPageNum(), dictQuery.getPageSize()), queryWrapper);
        List<DictVo> collect = page.getRecords().stream().map(dictMapper::toVO).collect(Collectors.toList());
        JsonPage<DictVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }

    /**
     * 添加
     * @param dict
     * @return
     */
    @ApiOperation(value = "添加信息", notes = "根据dict对象添加信息")
    @ApiImplicitParam(name = "dict", value = "详细实体dict", required = true, dataType = "DictDto")
    @PostMapping()
    public AjaxResult saveDict(@RequestBody @Validated({ValidationGroups.Insert.class}) DictDto dict) {
        DictEntity dictEntity = dictService.saveDict(dict);
        return AjaxResult.success(dictMapper.toVO(dictEntity));
    }

    /**
     * 修改
     * @param dict
     * @return
     */
    @ApiOperation(value = "修改信息", notes = "根据url的id来指定修改对象，并根据传过来的信息来修改详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "dict", value = "详细实体dict", required = true, dataType = "DictDto")
    })
    @PutMapping("/{id}")
    public AjaxResult updateDict(@PathVariable String id, @RequestBody @Validated({ValidationGroups.Update.class}) DictDto dict) {
        DictEntity dictEntity = dictService.updateDict(dict);
        return AjaxResult.success(dictMapper.toVO(dictEntity));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    public AjaxResult deleteDictById(@PathVariable String id) {
        dictService.deleteDictById(id);
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
    public AjaxResult deleteDictBatch(@PathVariable List<String> ids) {
        dictService.deleteDictBatch(ids);
        return AjaxResult.success();
    }

    /**
     * 刷新字典缓存
     *
     * @return
     */
    @GetMapping("/refresh")
    public AjaxResult refreshDict() {
        dictService.refreshDict();
        return AjaxResult.success();
    }
}
