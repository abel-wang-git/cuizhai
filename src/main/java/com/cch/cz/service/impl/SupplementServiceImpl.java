package com.cch.cz.service.impl;

import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Supplement;
import com.cch.cz.mapper.SupplementMapper;
import com.cch.cz.service.SupplementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class SupplementServiceImpl extends BaseServiceImpl<com.cch.cz.entity.Supplement, String> implements SupplementService {
    @Resource
    private SupplementMapper supplementMapper;

    @Override
    @Transactional
    public void expSupplement(List<Supplement> supplementList) {
        for (Supplement s : supplementList) {
            supplementMapper.save(s);
        }
    }
}
