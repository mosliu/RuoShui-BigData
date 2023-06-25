package com.ruoshui.standard.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoshui.core.database.base.DataScopeBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据标准类别表
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("standard_type")
public class TypeEntity extends DataScopeBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 标准类别编码
     */
    private String gbTypeCode;

    /**
     * 标准类别名称
     */
    private String gbTypeName;
}
