package com.cch.cz.ctrl;

import com.cch.cz.base.Table;
import com.cch.cz.entity.AdjustLog;
import com.cch.cz.service.AdjustLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 调案
 */
@Controller
@RequestMapping("/adjust")
public class  AdjustLogCtrl {

    @GetMapping(value = "/list")
    public String list(){
        return "/cz/adjust/list";
    }

}