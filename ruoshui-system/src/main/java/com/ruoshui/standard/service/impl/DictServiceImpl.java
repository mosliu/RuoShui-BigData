package com.ruoshui.standard.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.common.core.redis.RedisCache;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.core.database.core.RedisConstant;
import com.ruoshui.standard.dto.DictDto;
import com.ruoshui.standard.entity.DictEntity;
import com.ruoshui.standard.mapper.DictDao;
import com.ruoshui.standard.mapstruct.DictMapper;
import com.ruoshui.standard.service.DictService;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 数据标准字典表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends BaseServiceImpl<DictDao, DictEntity> implements DictService {

    @Resource
    private DictDao dictDao;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private RedisCache redisService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictEntity saveDict(DictDto dictDto) {
        DictEntity dict = dictMapper.toEntity(dictDto);
        dict.setCreateBy(getUsername());
        dictDao.insert(dict);
        return dict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictEntity updateDict(DictDto dictDto) {
        DictEntity dict = dictMapper.toEntity(dictDto);
        dict.setUpdateBy(getUsername());
        dictDao.updateById(dict);
        return dict;
    }

    @Override
    public DictEntity getDictById(String id) {
        DictEntity dictEntity = super.getById(id);
        return dictEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(String id) {
        dictDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictBatch(List<String> ids) {
        dictDao.deleteBatchIds(ids);
    }

    @Override
    public void refreshDict() {
        String dictKey = RedisConstant.STANDARD_DICT_KEY;
        Boolean hasDictKey = redisService.hasKey(dictKey);
        if (hasDictKey) {
            redisService.del(dictKey);
        }
        List<DictEntity> dictEntityList = dictDao.selectList(Wrappers.emptyWrapper());
        Map<String, List<DictEntity>> dictListMap = dictEntityList.stream().collect(Collectors.groupingBy(DictEntity::getTypeId));
        redisTemplate.opsForHash().putAll(dictKey, dictListMap);
    }
}
