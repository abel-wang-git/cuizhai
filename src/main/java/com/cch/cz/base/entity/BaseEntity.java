package com.cch.cz.base.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2017/12/7.
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public String Tablename() {
        Table table = this.getClass().getAnnotation(Table.class);
        if(table != null)
            return table.name();
        else
            throw new RuntimeException("undefine POJO @Table, need Tablename(@Table)");
    }

    public void clearField() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                if(Modifier.isPrivate(f.getModifiers())&&f.getAnnotation(Transient.class)==null){
                f.setAccessible(true);
                f.set(this, null);
                f.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
