package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.SupplementUrge;

import java.util.List;
import java.util.Map;

public interface SupplementUrgeService extends BaseService<com.cch.cz.entity.SupplementUrge, java.lang.Long> {
    List<Map> manager(Map c);

    @Override
    void save(SupplementUrge supplementUrge);
}