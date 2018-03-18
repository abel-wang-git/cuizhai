package com.cch.cz.authority;

import com.alibaba.fastjson.JSON;
import com.cch.cz.App;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.key.RolePowerKey;
import com.cch.cz.authority.service.RoleService;
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
public class RoleTest {
    @Resource
    private RoleService roleService;
    @Test
    public void getPowers(){


        System.out.println(JSON.toJSONString(roleService.getPowers(1l)));
    }
    @Test
    public  void save(){
        Role role = new Role();
        role.setName("test");
        role.setDesign("test");
        roleService.save(role);
    }

    @Test
    public  void savePowers(){
        List<RolePowerKey> list = new ArrayList<>();
        RolePowerKey powerKey = new RolePowerKey();
        powerKey.setPowerId("role:list");
        powerKey.setRoleId(1l);
        list.add(powerKey);
        roleService.savePowers(list);
    }
}
