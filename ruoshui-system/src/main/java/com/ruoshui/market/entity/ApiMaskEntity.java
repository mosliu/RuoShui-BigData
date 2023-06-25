package com.ruoshui.market.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoshui.core.database.base.DataScopeBaseEntity;
import com.ruoshui.market.dto.FieldRule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 数据API脱敏信息表
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */

@TableName(value = "market_api_mask", autoResultMap = true)
public class ApiMaskEntity extends DataScopeBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 数据API
     */
    private String apiId;

    /**
     * 脱敏名称
     */
    private String maskName;

    /**
     * 脱敏字段规则配置
     */
    @TableField(value = "config_json", typeHandler = JacksonTypeHandler.class)
    private List<FieldRule> rules;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getMaskName() {
        return maskName;
    }

    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    public List<FieldRule> getRules() {
        return rules;
    }

    public void setRules(List<FieldRule> rules) {
        this.rules = rules;
    }
}
