package com.ruoshui.web.controller.metadata;


import com.ruoshui.common.core.controller.BaseController;
import com.ruoshui.common.core.domain.AjaxResult;
import com.ruoshui.metadata.dto.SqlConsoleDto;
import com.ruoshui.metadata.service.SqlConsoleService;
import com.ruoshui.metadata.validate.ValidationGroups;
import com.ruoshui.metadata.vo.SqlConsoleVo;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/sql")
public class SqlConsoleController extends BaseController {

    @Resource
    private SqlConsoleService sqlConsoleService;

    @PostMapping("/run")
    public AjaxResult sqlRun(@RequestBody @Validated SqlConsoleDto sqlConsoleDto) throws SQLException {
        List<SqlConsoleVo> list = sqlConsoleService.sqlRun(sqlConsoleDto);
        return AjaxResult.success(list);
    }

    @PostMapping("/stop")
    public AjaxResult sqlStop(@RequestBody @Validated({ValidationGroups.Other.class}) SqlConsoleDto sqlConsoleDto){
        sqlConsoleService.sqlStop(sqlConsoleDto);
        return AjaxResult.success();
    }
}
