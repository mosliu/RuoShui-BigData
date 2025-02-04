package com.ruoshui.bigdata.service;

import com.ruoshui.bigdata.dto.DataXJsonBuildDto;

/**
 * com.wugui.datax json构建服务层接口
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/8/1
 */
public interface DataxJsonService {

    /**
     * build datax json
     *
     * @param dto
     * @return
     */
    String buildJobJson(DataXJsonBuildDto dto);
}
