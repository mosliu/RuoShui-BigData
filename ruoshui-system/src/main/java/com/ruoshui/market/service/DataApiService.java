package com.ruoshui.market.service;


import com.aspose.words.Document;
import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.market.dto.DataApiDto;
import com.ruoshui.market.dto.SqlParseDto;
import com.ruoshui.market.entity.DataApiEntity;
import com.ruoshui.market.vo.SqlParseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据API信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-03-31
 */
public interface DataApiService extends BaseService<DataApiEntity> {

    void saveDataApi(DataApiDto dataApi);

    void updateDataApi(DataApiDto dataApi);

    DataApiEntity getDataApiById(String id);

    void deleteDataApiById(String id);

    void deleteDataApiBatch(List<String> ids);

    SqlParseVo sqlParse(SqlParseDto sqlParseDto);

    void copyDataApi(String id);

    void releaseDataApi(String id);

    void cancelDataApi(String id);

    Document wordDataApi(String id) throws Exception;

    Map<String, Object> getDataApiDetailById(String id);

    List<DataApiEntity> getDataApiEntityList(String status);
}
