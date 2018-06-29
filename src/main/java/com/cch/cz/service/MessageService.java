package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface MessageService extends BaseService<com.cch.cz.entity.Message, java.lang.Long> {
    List<Map> getNoRead(String staffId);

    void read(String message);
}