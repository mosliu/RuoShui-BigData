package com.ruoshui.common.database;




import com.ruoshui.common.database.constants.DbType;
import com.ruoshui.common.database.dialect.DialectRegistry;
import com.ruoshui.common.database.service.DbDialect;


/**
 * 方言工厂类
 *
 * @author yuwei
 * @since 2020-03-14
 */
public class DialectFactory {

    private static final DialectRegistry DIALECT_REGISTRY = new DialectRegistry();

    public static DbDialect getDialect(DbType dbType) {
        return DIALECT_REGISTRY.getDialect(dbType);
    }
}
