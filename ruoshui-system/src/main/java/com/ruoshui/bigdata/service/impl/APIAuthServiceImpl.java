package com.ruoshui.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoshui.bigdata.entity.APIAuth;
import com.ruoshui.bigdata.mapper.APIAuthMapper;
import com.ruoshui.bigdata.service.APIAuthService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("apiAuthService")
public class APIAuthServiceImpl extends ServiceImpl<APIAuthMapper, APIAuth> implements APIAuthService {

    @Resource
    private APIAuthMapper apiAuthMapper;

}
