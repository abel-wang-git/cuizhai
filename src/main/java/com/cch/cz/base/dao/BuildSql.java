package com.cch.cz.base.dao;

import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static com.cch.cz.base.dao.provider.BaseProvider.addUnderscores;

/**
 * 处理对象和sql转换的各种操作
 */
public class BuildSql {
    /**
     * 根据属性属性名字给字段取别名 方便自动封装到对象
     * @param c
     * @return
     */
    public static String select(Class c){
        StringBuilder sele=new StringBuilder();
        Field[] fields=c.getDeclaredFields();
        for (Field f: fields) {
            if(Modifier.isPrivate(f.getModifiers()))
            sele.append(addUnderscores(f.getName())).append(" as ").append(f.getName()).append(" ,");
        }
        sele.deleteCharAt(sele.length()-1);
        return sele.toString();
    }

    public static  String tablename(Class c){
        Table table = (Table) c.getAnnotation(Table.class);
        return table.name();
    }

}
