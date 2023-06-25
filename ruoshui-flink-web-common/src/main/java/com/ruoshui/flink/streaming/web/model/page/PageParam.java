package com.ruoshui.flink.streaming.web.model.page;

import lombok.Data;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-15
 * @time 23:27
 */
@Data
public class PageParam {

    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页数量
     */
    private int pageSize = 15;

}
