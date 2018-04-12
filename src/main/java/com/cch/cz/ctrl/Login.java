package com.cch.cz.ctrl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.StaffService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/14.
 */
@Controller
public class Login {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private StaffService staffService;
    @Resource
    private CasesService casesService;
    @Resource
    private RoleService roleService;
    @GetMapping(value = "/")
    private String index(Model model){
         Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
         String staffId = staff.getLoginName();
         if (staff.getLoginName()!=null&&roleService.findOne(Long.valueOf(staff.getPlace())).getDesign().equals("管理员")) staffId="";

        List<Map> normal=casesService.listByCompany(staff.getCompanyId()!=null?staff.getCompanyId():null,staffId,0);

        List<Map> end=casesService.listByCompany(staff.getCompanyId()!=null?staff.getCompanyId():null,staffId,3);

        model.addAttribute("normal",normal);
        model.addAttribute("end",end);
        return "/admin";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        User u = new User();
        model.addAttribute(u);
        return "/login";
    }

    @PostMapping(value = "/login")
    public String login(Model model,User user,@RequestParam(defaultValue = "false") String rememberMe){

        UsernamePasswordToken token = new UsernamePasswordToken (user.getUserName (),user.Sal(),rememberMe);
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute(user);
        try {
            subject.login (token);
        } catch (UnknownAccountException e) {
            model.addAttribute("tig","账号或密码错误");
            e.printStackTrace();
            logger.info ("账号或密码错误");
        } catch (Exception e){
            model.addAttribute("tig","账号或密码错误");
            e.printStackTrace();
            logger.info ("账号或密码错误");
        }
        if (subject.isAuthenticated()) {
            subject.getSession ().setAttribute ("user",userService.getByuserName (user.getUserName ()));
            subject.getSession().setAttribute("staff",(staffService.findOne(user.getUserName())==null)?new  Staff():staffService.findOne(user.getUserName()));
            return "redirect:/";
        } else {

            return "/login";
        }
    }

    @GetMapping(value = "/loginOut")
    public String loginout(HttpSession session) {
        SecurityUtils.getSubject().getSession().removeAttribute("user");
        SecurityUtils.getSubject ().logout ();
        return "redirect:/";
    }
}
