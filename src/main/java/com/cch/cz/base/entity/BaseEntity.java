package com.cch.cz.base.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/7.
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public String getTablename() {
        Table table = this.getClass().getAnnotation(Table.class);
        if(table != null)
            return table.name();
        else
            throw new RuntimeException("undefine POJO @Table, need Tablename(@Table)");
    }


}
