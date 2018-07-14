package com.cch.cz.service.impl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Message;
import com.cch.cz.entity.MessageStatus;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.enu.MessageType;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.mapper.MessageMapper;
import com.cch.cz.mapper.MessageStatusMapper;
import com.cch.cz.mapper.StaffMapper;
import org.springframework.stereotype.Service;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.service.MessageService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class MessageServiceImpl extends BaseServiceImpl<com.cch.cz.entity.Message, java.lang.Long> implements MessageService {

    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageStatusMapper messageStatusMapper;
    @Resource
    private CasesMapper casesMapper;
    @Resource
    private StaffMapper staffMapper;

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
        if (message1.getType() == MessageType.END.value()) {
            cases.setStatus(Cases.FINALLYEND);
        }
        if (message1.getType() == MessageType.RETAIN.value()) {
            cases.setStatus(Cases.FINALLYRETAIN);
        }
        casesMapper.update(cases);
    }

    @Override
    public void refuse(String message) {
        MessageStatus messageStatus = JSON.parseObject(message, MessageStatus.class);
        messageStatus.setStatus(MgStatus.READ.value());
        messageStatusMapper.update(messageStatus);
        Message message1 = JSON.parseObject(message, Message.class);
        Cases cases = casesMapper.findOne(new Cases(), message1.getCaseId());
        if (message1.getType() == MessageType.END.value()) {
            cases.setStatus(Cases.NORMAL);
        }
        if (message1.getType() == MessageType.RETAIN.value()) {
            cases.setStatus(Cases.NORMAL);
        }
        casesMapper.update(cases);
    }

    @Override
    @Transactional
    public void notic(Message m) {
        m.setDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
        m.setId(UUID.randomUUID().getLeastSignificantBits());
        m.setType(MessageType.GLOBAL.value());
        save(m);
        List<Staff> staffs = staffMapper.findAll(new Staff());
        for (Staff s : staffs) {
            MessageStatus ms = new MessageStatus();
            ms.setMessageId(m.getId());
            ms.setReceiver(s.getLoginName());
            ms.setStatus(MgStatus.NOREAD.value());
            messageStatusMapper.save(ms);
        }
    }
}