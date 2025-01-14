package com.ruoshui.standard.service;

import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.standard.dto.DictDto;
import com.ruoshui.standard.entity.DictEntity;

import java.util.List;

/**
 * <p>
 * 数据标准字典表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
public interface DictService extends BaseService<DictEntity> {

    DictEntity saveDict(DictDto dict);

    DictEntity updateDict(DictDto dict);

    DictEntity getDictById(String id);

    void deleteDictById(String id);

    void deleteDictBatch(List<String> ids);

    void refreshDict();
}
