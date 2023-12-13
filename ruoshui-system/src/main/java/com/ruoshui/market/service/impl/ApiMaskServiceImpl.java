package com.ruoshui.market.service.impl;


import com.aspose.words.net.System.Data.DataException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.market.dto.ApiMaskDto;
import com.ruoshui.market.entity.ApiMaskEntity;
import com.ruoshui.market.mapper.ApiMaskDao;
import com.ruoshui.market.mapstruct.ApiMaskMapper;
import com.ruoshui.market.service.ApiMaskService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 数据API脱敏信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApiMaskServiceImpl extends BaseServiceImpl<ApiMaskDao, ApiMaskEntity> implements ApiMaskService {

    @Resource
    private ApiMaskDao apiMaskDao;

    @Resource
    private ApiMaskMapper apiMaskMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveApiMask(ApiMaskDto apiMaskDto) {
        ApiMaskEntity apiMask = apiMaskMapper.toEntity(apiMaskDto);
        // 校验api唯一
        int n = apiMaskDao.selectCount(Wrappers.<ApiMaskEntity>lambdaQuery().eq(ApiMaskEntity::getApiId, apiMask.getApiId()));
        if(n > 0){
            throw new DataException("该api已进行过脱敏配置");
        }
        apiMaskDao.insert(apiMask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApiMask(ApiMaskDto apiMaskDto) {
        ApiMaskEntity apiMask = apiMaskMapper.toEntity(apiMaskDto);
        apiMaskDao.updateById(apiMask);
    }

    @Override
    public ApiMaskEntity getApiMaskById(String id) {
        ApiMaskEntity apiMaskEntity = super.getById(id);
        return apiMaskEntity;
    }

    @Override
    public ApiMaskEntity getApiMaskByApiId(String apiId) {
        ApiMaskEntity apiMaskEntity = apiMaskDao.selectOne(new QueryWrapper<ApiMaskEntity>().eq("api_id", apiId));
        return apiMaskEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiMaskById(String id) {
        apiMaskDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiMaskBatch(List<String> ids) {
        apiMaskDao.deleteBatchIds(ids);
    }
}
