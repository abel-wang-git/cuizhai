package com.cch.cz.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adjust")
public class  AdjustLogCtrl {

    @GetMapping(value = "/list")
    public String list(){
        return "/cz/adjust/list";
    }

}