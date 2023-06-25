package com.ruoshui.bigdata.service;

import com.ruoshui.bigdata.dto.JsonBuildDto;

/**
 * com.ruoshui json构建服务层接口
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/8/1
 */
public interface JsonService {

    /**
     * build flinkx json
     *
     * @param dto
     * @return
     */
    String buildJobFlinkxJson(JsonBuildDto dto);
    String buildJobDataxJson(JsonBuildDto dto);
}
