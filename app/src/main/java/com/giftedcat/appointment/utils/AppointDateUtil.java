package com.giftedcat.appointment.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AppointDateUtil {

    static SimpleDateFormat selectFormat = new SimpleDateFormat("MM月dd日 E");

    /**
     * 最大可选择日期限制
     */
    static final int MAX = 30;
    static String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    static String[] min = new String[]{"00", "15", "30", "45"};

    /**
     * 根据position获取时间戳
     */
    private static long getTimeStampForPosition(long position) {
        long addMillis = (long) (24l * 60l * 60l * 1000l * (long) position);
        long zero = System.currentTimeMillis()/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        return zero + addMillis;
    }

    /**
     * 获取可预约时间戳列表
     */
    public static List<Long> getTimeStampList() {
        List<Long> timeStamps = new ArrayList<>();
        for (int i = 0; i < MAX; i++) {
            timeStamps.add(getTimeStampForPosition(i));
        }
        return timeStamps;
    }

    /**
     * 获取用于显示的时间列表
     */
    public static List<String> getDateList() {
        List<String> dates = new ArrayList<>();
        List<Long> timeStamps = getTimeStampList();
        Date date;
        for (int i = 0; i < timeStamps.size(); i++) {
            date = new Date(timeStamps.get(i));
            dates.add(selectFormat.format(date));
        }
        return dates;
    }

    /**
     * 获取所有预约的时刻列表
     */
    public static List<String> getHours() {
        List<String> appointHours = new ArrayList<>();
        for (int i = 0; i < hours.length; i++) {
            appointHours.add(hours[i]);
        }
        return appointHours;
    }

    /**
     * 获取所有预约的分钟列表
     */
    public static List<String> getMinutes() {
        List<String> appointMin = new ArrayList<>();
        for (int i = 0; i < min.length; i++) {
            appointMin.add(min[i]);
        }
        return appointMin;
    }

    /**
     * 根据当前时间，获取默认预约时间
     */
    public static int[] getNormalPosition() {
        int date_position = 0;
        int hour_position = 0;
        int min_position = 0;
        Date date = new Date(System.currentTimeMillis());
        hour_position = date.getHours();
        min_position = date.getMinutes();
        if (min_position <= 30) {
            min_position = 2;
        } else {
            min_position = 0;
            hour_position += 1;
            if (hour_position >= hours.length) {
                hour_position = 0;
                date_position += 1;
            }
        }
        return new int[]{date_position, hour_position, min_position};
    }

    /**
     * @param time 日期时间戳
     * @param hour 小时
     * @param minutes 分钟
     * */
    public static long cumulativeTime(long time, long hour, long minutes){
        return time + 60l * 60l * 1000l * hour + 60l * 1000l * minutes;
    }

}
