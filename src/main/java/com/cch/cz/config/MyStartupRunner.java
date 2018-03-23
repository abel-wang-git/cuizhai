package com.cch.cz.config;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.service.PowerService;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.common.ClassUtil;
import com.cch.cz.common.UtilFun;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wanghuiwen on 17-2-12.
 * 服务启动执行
 */
@Component
//@Order(value=2) 多个CommandLineRunner时 控制顺序
public class MyStartupRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private PowerService powerService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;



    public void run(String... strings) throws Exception {
        logger.info ("————————————————————init power——————————————");
        List<Method> methods = ClassUtil.MethodHasAnnotation (true,RequiresPermissions.class,"com.cch.cz");
        for (Method m : methods) {
            m.getDeclaringClass ();
            RequiresPermissions r = m.getAnnotation (RequiresPermissions.class);
            Power p = new Power ();
            p.setName (r.value ()[0]);
            if(powerService.findOne(r.value()[0])==null)
                powerService.save(p);

        }
        Role roles = roleService.getByname ("admin");
        if (roles==null) {
            logger.info ("————————————————————init role——————————————");
            Role role = new Role ();
            role.setName ("admin");
            roleService.save (role);
        }

        User u = userService.getByuserName("admin");
        if (u==null){
            u=new User();
            logger.info ("————————————————————init user——————————————");
            u.setPassWd("123456");
            u.setUserName("admin");
            u.setPassWd(u.Sal());
            userService.save(u);
        }

        UserRoleKey admin = new UserRoleKey();
        admin.setRoleId(roleService.getByname ("admin").getId());
        admin.setUserId("admin");
        userService.saveRoles(Arrays.asList(admin));
    }

}
