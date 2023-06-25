package com.ruoshui.bigdata.tool.flinkx.writer;

import java.util.Map;

/**
 * oracle writer构建类
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/8/2
 */
public class OraclelWriter extends BaseWriterPlugin implements FlinkxWriterInterface {
    @Override
    public String getName() {
        return "oraclewriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
