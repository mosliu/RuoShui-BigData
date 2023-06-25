package com.ruoshui.flink.ao;

import java.io.UnsupportedEncodingException;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-20
 * @time 23:11
 */
public interface JobServerAO {

    /**
     * 启动任务
     *
     * @author xinjingruoshui
     * @date 2022-07-20
     * @time 23:12
     */
    void start(Long id, Long savepointId, String userName) throws UnsupportedEncodingException;


    /**
     * 关闭任务
     *
     * @author xinjingruoshui
     * @date 2022-07-20
     * @time 23:12
     */
    void stop(Long id, String userName);


    /**
     * 执行savepoint
     *
     * @author xinjingruoshui
     * @date 2022-09-21
     * @time 00:41
     */
    void savepoint(Long id);


    /**
     * 开启配置
     *
     * @author xinjingruoshui
     * @date 2022-08-12
     * @time 21:14
     */
    void open(Long id, String userName);

    /**
     * 关闭配置
     *
     * @author xinjingruoshui
     * @date 2022-08-12
     * @time 21:14
     */
    void close(Long id, String userName);
}
