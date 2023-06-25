package com.ruoshui.standard.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据标准字典表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String typeId;
    private String gbCode;
    private String gbName;
}
