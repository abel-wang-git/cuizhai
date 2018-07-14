package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Message;
import com.cch.cz.entity.Staff;

import java.util.List;
import java.util.Map;

public interface MessageService extends BaseService<com.cch.cz.entity.Message, java.lang.Long> {
    List<Map> getNoRead(String staffId);

    void read(String message);

    void refuse(String message);

    void notic(Message m);
}