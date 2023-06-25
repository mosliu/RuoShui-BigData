package com.ruoshui.flink.service.impl;

import com.ruoshui.flink.streaming.web.enums.YN;
import com.ruoshui.flink.mapper.AlarmLogMapper;
import com.ruoshui.flink.streaming.web.model.dto.AlartLogDTO;
import com.ruoshui.flink.streaming.web.model.dto.PageModel;
import com.ruoshui.flink.streaming.web.model.entity.AlartLog;
import com.ruoshui.flink.streaming.web.model.param.AlartLogParam;
import com.ruoshui.flink.service.AlartLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xinjingruoshui
 * @Description:
 * @date 2022-09-25
 * @time 21:43
 */
@Slf4j
@Service
public class AlartLogServiceImpl implements AlartLogService {


    @Autowired
    private AlarmLogMapper alarmLogMapper;

    @Override
    public void addAlartLog(AlartLogDTO alartLogDTO) {
        if (alartLogDTO == null) {
            return;
        }
        alarmLogMapper.insert(AlartLogDTO.toEntity(alartLogDTO));
    }


    @Override
    public AlartLogDTO findLogById(Long id) {
        return AlartLogDTO.toDTO(alarmLogMapper.selectByPrimaryKey(id));
    }

    @Override
    public PageModel<AlartLogDTO> queryAlartLog(AlartLogParam alartLogParam) {
        if (alartLogParam == null) {
            alartLogParam = new AlartLogParam();
        }
        PageHelper.startPage(alartLogParam.getPageNum(), alartLogParam.getPageSize(), YN.Y.getCode());

        //只能查最近30天的
        Page<AlartLog> page = alarmLogMapper.selectByParam(alartLogParam);
        if (page == null) {
            return null;
        }
        PageModel<AlartLogDTO> pageModel = new PageModel<>();
        pageModel.setPageNum(page.getPageNum());
        pageModel.setPages(page.getPages());
        pageModel.setPageSize(page.getPageSize());
        pageModel.setTotal(page.getTotal());
        pageModel.addAll(AlartLogDTO.toListDTO(page.getResult()));
        return pageModel;

    }
}
