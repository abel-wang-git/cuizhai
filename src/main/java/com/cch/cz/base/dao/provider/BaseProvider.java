package com.cch.cz.base.dao.provider;

import com.cch.cz.base.entity.BaseEntity;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Created by Administrator on 2017/12/7.
 *
 */
public class BaseProvider<M extends BaseEntity,PK> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public String  save(M m) throws IllegalAccessException {
         String sql = new SQL(){{
            StringBuilder clo=new StringBuilder();
            StringBuilder val=new StringBuilder();
            Field[] fields=m.getClass().getDeclaredFields();

            for (Field f:fields) {
                if(f.getAnnotation(Transient.class)!=null|| !Modifier.isPrivate(f.getModifiers()))continue;
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

            INSERT_INTO(m.Tablename());
            INTO_COLUMNS(clo.deleteCharAt(clo.length()-1).toString());
            INTO_VALUES(val.deleteCharAt(val.length()-1).toString());
        }}.toString();
        logger.info(sql);
        return sql;

    }

    public String  delete (M m, PK pk){

         String sql=new SQL(){{
            String idname =null;
            Field[] fields=m.getClass().getDeclaredFields();
            for (Field f:fields) {
                if(f.getAnnotation(Id.class)!=null)idname=addUnderscores(f.getName());
            }
            DELETE_FROM(m.Tablename());
            WHERE(idname.toString()+"='"+pk+"'");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String  findAll(M m){
       String sql=  new SQL(){{
           String sele = getSelect(m);
            SELECT(sele.toString());
            FROM(m.Tablename());
        }}.toString();

       logger.info(sql);
        return sql;
    }



    public String  findOne(M m,PK pk){
         String sql= new SQL(){{
            String idname =null;
            Field[] fields=m.getClass().getDeclaredFields();
            for (Field f:fields) {
                if(f.getAnnotation(Id.class)!=null)idname=addUnderscores(f.getName());
            }
             String sele = getSelect(m);
            SELECT(sele);
            FROM(m.Tablename());
            WHERE(idname.toString()+"='"+pk+"'");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String  update(M m) throws IllegalAccessException {
        String sql = new SQL(){{
            UPDATE(m.Tablename());
            Field[] fields=m.getClass().getDeclaredFields();
            StringBuilder where = new StringBuilder(" ");
            for (Field f: fields) {
                f.setAccessible(true);
                if(f.getAnnotation(Id.class)!=null){
                    where.append(addUnderscores(f.getName())).append("='").append(f.get(m)).append("'");
                    continue;
                }
                if (f.getType()==Date.class){
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
                    String ctime = formatter.format((Date) f.get(m));
                    SET(addUnderscores(f.getName())+"='"+ctime+"'");
                }else {
                    SET(addUnderscores(f.getName())+"='"+f.get(m)+"'");

                }
                f.setAccessible(false);
            }

            WHERE(where.toString());
        }}.toString();

        logger.info(sql);
        return sql;
    }

    public String  count(M m){
         String sql= new SQL(){{
            SELECT("count(*)");
            FROM(m.Tablename());
        }}.toString();
        logger.info(sql);
        return sql;
    }
    public static String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));

        for(int i = 1; i < buf.length() - 1; ++i) {
            if(Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase(Locale.ROOT);
    }

    protected String getSelect(M m) {
        StringBuilder sele=new StringBuilder();
        Field[] fields=m.getClass().getDeclaredFields();
        for (Field f: fields) {
            if(Modifier.isPrivate(f.getModifiers()))
            sele.append(addUnderscores(f.getName())).append(" as ").append(f.getName()).append(" ,");
        }
        sele.deleteCharAt(sele.length()-1);
        return sele.toString();
    }
}
