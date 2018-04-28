package com.winning.bigscreen.simulation.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author Lemod
 * @Version 2017/10/24
 */
public class DateUtils {

    /**
     * 获取当前小时
     *
     * @return
     */
    public static int GetCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static String GetDayBefore(int gap){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -gap);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return GetStringFormatTimeToDay(cal.getTimeInMillis());
    }

    public static String GetMonthBefore(int gap) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -gap);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return GetStringFormatTimeToMonth(cal.getTimeInMillis());
    }

    public static String GetStringFormatTimeToMonth(long time) {
        return new SimpleDateFormat("yyyy-MM").format(new Date(time));
    }

    public static String GetStringFormatTimeToDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }
}
