package com.ruoshui.flink.streaming.web.model.dto;

import com.ruoshui.flink.streaming.web.common.SystemConstants;
import com.ruoshui.flink.streaming.web.enums.SysConfigEnum;
import lombok.Data;

import java.util.Map;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-17
 * @time 20:14
 */
@Data
public class JobRunParamDTO {

    /**
     * flink bin目录地址
     */
    private String flinkBinPath;

    /**
     * flink 运行参数 如：-yjm 1024m -ytm 2048m -yd -m yarn-cluster
     */
    private String flinkRunParam;

    /**
     * sql语句存放的目录
     */
    private String sqlPath;


    /**
     * checkpointConfig
     */
    private String flinkCheckpointConfig;

    /**
     * ruoshui-flink-savepoint 所在目录 如：/use/local/ruoshui-flink-savepoint
     */
    private String sysHome;

    /**
     * 主类jar地址
     */
    private String mainJarPath;


    public JobRunParamDTO(String flinkBinPath,
                          String flinkRunParam,
                          String sqlPath,
                          String sysHome,
                          String flinkCheckpointConfig) {
        this.flinkBinPath = flinkBinPath;
        this.flinkRunParam = flinkRunParam;
        this.sqlPath = sqlPath;
        this.sysHome = sysHome;
        this.flinkCheckpointConfig = flinkCheckpointConfig;
    }

    public static JobRunParamDTO buildJobRunParam(Map<String, String> systemConfigMap, JobConfigDTO jobConfigDTO, String sqlPath) {

        String flinkBinPath = SystemConstants.buildFlinkBin(systemConfigMap.get(SysConfigEnum.FLINK_HOME.getKey()));

        String flinkRunParam = jobConfigDTO.getFlinkRunConfig();

        String sysHome = systemConfigMap.get(SysConfigEnum.FLINK_STREAMING_PLATFORM_WEB_HOME.getKey());

        JobRunParamDTO jobRunParamDTO = new JobRunParamDTO(
                flinkBinPath,
                flinkRunParam,
                sqlPath,
                sysHome,
                jobConfigDTO.getFlinkCheckpointConfig()
        );

        return jobRunParamDTO;

    }
}
