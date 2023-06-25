package com.ruoshui.standard.service;



import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.standard.dto.ContrastDictDto;
import com.ruoshui.standard.entity.ContrastDictEntity;

import java.util.List;

/**
 * <p>
 * 字典对照信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
public interface ContrastDictService extends BaseService<ContrastDictEntity> {

    ContrastDictEntity saveContrastDict(ContrastDictDto contrastDict);

    ContrastDictEntity updateContrastDict(ContrastDictDto contrastDict);

    ContrastDictEntity getContrastDictById(String id);

    void deleteContrastDictById(String id);

    void deleteContrastDictBatch(List<String> ids);
}
