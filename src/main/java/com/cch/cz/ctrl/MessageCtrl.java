package com.cch.cz.ctrl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Message;
import com.cch.cz.entity.MessageStatus;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/message")
public class MessageCtrl {

    @Resource
    private MessageService messageService;

    @PostMapping(value = "/getmessage")
    @ResponseBody
    public Table getNoRead(@RequestParam String staffId) {
        List<Map> list = messageService.getNoRead(staffId);
        return new Table(list.size(), list);
    }

    @PostMapping(value = "/read")
    @ResponseBody
    public AjaxReturn read(@RequestParam String message) {
        messageService.read(message);
        return new AjaxReturn();
    }


}