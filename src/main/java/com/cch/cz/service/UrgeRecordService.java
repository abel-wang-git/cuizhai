package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.UrgeRecord;

import java.util.List;

/**
 *
 */
public interface UrgeRecordService extends BaseService<UrgeRecord,Long>  {
    /**
     * 查寻某个case的所有催记
     * @param id
     */
    List<UrgeRecord> findByCase(Long id);
}
