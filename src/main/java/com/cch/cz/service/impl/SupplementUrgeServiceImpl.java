package com.cch.cz.service.impl;

import com.cch.cz.entity.Cases;
import com.cch.cz.entity.SupplementUrge;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.mapper.SupplementMapper;
import com.cch.cz.mapper.SupplementUrgeMapper;
import org.springframework.stereotype.Service;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.service.SupplementUrgeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SupplementUrgeServiceImpl extends BaseServiceImpl<com.cch.cz.entity.SupplementUrge, java.lang.Long> implements SupplementUrgeService {
    @Resource
    private SupplementUrgeMapper supplementUrgeMapper;
    @Resource
    private CasesMapper casesMapper;

    @Override
    public List<Map> manager(Map c) {

        return supplementUrgeMapper.manager(c);
    }

    @Override
    public void save(SupplementUrge supplementUrge) {
        Cases cases = casesMapper.findOne(new Cases(), supplementUrge.getCaseId());
        cases.setLastUrge(supplementUrge.getCreateDate());
//        if (supplementUrge.getSuResult().equals("承诺还款")) cases.setStatus(Cases.PROMISE);
        casesMapper.update(cases);
        super.save(supplementUrge);
    }
}