package com.cch.cz.service.impl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Message;
import com.cch.cz.entity.MessageStatus;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.mapper.MessageMapper;
import com.cch.cz.mapper.MessageStatusMapper;
import org.springframework.stereotype.Service;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.service.MessageService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Service
public class MessageServiceImpl extends BaseServiceImpl<com.cch.cz.entity.Message, java.lang.Long> implements MessageService {

    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageStatusMapper messageStatusMapper;
    @Resource
    private CasesMapper casesMapper;

    @Override
    public List<Map> getNoRead(String staffId) {

        return messageMapper.getMessage(staffId, MgStatus.NOREAD.value());
    }

    @Override
    @Transactional
    public void read(String message) {
        MessageStatus messageStatus = JSON.parseObject(message, MessageStatus.class);
        messageStatus.setStatus(MgStatus.READ.value());
        messageStatusMapper.update(messageStatus);
        Message message1 = JSON.parseObject(message, Message.class);
        Cases cases = casesMapper.findOne(new Cases(), message1.getCaseId());
        cases.setStatus(Cases.FINALLYEND);
        casesMapper.update(cases);
    }
}