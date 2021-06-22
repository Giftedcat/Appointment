package com.giftedcat.appointment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {

    static SimpleDateFormat appointFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm");


    public static String stampToAppointDate(long lt) {
        Date date = new Date(lt);
        return appointFormat.format(date);
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        try{
            Date date = appointFormat.parse(s);
            return date.getTime();
        }catch (ParseException e){
            return 0;
        }
    }

}
