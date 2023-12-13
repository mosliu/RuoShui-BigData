package com.ruoshui.core.database.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;

public abstract class BaseServiceImpl<M extends BaseDao<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Resource
    protected M baseDao;
}
