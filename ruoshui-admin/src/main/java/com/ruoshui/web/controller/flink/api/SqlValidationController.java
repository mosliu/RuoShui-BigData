package com.ruoshui.web.controller.flink.api;

import com.ruoshui.flink.streaming.sql.validation.SqlValidation;
import com.ruoshui.flink.streaming.web.common.RestResult;
import com.ruoshui.web.controller.flink.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-07-07
 * @time 22:00
 */
@RestController
@RequestMapping("/flink")
@Slf4j
public class SqlValidationController extends BaseController {

    @RequestMapping("/checkfSql")
    public RestResult checkfSql(String flinkSql) {
        if (StringUtils.isEmpty(flinkSql)) {
            return RestResult.error("flinkSql 参数不能为空");
        }
        try {
            List<String> listSql = SqlValidation.toSqlList(flinkSql);
            if (CollectionUtils.isEmpty(listSql)) {
                return RestResult.error("没有检测到有效sql语句,是否缺少了 ; 分隔符");
            }
            SqlValidation.preCheckSql(listSql);
        } catch (Exception e) {
            log.warn("校验失败flinkSql={}   errorMessage= {} ", flinkSql, e.getMessage());
            return RestResult.error(e.getMessage());
        }
        return RestResult.success();
    }


}
