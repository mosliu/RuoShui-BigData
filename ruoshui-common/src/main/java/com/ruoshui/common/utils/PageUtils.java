package com.ruoshui.common.utils;

import com.github.pagehelper.PageHelper;
import com.ruoshui.common.core.page.PageDomain;
import com.ruoshui.common.core.page.TableSupport;
import com.ruoshui.common.utils.sql.SqlUtil;

/**
 * 分页工具类
 * 
 * @author ruoshui
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
