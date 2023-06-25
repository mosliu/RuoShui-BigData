package com.ruoshui.market.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.market.dto.ApiLogDto;
import com.ruoshui.market.entity.ApiLogEntity;
import com.ruoshui.market.mapper.ApiLogDao;
import com.ruoshui.market.mapstruct.ApiLogMapper;
import com.ruoshui.market.service.ApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApiLogServiceImpl extends BaseServiceImpl<ApiLogDao, ApiLogEntity> implements ApiLogService {

    @Autowired
    private ApiLogDao apiLogDao;

    @Autowired
    private ApiLogMapper apiLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiLogEntity saveApiLog(ApiLogDto apiLogDto) {
        ApiLogEntity apiLog = apiLogMapper.toEntity(apiLogDto);
        apiLogDao.insert(apiLog);
        return apiLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiLogEntity updateApiLog(ApiLogDto apiLogDto) {
        ApiLogEntity apiLog = apiLogMapper.toEntity(apiLogDto);
        apiLogDao.updateById(apiLog);
        return apiLog;
    }

    @Override
    public ApiLogEntity getApiLogById(String id) {
        ApiLogEntity apiLogEntity = super.getById(id);
        return apiLogEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiLogById(String id) {
        apiLogDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiLogBatch(List<String> ids) {
        apiLogDao.deleteBatchIds(ids);
    }
}
