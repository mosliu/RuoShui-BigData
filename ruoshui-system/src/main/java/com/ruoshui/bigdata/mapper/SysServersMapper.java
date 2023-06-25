package com.ruoshui.bigdata.mapper;

import com.ruoshui.bigdata.entity.SysServers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 执行器管理Mapper接口
 * 
 * @author ruoshui
 * @date 2022-04-28
 */
@Mapper
public interface SysServersMapper 
{
    /**
     * 查询执行器管理
     * 
     * @param id 执行器管理主键
     * @return 执行器管理
     */
    public SysServers selectSysServersById(Long id);

    /**
     * 查询执行器管理列表
     * 
     * @param sysServers 执行器管理
     * @return 执行器管理集合
     */
    public List<SysServers> selectSysServersList(SysServers sysServers);

    /**
     * 新增执行器管理
     * 
     * @param sysServers 执行器管理
     * @return 结果
     */
    public int insertSysServers(SysServers sysServers);

    /**
     * 修改执行器管理
     * 
     * @param sysServers 执行器管理
     * @return 结果
     */
    public int updateSysServers(SysServers sysServers);

    /**
     * 删除执行器管理
     * 
     * @param id 执行器管理主键
     * @return 结果
     */
    public int deleteSysServersById(Long id);

    /**
     * 批量删除执行器管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysServersByIds(Long[] ids);
}
