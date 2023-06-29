/*
 Navicat Premium Data Transfer

 Source Server         : cluster03.toroidal.com_3306
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : cluster03.toroidal.com:3306
 Source Schema         : bigdata

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 26/06/2023 10:12:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alart_log
-- ----------------------------
DROP TABLE IF EXISTS `alart_log`;
CREATE TABLE `alart_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_config_id` bigint(20) NOT NULL COMMENT 'job_config的id  如果0代表的是测试,',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `type` tinyint(1) NOT NULL COMMENT '1:钉钉',
  `status` tinyint(1) NOT NULL COMMENT '1:成功 0:失败',
  `fail_log` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '失败原因',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_job_config_id`(`job_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '告警发送情况日志' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for dev_env_setting
-- ----------------------------
DROP TABLE IF EXISTS `dev_env_setting`;
CREATE TABLE `dev_env_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'property name',
  `prop_value` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'property value',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL COMMENT 'creator id',
  `flag` tinyint(4) NULL DEFAULT 1 COMMENT '0 not available, 1 available',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for ip_status
-- ----------------------------
DROP TABLE IF EXISTS `ip_status`;
CREATE TABLE `ip_status`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip',
  `status` int(11) NOT NULL COMMENT '1:运行 -1:停止 ',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '最后心跳时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_uk_ip`(`ip`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'web服务运行ip' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_alarm_config
-- ----------------------------
DROP TABLE IF EXISTS `job_alarm_config`;
CREATE TABLE `job_alarm_config`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) UNSIGNED NOT NULL COMMENT 'job_config主表id',
  `type` tinyint(1) NOT NULL COMMENT '类型 1:钉钉告警 2:url回调 3:异常自动拉起任务',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '更新版本号  ',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_index_job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '告警辅助配置表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for job_config
-- ----------------------------
DROP TABLE IF EXISTS `job_config`;
CREATE TABLE `job_config`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `deploy_mode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交模式: standalone 、yarn 、yarn-session ',
  `flink_run_config` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'flink运行配置',
  `flink_sql` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sql语句',
  `flink_checkpoint_config` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'checkPoint配置',
  `job_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行后的任务id',
  `is_open` tinyint(1) NOT NULL COMMENT '1:开启 0: 关闭',
  `status` tinyint(1) NOT NULL COMMENT '1:运行中 0: 停止中 -1:运行失败',
  `ext_jar_path` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'udf地址已经连接器jar 如http://xxx.xxx.com/flink-streaming-udf.jar',
  `last_start_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次启动时间',
  `last_run_log_id` bigint(20) NULL DEFAULT NULL COMMENT '最后一次日志',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '更新版本号 用于乐观锁',
  `job_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '任务类型 0:sql 1:自定义jar',
  `custom_args` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '启动jar可能需要使用的自定义参数',
  `custom_main_class` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '程序入口类',
  `custom_jar_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义jar的http地址 如:http://ccblog.cn/xx.jar',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `flinkCluster` bigint(20) NULL DEFAULT NULL COMMENT 'flink资源id',
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_index`(`job_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'flink任务配置表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_config_history
-- ----------------------------
DROP TABLE IF EXISTS `job_config_history`;
CREATE TABLE `job_config_history`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_config_id` bigint(20) NOT NULL COMMENT 'job_config主表Id',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `deploy_mode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交模式: standalone 、yarn 、yarn-session ',
  `flink_run_config` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'flink运行配置',
  `flink_sql` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'sql语句',
  `flink_checkpoint_config` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'checkPoint配置',
  `ext_jar_path` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'udf地址及连接器jar 如http://xxx.xxx.com/flink-streaming-udf.jar',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '更新版本号',
  `job_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '任务类型 0:sql 1:自定义jar',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_job_config_id`(`job_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 274 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'flink任务配置历史变更表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_group
-- ----------------------------
DROP TABLE IF EXISTS `job_group`;
CREATE TABLE `job_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行器名称',
  `order` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for job_info
-- ----------------------------
DROP TABLE IF EXISTS `job_info`;
CREATE TABLE `job_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '所属项目id',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL COMMENT '修改用户',
  `alarm_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位分钟',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  `job_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'datax运行脚本',
  `replace_param` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动态参数',
  `jvm_param` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'jvm参数',
  `inc_start_time` datetime(0) NULL DEFAULT NULL COMMENT '增量初始时间',
  `partition_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分区信息',
  `last_handle_code` int(11) NULL DEFAULT 0 COMMENT '最近一次执行状态',
  `replace_param_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '增量时间格式',
  `reader_table` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'reader表名称',
  `primary_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '增量表主键',
  `inc_start_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '增量初始id',
  `increment_type` tinyint(4) NULL DEFAULT 0 COMMENT '增量类型',
  `datasource_id` bigint(20) NULL DEFAULT NULL COMMENT '数据源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_jdbc_datasource
-- ----------------------------
DROP TABLE IF EXISTS `job_jdbc_datasource`;
CREATE TABLE `job_jdbc_datasource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `datasource_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源名称',
  `datasource` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源',
  `datasource_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Default' COMMENT '数据源分组',
  `database_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库名',
  `jdbc_username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `jdbc_password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `jdbc_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'jdbc url',
  `jdbc_driver_class` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'jdbc驱动类',
  `zk_adress` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0删除 1启用 2禁用',
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `orcschema` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'orcle库名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'jdbc数据源配置' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_lock
-- ----------------------------
DROP TABLE IF EXISTS `job_lock`;
CREATE TABLE `job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_lock
-- ----------------------------
INSERT INTO `job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for job_log
-- ----------------------------
DROP TABLE IF EXISTS `job_log`;
CREATE TABLE `job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `executor_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '调度-日志',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `process_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'datax进程Id',
  `max_id` bigint(20) NULL DEFAULT NULL COMMENT '增量表max id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11657 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `job_log_report`;
CREATE TABLE `job_log_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 419 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `job_logglue`;
CREATE TABLE `job_logglue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for job_permission
-- ----------------------------
DROP TABLE IF EXISTS `job_permission`;
CREATE TABLE `job_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `description` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_permission
-- ----------------------------

-- ----------------------------
-- Table structure for job_project
-- ----------------------------
DROP TABLE IF EXISTS `job_project`;
CREATE TABLE `job_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'project name',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL COMMENT 'creator id',
  `flag` tinyint(4) NULL DEFAULT 1 COMMENT '0 not available, 1 available',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'create time',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for job_registry
-- ----------------------------
DROP TABLE IF EXISTS `job_registry`;
CREATE TABLE `job_registry`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_key` varchar(191) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registry_value` varchar(191) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cpu_usage` double NULL DEFAULT NULL,
  `memory_usage` double NULL DEFAULT NULL,
  `load_average` double NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for job_run_log
-- ----------------------------
DROP TABLE IF EXISTS `job_run_log`;
CREATE TABLE `job_run_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_config_id` bigint(20) NOT NULL,
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `deploy_mode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交模式: standalone 、yarn 、yarn-session ',
  `job_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行后的任务id',
  `local_log` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '启动时本地日志',
  `run_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务运行所在的机器',
  `remote_log_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '远程日志url的地址',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '启动时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '启动时间',
  `job_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 727 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '运行任务日志' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_template
-- ----------------------------
DROP TABLE IF EXISTS `job_template`;
CREATE TABLE `job_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL COMMENT '修改用户',
  `alarm_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_last_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  `job_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'datax运行脚本',
  `jvm_param` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'jvm参数',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '所属项目Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for job_user
-- ----------------------------
DROP TABLE IF EXISTS `job_user`;
CREATE TABLE `job_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_user
-- ----------------------------
INSERT INTO `job_user` VALUES (1, 'admin', '$2a$10$2KCqRbra0Yn2TwvkZxtfLuWuUP5KyCWsljO/ci5pLD27pqR3TV1vy', 'ROLE_ADMIN', NULL);
-- ----------------------------
-- Table structure for lark_base_resource
-- ----------------------------
DROP TABLE IF EXISTS `lark_base_resource`;
CREATE TABLE `lark_base_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serverIp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serverUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serverPassword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for market_api
-- ----------------------------
DROP TABLE IF EXISTS `market_api`;
CREATE TABLE `market_api`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'API名称',
  `api_version` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'API版本',
  `api_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'API路径',
  `req_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `res_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回格式',
  `deny` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP黑名单多个，隔开',
  `limit_json` json NULL COMMENT '限流配置',
  `config_json` json NULL COMMENT '执行配置',
  `req_json` json NULL COMMENT '请求参数',
  `res_json` json NULL COMMENT '返回参数',
  `api_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口key',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据API信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for market_api_log
-- ----------------------------
DROP TABLE IF EXISTS `market_api_log`;
CREATE TABLE `market_api_log`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `api_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用api',
  `caller_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用者id',
  `caller_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用者ip',
  `caller_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用url',
  `caller_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用参数',
  `caller_date` datetime(0) NULL DEFAULT NULL COMMENT '调用时间',
  `caller_size` int(11) NULL DEFAULT NULL COMMENT '调用数据量',
  `time` int(11) NULL DEFAULT NULL COMMENT '调用耗时',
  `msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息记录',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0失败，1成功）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'api调用日志信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for market_api_mask
-- ----------------------------
DROP TABLE IF EXISTS `market_api_mask`;
CREATE TABLE `market_api_mask`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '脱敏主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `api_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据API',
  `mask_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脱敏名称',
  `config_json` json NULL COMMENT '脱敏字段规则配置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据API脱敏信息表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for market_service_integration
-- ----------------------------
DROP TABLE IF EXISTS `market_service_integration`;
CREATE TABLE `market_service_integration`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `service_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务编号',
  `service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `service_type` tinyint(4) NULL DEFAULT NULL COMMENT '服务类型（1http，2webservice）',
  `httpservice_json` json NULL COMMENT 'http接口配置',
  `webservice_json` json NULL COMMENT 'webservice接口配置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务集成表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for market_service_log
-- ----------------------------
DROP TABLE IF EXISTS `market_service_log`;
CREATE TABLE `market_service_log`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `service_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务id',
  `caller_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用者id',
  `caller_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用者ip',
  `caller_date` datetime(0) NULL DEFAULT NULL COMMENT '调用时间',
  `caller_header` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用请求头',
  `caller_param` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用请求参数',
  `caller_soap` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用报文',
  `time` int(11) NULL DEFAULT NULL COMMENT '调用耗时',
  `msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息记录',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0失败，1成功）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务集成调用日志表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for metadata_change_record
-- ----------------------------
DROP TABLE IF EXISTS `metadata_change_record`;
CREATE TABLE `metadata_change_record`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` tinyint(4) NULL DEFAULT NULL COMMENT '版本号',
  `object_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源数据的表名或者能唯一对应的源数据表的标识（可废弃）',
  `object_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源数据表主键',
  `field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改的源数据表的字段名',
  `field_old_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该字段原来的值',
  `field_new_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该字段最新的值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '元数据变更记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of metadata_change_record
-- ----------------------------

-- ----------------------------
-- Table structure for metadata_column
-- ----------------------------
DROP TABLE IF EXISTS `metadata_column`;
CREATE TABLE `metadata_column`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `source_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属数据源',
  `table_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属数据表',
  `column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `column_comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字段注释',
  `column_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段是否主键(1是0否)',
  `column_nullable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段是否允许为空(1是0否)',
  `column_position` int(11) NULL DEFAULT NULL COMMENT '字段序号',
  `data_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据类型',
  `data_length` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据长度',
  `data_precision` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据精度',
  `data_scale` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据小数位',
  `data_default` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据默认值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663468058091417603 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '元数据信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for metadata_source
-- ----------------------------
DROP TABLE IF EXISTS `metadata_source`;
CREATE TABLE `metadata_source`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_type` tinyint(4) NULL DEFAULT NULL COMMENT '数据源类型',
  `source_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `is_sync` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元数据同步（0否，1是）',
  `db_schema` json NULL COMMENT '数据源连接信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据源信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for metadata_table
-- ----------------------------
DROP TABLE IF EXISTS `metadata_table`;
CREATE TABLE `metadata_table`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `source_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属数据源',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `table_comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表注释',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663468058011725827 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据库表信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for quality_check_report
-- ----------------------------
DROP TABLE IF EXISTS `quality_check_report`;
CREATE TABLE `quality_check_report`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `check_rule_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查规则主键',
  `check_date` datetime(0) NULL DEFAULT NULL COMMENT '核查时间',
  `check_result` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查结果',
  `check_total_count` int(11) NULL DEFAULT NULL COMMENT '核查数量',
  `check_error_count` int(11) NULL DEFAULT NULL COMMENT '不合规数量',
  `check_batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查批次号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '核查报告信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for quality_check_rule
-- ----------------------------
DROP TABLE IF EXISTS `quality_check_rule`;
CREATE TABLE `quality_check_rule`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `rule_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `rule_type_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则类型',
  `rule_item_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查类型',
  `rule_level_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则级别',
  `rule_db_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源类型',
  `rule_source_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源主键',
  `rule_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源',
  `rule_table_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表主键',
  `rule_table` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表',
  `rule_table_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表名称',
  `rule_column_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段主键',
  `rule_column` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `rule_column_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `config_json` json NULL COMMENT '核查配置',
  `rule_sql` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查脚本',
  `last_check_batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最近核查批次号（关联确定唯一核查报告）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '核查规则信息表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for quality_rule_item
-- ----------------------------
DROP TABLE IF EXISTS `quality_rule_item`;
CREATE TABLE `quality_rule_item`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `rule_type_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则类型',
  `item_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查项编码',
  `item_explain` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '核查项解释',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则核查类型信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_rule_item
-- ----------------------------
INSERT INTO `quality_rule_item` VALUES ('1316555325628764161', '1310055102039498754', 'unique_key', '字段唯一性');
INSERT INTO `quality_rule_item` VALUES ('1316555329772736514', '1310055114131677186', 'consistent_key', '字典一致性');
INSERT INTO `quality_rule_item` VALUES ('1316555332956213250', '1310055106909085697', 'integrity_key', '非空完整性');
INSERT INTO `quality_rule_item` VALUES ('1316555336190021633', '1310055118023991297', 'relevance_key', '字段关联性');
INSERT INTO `quality_rule_item` VALUES ('1316555339457384449', '1310055122348318721', 'timeliness_key', '字段及时性');
INSERT INTO `quality_rule_item` VALUES ('1316555342435340289', '1310055110574907393', 'accuracy_key_length', '长度准确性');

-- ----------------------------
-- Table structure for quality_rule_level
-- ----------------------------
DROP TABLE IF EXISTS `quality_rule_level`;
CREATE TABLE `quality_rule_level`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则级别编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则级别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则级别信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_rule_level
-- ----------------------------
INSERT INTO `quality_rule_level` VALUES ('1316243642557419521', '1', '低');
INSERT INTO `quality_rule_level` VALUES ('1316243646609117186', '2', '中');
INSERT INTO `quality_rule_level` VALUES ('1316243649473826818', '3', '高');

-- ----------------------------
-- Table structure for quality_rule_type
-- ----------------------------
DROP TABLE IF EXISTS `quality_rule_type`;
CREATE TABLE `quality_rule_type`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则类型信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_rule_type
-- ----------------------------
INSERT INTO `quality_rule_type` VALUES ('1310055102039498754', '唯一性校验', 'unique');
INSERT INTO `quality_rule_type` VALUES ('1310055106909085697', '完整性校验', 'integrity');
INSERT INTO `quality_rule_type` VALUES ('1310055110574907393', '准确性校验', 'accuracy');
INSERT INTO `quality_rule_type` VALUES ('1310055114131677186', '一致性校验', 'consistent');
INSERT INTO `quality_rule_type` VALUES ('1310055118023991297', '关联性校验', 'relevance');
INSERT INTO `quality_rule_type` VALUES ('1310055122348318721', '及时性校验', 'timeliness');

-- ----------------------------
-- Table structure for quality_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `quality_schedule_job`;
CREATE TABLE `quality_schedule_job`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `job_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `bean_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'bean名称',
  `method_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `method_params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（1运行 0暂停）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据质量监控任务信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_schedule_job
-- ----------------------------
INSERT INTO `quality_schedule_job` VALUES ('1310823026538962945', '数据质量监控任务', 'qualityTask', 'task', NULL, '0 0 2 * * ?', 0);

-- ----------------------------
-- Table structure for quality_schedule_log
-- ----------------------------
DROP TABLE IF EXISTS `quality_schedule_log`;
CREATE TABLE `quality_schedule_log`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态（1成功 0失败）',
  `execute_job_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行任务主键',
  `execute_rule_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行规则主键',
  `execute_date` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `execute_result` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行结果',
  `execute_batch` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行批次号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据质量监控任务日志信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quality_schedule_log
-- ----------------------------
INSERT INTO `quality_schedule_log` VALUES ('1340552514940035074', '1', '1310823026538962945', '1318749656079646721', '2020-12-20 15:00:00', NULL, '20201220020000');
INSERT INTO `quality_schedule_log` VALUES ('1340552515988611073', '1', '1310823026538962945', '1318749963664736258', '2020-12-20 15:00:00', NULL, '20201220020000');
INSERT INTO `quality_schedule_log` VALUES ('1340552517041381378', '1', '1310823026538962945', '1318750378762420225', '2020-12-20 15:00:00', NULL, '20201220020000');
INSERT INTO `quality_schedule_log` VALUES ('1340552518194814978', '1', '1310823026538962945', '1336564556213846017', '2020-12-20 15:00:00', NULL, '20201220020000');
INSERT INTO `quality_schedule_log` VALUES ('1346516951155232769', '0', '1310823026538962945', '1318749656079646721', '2021-01-06 02:00:03', '获取数据源接口出错', '20210106020000');
INSERT INTO `quality_schedule_log` VALUES ('1346516953088806914', '0', '1310823026538962945', '1318749963664736258', '2021-01-06 02:00:03', '获取数据源接口出错', '20210106020000');
INSERT INTO `quality_schedule_log` VALUES ('1346516953143332866', '0', '1310823026538962945', '1318750378762420225', '2021-01-06 02:00:03', '获取数据源接口出错', '20210106020000');
INSERT INTO `quality_schedule_log` VALUES ('1346516953336270850', '0', '1310823026538962945', '1336564556213846017', '2021-01-06 02:00:03', '获取数据源接口出错', '20210106020000');
INSERT INTO `quality_schedule_log` VALUES ('1346879443580620802', '1', '1310823026538962945', '1318749656079646721', '2021-01-07 02:00:04', NULL, '20210107020000');
INSERT INTO `quality_schedule_log` VALUES ('1346879444222349313', '1', '1310823026538962945', '1318749963664736258', '2021-01-07 02:00:04', NULL, '20210107020000');
INSERT INTO `quality_schedule_log` VALUES ('1346879446915092482', '1', '1310823026538962945', '1318750378762420225', '2021-01-07 02:00:04', NULL, '20210107020000');
INSERT INTO `quality_schedule_log` VALUES ('1346879447737176066', '1', '1310823026538962945', '1336564556213846017', '2021-01-07 02:00:04', NULL, '20210107020000');

-- ----------------------------
-- Table structure for savepoint_backup
-- ----------------------------
DROP TABLE IF EXISTS `savepoint_backup`;
CREATE TABLE `savepoint_backup`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_config_id` bigint(20) NOT NULL,
  `savepoint_path` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `backup_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '备份时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index`(`job_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'savepoint备份地址' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for standard_contrast
-- ----------------------------
DROP TABLE IF EXISTS `standard_contrast`;
CREATE TABLE `standard_contrast`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `source_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源主键',
  `source_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源',
  `table_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表主键',
  `table_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表',
  `table_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据表名称',
  `column_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段主键',
  `column_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `column_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `gb_type_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标准类别主键',
  `bind_gb_column` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定标准字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '对照表信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of standard_contrast
-- ----------------------------
INSERT INTO `standard_contrast` VALUES ('1336483007929868290', 1, '1214835832967581698', '2020-12-09 09:29:14', '1197789917762031617', '1214835832967581698', '2020-12-09 09:29:14', NULL, '1336474987430793217', 'robot数据库', '1336479261791473665', 'robot_patient', '患者表', '1336479264106729474', 'patient_sex', '患者性别（1男2女）', '1303245849463218178', 'gb_code');

-- ----------------------------
-- Table structure for standard_contrast_dict
-- ----------------------------
DROP TABLE IF EXISTS `standard_contrast_dict`;
CREATE TABLE `standard_contrast_dict`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0未对照，1已对照）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `contrast_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典对照主键',
  `col_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `col_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `contrast_gb_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对照的标准字典',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典对照信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of standard_contrast_dict
-- ----------------------------
INSERT INTO `standard_contrast_dict` VALUES ('1336483232853614594', 1, '1214835832967581698', '2020-12-09 09:30:08', '1197789917762031617', '1214835832967581698', '2020-12-09 09:30:08', NULL, '1336483007929868290', '1', '男', '1303247360368926722');
INSERT INTO `standard_contrast_dict` VALUES ('1336483277371957249', 1, '1214835832967581698', '2020-12-10 11:30:19', '1197789917762031617', '1214835832967581698', '2020-12-10 11:30:19', NULL, '1336483007929868290', '2', '女', '1303247362688376833');

-- ----------------------------
-- Table structure for standard_dict
-- ----------------------------
DROP TABLE IF EXISTS `standard_dict`;
CREATE TABLE `standard_dict`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属类别',
  `gb_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标准编码',
  `gb_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标准名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据标准字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of standard_dict
-- ----------------------------
INSERT INTO `standard_dict` VALUES ('1303247357105758209', 1, '1214835832967581698', '2020-09-08 16:16:38', '1197789917762031617', '1214835832967581698', '2020-09-08 16:16:38', NULL, '1303245849463218178', '0', '未知的性别');
INSERT INTO `standard_dict` VALUES ('1303247360368926722', 1, '1214835832967581698', '2020-09-08 16:16:38', '1197789917762031617', '1214835832967581698', '2020-09-08 16:16:38', NULL, '1303245849463218178', '1', '男性');
INSERT INTO `standard_dict` VALUES ('1303247362688376833', 1, '1214835832967581698', '2020-09-08 16:16:38', '1197789917762031617', '1214835832967581698', '2020-09-08 16:16:38', NULL, '1303245849463218178', '2', '女性');
INSERT INTO `standard_dict` VALUES ('1303247366693937153', 1, '1214835832967581698', '2020-09-08 16:16:38', '1197789917762031617', '1214835832967581698', '2020-09-08 16:16:38', NULL, '1303245849463218178', '9', '未说明的性别');
INSERT INTO `standard_dict` VALUES ('1303249289220210689', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '10', '未婚');
INSERT INTO `standard_dict` VALUES ('1303249292659539970', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '20', '已婚');
INSERT INTO `standard_dict` VALUES ('1303249295721381890', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '21', '初婚');
INSERT INTO `standard_dict` VALUES ('1303249298619645953', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '22', '再婚');
INSERT INTO `standard_dict` VALUES ('1303249302188998658', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '23', '复婚');
INSERT INTO `standard_dict` VALUES ('1303249306152615937', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '30', '丧偶');
INSERT INTO `standard_dict` VALUES ('1303249308417540097', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '40', '离婚');
INSERT INTO `standard_dict` VALUES ('1303249312116916225', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, '1303245946938843137', '90', '未说明的婚姻状况');
INSERT INTO `standard_dict` VALUES ('1303250886239223810', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '11', '国家公务员');
INSERT INTO `standard_dict` VALUES ('1303250889280094210', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '13', '国家公务员');
INSERT INTO `standard_dict` VALUES ('1303250891419189250', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '17', '职员');
INSERT INTO `standard_dict` VALUES ('1303250895265366018', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '21', '企业管理人员');
INSERT INTO `standard_dict` VALUES ('1303250898415288322', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '24', '工 人');
INSERT INTO `standard_dict` VALUES ('1303250902022389761', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '27', '农民');
INSERT INTO `standard_dict` VALUES ('1303250904572526594', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '31', '学生');
INSERT INTO `standard_dict` VALUES ('1303250907172995074', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '37', '现役军人');
INSERT INTO `standard_dict` VALUES ('1303250910394220545', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '51', '自由职业者');
INSERT INTO `standard_dict` VALUES ('1303250914454306817', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '54', '个体经营者');
INSERT INTO `standard_dict` VALUES ('1303250918308872194', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '70', '无业 人员');
INSERT INTO `standard_dict` VALUES ('1303250920888369153', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '80', '退（离）休人员');
INSERT INTO `standard_dict` VALUES ('1303250924432556033', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, '1303246143370682369', '90', '其他');
INSERT INTO `standard_dict` VALUES ('1303252645292556289', 1, '1214835832967581698', '2020-09-08 16:18:13', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:13', NULL, '1303246245158051841', '1', '香港同胞亲属');
INSERT INTO `standard_dict` VALUES ('1303252649616883713', 1, '1214835832967581698', '2020-09-08 16:18:13', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:13', NULL, '1303246245158051841', '2', '澳门同胞亲属');
INSERT INTO `standard_dict` VALUES ('1303252652871663617', 1, '1214835832967581698', '2020-09-08 16:18:13', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:13', NULL, '1303246245158051841', '3', '台湾同胞亲属');
INSERT INTO `standard_dict` VALUES ('1303252656952721409', 1, '1214835832967581698', '2020-09-08 16:18:13', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:13', NULL, '1303246245158051841', '4', '海外侨胞亲属');
INSERT INTO `standard_dict` VALUES ('1303253227483033601', 1, '1214835832967581698', '2020-09-08 16:18:50', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:50', NULL, '1303246401513316353', '1', '两院院士');
INSERT INTO `standard_dict` VALUES ('1303253232226791425', 1, '1214835832967581698', '2020-09-08 16:18:50', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:50', NULL, '1303246401513316353', '2', '中国科学院院士');
INSERT INTO `standard_dict` VALUES ('1303253234995032066', 1, '1214835832967581698', '2020-09-08 16:18:50', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:50', NULL, '1303246401513316353', '3', '中国工程院院士');

-- ----------------------------
-- Table structure for standard_type
-- ----------------------------
DROP TABLE IF EXISTS `standard_type`;
CREATE TABLE `standard_type`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态（0不启用，1启用）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人所属部门',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gb_type_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标准类别编码',
  `gb_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标准类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据标准类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of standard_type
-- ----------------------------
INSERT INTO `standard_type` VALUES ('1303245849463218178', 1, '1214835832967581698', '2020-09-08 16:16:38', '1197789917762031617', '1214835832967581698', '2020-09-08 16:16:38', NULL, 'GB/T 2261.1-2003', '人的性别代码');
INSERT INTO `standard_type` VALUES ('1303245946938843137', 1, '1214835832967581698', '2020-09-08 16:17:02', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:02', NULL, 'GB/T 2261.2-2003', '婚姻状况代码');
INSERT INTO `standard_type` VALUES ('1303246143370682369', 1, '1214835832967581698', '2020-09-08 16:17:48', '1197789917762031617', '1214835832967581698', '2020-09-08 16:17:48', NULL, 'GB/T 2261.4-2003', '从业状况(个人身份)代码');
INSERT INTO `standard_type` VALUES ('1303246245158051841', 1, '1214835832967581698', '2020-09-08 16:18:13', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:13', NULL, 'GB/T 2261.5-2003', '港澳台侨属代码');
INSERT INTO `standard_type` VALUES ('1303246401513316353', 1, '1214835832967581698', '2020-09-08 16:18:50', '1197789917762031617', '1214835832967581698', '2020-09-08 16:18:50', NULL, 'GB/T 2261.7-2003', '院士代码');

-- ----------------------------
-- Table structure for sys_api_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_api_config`;
CREATE TABLE `sys_api_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'api名称',
  `data_source_id` int(11) NULL DEFAULT NULL COMMENT '数据源id',
  `data_source_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `query_sql` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '查询sql',
  `parameter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'json参数',
  `publickey` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公钥',
  `privatekey` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '私钥',
  `request_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式(GET,POST)',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
  `Ip_rights` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否限制请求ip(1:是 0:否)',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `expiration_time` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  `api_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'api状态（0:启用 1:停用）',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-red', 'Y', 'admin', '2022-05-09 15:26:06', 'admin', '2022-10-24 11:15:11', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2022-05-09 15:26:06', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2022-05-09 15:26:06', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaOnOff', 'false', 'Y', 'admin', '2022-05-09 15:26:06', 'admin', '2022-06-01 09:08:52', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2022-05-09 15:26:06', '', NULL, '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_data_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_base`;
CREATE TABLE `sys_data_base`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `base_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库库名',
  `data_source_id` int(11) NULL DEFAULT NULL COMMENT '数据源id',
  `data_source_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源编码',
  `data_source_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `colony_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是集群（1：是  0：否）',
  `colony_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '集群名称',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_base
-- ----------------------------
INSERT INTO `sys_data_base` VALUES (37, 'dwm', 9, 'clickhouse', 'ck测试', '0', NULL, 'admin', '2022-08-24 11:08:44', NULL, NULL, NULL);
INSERT INTO `sys_data_base` VALUES (38, 'dbctest', 16, 'oracle', '本地orcle', '0', NULL, 'admin', '2022-09-30 09:18:25', NULL, NULL, NULL);
INSERT INTO `sys_data_base` VALUES (39, 'dbc', 18, 'oracle', '梦洁数仓', '0', NULL, 'admin', '2022-09-30 14:42:19', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_data_rule
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_rule`;
CREATE TABLE `sys_data_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_source_id` int(11) NULL DEFAULT NULL COMMENT '数据源id',
  `data_source_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源编码',
  `data_source_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `data_layer_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库层级编码',
  `data_layer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库层级名称',
  `rule_type_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则类型编码',
  `rule_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则类型名称',
  `rule_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则表达式',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_rule
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-08 14:52:25', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(11) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2022-04-08 14:52:38', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-08 14:52:39', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2022-04-08 14:52:40', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 12, 'Datax', 'datax', 'sys_job_group', NULL, 'default', 'N', '0', 'admin', '2022-04-26 16:42:47', 'admin', '2022-04-26 16:43:02', '自定义分组');
INSERT INTO `sys_dict_data` VALUES (101, 13, 'FlinkX', 'flinkx', 'sys_job_group', NULL, 'default', 'N', '0', 'admin', '2022-04-26 16:44:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (102, 0, 'Datax', 'datax', 'Actuator_type', NULL, 'primary', 'N', '0', 'admin', '2022-04-29 14:07:20', 'admin', '2022-07-20 09:40:01', NULL);
INSERT INTO `sys_dict_data` VALUES (103, 1, 'FlinkX', 'flinkx', 'Actuator_type', NULL, 'success', 'N', '0', 'admin', '2022-04-29 14:07:40', 'admin', '2022-07-20 09:40:05', NULL);
INSERT INTO `sys_dict_data` VALUES (104, 0, '库名规则', 'DATABASE_RULE', 'DATA_ASSETS_TYPE', NULL, 'primary', 'N', '0', 'admin', '2022-06-15 14:32:08', 'admin', '2022-07-20 09:39:45', NULL);
INSERT INTO `sys_dict_data` VALUES (105, 1, '表名规则', 'TABLE_RULE', 'DATA_ASSETS_TYPE', NULL, 'success', 'N', '0', 'admin', '2022-06-15 14:33:14', 'admin', '2022-07-20 09:39:49', NULL);
INSERT INTO `sys_dict_data` VALUES (106, 0, '业务层', 'ODS', 'DATABASE_LAYER', NULL, 'primary', 'N', '0', 'admin', '2022-06-16 17:04:58', 'admin', '2022-07-20 09:38:55', '数据运营层：Operation Data Store 数据准备区，也称为贴源层。数据源中的数据，经过抽取、洗净、传输，也就是ETL过程之后进入本层。');
INSERT INTO `sys_dict_data` VALUES (107, 0, '数据细节层', 'DWD', 'DATABASE_LAYER', NULL, 'success', 'N', '0', 'admin', '2022-06-16 17:05:48', 'admin', '2022-07-20 09:39:01', '该层是业务层和数据仓库的隔离层，保持和ODS层一样的数据颗粒度；主要是对ODS数据层做一些数据的清洗和规范化的操作，比如去除空数据、脏数据、离群值等。');
INSERT INTO `sys_dict_data` VALUES (108, 0, '数据中间层', 'DWM', 'DATABASE_LAYER', NULL, 'warning', 'N', '0', 'admin', '2022-06-16 17:06:32', 'admin', '2022-07-20 09:39:12', '该层是在DWD层的数据基础上，对数据做一些轻微的聚合操作，生成一些列的中间结果表，提升公共指标的复用性，减少重复加工的工作。');
INSERT INTO `sys_dict_data` VALUES (109, 0, '数据服务层', 'DWS', 'DATABASE_LAYER', NULL, 'danger', 'N', '0', 'admin', '2022-06-16 17:07:12', 'admin', '2022-07-20 09:39:17', '该层是基于DWM上的基础数据，整合汇总成分析某一个主题域的数据服务层，一般是宽表，用于提供后续的业务查询，OLAP分析，数据分发等。');
INSERT INTO `sys_dict_data` VALUES (110, 0, '数据应用层', 'ADS', 'DATABASE_LAYER', NULL, 'info', 'N', '0', 'admin', '2022-06-16 17:07:46', 'admin', '2022-07-20 09:39:29', '该层主要是提供给数据产品和数据分析使用的数据，一般会存放在ES、Redis、PostgreSql等系统中供线上系统使用；也可能存放在hive或者Druid中，供数据分析和数据挖掘使用，比如常用的数据报表就是存在这里的。');
INSERT INTO `sys_dict_data` VALUES (111, 0, '是', '1', 'COLONY_STATE', NULL, 'success', 'N', '0', 'admin', '2022-06-18 11:48:22', 'admin', '2022-07-20 09:38:30', NULL);
INSERT INTO `sys_dict_data` VALUES (112, 0, '否', '0', 'COLONY_STATE', NULL, 'warning', 'N', '0', 'admin', '2022-06-18 11:48:34', 'admin', '2022-07-20 09:38:40', NULL);
INSERT INTO `sys_dict_data` VALUES (113, 0, '数仓建库', 'warehouse_building', 'source_group', NULL, 'primary', 'N', '0', 'admin', '2022-08-22 17:30:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (114, 0, '数据源', 'source', 'source_group', NULL, 'success', 'N', '0', 'admin', '2022-08-22 17:31:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (115, 0, '数据下沉', 'sink', 'source_group', NULL, 'warning', 'N', '0', 'admin', '2022-08-22 17:32:37', 'admin', '2022-08-22 17:32:44', NULL);
INSERT INTO `sys_dict_data` VALUES (116, 0, '字段', 'field', 'data_table_field', NULL, 'danger', 'N', '0', 'admin', '2022-08-23 11:23:26', 'admin', '2022-08-23 11:23:31', NULL);
INSERT INTO `sys_dict_data` VALUES (117, 0, '表', 'table', 'data_table_field', NULL, 'success', 'N', '0', 'admin', '2022-08-23 11:23:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 0, '数据', 'data', 'table_action_type', NULL, 'primary', 'N', '0', 'admin', '2022-08-24 13:57:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (119, 1, '档案', 'archives', 'table_action_type', NULL, 'success', 'N', '0', 'admin', '2022-08-24 13:57:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (120, 0, '指标', 'index', 'table_action_type', NULL, 'warning', 'N', '0', 'admin', '2022-08-24 13:58:11', 'admin', '2022-08-24 13:58:16', NULL);
INSERT INTO `sys_dict_data` VALUES (121, 0, '零售', 'retail', 'table_theme', NULL, 'primary', 'N', '0', 'admin', '2022-08-24 13:58:50', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (122, 0, '商品', 'goods', 'table_theme', NULL, 'success', 'N', '0', 'admin', '2022-08-24 13:59:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (123, 0, '会员', 'vip', 'table_theme', NULL, 'warning', 'N', '0', 'admin', '2022-08-24 13:59:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (124, 0, '库存', 'stock', 'table_theme', NULL, 'danger', 'N', '0', 'admin', '2022-08-24 13:59:50', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (125, 0, 'POST', 'POST', 'sys_api_mode', NULL, 'danger', 'N', '0', 'admin', '2022-09-27 10:53:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (126, 1, 'GET', 'GET', 'sys_api_mode', NULL, 'success', 'N', '0', 'admin', '2022-09-27 10:53:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (127, 0, '是', '1', 'sys_api_id', NULL, 'danger', 'N', '0', 'admin', '2022-09-27 10:53:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (128, 0, '否', '0', 'sys_api_id', NULL, 'success', 'N', '0', 'admin', '2022-09-27 10:54:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (131, 0, '启用', '1', 'sys_data_status', NULL, 'success', 'N', '0', 'admin', '2023-02-20 20:45:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (132, 0, '禁用', '0', 'sys_data_status', NULL, 'danger', 'N', '0', 'admin', '2023-02-20 20:45:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (133, 0, 'MySql数据库', '1', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 20:59:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (134, 1, 'MariaDB数据库', '2', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:00:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (135, 2, 'Oracle11g及以下数据库', '3', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:00:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (136, 3, 'Oracle12c+数据库', '4', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:00:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (137, 4, 'PostgreSql数据库', '5', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:00:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (138, 5, 'SQLServer2008及以下数据库', '6', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:00:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (139, 6, 'SQLServer2012+数据库', '7', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:01:09', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (140, 9, '其他数据库', '8', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-20 21:01:21', 'admin', '2023-02-27 14:01:39', NULL);
INSERT INTO `sys_dict_data` VALUES (141, 7, 'Clickhouse数据库', '9', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-27 14:01:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (142, 8, 'Hive数据库', '10', 'data_db_type', NULL, 'default', 'N', '0', 'admin', '2023-02-27 14:01:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (143, 0, '服务器资源', '1', 'data_base_type', NULL, 'warning', 'N', '0', 'admin', '2023-03-29 17:21:50', 'admin', '2023-03-31 15:25:20', NULL);
INSERT INTO `sys_dict_data` VALUES (147, 0, '文件资源', '2', 'data_base_type', NULL, 'primary', 'N', '0', 'admin', '2023-03-31 15:25:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (148, 0, '已对照', '1', 'data_contrast_status', NULL, 'primary', 'N', '0', 'admin', '2023-04-27 16:29:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (149, 0, '未对照', '0', 'data_contrast_status', NULL, 'danger', 'N', '0', 'admin', '2023-04-27 16:29:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (150, 0, '启用', '1', 'data_rule_statu', NULL, 'primary', 'N', '0', 'admin', '2023-04-28 15:51:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (151, 0, '关闭', '0', 'data_rule_statu', NULL, 'danger', 'N', '0', 'admin', '2023-04-28 15:52:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (152, 0, '成功', '1', 'task_log_status', NULL, 'success', 'N', '0', 'admin', '2023-04-28 16:57:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (153, 0, '失败', '0', 'task_log_status', NULL, 'danger', 'N', '0', 'admin', '2023-04-28 16:57:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (154, 0, '启用', '1', 'sys_api_status', NULL, 'primary', 'N', '0', 'admin', '2023-05-09 15:56:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (155, 0, '关闭', '0', 'sys_api_status', NULL, 'danger', 'N', '0', 'admin', '2023-05-09 15:56:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (156, 0, '未发布', '3', 'sys_api_status', NULL, 'info', 'N', '0', 'admin', '2023-05-09 15:56:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (157, 0, '发布', '2', 'sys_api_status', NULL, 'success', 'N', '0', 'admin', '2023-05-09 15:57:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (158, 0, 'JSON', 'JSON', 'sys_api_return', NULL, 'primary', 'N', '0', 'admin', '2023-05-09 16:13:25', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (159, 0, '否', '0', 'sys_api_limit', NULL, 'success', 'N', '0', 'admin', '2023-05-09 16:17:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (160, 0, '是', '1', 'sys_api_limit', NULL, 'primary', 'N', '0', 'admin', '2023-05-09 16:17:32', 'admin', '2023-05-09 16:17:39', NULL);
INSERT INTO `sys_dict_data` VALUES (161, 0, '表引导模式', '1', 'sys_api_config_type', NULL, 'primary', 'N', '0', 'admin', '2023-05-09 16:26:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (162, 0, '脚本模式', '2', 'sys_api_config_type', NULL, 'success', 'N', '0', 'admin', '2023-05-09 16:26:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (163, 0, '等于', '1', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:51:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (164, 0, '不等于', '2', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:51:37', 'admin', '2023-05-09 16:52:08', NULL);
INSERT INTO `sys_dict_data` VALUES (165, 0, '全模糊查询', '3', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:51:55', 'admin', '2023-05-09 16:52:15', NULL);
INSERT INTO `sys_dict_data` VALUES (166, 0, '左模糊查询', '4', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:52:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (167, 0, '右模糊查询', '5', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:52:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (168, 0, '大于', '6', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (169, 0, '大于等于', '7', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:10', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (170, 0, '小于', '8', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (171, 0, '小于等于', '9', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (172, 0, '是否为空', '10', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (173, 0, '是否不为空', '11', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:53:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (174, 0, '包含(IN)', '12', 'data_where_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:54:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (175, 0, '字符串', '1', 'data_param_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:55:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (176, 0, '整型', '2', 'data_param_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:55:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (177, 0, '浮点型', '3', 'data_param_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:55:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (178, 0, '时间型', '4', 'data_param_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:56:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (179, 0, '集合', '5', 'data_param_type', NULL, 'default', 'N', '0', 'admin', '2023-05-09 16:56:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (180, 0, '正则替换', '1', 'data_cipher_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (181, 0, '加密算法', '2', 'data_cipher_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:26:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (182, 0, '中文姓名', '1', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:27:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (183, 0, '身份证号', '2', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (184, 0, '固定电话', '3', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (185, 0, '手机号码', '4', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (186, 0, '地址', '5', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (187, 0, '电子邮箱', '6', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (188, 0, '银行卡号', '7', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:28:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (189, 0, '公司开户银行联号', '8', 'data_regex_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:29:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (190, 0, 'BASE64加密', '1', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:30:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (191, 0, 'MD5加密', '2', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:30:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (192, 0, 'SHA_1加密', '3', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:30:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (193, 0, 'SHA_256加密', '4', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:30:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (194, 0, 'AES加密', '5', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:30:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (195, 0, 'DES加密', '6', 'data_algorithm_crypto', NULL, 'default', 'N', '0', 'admin', '2023-05-10 14:31:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (196, 0, '开启', '1', 'data_cipher_status', NULL, 'success', 'N', '0', 'admin', '2023-05-10 14:33:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (197, 0, '关闭', '0', 'data_cipher_status', NULL, 'danger', 'N', '0', 'admin', '2023-05-10 14:34:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (198, 0, 'SQL流任务', '0', 'flink_job_type', NULL, 'success', 'N', '0', 'admin', '2023-05-12 11:40:43', 'admin', '2023-05-12 11:44:17', NULL);
INSERT INTO `sys_dict_data` VALUES (199, 0, 'JAR流任务', '1', 'flink_job_type', NULL, 'primary', 'N', '0', 'admin', '2023-05-12 11:41:36', 'admin', '2023-05-12 11:44:40', NULL);
INSERT INTO `sys_dict_data` VALUES (200, 0, 'JAR批任务', '3', 'flink_job_type', NULL, 'warning', 'N', '0', 'admin', '2023-05-12 11:42:34', 'admin', '2023-05-12 13:56:53', NULL);
INSERT INTO `sys_dict_data` VALUES (201, 0, 'SQL批任务', '2', 'flink_job_type', NULL, 'info', 'N', '0', 'admin', '2023-05-12 13:55:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (202, 0, '未同步', '0', 'datasource_Synchronization_status', NULL, 'info', 'N', '0', 'admin', '2023-06-29 14:52:18', 'admin', '2023-06-29 14:52:38', NULL);
INSERT INTO `sys_dict_data` VALUES (203, 0, '同步中', '1', 'datasource_Synchronization_status', NULL, 'warning', 'N', '0', 'admin', '2023-06-29 14:53:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (204, 0, '已同步', '2', 'datasource_Synchronization_status', NULL, 'success', 'N', '0', 'admin', '2023-06-29 14:53:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (205, 0, '同步失败', '3', 'datasource_Synchronization_status', NULL, 'danger', 'N', '0', 'admin', '2023-06-29 14:53:40', '', NULL, NULL);
-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2022-04-08 14:52:37', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '执行器类型', 'Actuator_type', '0', 'admin', '2022-04-29 14:06:45', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (101, '数据资产规则类型', 'DATA_ASSETS_TYPE', '0', 'admin', '2022-06-15 14:30:42', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (102, '数据库层级', 'DATABASE_LAYER', '0', 'admin', '2022-06-16 17:02:20', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (103, '集群状态', 'COLONY_STATE', '0', 'admin', '2022-06-18 11:48:07', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (105, '数据源分组', 'source_group', '0', 'admin', '2022-08-22 17:29:22', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (106, '数据表与字段类型', 'data_table_field', '0', 'admin', '2022-08-23 11:22:36', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (107, '表作用类型', 'table_action_type', '0', 'admin', '2022-08-24 13:56:23', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (108, '表主题', 'table_theme', '0', 'admin', '2022-08-24 13:56:55', 'admin', '2022-08-24 14:05:18', NULL);
INSERT INTO `sys_dict_type` VALUES (109, 'api请求方式', 'sys_api_mode', '0', 'admin', '2022-09-27 10:33:31', 'admin', '2022-09-27 10:34:26', NULL);
INSERT INTO `sys_dict_type` VALUES (110, 'api_ip限制', 'sys_api_id', '0', 'admin', '2022-09-27 10:34:52', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (112, '数据状态', 'sys_data_status', '0', 'admin', '2023-02-20 20:44:38', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (113, '数据库类型', 'data_db_type', '0', 'admin', '2023-02-20 20:59:14', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (114, '资源类型', 'data_base_type', '0', 'admin', '2023-03-29 17:21:15', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (115, '字典对照状态', 'data_contrast_status', '0', 'admin', '2023-04-27 16:28:39', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (116, '规则开启状态', 'data_rule_statu', '0', 'admin', '2023-04-28 15:51:18', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (117, '规则任务日志状态', 'task_log_status', '0', 'admin', '2023-04-28 16:57:27', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (118, '数据API接口发布状态', 'sys_api_status', '0', 'admin', '2023-05-09 15:55:12', 'admin', '2023-05-09 16:11:45', NULL);
INSERT INTO `sys_dict_type` VALUES (119, 'api接口返回方式', 'sys_api_return', '0', 'admin', '2023-05-09 16:13:06', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (120, 'api是否限流', 'sys_api_limit', '0', 'admin', '2023-05-09 16:17:05', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (121, 'api配置方式', 'sys_api_config_type', '0', 'admin', '2023-05-09 16:26:00', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (122, 'Sql操作符', 'data_where_type', '0', 'admin', '2023-05-09 16:50:51', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (123, '参数类型', 'data_param_type', '0', 'admin', '2023-05-09 16:55:04', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (124, '脱敏类型', 'data_cipher_type', '0', 'admin', '2023-05-10 14:25:34', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (125, '正则规则类型', 'data_regex_crypto', '0', 'admin', '2023-05-10 14:27:18', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (126, '加密规则类型', 'data_algorithm_crypto', '0', 'admin', '2023-05-10 14:29:48', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (127, '脱敏状态', 'data_cipher_status', '0', 'admin', '2023-05-10 14:33:38', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (128, 'flink任务类型', 'flink_job_type', '0', 'admin', '2023-05-12 11:40:09', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (129, '数据源同步状态', 'datasource_Synchronization_status', '0', 'admin', '2023-06-29 14:51:48', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2022-04-08 14:52:40', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2022-04-08 14:52:40', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2022-04-08 14:52:41', '', NULL, '');
INSERT INTO `sys_job` VALUES (104, '数据标准', 'SYSTEM', 'QualityTask.task', '0 0/30 * * * ?', '1', '1', '0', 'admin', '2023-05-04 09:39:21', 'admin', '2023-05-04 10:42:46', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1546 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1118 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(11) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(11) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2139 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2022-04-08 14:52:27', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2022-04-08 14:52:27', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2022-04-08 14:52:27', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2022-04-08 14:52:28', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2022-04-08 14:52:28', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2022-04-08 14:52:28', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2022-04-08 14:52:28', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2022-04-08 14:52:28', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2022-04-08 14:52:28', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2022-04-08 14:52:28', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2022-04-08 14:52:28', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2022-04-08 14:52:28', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2022-04-08 14:52:28', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2022-04-08 14:52:28', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2022-04-08 14:52:28', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2022-04-08 14:52:28', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2022-04-08 14:52:28', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2022-04-08 14:52:28', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2022-04-08 14:52:28', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2022-04-08 14:52:28', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2022-04-08 14:52:28', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2022-04-08 14:52:28', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2022-04-08 14:52:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2022-04-08 14:52:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2022-04-08 14:52:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2022-04-08 14:52:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 7, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 115, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 115, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 115, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 115, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 115, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 115, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2022-04-08 14:52:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '数据集成', 0, 5, 'sjjc', NULL, NULL, 1, 0, 'M', '0', '0', '', 'code', 'admin', '2022-04-19 17:10:53', 'admin', '2022-04-20 15:11:01', '');
INSERT INTO `sys_menu` VALUES (2001, '数智大屏', 0, 99, 'dashboard2', NULL, NULL, 1, 0, 'M', '1', '1', '', 'druid', 'admin', '2022-04-19 17:24:01', 'admin', '2023-05-15 10:09:12', '');
INSERT INTO `sys_menu` VALUES (2002, '测试', 2001, 1, 'bigData', 'dashboard2/admin/index', NULL, 1, 0, 'C', '1', '1', '', 'druid', 'admin', '2022-04-19 17:24:46', 'admin', '2023-05-15 10:08:42', '');
INSERT INTO `sys_menu` VALUES (2003, '基础建设', 0, 4, 'jcjs', NULL, NULL, 1, 0, 'M', '0', '0', '', 'swagger', 'admin', '2022-04-20 08:45:06', 'admin', '2022-04-20 08:45:40', '');
INSERT INTO `sys_menu` VALUES (2004, '项目管理', 2003, 1, 'xmgl', 'datax/jobProject/index', NULL, 1, 0, 'C', '0', '0', 'datax:jobProject:list', 'nested', 'admin', '2022-04-20 08:58:06', 'admin', '2023-05-04 11:20:31', '');
INSERT INTO `sys_menu` VALUES (2005, '数据源管理', 2003, 2, 'sjy', 'datax/jdbc-datasource/index', NULL, 1, 0, 'C', '0', '0', 'datax:Datasource:list', 'list', 'admin', '2022-04-20 11:48:34', 'admin', '2023-05-04 11:04:12', '');
INSERT INTO `sys_menu` VALUES (2006, '资源管理', 2003, 3, 'zygl', 'datax/resource/index', NULL, 1, 0, 'C', '0', '0', 'datax:resource:list', 'documentation', 'admin', '2022-04-20 14:35:17', 'admin', '2023-05-04 11:30:37', '');
INSERT INTO `sys_menu` VALUES (2007, '调度模板', 2000, 1, 'jobTemplate', 'datax/jobTemplate/index', NULL, 1, 0, 'C', '0', '0', 'datax:jobTemplate:list', 'component', 'admin', '2022-04-20 15:11:50', 'admin', '2023-05-04 11:35:29', '');
INSERT INTO `sys_menu` VALUES (2008, '任务构建', 2000, 2, 'jsonBuild', 'datax/json-build/index', NULL, 1, 0, 'C', '0', '0', NULL, 'form', 'admin', '2022-04-21 11:26:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2009, '实例管理', 2000, 3, 'jobInfo', 'datax/jobInfo/index', NULL, 1, 0, 'C', '0', '0', 'datax:job:list', 'guide', 'admin', '2022-04-21 15:01:10', 'admin', '2023-05-04 11:44:26', '');
INSERT INTO `sys_menu` VALUES (2010, '执行日志', 2000, 4, 'joblog', 'datax/jobLog/index', NULL, 1, 0, 'C', '0', '0', 'datax:joblog:list', 'log', 'admin', '2022-04-21 15:21:09', 'admin', '2023-05-04 14:01:56', '');
INSERT INTO `sys_menu` VALUES (2017, '执行器管理', 2003, 1, 'executor', 'datax/executor/index', NULL, 1, 0, 'C', '0', '0', 'datax:executor:list', 'slider', 'admin', '2022-04-28 16:44:15', 'admin', '2023-05-04 11:15:20', '执行器管理菜单');
INSERT INTO `sys_menu` VALUES (2018, '执行器管理查询', 2017, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'datax:executor:query', '#', 'admin', '2022-04-28 16:44:15', 'admin', '2023-05-04 11:15:26', '');
INSERT INTO `sys_menu` VALUES (2019, '执行器管理新增', 2017, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'datax:executor:add', '#', 'admin', '2022-04-28 16:44:15', 'admin', '2023-05-04 11:15:38', '');
INSERT INTO `sys_menu` VALUES (2020, '执行器管理修改', 2017, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'datax:executor:edit', '#', 'admin', '2022-04-28 16:44:15', 'admin', '2023-05-04 11:15:46', '');
INSERT INTO `sys_menu` VALUES (2021, '执行器管理删除', 2017, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'datax:executor:remove', '#', 'admin', '2022-04-28 16:44:15', 'admin', '2023-05-04 11:15:53', '');
INSERT INTO `sys_menu` VALUES (2023, '数据开发', 0, 6, 'flink', NULL, NULL, 1, 0, 'M', '0', '0', '', 'table', 'admin', '2022-05-10 14:53:29', 'admin', '2022-07-06 17:31:18', '');
INSERT INTO `sys_menu` VALUES (2024, '任务列表', 2023, 1, 'FlinkTaskManage', 'flink/task-manage/index', NULL, 1, 0, 'C', '0', '0', 'flink:job:listTask', 'list', 'admin', '2022-05-10 14:57:38', 'admin', '2023-05-04 14:34:47', '');
INSERT INTO `sys_menu` VALUES (2025, '历史版本', 2023, 2, 'HistoryTask', 'flink/task-manage/history', NULL, 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2022-05-10 15:49:02', 'admin', '2022-05-12 10:52:59', '');
INSERT INTO `sys_menu` VALUES (2027, '运行日志', 2023, 3, 'FlinkLogManage', 'flink/log-manage/index', NULL, 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2022-05-12 14:56:06', 'admin', '2022-05-12 14:56:14', '');
INSERT INTO `sys_menu` VALUES (2028, '告警设置', 2023, 4, 'walle', 'flink/alarm-manage/alarmcfg', NULL, 1, 0, 'C', '0', '0', NULL, 'bug', 'admin', '2022-05-12 15:04:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '告警日志', 2023, 5, 'wallelogs', 'flink/alarm-manage/index', NULL, 1, 0, 'C', '0', '0', NULL, 'documentation', 'admin', '2022-05-12 15:05:58', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, '系统设置', 2023, 6, 'system-manage', 'flink/system-manage/index', NULL, 1, 0, 'C', '0', '0', NULL, 'system', 'admin', '2022-05-12 15:22:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2031, '任务批量构建', 2000, 2, 'job', 'datax/json-build-batch/index', NULL, 1, 0, 'C', '0', '0', NULL, 'number', 'admin', '2022-05-16 15:58:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2032, '资源监控', 2000, 6, 'registry', 'datax/registry/index', NULL, 1, 0, 'C', '0', '0', NULL, 'monitor', 'admin', '2022-05-16 16:00:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '数据资产', 0, 7, 'dataAssets', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'build', 'admin', '2022-05-19 11:05:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2060, '指标管理', 0, 15, 'target', NULL, NULL, 1, 0, 'M', '0', '0', '', 'server', 'admin', '2022-08-01 16:02:39', 'admin', '2023-04-27 16:14:34', '');
INSERT INTO `sys_menu` VALUES (2063, '指标配置', 2060, 1, 'index', NULL, NULL, 1, 0, 'M', '0', '0', '', 'component', 'admin', '2022-08-24 10:19:15', 'admin', '2022-08-24 10:19:40', '');
INSERT INTO `sys_menu` VALUES (2071, '数据源', 2033, 1, 'datasource', 'rule/datasource/index', NULL, 1, 0, 'C', '0', '0', 'metadata:tables:list', 'button', 'admin', '2023-02-16 11:23:55', 'admin', '2023-05-04 14:47:26', '');
INSERT INTO `sys_menu` VALUES (2072, '元数据', 2033, 2, 'datacolumn', 'rule/datacolumn/index', NULL, 1, 0, 'C', '0', '0', '', 'build', 'admin', '2023-02-24 10:14:53', 'admin', '2023-02-24 15:42:03', '');
INSERT INTO `sys_menu` VALUES (2073, 'Jar包管理', 2023, 7, 'upload', 'flink/upload/index', NULL, 1, 0, 'C', '0', '0', '', 'download', 'admin', '2023-03-29 11:04:07', 'admin', '2023-03-29 14:23:13', '');
INSERT INTO `sys_menu` VALUES (2074, '数据血缘', 2033, 4, 'datablood', 'rule/datablood/index', NULL, 1, 0, 'C', '0', '0', '', 'example', 'admin', '2023-04-20 10:59:33', 'admin', '2023-04-20 11:02:27', '');
INSERT INTO `sys_menu` VALUES (2076, 'Sql工作台', 2033, 5, 'sqlconsole', 'rule/sqlconsole/index', NULL, 1, 0, 'C', '0', '0', '', 'button', 'admin', '2023-04-20 11:22:53', 'admin', '2023-04-20 11:23:30', '');
INSERT INTO `sys_menu` VALUES (2077, '数据标准', 0, 9, 'standard', NULL, NULL, 1, 0, 'M', '0', '0', '', 'skill', 'admin', '2023-04-27 16:15:30', 'admin', '2023-04-27 16:16:00', '');
INSERT INTO `sys_menu` VALUES (2078, '标准字典', 2077, 1, 'datadict', 'standard/datadict/index', NULL, 1, 0, 'C', '0', '0', '', 'dict', 'admin', '2023-04-27 16:18:17', 'admin', '2023-04-27 16:25:25', '');
INSERT INTO `sys_menu` VALUES (2079, '对照表单', 2077, 2, 'dictcontrast', 'standard/dictcontrast/index', NULL, 1, 0, 'C', '0', '0', NULL, 'documentation', 'admin', '2023-04-27 16:46:12', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2080, '字典对照', 2077, 3, 'dictmapping', 'standard/dictmapping/index', NULL, 1, 0, 'C', '0', '0', NULL, 'documentation', 'admin', '2023-04-27 16:51:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2081, '对照统计', 2077, 4, 'contraststat', 'standard/contraststat/index', NULL, 1, 0, 'C', '0', '0', NULL, 'chart', 'admin', '2023-04-27 16:53:10', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2082, '变更记录', 2033, 6, 'changerecord', 'rule/changerecord/index', NULL, 1, 0, 'C', '0', '0', NULL, 'documentation', 'admin', '2023-04-28 10:34:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2083, '数据质量', 0, 10, 'quality', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'eye', 'admin', '2023-04-28 14:25:03', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2084, '规则配置', 2083, 1, 'checkrule', 'quality/checkrule/index', NULL, 1, 0, 'C', '0', '0', NULL, 'color', 'admin', '2023-04-28 14:26:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2085, '问题统计', 2083, 2, 'checkstat', 'quality/checkstat/index', NULL, 1, 0, 'C', '0', '0', NULL, 'chart', 'admin', '2023-04-28 14:26:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2086, '质量报告', 2083, 3, 'checkreport', 'quality/checkreport/index', NULL, 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2023-04-28 14:27:29', 'admin', '2023-04-28 14:27:36', '');
INSERT INTO `sys_menu` VALUES (2089, '数据源管理新增', 2005, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:Datasource:add', '#', 'admin', '2023-05-04 11:04:41', 'admin', '2023-05-04 11:05:12', '');
INSERT INTO `sys_menu` VALUES (2090, '数据源管理修改', 2005, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:Datasource:edit', '#', 'admin', '2023-05-04 11:05:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2091, '数据源管理删除', 2005, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:Datasource:remove', '#', 'admin', '2023-05-04 11:06:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2092, '项目管理新增', 2004, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobProject:add', '#', 'admin', '2023-05-04 11:21:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2093, '项目管理编辑', 2004, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobProject:edit', '#', 'admin', '2023-05-04 11:21:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2094, '项目管理删除', 2004, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobProject:remove', '#', 'admin', '2023-05-04 11:21:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2095, '资源管理新增', 2006, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:resource:add', '#', 'admin', '2023-05-04 11:30:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2096, '资源管理修改', 2006, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:resource:edit', '#', 'admin', '2023-05-04 11:31:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2097, '资源管理删除', 2006, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:resource:remove', '#', 'admin', '2023-05-04 11:31:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2098, '资源管理访问', 2006, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:resource:visit', '#', 'admin', '2023-05-04 11:32:01', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2099, '调度模板新增', 2007, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobTemplate:add', '#', 'admin', '2023-05-04 11:35:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2100, '调度模板修改', 2007, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobTemplate:edit', '#', 'admin', '2023-05-04 11:36:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2101, '调度模板删除', 2007, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobTemplate:remove', '#', 'admin', '2023-05-04 11:36:48', 'admin', '2023-05-04 11:37:00', '');
INSERT INTO `sys_menu` VALUES (2102, '实例管理新增', 2009, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:add', '#', 'admin', '2023-05-04 11:44:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2103, '实例管理修改', 2009, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:edit', '#', 'admin', '2023-05-04 11:44:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2104, '实例管理删除', 2009, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:remove', '#', 'admin', '2023-05-04 11:45:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2105, '实例管理状态', 2009, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:startorstop', '#', 'admin', '2023-05-04 11:45:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2106, '实例管理执行', 2009, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:trigger', '#', 'admin', '2023-05-04 11:46:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2107, '执行日志清除', 2010, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:joblog:clearLog', '#', 'admin', '2023-05-04 14:02:37', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2108, '执行日志明细', 2010, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:joblog:logDetailCat', '#', 'admin', '2023-05-04 14:03:08', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2109, '执行日志终止任务', 2010, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:joblog:killJob', '#', 'admin', '2023-05-04 14:03:33', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2110, '任务列表新增', 2024, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:add', '#', 'admin', '2023-05-04 14:35:24', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2111, '任务列表编辑', 2024, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:edit', '#', 'admin', '2023-05-04 14:35:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2112, '任务列表删除', 2024, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:delete', '#', 'admin', '2023-05-04 14:36:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2113, '人物列表开启', 2024, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:closeoropen', '#', 'admin', '2023-05-04 14:37:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2114, '任务列表启动', 2024, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:start', '#', 'admin', '2023-05-04 14:37:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2115, '任务列表停止', 2024, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:stop', '#', 'admin', '2023-05-04 14:38:16', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2116, '任务列表备份', 2024, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:savepoint', '#', 'admin', '2023-05-04 14:38:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2117, '任务列表恢复', 2024, 8, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:savepoint', '#', 'admin', '2023-05-04 14:39:11', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2118, '任务列表复制', 2024, 9, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:copyConfig', '#', 'admin', '2023-05-04 14:39:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2119, '数据源新增', 2071, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'metadata:tables:add', '#', 'admin', '2023-05-04 14:47:48', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2120, '数据源修改', 2071, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'metadata:tables:edit', '#', 'admin', '2023-05-04 14:48:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2121, '数据源删除', 2071, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'metadata:tables:remove', '#', 'admin', '2023-05-04 14:48:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2122, '数据源明细', 2071, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'metadata:tables:detail', '#', 'admin', '2023-05-04 14:52:13', 'admin', '2023-05-04 14:52:23', '');
INSERT INTO `sys_menu` VALUES (2123, '项目管理查询', 2004, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobProject:query', '#', 'admin', '2023-05-04 15:03:49', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2124, '数据源管理查询', 2005, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:Datasource:query', '#', 'admin', '2023-05-04 15:05:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2125, '资源管理查询', 2006, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:resource:query', '#', 'admin', '2023-05-04 15:05:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2126, '调度模板查询', 2007, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:jobTemplate:list', '#', 'admin', '2023-05-04 15:06:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2127, '实例管理查询', 2009, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:job:query', '#', 'admin', '2023-05-04 15:07:40', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2128, '执行日志查询', 2010, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'datax:joblog:list', '#', 'admin', '2023-05-04 15:08:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2129, '任务列表查询', 2024, 10, '', NULL, NULL, 1, 0, 'F', '0', '0', 'flink:job:listTask', '#', 'admin', '2023-05-04 15:08:55', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2130, '数据源查询', 2071, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'metadata:tables:list', '#', 'admin', '2023-05-04 15:09:50', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2131, '数据API', 0, 11, 'market', NULL, NULL, 1, 0, 'M', '0', '0', '', 'guide', 'admin', '2023-05-08 15:10:24', 'admin', '2023-05-08 15:11:36', '');
INSERT INTO `sys_menu` VALUES (2132, '数据服务', 2131, 1, 'dataapi', 'market/dataapi/index', NULL, 1, 0, 'C', '0', '0', '', 'cascader', 'admin', '2023-05-08 15:11:26', 'admin', '2023-05-08 15:12:31', '');
INSERT INTO `sys_menu` VALUES (2133, '数据脱敏', 2131, 2, 'apimask', 'market/apimask/index', NULL, 1, 0, 'C', '0', '0', NULL, 'lock', 'admin', '2023-05-10 14:24:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2138, 'API日志', 2131, 3, 'apilog', 'market/apilog/index', NULL, 1, 0, 'C', '0', '0', NULL, 'log', 'admin', '2023-05-15 10:11:05', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2022-04-08 14:52:41', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2022-04-08 14:52:41', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(11) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(11) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(11) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2014 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2022-04-08 14:52:27', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2022-04-08 14:52:27', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2022-04-08 14:52:27', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2022-04-08 14:52:27', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2022-04-08 14:52:27', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2022-04-08 14:52:27', 'admin', '2023-05-04 16:07:43', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2001);
INSERT INTO `sys_role_menu` VALUES (2, 2002);
INSERT INTO `sys_role_menu` VALUES (2, 2003);
INSERT INTO `sys_role_menu` VALUES (2, 2004);
INSERT INTO `sys_role_menu` VALUES (2, 2005);
INSERT INTO `sys_role_menu` VALUES (2, 2006);
INSERT INTO `sys_role_menu` VALUES (2, 2007);
INSERT INTO `sys_role_menu` VALUES (2, 2009);
INSERT INTO `sys_role_menu` VALUES (2, 2010);
INSERT INTO `sys_role_menu` VALUES (2, 2017);
INSERT INTO `sys_role_menu` VALUES (2, 2018);
INSERT INTO `sys_role_menu` VALUES (2, 2019);
INSERT INTO `sys_role_menu` VALUES (2, 2020);
INSERT INTO `sys_role_menu` VALUES (2, 2021);
INSERT INTO `sys_role_menu` VALUES (2, 2023);
INSERT INTO `sys_role_menu` VALUES (2, 2024);
INSERT INTO `sys_role_menu` VALUES (2, 2033);
INSERT INTO `sys_role_menu` VALUES (2, 2060);
INSERT INTO `sys_role_menu` VALUES (2, 2063);
INSERT INTO `sys_role_menu` VALUES (2, 2071);
INSERT INTO `sys_role_menu` VALUES (2, 2077);
INSERT INTO `sys_role_menu` VALUES (2, 2078);
INSERT INTO `sys_role_menu` VALUES (2, 2079);
INSERT INTO `sys_role_menu` VALUES (2, 2080);
INSERT INTO `sys_role_menu` VALUES (2, 2081);
INSERT INTO `sys_role_menu` VALUES (2, 2083);
INSERT INTO `sys_role_menu` VALUES (2, 2084);
INSERT INTO `sys_role_menu` VALUES (2, 2085);
INSERT INTO `sys_role_menu` VALUES (2, 2086);
INSERT INTO `sys_role_menu` VALUES (2, 2089);
INSERT INTO `sys_role_menu` VALUES (2, 2090);
INSERT INTO `sys_role_menu` VALUES (2, 2091);
INSERT INTO `sys_role_menu` VALUES (2, 2092);
INSERT INTO `sys_role_menu` VALUES (2, 2093);
INSERT INTO `sys_role_menu` VALUES (2, 2094);
INSERT INTO `sys_role_menu` VALUES (2, 2095);
INSERT INTO `sys_role_menu` VALUES (2, 2096);
INSERT INTO `sys_role_menu` VALUES (2, 2097);
INSERT INTO `sys_role_menu` VALUES (2, 2098);
INSERT INTO `sys_role_menu` VALUES (2, 2099);
INSERT INTO `sys_role_menu` VALUES (2, 2102);
INSERT INTO `sys_role_menu` VALUES (2, 2107);
INSERT INTO `sys_role_menu` VALUES (2, 2108);
INSERT INTO `sys_role_menu` VALUES (2, 2109);
INSERT INTO `sys_role_menu` VALUES (2, 2110);
INSERT INTO `sys_role_menu` VALUES (2, 2119);
INSERT INTO `sys_role_menu` VALUES (2, 2123);
INSERT INTO `sys_role_menu` VALUES (2, 2124);
INSERT INTO `sys_role_menu` VALUES (2, 2125);
INSERT INTO `sys_role_menu` VALUES (2, 2126);
INSERT INTO `sys_role_menu` VALUES (2, 2127);
INSERT INTO `sys_role_menu` VALUES (2, 2128);
INSERT INTO `sys_role_menu` VALUES (2, 2129);
INSERT INTO `sys_role_menu` VALUES (2, 2130);

-- ----------------------------
-- Table structure for sys_servers
-- ----------------------------
DROP TABLE IF EXISTS `sys_servers`;
CREATE TABLE `sys_servers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组名',
  `groupCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组编码',
  `serverAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务器地址',
  `osName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统名',
  `startTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '程序启动时间',
  `pid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'pid',
  `cpuCores` int(11) NULL DEFAULT NULL COMMENT 'cpu核心数',
  `cpuUtilization` double(11, 2) NULL DEFAULT NULL COMMENT 'cpu使用率',
  `cpuRate` double(11, 2) NULL DEFAULT NULL COMMENT 'cpu空闲率',
  `jvmInitialMemory` double(11, 2) NULL DEFAULT NULL COMMENT 'JVM初始内存',
  `jvmMaxMemory` double(11, 2) NULL DEFAULT NULL COMMENT 'JVM最大内存',
  `jvmUsedMemory` double(11, 2) NULL DEFAULT NULL COMMENT 'JVM已用内存',
  `physicalMemory` double(11, 2) NULL DEFAULT NULL COMMENT '总物理内存',
  `surplusMemory` double(11, 2) NULL DEFAULT NULL COMMENT '剩余物理内存',
  `usedMemory` double(11, 2) NULL DEFAULT NULL COMMENT '已用物理内存',
  `diskStatus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '磁盘状态',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_servers
-- ----------------------------
INSERT INTO `sys_servers` VALUES (5, 'datax', 'datax', 'localhost', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-04-29 14:30:24', 'admin');
INSERT INTO `sys_servers` VALUES (6, 'flinkx', 'flinkx', 'localhost', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-04-29 14:32:52', 'admin');
INSERT INTO `sys_servers` VALUES (7, 'datax', 'datax', '192.168.172.235', 'Linux', '2022-04-29 15:29:54', '13523', 8, 0.00, 0.99, 502.00, 7115.00, 17.92, 31.26, 7.27, 23.99, '/总:466.8G,可用空间:439.9G,空闲空间:439.9G\n', '2022-04-29 15:30:11', 'admin');

-- ----------------------------
-- Table structure for sys_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_table`;
CREATE TABLE `sys_table`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
  `parent_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父名称',
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '祖籍列表',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库/表/字段',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `lenth` int(11) NULL DEFAULT NULL COMMENT '长度',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库/表/字段名',
  `createTableQuery` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '建表语句',
  `action_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作用类型（档案,指标,数据）',
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表主题（零售，会员，库存等）',
  `level_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TABLE:表  DATABASE:库  FIELD:字段',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_systable`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18035 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '192.168.90.44', '2023-06-26 09:19:50', 'admin', '2022-04-08 14:52:25', '', '2023-06-26 09:19:49', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '2', '127.0.0.1', '2022-04-08 14:52:26', 'admin', '2022-04-08 14:52:26', '', NULL, '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (100, 4);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (100, 2);

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key值',
  `val` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'value',
  `type` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 如:sys  alarm',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名字',
  `file_path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '1:jar',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `resource_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源类型',
  `resource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `resource_id` int(11) NULL DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '上传文件管理' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户帐号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `status` tinyint(1) NOT NULL COMMENT '1:启用 0: 停用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1:删除 0: 未删除',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `edit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'sys',
  `editor` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_uk`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', 1, 0, '2020-07-10 22:15:04', '2020-07-24 22:21:35', 'sys', 'sys');

SET FOREIGN_KEY_CHECKS = 1;
