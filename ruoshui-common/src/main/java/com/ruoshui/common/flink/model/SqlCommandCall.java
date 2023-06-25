package com.ruoshui.common.flink.model;


import com.ruoshui.common.flink.enums.SqlCommand;
import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-06-23
 * @time 02:56
 */
@Data
public class SqlCommandCall {

    public SqlCommand sqlCommand;

    public String[] operands;

    public SqlCommandCall(SqlCommand sqlCommand, String[] operands) {
        this.sqlCommand = sqlCommand;
        this.operands = operands;
    }

    public SqlCommandCall(String[] operands) {
        this.operands = operands;
    }

    public SqlCommand getSqlCommand() {
        return sqlCommand;
    }

    public void setSqlCommand(SqlCommand sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public String[] getOperands() {
        return operands;
    }

    public void setOperands(String[] operands) {
        this.operands = operands;
    }
}
