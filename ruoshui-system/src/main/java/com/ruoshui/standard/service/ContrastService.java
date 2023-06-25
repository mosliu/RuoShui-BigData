package com.ruoshui.standard.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.standard.dto.ContrastDto;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.vo.ContrastTreeVo;

import java.util.List;

/**
 * <p>
 * 对照表信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
public interface ContrastService extends BaseService<ContrastEntity> {

    ContrastEntity saveContrast(ContrastDto contrast);

    ContrastEntity updateContrast(ContrastDto contrast);

    ContrastEntity getContrastById(String id);

    void deleteContrastById(String id);

    void deleteContrastBatch(List<String> ids);

    List<ContrastTreeVo> getContrastTree();

    IPage<ContrastEntity> statistic(IPage<ContrastEntity> page, Wrapper<ContrastEntity> queryWrapper);
}
