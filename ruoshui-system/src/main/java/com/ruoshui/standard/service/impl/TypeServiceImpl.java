package com.ruoshui.standard.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.standard.dto.TypeDto;
import com.ruoshui.standard.entity.TypeEntity;
import com.ruoshui.standard.mapper.TypeDao;
import com.ruoshui.standard.mapstruct.TypeMapper;
import com.ruoshui.standard.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 数据标准类别表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-08-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TypeServiceImpl extends BaseServiceImpl<TypeDao, TypeEntity> implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TypeEntity saveType(TypeDto typeDto) {
        TypeEntity type = typeMapper.toEntity(typeDto);
        type.setCreateBy(getUsername());
        typeDao.insert(type);
        return type;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TypeEntity updateType(TypeDto typeDto) {
        TypeEntity type = typeMapper.toEntity(typeDto);
        type.setUpdateBy(getUsername());
        typeDao.updateById(type);
        return type;
    }

    @Override
    public TypeEntity getTypeById(String id) {
        TypeEntity typeEntity = super.getById(id);
        return typeEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTypeById(String id) {
        typeDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTypeBatch(List<String> ids) {
        typeDao.deleteBatchIds(ids);
    }
}
