package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;

import com.cch.cz.entity.Supplement;

import java.util.List;

public interface SupplementService extends BaseService<com.cch.cz.entity.Supplement, String> {
    void expSupplement(List<Supplement> supplementList);
}
