package com.ruoshui.standard.service;


import com.ruoshui.standard.dto.ManualMappingDto;

import java.util.Map;

public interface DictMappingService {

    Map<String, Object> getDictMapping(String id);

    void dictAutoMapping(String id);

    void dictManualMapping(ManualMappingDto manualMappingDto);

    void dictCancelMapping(String id);
}
