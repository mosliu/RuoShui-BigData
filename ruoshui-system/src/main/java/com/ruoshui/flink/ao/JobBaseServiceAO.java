package com.ruoshui.flink.ao;

import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.flink.streaming.web.model.dto.JobRunParamDTO;

import java.io.UnsupportedEncodingException;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2021/3/28
 * @time 10:01
 */
public interface JobBaseServiceAO {

    /**
     * 提交任务前校验数据
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 10:12
     */
    void checkStart(JobConfigDTO jobConfigDTO);

    /**
     * Savepoint前校验数据
     *
     * @author xinjingruoshui
     * @date 2021/3/31
     * @time 19:54
     */
    void checkSavepoint(JobConfigDTO jobConfigDTO);

    /**
     * 管配置检查
     *
     * @author xinjingruoshui
     * @date 2021/3/31
     * @time 20:06
     */
    void checkClose(JobConfigDTO jobConfigDTO);

    /**
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 10:12
     */
    Long insertJobRunLog(JobConfigDTO jobConfigDTO, String userName);


    /**
     * 将配置的sql 写入文件并且返回运行所需参数
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 10:37
     */
    JobRunParamDTO writeSqlToFile(JobConfigDTO jobConfigDTO,String serverIp,String serverUser,String serverPassword) throws UnsupportedEncodingException;


    /**
     * 异步执行任务
     *
     * @author xinjingruoshui
     * @date 2021/3/28
     * @time 10:55
     */
    void aSyncExecJob(final JobRunParamDTO jobRunParamDTO, final JobConfigDTO jobConfig,
                      final Long jobRunLogId, final String savepointPath,String serverIp,String serverUser,String serverPassword);


}
