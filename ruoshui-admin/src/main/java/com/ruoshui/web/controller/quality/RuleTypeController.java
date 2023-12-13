package com.ruoshui.web.controller.quality;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.entity.RuleTypeReportEntity;
import com.ruoshui.quality.mapstruct.RuleTypeMapper;
import com.ruoshui.quality.query.RuleTypeQuery;
import com.ruoshui.quality.service.RuleTypeService;
import com.ruoshui.quality.vo.RuleTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 规则类型信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Api(tags = {"规则类型信息表"})
@RestController
@RequestMapping("/quality/ruleTypes")
public class RuleTypeController extends BaseController {

    @Resource
    private RuleTypeService ruleTypeService;

    @Resource
    private RuleTypeMapper ruleTypeMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getRuleTypeById(@PathVariable String id) {
        RuleTypeEntity ruleTypeEntity = ruleTypeService.getRuleTypeById(id);
        return AjaxResult.success(ruleTypeMapper.toVO(ruleTypeEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public AjaxResult getRuleTypeList() {
        List<RuleTypeEntity> list = ruleTypeService.list(Wrappers.emptyWrapper());
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/report/list")
    public AjaxResult getRuleTypeListForReport() {
        List<RuleTypeReportEntity> list = ruleTypeService.getRuleTypeListForReport();
        return AjaxResult.success(list);
    }

    /**
     * 分页查询信息
     *
     * @param ruleTypeQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleTypeQuery", value = "查询实体ruleTypeQuery", required = true, dataTypeClass = RuleTypeQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getRuleTypePage(RuleTypeQuery ruleTypeQuery) {
        QueryWrapper<RuleTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(ruleTypeQuery.getName()), "name", ruleTypeQuery.getName());
        IPage<RuleTypeEntity> page = ruleTypeService.page(new Page<>(ruleTypeQuery.getPageNum(), ruleTypeQuery.getPageSize()), queryWrapper);
        List<RuleTypeVo> collect = page.getRecords().stream().map(ruleTypeMapper::toVO).collect(Collectors.toList());
        JsonPage<RuleTypeVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }
}
