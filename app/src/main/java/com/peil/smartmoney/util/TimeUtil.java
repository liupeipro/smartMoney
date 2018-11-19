package com.peil.smartmoney.util;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TimeUtil {

    public static String millis2Str(long millis, String format) {
        return TimeUtils.millis2String(millis, new SimpleDateFormat(format, Locale.getDefault()));
    }

    /**
     * @param tempDate yyyy-MM-dd
     */
    public static long str2Millis(String tempDate, String format) {
        return TimeUtils.string2Millis(tempDate,
                new SimpleDateFormat(format, Locale.getDefault()));
    }

    /**
     * 获取现在的年月日
     */
    public static Map<String, Integer> getNowYearMonthDay() {
        Map<String, Integer> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        result.put("year", calendar.get(Calendar.YEAR));
        result.put("month", calendar.get(Calendar.MONTH)+1);
        result.put("day", calendar.get(Calendar.DAY_OF_MONTH));

        return result;
    }

    /**
     * 获取一个月前的日期
     */
    public static Long getMonthAgoForNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTimeInMillis();
    }
}


