package piv.beans.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/6/25.
 */
public class MyDateUtils {

    /**
     * 年月日+时分秒
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年月日
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 年月
     */
    public static final String YYYY_MM = "yyyy-MM";

    /**
     * 年月日+0时0分0秒
     */
    public static final String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";

    /**
     * 年月日+23时59分59秒
     */
    public static final String YYYY_MM_DD_23_59_59 = "yyyy-MM-dd 23:59:59";

    /**
     * 年月
     */
    public static final String YYYY_CN_MM_CN = "yyyy年MM月";

    /**
     * 日起分隔符
     */
    public static final String DATE_SPLIT = " ";

    /**
     * 昨天
     */
    public static final String YESTERDAY = "昨天";

    /**
     * 今天
     */
    public static final String TODAY = "今天";

    /**
     * 明天
     */
    public static final String TOMORROW = "明天";

    /**
     * yyyy-MM-dd 日期字符串的长度
     */
    public static final int YYYY_MM_DD_LENGTH = 10;
    /**
     * 完整的日期长度
     */
    public static final int YYYY_MM_DD_HH_MM_SS_LENGTH = 19;

    /**
     *
     * 方法作用说明:dateToString
     * 使用说明: date 时间转 字符串
     * 使用注意事项:
     *
     * @param value 时间
     * @param pattern 格式化格式
     * @return string 时间字符串
     * @since JDK 1.8
     */
    public static String dateToString(Object value, String pattern) {
        String val = "";
        if (value instanceof Date) {
            val = DateFormatUtils.format((Date) value, pattern);
        } else {
            val = value.toString();
        }
        return val;
    }

    /**
     *
     * 方法作用说明:stringToDate
     * 使用说明: string 时间字符串转 date类型
     * 使用注意事项:
     *
     * @param dateStr 时间字符串
     * @param pattern 时间字符串格式
     * @return date类型时间
     * @since JDK 1.8
     */
    public static Date stringToDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 方法作用说明:dateEqDate
     * 使用说明: 比较两个时间是否相等
     * 使用注意事项:
     *
     * @param oneDate 第一个时间
     * @param twoDate 第二个时间
     * @param pattern 时间的格式
     * @return true 相等 false 不相等
     * @since JDK 1.8
     */
    public static boolean dateEqDate(Date oneDate, Date twoDate, String pattern) {
        String oneDateStr = dateToString(oneDate, pattern);
        String twoDateStr = dateToString(twoDate, pattern);
        if (oneDateStr.equals(twoDateStr)) {
            return true;
        }
        return false;
    }

    /**
     *
     * 方法作用说明:betweenDates
     * 使用说明: 比较 date 是否在 start 与 end 之间
     * 使用注意事项:
     *
     * @param date  要比较的时间
     * @param start 开始时间
     * @param end 结束时间
     * @return true 在两个时间之间  false 不在
     * @since JDK 1.8
     */
    public static boolean betweenDates(Date date, Date start, Date end) {
        if (date.compareTo(start) >= 0 && date.compareTo(end) == -1) {
            return true;
        }
        return false;
    }

    /**
     *
     * 方法作用说明:differDays
     * 使用说明: 比较 end 与 start 相差的天数
     * 使用注意事项:
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 两个时间相差天数
     * @since JDK 1.8
     */
    public static int differDays(Date start, Date end) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(start);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(end);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;

    }

    /**
     *
     * 方法作用说明:formartDateChinese
     * 使用说明: 日期格式化为  昨天 今天 明天
     * 使用注意事项:
     *
     * @param date 需要格式化的日期
     * @param pattern 日期格式
     * @return 格式化好的日期字符串
     * @since JDK 1.8
     */
    public static String formartDateChinese(Object date, String pattern) {
        if (date instanceof Date) {
            String dateStr = dateToString(date, pattern);
            return resultChineseDateStr(dateStr, pattern);
        } else if (date instanceof String) {
            String dateStr = date.toString();
            if (dateStr.length() == YYYY_MM_DD_LENGTH) {
                int day = differDays(stringToDate(dateStr, pattern), new Date());
                if (day == 1) {
                    return YESTERDAY;
                } else if (day == 0) {
                    return TODAY;
                } else if (day == -1) {
                    return TOMORROW;
                } else {
                    return dateStr;
                }
            } else if (dateStr.length() == YYYY_MM_DD_HH_MM_SS_LENGTH) {
                return resultChineseDateStr(dateStr, pattern);
            }
        }
        return null;
    }

    /**
     *
     * 方法作用说明:resultChineseDateStr
     * 使用说明: 根据日期返回 昨天 今天 明天
     * 使用注意事项:
     *
     * @param dateStr 日期的字符串
     * @param pattern 日期格式
     * @return 格式化后的日期
     * @since JDK 1.8
     */
    private static String resultChineseDateStr(String dateStr, String pattern) {
        String[] days = dateStr.split(DATE_SPLIT);
        int day = differDays(stringToDate(dateStr, pattern), new Date());
        if (day == 1) {
            return YESTERDAY + DATE_SPLIT + days[1];
        } else if (day == 0) {
            return TODAY + DATE_SPLIT + days[1];
        } else if (day == -1) {
            return TOMORROW + DATE_SPLIT + days[1];
        } else {
            return dateStr;
        }
    }

    /**
     *
     * 方法作用说明:getWeekDay
     * 使用说明: 获取今天是星期几
     * 使用注意事项:
     *
     * @param c 日历对象
     * @return 星期几
     * @since JDK 1.8
     */
    public static String getWeekDay(Calendar c) {
        if (c == null) {
            return "星期一";
        }
        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期一";
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期二";
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期三";
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期四";
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期五";
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期六";
        }
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期日";
        }
        return "星期一";
    }
}
