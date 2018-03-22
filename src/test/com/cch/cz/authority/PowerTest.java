package com.cch.cz.authority;

import com.cch.cz.App;
import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.service.PowerService;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class PowerTest {

    @Resource
    private PowerService powerService;

    @Test
    public void save(){
        Power power = new Power();
        power.setName("test");

        powerService.save(power);
    }
    @Test
    public void findOne(){
        UtilFun.prinrObject(powerService.findOne("role:add"));
    }

    public static void main(String[] args) {
        StringBuilder sele=new StringBuilder();
        Field[] fields= Cases.class.getDeclaredFields();
        for (Field f: fields) {
            sele.append(addUnderscores(f.getName())).append(" as ").append(f.getName()).append(" ,");
        }

        System.out.println(sele.deleteCharAt(sele.length()-1).toString());
    }

    private static String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));

        for(int i = 1; i < buf.length() - 1; ++i) {
            if(Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase(Locale.ROOT);
    }
}
