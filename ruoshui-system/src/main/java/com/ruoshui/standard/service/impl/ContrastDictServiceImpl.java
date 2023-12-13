package com.ruoshui.standard.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.standard.dto.ContrastDictDto;
import com.ruoshui.standard.entity.ContrastDictEntity;
import com.ruoshui.standard.mapper.ContrastDictDao;
import com.ruoshui.standard.mapstruct.ContrastDictMapper;
import com.ruoshui.standard.service.ContrastDictService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;


/**
 * <p>
 * 字典对照信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContrastDictServiceImpl extends BaseServiceImpl<ContrastDictDao, ContrastDictEntity> implements ContrastDictService {

    @Resource
    private ContrastDictDao contrastDictDao;

    @Resource
    private ContrastDictMapper contrastDictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContrastDictEntity saveContrastDict(ContrastDictDto contrastDictDto) {
        ContrastDictEntity contrastDict = contrastDictMapper.toEntity(contrastDictDto);
        contrastDict.setCreateBy(getUsername());
        contrastDictDao.insert(contrastDict);
        return contrastDict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContrastDictEntity updateContrastDict(ContrastDictDto contrastDictDto) {
        ContrastDictEntity contrastDict = contrastDictMapper.toEntity(contrastDictDto);
        contrastDict.setUpdateBy(getUsername());
        contrastDictDao.updateById(contrastDict);
        return contrastDict;
    }

    @Override
    public ContrastDictEntity getContrastDictById(String id) {
        ContrastDictEntity contrastDictEntity = super.getById(id);
        return contrastDictEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContrastDictById(String id) {
        contrastDictDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContrastDictBatch(List<String> ids) {
        contrastDictDao.deleteBatchIds(ids);
    }
}
