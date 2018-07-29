package com.cch.cz.ctrl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Message;
import com.cch.cz.entity.MessageStatus;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.enu.MessageType;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.service.MessageService;
import com.cch.cz.service.MessageStatusService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/message")
public class MessageCtrl {

    @Resource
    private MessageService messageService;
    @Resource
    private MessageStatusService messageStatusService;
    @PostMapping(value = "/getmessage")
    @ResponseBody
    public Table getNoRead(@RequestParam String staffId) {
        List<Map> list = messageService.getNoRead(staffId);
        return new Table(list.size(), list);
    }

    //同意
    @PostMapping(value = "/cat")
    @ResponseBody
    public AjaxReturn cat(@RequestParam String message) {
        MessageStatus m = JSON.parseObject(message, MessageStatus.class);
        m.setStatus(MgStatus.READ.value());
        messageStatusService.update(m);
        return new AjaxReturn(0, "操作成功");
    }

    //拒绝
    @PostMapping(value = "/refuse")
    @ResponseBody
    public AjaxReturn refuse(@RequestParam String message) {
        messageService.refuse(message);
        return new AjaxReturn(0, "操作成功");
    }

    //拒绝
    @PostMapping(value = "/read")
    @ResponseBody
    public AjaxReturn read(@RequestParam String message) {
        messageService.read(message);
        return new AjaxReturn(0, "操作成功");
    }

    //公告
    @GetMapping(value = "/notice")
    public String tonotice() {
//        messageService.add(message);
        return "/cz/message/notice";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "0") int limit) {
        Message where = new Message();
        where.setType(MessageType.GLOBAL.value());
        PageHelper.startPage(page, limit);

        Page<Message> list = (Page<Message>) messageService.findByEntity(where);

        return new Table((int) list.getTotal(), list);
    }
    //公告
    @PostMapping(value = "/notice")
    @ResponseBody
    public AjaxReturn notice(@RequestParam String message) {
        Message m = JSON.parseObject(message, Message.class);

        messageService.notic(m);
        return new AjaxReturn(0, "操作成功");
    }




}