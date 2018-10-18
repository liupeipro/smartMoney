package com.peil.smartmoney.util;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TimeUtil {

    /**
     * 获取现在的年月日
     *
     * @return
     */
    public static Map<String, Integer> getNowYearMonthDay() {
        Map<String, Integer> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        result.put("year", calendar.get(Calendar.YEAR));
        result.put("month", calendar.get(Calendar.MONTH));
        result.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }

    /**
     * @param tempDate yyyy-MM-dd
     */
    public static long str2Millis(String tempDate, String format) {
        return TimeUtils.string2Millis(tempDate, new SimpleDateFormat(format, Locale.getDefault()));
    }

    public static String millis2Str(long millis,String format){
       return TimeUtils.millis2String(millis,new SimpleDateFormat(format, Locale.getDefault()));
    }


}
