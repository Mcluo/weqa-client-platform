package com.netease.vcloud.qa.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 20:41
 */
public class DateUtils {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static Date getFirstTimeOfDay(Long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    public static Date getFirstTimeOfWeek(Long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.DAY_OF_WEEK,1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    public static Date getFirstTimeOfMonth(Long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}
