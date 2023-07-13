package com.ruoshui.metadata.service;



import com.ruoshui.metadata.dto.SqlConsoleDto;
import com.ruoshui.metadata.vo.SqlConsoleVo;

import java.sql.SQLException;
import java.util.List;

public interface SqlConsoleService {
    
    List<SqlConsoleVo> sqlRun(SqlConsoleDto sqlConsoleDto) throws SQLException;

    void sqlStop(SqlConsoleDto sqlConsoleDto);
}
