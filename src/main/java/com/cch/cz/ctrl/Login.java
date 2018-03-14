package com.cch.cz.ctrl;

import com.cch.cz.authority.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/3/14.
 */
@Controller
public class Login {
    @RequestMapping(value = "/login")
    public String login(Model model){
        User u = new User();
        model.addAttribute(u);
        return "/login";
    }
}
