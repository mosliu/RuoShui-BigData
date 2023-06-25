package com.ruoshui.web.controller.standard;

import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.standard.dto.ManualMappingDto;
import com.ruoshui.standard.service.DictMappingService;
import io.swagger.annotations.Api;
import com.ruoshui.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"字典对照映射"})
@RestController
@RequestMapping("/standard/mappings")
public class DictMappingController extends BaseController {

    @Autowired
    private DictMappingService dictMappingService;

    @GetMapping("/{id}")
    public AjaxResult getDictMapping(@PathVariable String id) {
        Map<String, Object> map = dictMappingService.getDictMapping(id);
        return AjaxResult.success(map);
    }

    @PostMapping("/auto/{id}")
    public AjaxResult dictAutoMapping(@PathVariable String id) {
        dictMappingService.dictAutoMapping(id);
        return AjaxResult.success();
    }

    @PostMapping("/manual")
    public AjaxResult dictManualMapping(@RequestBody @Validated ManualMappingDto manualMappingDto) {
        dictMappingService.dictManualMapping(manualMappingDto);
        return AjaxResult.success();
    }

    @PostMapping("/cancel/{id}")
    public AjaxResult dictCancelMapping(@PathVariable String id) {
        dictMappingService.dictCancelMapping(id);
        return AjaxResult.success();
    }
}
