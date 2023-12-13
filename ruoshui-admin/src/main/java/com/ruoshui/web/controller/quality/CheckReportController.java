package com.ruoshui.web.controller.quality;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.core.database.core.JsonPage;
import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.entity.DataReportEntity;
import com.ruoshui.quality.mapstruct.CheckReportMapper;
import com.ruoshui.quality.query.CheckReportQuery;
import com.ruoshui.quality.service.CheckReportService;
import com.ruoshui.quality.vo.CheckReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 核查报告信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Api(tags = {"核查报告信息表"})
@RestController
@RequestMapping("/quality/checkReports")
public class CheckReportController extends BaseController {

    @Resource
    private CheckReportService checkReportService;

    @Resource
    private CheckReportMapper checkReportMapper;

    /**
     * 通过ID查询信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    public AjaxResult getCheckReportById(@PathVariable String id) {
        CheckReportEntity checkReportEntity = checkReportService.getCheckReportById(id);
        return AjaxResult.success(checkReportMapper.toVO(checkReportEntity));
    }

    /**
     * 分页查询信息
     *
     * @param checkReportQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkReportQuery", value = "查询实体checkReportQuery", required = true, dataTypeClass = CheckReportQuery.class)
    })
    @GetMapping("/page")
    public AjaxResult getCheckReportPage(CheckReportQuery checkReportQuery) {
        QueryWrapper<CheckReportEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(checkReportQuery.getRuleTypeId()), "r.rule_type_id", checkReportQuery.getRuleTypeId());
        queryWrapper.like(StrUtil.isNotBlank(checkReportQuery.getRuleName()), "r.rule_name", checkReportQuery.getRuleName());
        queryWrapper.like(StrUtil.isNotBlank(checkReportQuery.getRuleSource()), "r.rule_source", checkReportQuery.getRuleSource());
        queryWrapper.like(StrUtil.isNotBlank(checkReportQuery.getRuleTable()), "r.rule_table", checkReportQuery.getRuleTable());
        queryWrapper.like(StrUtil.isNotBlank(checkReportQuery.getRuleColumn()), "r.rule_column", checkReportQuery.getRuleColumn());
        // 确定唯一核查报告
        queryWrapper.apply("c.check_batch = r.last_check_batch");
        IPage<CheckReportEntity> page = checkReportService.page(new Page<>(checkReportQuery.getPageNum(), checkReportQuery.getPageSize()), queryWrapper);
        List<CheckReportVo> collect = page.getRecords().stream().map(checkReportMapper::toVO).collect(Collectors.toList());
        JsonPage<CheckReportVo> jsonPage = new JsonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), collect);
        return AjaxResult.success(jsonPage);
    }

    @GetMapping("/getReportBySource")
    public AjaxResult getReportBySource(CheckReportQuery checkReportQuery) {
        LocalDate checkDate = checkReportQuery.getCheckDate();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(checkDate);
        List<DataReportEntity> list = checkReportService.getReportBySource(date);
        return AjaxResult.success(list);
    }

    @GetMapping("/getReportByType")
    public AjaxResult getReportByType(CheckReportQuery checkReportQuery) {
        LocalDate checkDate = checkReportQuery.getCheckDate();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(checkDate);
        List<DataReportEntity> list = checkReportService.getReportByType(date);
        return AjaxResult.success(list);
    }

    @GetMapping("/getReportDetail")
    public AjaxResult getReportDetail(CheckReportQuery checkReportQuery) {
        LocalDate checkDate = checkReportQuery.getCheckDate();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(checkDate);
        Map<String, Object> map = checkReportService.getReportDetail(date);
        return AjaxResult.success(map);
    }
}
