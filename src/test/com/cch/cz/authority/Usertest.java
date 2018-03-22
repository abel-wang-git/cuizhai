package com.cch.cz.authority;

import com.alibaba.fastjson.JSON;
import com.cch.cz.App;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.common.UtilFun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class Usertest {

    @Resource
    private UserService userService;

    @Test
    public void save(){
        User user = new User();
        user.setPassWd("123456");
        user.setUserName("admin");
        user.setPassWd(user.Sal());
        userService.save(user);
    }


    @Test
    public void update(){
        User user = new User();
        user.setPassWd("123456");
        user.setUserName("admin");
        user.setPassWd(user.Sal());
        userService.update(user);
    }

    @Test
    public void getByuserName() throws Exception {
        User user = userService.getByuserName("admin");
        System.err.println(JSON.toJSONString(user));
    }

    @Test
    public void getRoleList() throws Exception {
        System.err.println(JSON.toJSONString(userService.getRoleList("678")));
    }

    @Test
    public void saveRoles() throws Exception {
        List<UserRoleKey> roleKeys=new ArrayList<>();
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId("678");
        userRoleKey.setRoleId(1L);
        roleKeys.add(userRoleKey);
        userService.saveRoles(roleKeys);
    }
    @Test
    public void findone(){
        User u=userService.findOne("admin");
        UtilFun.prinrObject(u);
    }
}
