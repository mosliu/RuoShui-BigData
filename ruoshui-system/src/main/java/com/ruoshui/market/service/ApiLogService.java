package com.ruoshui.market.service;



import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.market.dto.ApiLogDto;
import com.ruoshui.market.entity.ApiLogEntity;

import java.util.List;

public interface ApiLogService extends BaseService<ApiLogEntity> {

    ApiLogEntity saveApiLog(ApiLogDto apiLog);

    ApiLogEntity updateApiLog(ApiLogDto apiLog);

    ApiLogEntity getApiLogById(String id);

    void deleteApiLogById(String id);

    void deleteApiLogBatch(List<String> ids);
}
