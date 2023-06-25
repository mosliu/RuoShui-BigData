package com.ruoshui.standard.service;

import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.standard.dto.TypeDto;
import com.ruoshui.standard.entity.TypeEntity;

import java.util.List;

/**
 * <p>
 * 数据标准类别表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
public interface TypeService extends BaseService<TypeEntity> {

    TypeEntity saveType(TypeDto type);

    TypeEntity updateType(TypeDto type);

    TypeEntity getTypeById(String id);

    void deleteTypeById(String id);

    void deleteTypeBatch(List<String> ids);
}
