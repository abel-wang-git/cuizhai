package com.cch.cz.common;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghuiwen on 17-2-23.
 */
public class UtilFun {

    public  static  final  String YYYYMMDD="yyyy-MM-dd hh:mm:ss";
    public  static  final  String YYYYMMDD2="yyyy/MM/dd";
    public  static  final  String YMD="yyyy-MM-dd";
    /**
     * 判断list不为空
     *
     * @param list
     * @return 不为空返回true
     */
    public static boolean isEmptyList(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断str不为空
     *
     * @param str
     * @return 不为空返回true
     */
    public static boolean isEmptyString(String str) {
        if (str != null && !str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
                || ip.split(".").length != 4) {
            return false;
        }
        return true;
    }
    public static void prinrObject(Object o){
        System.out.println(JSON.toJSONString(o));
    }


    public  static  String DateToString(Date date,String fromat){
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        return sdf.format(date);
    }

    public  static Date StringToDate(String datestr,String formatstr){
        DateFormat format= new SimpleDateFormat(formatstr);
        try {
            Date date =format.parse(datestr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static  Date addDay(String date,int day){
        return addDay(StringToDate(date,YYYYMMDD),day);
    }

    public static  Date addDay(String date,int day,String fromat){
        return addDay(StringToDate(date,fromat),day);
    }


    public static Date addDay(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        return calendar.getTime();
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
     public static String upperFristCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
