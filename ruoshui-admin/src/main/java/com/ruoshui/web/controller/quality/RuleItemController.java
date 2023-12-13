package com.ruoshui.web.controller.quality;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.quality.entity.RuleItemEntity;
import com.ruoshui.quality.mapstruct.RuleItemMapper;
import com.ruoshui.quality.query.RuleItemQuery;
import com.ruoshui.quality.service.RuleItemService;
import com.ruoshui.quality.vo.RuleItemVo;
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
 * 规则核查项信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-10-15
 */
@Api(tags = {"规则核查项信息表"})
@RestController
@RequestMapping("/quality/ruleItems")
public class RuleItemController extends BaseController {

    @Resource
    private RuleItemService ruleItemService;

    @Resource
    private RuleItemMapper ruleItemMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getRuleItemById(@PathVariable String id) {
        RuleItemEntity ruleItemEntity = ruleItemService.getRuleItemById(id);
        return AjaxResult.success(ruleItemMapper.toVO(ruleItemEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public AjaxResult getRuleTypeList(RuleItemQuery ruleItemQuery) {
        QueryWrapper<RuleItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(ruleItemQuery.getRuleTypeId()), "rule_type_id", ruleItemQuery.getRuleTypeId());
        List<RuleItemEntity> list = ruleItemService.list(queryWrapper);
        return AjaxResult.success(list);
    }

    /**
     * 分页查询信息
     *
     * @param ruleItemQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleItemQuery", value = "查询实体ruleItemQuery", required = true, dataTypeClass = RuleItemQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getRuleItemPage(RuleItemQuery ruleItemQuery) {
        QueryWrapper<RuleItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(ruleItemQuery.getRuleTypeId()), "rule_type_id", ruleItemQuery.getRuleTypeId());
        IPage<RuleItemEntity> page = ruleItemService.page(new Page<>(ruleItemQuery.getPageNum(), ruleItemQuery.getPageSize()), queryWrapper);
        List<RuleItemVo> collect = page.getRecords().stream().map(ruleItemMapper::toVO).collect(Collectors.toList());
        JsonPage<RuleItemVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }
}
