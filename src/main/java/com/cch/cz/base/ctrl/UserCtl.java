package com.cch.cz.base.ctrl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.AjaxReturn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserCtl {
    @Resource
    private UserService userService;

    @PostMapping(value = "/changpwd",produces="application/json;charset=UTF-8")
    @ResponseBody
    public AjaxReturn changPwd(@RequestBody User data){
        data.setPassWd(data.Sal());
        userService.update(data);
        return new AjaxReturn(0,"修改成功");
    }
}
