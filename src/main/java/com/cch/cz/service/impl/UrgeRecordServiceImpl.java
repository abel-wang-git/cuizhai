package com.cch.cz.service.impl;

import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.entity.WhiteList;
import com.cch.cz.mapper.UrgeRecordMapper;
import com.cch.cz.service.UrgeRecordService;
import com.cch.cz.service.WhiteListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class UrgeRecordServiceImpl extends BaseServiceImpl<UrgeRecord,Long> implements UrgeRecordService {

    @Resource
    private UrgeRecordMapper urgeRecordMapper;

    @Override
    public List<UrgeRecord> findByCase(Long id) {

        return urgeRecordMapper.findByCase(id);
    }
}
