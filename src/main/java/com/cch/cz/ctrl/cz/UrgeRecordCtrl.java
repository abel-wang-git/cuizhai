package com.cch.cz.ctrl.cz;

import com.cch.cz.service.UrgeRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 *崔记管理
 */
@Controller
@RequestMapping(value = "/urge")
public class UrgeRecordCtrl {

    @Resource
    private UrgeRecordService urgeRecordService;

    @GetMapping(value = "/list")
    public  String list(Model model){
        model.addAttribute(urgeRecordService.findAll());
        return "/cz/urge/list";
    }
}
