package com.ruoshui.market.service;


import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.market.dto.ApiMaskDto;
import com.ruoshui.market.entity.ApiMaskEntity;

import java.util.List;

/**
 * <p>
 * 数据API脱敏信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
public interface ApiMaskService extends BaseService<ApiMaskEntity> {

    void saveApiMask(ApiMaskDto dataApiMask);

    void updateApiMask(ApiMaskDto dataApiMask);

    ApiMaskEntity getApiMaskById(String id);

    ApiMaskEntity getApiMaskByApiId(String apiId);

    void deleteApiMaskById(String id);

    void deleteApiMaskBatch(List<String> ids);
}
