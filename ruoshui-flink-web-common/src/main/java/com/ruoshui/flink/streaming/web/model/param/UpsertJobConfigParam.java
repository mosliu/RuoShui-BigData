package com.ruoshui.flink.streaming.web.model.param;

import com.ruoshui.flink.streaming.web.enums.*;
import com.ruoshui.flink.streaming.web.model.dto.JobConfigDTO;
import com.ruoshui.common.flink.constant.SystemConstant;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-08-06
 * @time 22:22
 */
@Data
public class UpsertJobConfigParam {

    private Long id;

    private Long flinkCluster;

    /**
     * 任务名称
     */
    private String jobName;


    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     *
     */
    private String deployMode;

    /**
     * flink运行配置
     */
    private String flinkRunConfig;

    /**
     * Checkpoint信息
     */
    private String flinkCheckpointConfig;


    /**
     * sql语句
     */
    private String flinkSql;


    /**
     * 三方jar udf、 连接器 等jar如http://xxx.xxx.com/flink-streaming-udf.jar
     */
    private String extJarPath;


    /**
     * @see AlarmTypeEnum
     */
    private String alarmTypes;


    /**
     * 任务类型 0:sql 1:自定义jar'
     */
    private Integer jobType;

    /**
     * 启动jar可能需要使用的自定义参数
     */
    private String customArgs;

    /**
     * 程序入口类
     */
    private String customMainClass;

    /**
     * 自定义jar的http地址 如:http://ccblog.cn/xx.jar
     */
    private String customJarUrl;


    /**
     * 1:开启 0: 关闭
     */
    private Integer isOpen = YN.N.getValue();

    /**
     * @see JobConfigStatus
     * 1:运行中 0: 停止中 -1:运行失败
     */
    private Integer status = JobConfigStatus.STOP.getCode();

    private String cron;


    public static JobConfigDTO toDTO(UpsertJobConfigParam upsertJobConfigParam) {
        if (upsertJobConfigParam == null) {
            return null;
        }
        JobConfigDTO jobConfigDTO = new JobConfigDTO();
        jobConfigDTO.setId(upsertJobConfigParam.getId());
        jobConfigDTO.setFlinkCluster(upsertJobConfigParam.getFlinkCluster());
        jobConfigDTO.setDeployModeEnum(DeployModeEnum.getModel(upsertJobConfigParam.getDeployMode()));
        jobConfigDTO.setJobName(upsertJobConfigParam.getJobName());
        jobConfigDTO.setJobDesc(upsertJobConfigParam.getJobDesc());
        jobConfigDTO.setFlinkRunConfig(upsertJobConfigParam.getFlinkRunConfig());
        jobConfigDTO.setFlinkCheckpointConfig(upsertJobConfigParam.getFlinkCheckpointConfig());
        jobConfigDTO.setCron(upsertJobConfigParam.getCron());

        if (StringUtils.isNotEmpty(upsertJobConfigParam.getFlinkSql())
                && (JobTypeEnum.SQL_STREAMING.getCode() == upsertJobConfigParam.getJobType()
                || JobTypeEnum.SQL_BATCH.getCode() == upsertJobConfigParam.getJobType())) {
            jobConfigDTO.setFlinkSql(upsertJobConfigParam.getFlinkSql());
        } else {
            jobConfigDTO.setFlinkSql(SystemConstant.SPACE);
        }

        jobConfigDTO.setJobTypeEnum(JobTypeEnum.getJobTypeEnum(upsertJobConfigParam.getJobType()));
        jobConfigDTO.setCustomArgs(upsertJobConfigParam.getCustomArgs());
        jobConfigDTO.setCustomMainClass(upsertJobConfigParam.getCustomMainClass());
        jobConfigDTO.setCustomJarUrl(upsertJobConfigParam.getCustomJarUrl());

        jobConfigDTO.setIsOpen(upsertJobConfigParam.getIsOpen());
        jobConfigDTO.setStatus(JobConfigStatus.getJobConfigStatus(upsertJobConfigParam.getStatus()));
        if (StringUtils.isNotEmpty(upsertJobConfigParam.getExtJarPath())) {
            jobConfigDTO.setExtJarPath(upsertJobConfigParam.getExtJarPath().trim());
        }else{
            jobConfigDTO.setExtJarPath(SystemConstant.SPACE);
        }
        if (StringUtils.isNotEmpty(upsertJobConfigParam.getAlarmTypes())) {
            List<AlarmTypeEnum> list = new ArrayList<>();
            String[] types = upsertJobConfigParam.getAlarmTypes().split(",");
            for (String code : types) {
                AlarmTypeEnum alarmTypeEnum = AlarmTypeEnum.getAlarmTypeEnum(Integer.valueOf(code));
                if (alarmTypeEnum != null) {
                    list.add(alarmTypeEnum);
                }
            }
            if (CollectionUtils.isNotEmpty(list)) {
                jobConfigDTO.setAlarmTypeEnumList(list);
            }
        }

        return jobConfigDTO;
    }


}
