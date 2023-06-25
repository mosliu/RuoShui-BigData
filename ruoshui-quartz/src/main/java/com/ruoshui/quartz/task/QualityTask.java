package com.ruoshui.quartz.task;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.aspose.words.net.System.Data.DataException;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.common.database.constants.DbQueryProperty;
import com.ruoshui.common.database.service.DataSourceFactory;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.common.utils.SpringContextHolder;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.core.util.DateUtil;
import com.ruoshui.metadata.dto.DbSchema;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.service.MetadataSourceService;
import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.entity.CheckRuleEntity;
import com.ruoshui.quality.entity.ScheduleLogEntity;
import com.ruoshui.quality.service.CheckReportService;
import com.ruoshui.quality.service.CheckRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Component("QualityTask")
public class QualityTask {

    @Autowired
    private CheckRuleService checkRuleService;

    @Autowired
    private CheckReportService checkReportService;

    @Autowired
    private static MetadataSourceService metadataSourceService;

    public void task() {
        // 结果集
        List<CheckReportEntity> result = new ArrayList<>();
        // 获取可执行的核查规则
        List<CheckRuleEntity> list = checkRuleService.list(Wrappers.<CheckRuleEntity>lambdaQuery().eq(CheckRuleEntity::getStatus, DataConstant.TrueOrFalse.TRUE.getKey()));
        // 定义固定长度的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                new BasicThreadFactory.Builder().namingPattern("executor-schedule-pool-%d").daemon(true).build());
        // 定义计数器
        final CountDownLatch latch = new CountDownLatch(list.size());
        // Callable用于产生结果
        List<TaskHander> tasks = new ArrayList<>();
        list.stream().forEach(rule -> {
            TaskHander task = new TaskHander(latch, rule);
            tasks.add(task);
        });
        List<Future<CheckReportEntity>> futures;
        try {
            futures = threadPoolExecutor.invokeAll(tasks);
            // 处理线程返回结果
            for (Future<CheckReportEntity> future : futures) {
                result.add(future.get());
            }
            // 主线程阻塞，等待所有子线程执行完成
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭线程池
        threadPoolExecutor.shutdown();
        // 核查报告
        result.stream().forEach(s -> {
            // 插入核查结果正常的数据
            String status = StrUtil.isBlank(s.getCheckResult()) ? DataConstant.TrueOrFalse.TRUE.getKey() : DataConstant.TrueOrFalse.FALSE.getKey();
            if (StrUtil.isBlank(s.getCheckResult())) {
                s.setCheckBatch(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
                checkReportService.save(s);
                // 更新最近核查批次号
                LambdaUpdateWrapper<CheckRuleEntity> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(CheckRuleEntity::getLastCheckBatch,DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
                updateWrapper.eq(CheckRuleEntity::getId, s.getCheckRuleId());
                checkRuleService.update(updateWrapper);
            }
            // 定时任务日志
            ScheduleLogEntity scheduleLogEntity = new ScheduleLogEntity();
            scheduleLogEntity.setExecuteJobId("1310823026538962945");
            scheduleLogEntity.setExecuteBatch(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
            scheduleLogEntity.setExecuteDate(s.getCheckDate());
            scheduleLogEntity.setExecuteRuleId(s.getCheckRuleId());
            scheduleLogEntity.setExecuteResult(s.getCheckResult());
            scheduleLogEntity.setStatus(status);
        });
    }

    static class TaskHander implements Callable<CheckReportEntity> {

        private CountDownLatch latch;
        private CheckRuleEntity checkRuleEntity;

        public TaskHander(CountDownLatch latch, CheckRuleEntity checkRuleEntity) {
            super();
            this.latch = latch;
            this.checkRuleEntity = checkRuleEntity;
        }

        @Override
        public CheckReportEntity call() {
            log.info("任务 - 规则id：{}，规则名称：{}， 时间：{}", checkRuleEntity.getId(), checkRuleEntity.getRuleName(), System.currentTimeMillis());
            CheckReportEntity checkReportEntity = new CheckReportEntity();
            checkReportEntity.setCheckRuleId(checkRuleEntity.getId());
            checkReportEntity.setCheckDate(LocalDateTime.now());
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                MetadataSourceEntity dataSource = Optional.ofNullable(metadataSourceService.getMetadataSourceById(checkRuleEntity.getRuleSourceId())).orElseThrow(() -> new DataException("获取数据源接口出错"));
                DbSchema dbSchema = dataSource.getDbSchema();
                DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                        dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
                DataSourceFactory dataSourceFactory = SpringContextHolder.getBean(DataSourceFactory.class);
                DbQuery dbQuery = Optional.ofNullable(dataSourceFactory.createDbQuery(dbQueryProperty)).orElseThrow(() -> new DataException("创建数据查询接口出错"));

                conn = dbQuery.getConnection();
                stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(checkRuleEntity.getRuleSql());
                while (rs.next()) {
                    Integer checkErrorCount = rs.getInt(1);
                    checkReportEntity.setCheckErrorCount(checkErrorCount);
                    Integer checkTotalCount = rs.getInt(2);
                    checkReportEntity.setCheckTotalCount(checkTotalCount);
                }
            } catch (Exception e) {
                checkReportEntity.setCheckResult(e.getMessage());
            } finally {
                latch.countDown();
                try {
                    if(rs != null){
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    checkReportEntity.setCheckResult("释放数据库连接出错");
                }
                return checkReportEntity;
            }
        }
    }
}
