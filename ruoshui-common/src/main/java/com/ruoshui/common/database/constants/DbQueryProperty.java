package com.ruoshui.common.database.constants;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DbQueryProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dbType;
    private String host;
    private String username;
    private String password;
    private Integer port;
    private String dbName;
    private String sid;

    /**
     * 参数合法性校验
     */
    public void viald() {
        if (StringUtils.isEmpty(dbType) || StringUtils.isEmpty(host) ||
                StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(port)) {
            throw new RuntimeException("参数不完整");
        }
        if (DbType.OTHER.getDb().equals(dbType)) {
            throw new RuntimeException("不支持的数据库类型");
        }
    }
}
