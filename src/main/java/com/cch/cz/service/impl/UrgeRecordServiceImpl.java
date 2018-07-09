package com.cch.cz.service.impl;

import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.entity.WhiteList;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.mapper.UrgeRecordMapper;
import com.cch.cz.service.UrgeRecordService;
import com.cch.cz.service.WhiteListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class UrgeRecordServiceImpl extends BaseServiceImpl<UrgeRecord,Long> implements UrgeRecordService {

    @Resource
    private UrgeRecordMapper urgeRecordMapper;

    @Resource
    private CasesMapper casesMapper;
    @Override
    public List<UrgeRecord> findByCase(Long id) {

        return urgeRecordMapper.findByCase(id);
    }

    @Override
    @Transactional
    public void save(UrgeRecord urgeRecord) {
        Cases cases= casesMapper.findOne(new Cases(),urgeRecord.getCaseId());
        cases.setLastUrge(urgeRecord.getCreateDate());
        if (urgeRecord.getResult().equals("承诺还款")) cases.setStatus(Cases.PROMISE);
        casesMapper.update(cases);
        super.save(urgeRecord);
    }

    @Override
    public List<Map> manager(Map c) {

        return urgeRecordMapper.manager(c);
    }
}
