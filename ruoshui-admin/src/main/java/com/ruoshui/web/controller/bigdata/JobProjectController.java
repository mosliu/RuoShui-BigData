package com.ruoshui.web.controller.bigdata;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ruoshui.bigdata.entity.JobProject;
import com.ruoshui.bigdata.service.JobProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * project manage controller
 *
 * @author jingwk 2022-05-24 16:13:16
 */
@RestController
@RequestMapping("/api/jobProject")
@Api(tags = "项目管理模块")
public class JobProjectController extends BaseController {

    @Resource
    private JobProjectService jobProjectService;


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("分页查询所有数据")
    @PreAuthorize("@ss.hasPermi('datax:jobProject:list')")
    public R<IPage<JobProject>> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return success(jobProjectService.getProjectListPaging(pageSize, pageNo, searchVal));
    }

    /**
     * Get all project
     *
     * @return
     */
    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('datax:jobProject:query')")
    public R<List<JobProject>> selectList() {
        QueryWrapper<JobProject> query = new QueryWrapper();
        query.eq("flag", true);
        return success(jobProjectService.list(query));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    @PreAuthorize("@ss.hasPermi('datax:jobProject:query')")
    public R<JobProject> selectOne(@PathVariable Serializable id) {
        return success(this.jobProjectService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    @PreAuthorize("@ss.hasPermi('datax:jobProject:add')")
    public R<Boolean> insert(HttpServletRequest request, @RequestBody JobProject entity) {
        entity.setUserId(getUserId());
        return success(this.jobProjectService.save(entity));
    }


    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改数据")
    @PreAuthorize("@ss.hasPermi('datax:jobProject:edit')")
    public R<Boolean> update(@RequestBody JobProject entity) {
        JobProject project = jobProjectService.getById(entity.getId());
        project.setName(entity.getName());
        project.setDescription(entity.getDescription());
        return success(this.jobProjectService.updateById(entity));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除数据")
    @PreAuthorize("@ss.hasPermi('datax:jobProject:remove')")
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return success(this.jobProjectService.removeByIds(idList));
    }
}