package com.ruoshui.metadata.service;



import com.ruoshui.metadata.dto.SqlConsoleDto;
import com.ruoshui.metadata.vo.SqlConsoleVo;

import java.util.List;

public interface SqlConsoleService {
    
    List<SqlConsoleVo> sqlRun(SqlConsoleDto sqlConsoleDto);

    void sqlStop(SqlConsoleDto sqlConsoleDto);
}
