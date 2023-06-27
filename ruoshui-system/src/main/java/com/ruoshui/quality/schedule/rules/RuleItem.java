package com.ruoshui.quality.schedule.rules;



import com.ruoshui.common.database.constants.DbType;

import java.util.Map;

public interface RuleItem {

    String parse(DbType dbType, String table, String column, Map<String, Object> map);

    String code();
}
