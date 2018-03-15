package com.cch.cz.ctrl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/14.
 */
@Controller
public class Login {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @RequestMapping(value = "/")
    private String index(){
        return "/admin";
    }

    @RequestMapping(value = "/login")
    public String login(Model model){
        User u = new User();
        model.addAttribute(u);
        return "/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user,@RequestParam(defaultValue = "false") String rememberMe){

        UsernamePasswordToken token = new UsernamePasswordToken (user.getUserName (),user.getSal(),rememberMe);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login (token);
        } catch (UnknownAccountException e) {
            logger.info ("账号或密码错误");
        } catch (IncorrectCredentialsException e) {
            logger.info ("账号或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info ("账号或密码错误");
        }
        if (subject.isAuthenticated()) {
            subject.getSession ().setAttribute ("user",userService.getByuserName (user.getUserName ()));
            return "redirect:/";
        } else {
            return "/login";
        }
    }
}
