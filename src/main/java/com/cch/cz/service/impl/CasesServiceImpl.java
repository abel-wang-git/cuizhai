package com.cch.cz.service.impl;

import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Cases;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.service.CasesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 *
 */
@Service
public class CasesServiceImpl extends BaseServiceImpl<Cases,Long> implements CasesService {
    @Resource
    private CasesMapper casesMapper;

    @Override
    @Transactional
    public void expCase(List<Cases> casesList) {
        for (Cases c:casesList) {
            casesMapper.save(c);
        }
    }

    @Override
    public List<Map> getCasesByArea() {
        return casesMapper.getCasesByArea();
    }
}
