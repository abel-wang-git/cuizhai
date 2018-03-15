package com.cch.cz.base.dao.provider;

import com.cch.cz.base.entity.BaseEntity;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Created by Administrator on 2017/12/7.
 *
 */
public class BaseProvider<M extends BaseEntity,PK> {
    public String  save(M m) throws IllegalAccessException {
        return new SQL(){{
            StringBuilder clo=new StringBuilder();
            StringBuilder val=new StringBuilder();
            Field[] fields=m.getClass().getDeclaredFields();

            for (Field f:fields) {
                if(f.getAnnotation(Transient.class)!=null)continue;
                if(f.getAnnotation(Id.class)!=null&&f.getAnnotation(GeneratedValue.class)!=null) continue;
                clo.append(addUnderscores(f.getName())+",");
                f.setAccessible(true);
                if (f.getType()==String.class){
                    val.append("'"+f.get(m)+"',");
                }else if(f.getType()==Date.class) {
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
                    String ctime = formatter.format((Date) f.get(m));
                    val.append("'"+ctime+"',");
                }else{
                    val.append(f.get(m)+",");
                }
            }

            INSERT_INTO(m.getTablename());
            INTO_COLUMNS(clo.deleteCharAt(clo.length()-1).toString());
            INTO_VALUES(val.deleteCharAt(val.length()-1).toString());
        }}.toString();
    }

    public String  delete (M m, PK pk){
        return new SQL(){{
            DELETE_FROM(m.getTablename());
            WHERE("id="+pk);
        }}.toString();
    }

    public String  findAll(M m){
        return new SQL(){{
            SELECT("*");
            FROM(m.getTablename());
        }}.toString();
    }

    public String  findOne(M m,PK pk){
        return new SQL(){{
            SELECT("*");
            FROM(m.getTablename());
            WHERE("id="+pk);
        }}.toString();
    }

    public String  count(M m){
        return new SQL(){{
            SELECT("count(*)");
            FROM(m.getTablename());
        }}.toString();
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
