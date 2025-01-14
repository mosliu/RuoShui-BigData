package com.ruoshui.web.controller.bigdata;


import com.ruoshui.core.biz.model.ReturnT;
import com.ruoshui.bigdata.core.cron.CronExpression;
import com.ruoshui.bigdata.core.util.I18nUtil;
import com.ruoshui.bigdata.entity.JobTemplate;
import com.ruoshui.bigdata.service.JobTemplateService;
import com.ruoshui.core.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * template controller
 *
 * @author jingwk 2019-12-22 16:13:16
 */
@Api(tags = "任务配置接口")
@RestController
@RequestMapping("/api/jobTemplate")
public class JobTemplateController extends BaseController{

    @Autowired
    private JobTemplateService jobTemplateService;

    @GetMapping("/pageList")
    @ApiOperation("任务模板列表")
    @PreAuthorize("@ss.hasPermi('datax:jobTemplate:list')")
    public ReturnT<Map<String, Object>> pageList(@RequestParam(required = false, defaultValue = "0") int current,
                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                 int jobGroup, String jobDesc, String executorHandler, int userId, Integer[] projectIds) {

        return new ReturnT<>(jobTemplateService.pageList((current-1)*size, size, jobGroup, jobDesc, executorHandler, userId, projectIds));
    }

    @PostMapping("/add")
    @ApiOperation("添加任务模板")
    @PreAuthorize("@ss.hasPermi('datax:jobTemplate:add')")
    public ReturnT<String> add(HttpServletRequest request, @RequestBody JobTemplate jobTemplate) {
        jobTemplate.setUserId(getUserId());
        return jobTemplateService.add(jobTemplate);
    }

    @PostMapping("/update")
    @ApiOperation("更新任务")
    @PreAuthorize("@ss.hasPermi('datax:jobTemplate:edit')")
    public ReturnT<String> update(HttpServletRequest request,@RequestBody JobTemplate jobTemplate) {
        jobTemplate.setUserId(getUserId());
        return jobTemplateService.update(jobTemplate);
    }

    @PostMapping(value = "/remove/{id}")
    @ApiOperation("移除任务模板")
    @PreAuthorize("@ss.hasPermi('datax:jobTemplate:remove')")
    public ReturnT<String> remove(@PathVariable(value = "id") int id) {
        return jobTemplateService.remove(id);
    }

    @GetMapping("/nextTriggerTime")
    @ApiOperation("获取近5次触发时间")
    public ReturnT<List<String>> nextTriggerTime(String cron) {
        List<String> result = new ArrayList<>();
        try {
            CronExpression cronExpression = new CronExpression(cron);
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = cronExpression.getNextValidTimeAfter(lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_invalid"));
        }
        return new ReturnT<>(result);
    }
}
