package com.ruoshui.web.controller.quality;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.quality.entity.RuleLevelEntity;
import com.ruoshui.quality.mapstruct.RuleLevelMapper;
import com.ruoshui.quality.query.RuleLevelQuery;
import com.ruoshui.quality.service.RuleLevelService;
import com.ruoshui.quality.vo.RuleLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 规则级别信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-10-14
 */
@Api(tags = {"规则级别信息表"})
@RestController
@RequestMapping("/quality/ruleLevels")
public class RuleLevelController extends BaseController {

    @Autowired
    private RuleLevelService ruleLevelService;

    @Autowired
    private RuleLevelMapper ruleLevelMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getRuleLevelById(@PathVariable String id) {
        RuleLevelEntity ruleLevelEntity = ruleLevelService.getRuleLevelById(id);
        return AjaxResult.success(ruleLevelMapper.toVO(ruleLevelEntity));
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping("/list")
    public AjaxResult getRuleTypeList() {
        List<RuleLevelEntity> list = ruleLevelService.list(Wrappers.emptyWrapper());
        return AjaxResult.success(list);
    }

    /**
     * 分页查询信息
     *
     * @param ruleLevelQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleLevelQuery", value = "查询实体ruleLevelQuery", required = true, dataTypeClass = RuleLevelQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getRuleLevelPage(RuleLevelQuery ruleLevelQuery) {
        QueryWrapper<RuleLevelEntity> queryWrapper = new QueryWrapper<>();
        IPage<RuleLevelEntity> page = ruleLevelService.page(new Page<>(ruleLevelQuery.getPageNum(), ruleLevelQuery.getPageSize()), queryWrapper);
        List<RuleLevelVo> collect = page.getRecords().stream().map(ruleLevelMapper::toVO).collect(Collectors.toList());
        JsonPage<RuleLevelVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }
}
