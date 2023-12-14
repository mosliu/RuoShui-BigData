package com.ruoshui.bigdata.service.impl;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoshui.bigdata.entity.SysServers;
import com.ruoshui.bigdata.mapper.SysServersMapper;
import com.ruoshui.bigdata.service.ISysServersService;
import com.ruoshui.bigdata.service.RpcService;
import com.ruoshui.common.annotation.Excel;
import com.ruoshui.common.config.RPCClient;
import com.ruoshui.common.utils.DateUtils;
import com.ruoshui.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 执行器管理Service业务层处理
 * 
 * @author ruoshui
 * @date 2022-04-28
 */
@Service
public class SysServersServiceImpl implements ISysServersService
{
    @Autowired
    private SysServersMapper sysServersMapper;

    /**
     * 查询执行器管理
     * 
     * @param id 执行器管理主键
     * @return 执行器管理
     */
    @Override
    public SysServers selectSysServersById(Long id)
    {
        return sysServersMapper.selectSysServersById(id);
    }

    /**
     * 查询执行器管理列表
     * 
     * @param sysServers 执行器管理
     * @return 执行器管理
     */
    @Override
    public List<SysServers> selectSysServersList(SysServers sysServers)
    {
        return sysServersMapper.selectSysServersList(sysServers);
    }

    /**
     * 新增执行器管理
     * 
     * @param sysServers 执行器管理
     * @return 结果
     */
    @Override
    public int insertSysServers(SysServers sysServers)
    {
        SysServers sysServer = new SysServers();
        sysServers.setCreateTime(DateUtils.getNowDate());
        if(!StringUtils.isEmpty(sysServers.getServeraddress())){
            if(!"localhost".equals(sysServers.getServeraddress())) {
                RpcService service = RPCClient.getRemoteProxyObj(RpcService.class, new InetSocketAddress(sysServers.getServeraddress(), 8088));
                if (!StringUtils.isEmpty(service.getMonitor())) {
                    sysServer = JSONObject.parseObject(service.getMonitor(), SysServers.class);
                } else {
                    throw new RuntimeException("获取服务器状态失败,请检查执行器服务或端口号是否开启!");
                }
            }
        }else{
            throw new RuntimeException("服务IP不能为空!");
        }
        sysServer.setGroupcode(sysServers.getGroupname());
        sysServer.setGroupname(sysServers.getGroupname());
        sysServer.setServeraddress(sysServers.getServeraddress());
        sysServer.setCreateTime(sysServers.getCreateTime());
        sysServer.setCreateBy(sysServers.getCreateBy());
        return sysServersMapper.insertSysServers(sysServer);
    }

    /**
     * 修改执行器管理
     * 
     * @param sysServers 执行器管理
     * @return 结果
     */
    @Override
    public int updateSysServers(SysServers sysServers)
    {
        if("localhost".equals(sysServers.getServeraddress())){
            throw new RuntimeException("本机地址不能删除");
        }
        return sysServersMapper.updateSysServers(sysServers);
    }

    /**
     * 批量删除执行器管理
     * 
     * @param ids 需要删除的执行器管理主键
     * @return 结果
     */
    @Override
    public int deleteSysServersByIds(Long[] ids)
    {

        return sysServersMapper.deleteSysServersByIds(ids);
    }

    /**
     * 删除执行器管理信息
     * 
     * @param id 执行器管理主键
     * @return 结果
     */
    @Override
    public int deleteSysServersById(Long id)
    {
        return sysServersMapper.deleteSysServersById(id);
    }
}
