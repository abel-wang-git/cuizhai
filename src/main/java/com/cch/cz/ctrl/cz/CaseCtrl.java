package com.cch.cz.ctrl.cz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/3/16.
 * 委案管理
 */
@Controller
@RequestMapping(value = "/case")
public class CaseCtrl {

    @GetMapping(value = "/up")
    public String toCase(){
        return "/cz/cases/case";
    }
}
