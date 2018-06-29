package com.cch.cz.ctrl;

import com.cch.cz.base.Table;
import com.cch.cz.entity.MessageStatus;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.service.MessageStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/message")
public class MessageStatusCtrl {

    @Resource
    private MessageStatusService messageStatusService;

    //    @PostMapping(value = "/getmessage")
    @ResponseBody
    public Table getNoRead(@RequestParam String staffId) {
        MessageStatus messageStatus = new MessageStatus();
        messageStatus.setStatus(MgStatus.NOREAD.value());
        messageStatus.setReceiver(staffId);
        List<MessageStatus> list = messageStatusService.findByEntity(messageStatus);
        return new Table(list.size(), list);
    }

}