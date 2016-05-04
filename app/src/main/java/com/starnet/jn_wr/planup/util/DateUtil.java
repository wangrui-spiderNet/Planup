package com.starnet.jn_wr.planup.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static final String TIME_FORMAT_CH="yyyy年MM月dd日";
    public static final String TIME_FORMAT_SEPERATOR="yyyy/MM/dd";
    public static final String TIME_FORMAT_LINE="yyyy-MM-dd HH:mm";

    public static final String[] WEEKS={"周日","周一","周二","周三","周四","周五","周六"};

    public static final long oneday = 0x5265c00L;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_CH);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_LINE);
    private static final SimpleDateFormat msdf = new SimpleDateFormat("yyyy.MM.dd");



    public static String getShowDateStyle(long ct) {

        long time = new Date().getTime() / 1000 - ct;

        if (time <= -86400) {
            return (-time / 86400) + "天后";
        } else if (time <= -3600 && time > -86400) {
            return (-time / 3600) + "小时后";
        } else if (-3600 <= time && time < -60) {
            return (-time / 60) + "分钟后";
        } else if (-60 <= time && time < 0) {
            return (-time) + "秒后";
        } else if (time < 60) {
            // return time + "秒前";
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return (time / 60) + "分钟前";
        } else if (time >= 3600 && time < 86400) {
            return (time / 3600) + "小时前";
        } else if (time >= 86400 && time < 172800) {
            return (time / 86400) + "天前";
        } else {
            return sdf.format(new Date(ct * 1000L));
        }
    }

    public static String getShowFrontPageDateStyle(long ct) {

        long time = new Date().getTime() / 1000 - ct;

        if (time <= -86400) {
            return (-time / 86400) + "天后";
        } else if (time <= -3600 && time > -86400) {
            return (-time / 3600) + "小时后";
        } else if (-3600 <= time && time < -60) {
            return (-time / 60) + "分钟后";
        } else if (-60 <= time && time < 0) {
            return (-time) + "秒后";
        } else if (time < 60) {
            // return time + "秒前";
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return (time / 60) + "分钟前";
        } else if (time >= 3600 && time < 86400) {
            return (time / 3600) + "小时前";
        } else if (time >= 86400 && time < 172800) {
            return (time / 86400) + "天前";
        } else {
            return simpleDateFormat.format(new Date(ct * 1000L));
        }
    }

    public static String getShowDateCommentStyle(long ct) {

        long time = new Date().getTime() - ct;

        if (time <= -86400) {
            return (-time / 86400) + "天后";
        } else if (time <= -3600 && time > -86400) {
            return (-time / 3600) + "小时后";
        } else if (-3600 <= time && time < -60) {
            return (-time / 60) + "分钟后";
        } else if (-60 <= time && time < 0) {
            return (-time) + "秒后";
        } else if (time < 60) {
            // return time + "秒前";
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return (time / 60) + "分钟前";
        } else if (time >= 3600 && time < 86400) {
            return (time / 3600) + "小时前";
        } else if (time >= 86400 && time < 172800) {
            return (time / 86400) + "天前";
        } else {
            return msdf.format(new Date(ct * 1000L));
        }
    }

    public static String getShowSecretaryDateStyle(long ct) {
        long time =new Date().getTime() - ct;
       /* long time = System.currentTimeMillis() / 1000 - ct / 1000;*/
        if (time < 2) {
            return "刚刚";
        } else if (time < 60) {
            // return time + "秒前";
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return (time / 60) + "分钟前";
        } else if (time >= 3600 && time < 86400) {
            return (time / 3600) + "小时前";
        } else if (time >= 86400 && time < 172800) {
            return (time / 86400) + "天前";
        } else {
            return sdf.format(new Date(ct));
        }
    }

    /**
     * 毫秒转成字符串时间
     *
     * @param dateFormat
     * @param millSec
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    // long类型转换为String类型

    // currentTime要转换的long类型的时间

    // formatType要转换的string类型的时间格式

    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        try {
            Date date = longToDate(currentTime, formatType); // long类型转成Date类型\
            strTime = dateToString(date, formatType); // date类型转成String
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strTime;
    }

    // date类型转换为String类型

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒

    // data Date类型的时间

    public static String dateToString(Date date, String formatType) {

        return new SimpleDateFormat(formatType).format(date);

    }

    /**
     * 字符串时间转成毫秒
     *
     * @param adate
     * @param formatString
     * @return
     * @throws ParseException
     */
    public static long stringToDateLong(String adate, String formatString)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        return formatter.parse(adate).getTime();
    }

    private static Calendar calendar = Calendar.getInstance(TimeZone
            .getDefault());


    // long转换为Date类型

    // currentTime要转换的long类型的时间

    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒

    public static Date longToDate(long currentTime, String formatType)

            throws ParseException {

        Date dateOld = new Date(currentTime * 1000); // 根据long类型的毫秒数生命一个date类型的时间

        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string

        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型

        return date;

    }

    // string类型转换为date类型

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日

    // HH时mm分ss秒，

    // strTime的时间格式必须要与formatType的时间格式相同

    public static Date stringToDate(String strTime, String formatType)

            throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(formatType);

        Date date = null;

        date = formatter.parse(strTime);

        return date;

    }

    /**
     * 获取相对时间
     *
     * @param postTime
     * @return
     */
    public static String getRelativeTimeStr(long postTime) {
        String ret = "";
        postTime = postTime / 1000;
        long currentTime = System.currentTimeMillis() / 1000;
        long gapDays = (currentTime - postTime) / 3600 / 24;// 相隔多少天
        long gapHours = (currentTime - postTime) / 3600;
        long gapMins = ((currentTime - postTime) % 3600) / 60;
        calendar.setTimeInMillis(postTime * 1000);
        calendar.setTimeZone(TimeZone.getDefault());

        if (gapHours >= 1 && gapHours < 24) {
            // 相隔一天内的周期
            ret = gapHours + "小时以前";
        } else if (gapHours >= 24 && gapHours < 240) {
            // 1天至10天内的周期
            ret = gapDays + "天前";
        } else if (gapHours < 1) {
            // 相隔一小时内的周期
            ret = gapMins + "分钟以前";
            if (gapMins < 1) {
                ret = "刚刚";
            }
        } else {
            ret = (calendar.get(Calendar.MONTH) + 1) + "月"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "日" + " "
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE);
        }
        return ret;
    }

    /**
     * 获取友好时间
     *
     * @param postTime
     * @return
     */
    public static String getFriendTimeStr(long postTime) {
        String ret = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(postTime);
        Calendar curCalendar = Calendar.getInstance();

        int gapYear = curCalendar.get(Calendar.YEAR)
                - calendar.get(Calendar.YEAR);

        if (gapYear > 0) {
            ret = gapYear + "年以前";
            return ret;
        } else if (gapYear == 0) {
            int gapMonth = curCalendar.get(Calendar.MONTH)
                    - calendar.get(Calendar.MONTH);
            if (gapMonth > 0) {
                ret = gapMonth + "月以前";
                return ret;
            } else if (gapMonth == 0) {
                postTime = postTime / 1000;
                long currentTime = System.currentTimeMillis() / 1000;
                long gapDays = (currentTime - postTime) / 3600 / 24;// 相隔多少天
                long gapHours = (currentTime - postTime) / 3600;
                long gapMins = ((currentTime - postTime) % 3600) / 60;
                calendar.setTimeInMillis(postTime * 1000);
                calendar.setTimeZone(TimeZone.getDefault());

                if (gapHours >= 1 && gapHours < 24) {
                    // 相隔一天内的周期
                    ret = gapHours + "小时以前";
                    return ret;
                } else if (gapHours >= 24 && gapHours < 240) {
                    // 1天至10天内的周期
                    ret = gapDays + "天前";
                    return ret;
                } else if (gapHours < 1) {
                    // 相隔一小时内的周期
                    ret = gapMins + "分钟以前";
                    if (gapMins < 1) {
                        ret = "刚刚";
                    }
                    return ret;
                }
            }
        }
        ret = DateUtil.dataFormat(calendar.getTime(), "yyyy-mm-dd");
        return ret;
    }

    /**
     * 获取友好时间
     *
     * @param postTime
     * @return
     */
    public static String getFriendImpTimeStr(long postTime) {
        String ret = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(postTime);
        Calendar curCalendar = Calendar.getInstance();

        int gapYear = curCalendar.get(Calendar.YEAR)
                - calendar.get(Calendar.YEAR);

        if (gapYear == 0) {
            int gapMonth = curCalendar.get(Calendar.MONTH)
                    - calendar.get(Calendar.MONTH);
            if (gapMonth > 0) {
                ret = DateUtil.dataFormat(calendar.getTime(), "MM-dd");
                return ret;
            } else if (gapMonth == 0) {
                int gapDay = curCalendar.get(Calendar.DAY_OF_MONTH)
                        - calendar.get(Calendar.DAY_OF_MONTH);

                if (gapDay > 0) {
                    ret = DateUtil.dataFormat(calendar.getTime(), "MM-dd");
                    return ret;
                } else if (gapDay == 0) {
                    ret = DateUtil.dataFormat(calendar.getTime(), "HH:mm:ss");
                    return ret;
                }
            }
        }
        ret = DateUtil.dataFormat(calendar.getTime(), "yyyy-MM-dd");
        return ret;
    }

    /**
     * 得到两个日期相差的天数
     */
    public static int getIntervalDays(String begindate, String enddate,
                                      String formatString) throws ParseException {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar
                .setTimeInMillis(stringToDateLong(begindate, formatString));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(stringToDateLong(enddate, formatString));
        int interval = (int) ((endCalendar.getTimeInMillis() - beginCalendar
                .getTimeInMillis()) / oneday);
        return interval;
    }

    /**
     * 得到两个日期相差的小时数
     */
    public static int getIntervalHours(String begindate, String enddate,
                                       String formatString) throws ParseException {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar
                .setTimeInMillis(stringToDateLong(begindate, formatString));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(stringToDateLong(enddate, formatString));
        int total = (int) ((endCalendar.getTimeInMillis() - beginCalendar
                .getTimeInMillis()) % oneday);
        return total / (1000 * 60 * 60);
    }

    /**
     * 得到两个日期相差的秒数
     */
    public static int getIntervalSecond(String begindate, String enddate,
                                        String formatString) throws ParseException {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar
                .setTimeInMillis(stringToDateLong(begindate, formatString));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(stringToDateLong(enddate, formatString));
        int total = (int) ((endCalendar.getTimeInMillis() - beginCalendar
                .getTimeInMillis()) % oneday);
        return total % (1000 * 60 * 60) / (1000);
    }

    /**
     * 得到两个日期相差的分钟数
     */
    public static int getIntervalMinutes(String begindate, String enddate,
                                         String formatString) throws ParseException {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar
                .setTimeInMillis(stringToDateLong(begindate, formatString));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(stringToDateLong(enddate, formatString));
        int total = (int) ((endCalendar.getTimeInMillis() - beginCalendar
                .getTimeInMillis()) % oneday);

        return total % (1000 * 60 * 60) / (1000 * 60);
    }

    /**
     * 用于返回指定日期的下一天的日期
     *
     * @param appDate 指定日期
     * @return 指定日期的下一天的日期
     */
    public static String getNextDay(String appDate) {
        return getFutureDay(appDate, "yyyy-MM-dd", 1);
    }

    /**
     * 用于返回指定日期增加指定天数的日期
     *
     * @param appDate 指定日期
     * @param days    指定天数
     * @return 指定日期增加指定天数的日期
     */
    public static String getFutureDay(String appDate, int days) {
        return getFutureDay(appDate, "yyyy-MM-dd", days);
    }

    /**
     * 用于返回指定日期格式的日期增加指定天数的日期
     *
     * @param appDate 指定日期
     * @param format  指定日期格式
     * @param days    指定天数
     * @return 指定日期格式的日期增加指定天数的日期
     */
    public static String getFutureDay(String appDate, String format, int days) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return future;
    }

    /**
     * 返回以当前日期为标准的正负月数的日期，months可为负数
     *
     * @param appDate 输入的时时间
     * @param months  时间间隔
     * @return String 类型
     */
    public static String getPastMonth(String appDate, int months) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, months);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return future;
    }

    /**
     * 返回以当前日期为标准的正负日期，days可为负数
     *
     * @param appDate 输入的时间
     * @param days    时间间隔
     * @return String 类型
     */
    public static String getPastDay(String appDate, int days) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return future;
    }

    /**
     * 返回当前的年份
     *
     * @return String类型
     */
    public static String returnYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取当前时间
     *
     * @return 格式化后的日期字符串（yyyy-MM-dd hh:mm:ss）
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取当前日期
     *
     * @return 格式化后的日期字符串（yyyy-MM-dd）
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取当前日期的前一天
     *
     * @return 格式化后的日期字符串（yyyy-MM-dd）
     */
    public static String getPreDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当前日期的后一天
     *
     * @return 格式化后的日期字符串（yyyy-MM-dd）
     */
    public static String getNextDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 将英文星期名称转化为中文星期名称
     *
     * @param enWeek 英文星期名称（比如monday）
     * @param prefix 中文星期名称的前缀，比如周或者星期，可为空
     * @return 中文星期名称（比如周一）
     */
    public static String transWeekToCn(String enWeek, String prefix) {
        if (StringUtil.isEmpty(enWeek)) {
            return "";
        }
        if (prefix == null) {
            prefix = "";
        }
        enWeek = enWeek.toLowerCase();
        if (enWeek.startsWith("mon")) {
            return prefix + "一";
        } else if (enWeek.startsWith("tue")) {
            return prefix + "二";
        } else if (enWeek.startsWith("wed")) {
            return prefix + "三";
        } else if (enWeek.startsWith("thu")) {
            return prefix + "四";
        } else if (enWeek.startsWith("fri")) {
            return prefix + "五";
        } else if (enWeek.startsWith("sat")) {
            return prefix + "六";
        } else if (enWeek.startsWith("sun")) {
            return prefix + "日";
        } else {
            return "";
        }
    }

    /**
     * 字符串转换成时间
     */
    public static String dateString2String(String str, String format) {
        try {
            return DateUtil.dataFormat(new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss").parse(str), format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 字符串转换成时间
     */
    public static String dateString2String_1(String str, String format) {
        try {
            return DateUtil.dataFormat(new SimpleDateFormat(
                    "yyyy/MM/dd HH:mm:ss").parse(str), format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 日期转换时间
     */
    public static String date2String(Date _date) {
        if (null == _date) {
            _date = Calendar.getInstance().getTime();
        }

        return dataFormat(_date);
    }

    /**
     * 格式化时间
     */
    public static String dataFormat(Date date) {
        return dataFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间
     */
    public static String dataFormat(Date date, String format) {
        return null == date ? "" : new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式化日期，只返回月日时分(MM-dd HH:mm)
     *
     * @param appDate
     * @return
     */
    public static String getDateNoYearSecond(String appDate) {
        String mdate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(appDate);
            simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            mdate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mdate;
    }

    /**
     * 格式化日期，只返回月日时分秒(MM-dd HH:mm:SS)
     *
     * @return
     */
    public static String getDateNoYear() {
        String mdate = "";
        try {

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "MM-dd HH:mm:ss");
            mdate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mdate;
    }

    /**
     * @param format 转化的模型
     * @param value  相对当前时间的偏移量
     * @return String
     */
    public static String getFormatTime(SimpleDateFormat format, int value) {
        Calendar time = Calendar.getInstance(TimeZone.getDefault());
        time.add(Calendar.HOUR_OF_DAY, value);
        Date date = time.getTime();
        String formattime = format.format(date);
        return formattime;
    }

    /**
     * 将秒数转化为时分秒
     */
    public static String transSecondToTime(Long second) {
        String mdate = "";
        try {
            SimpleDateFormat simpleDateFormat;
            Date date = new Date(second);
            simpleDateFormat = new SimpleDateFormat("dd HH:mm");
            mdate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mdate;
    }

    /**
     * 两个时间比较
     *
     * @param s1 String类型的时间(yyyy-MM-dd)
     * @param s2 String类型的时间
     * @return s1>s2 true
     */
    public static boolean DateCompare(String s1, String s2) {
        // s1>s2 返回true
        try {
            // 设定时间的模模型
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // 得到指定模范的时间
            Date d1 = format.parse(s1);
            Date d2 = format.parse(s2);
            // 比较
            if (d1.getTime() > d2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 两个时间比较
     *
     * @param date1 String类型的时间(yyyy-MM-dd HH:mm:ss)
     * @param date2 String类型的时间(yyyy-MM-dd HH:mm:ss)
     * @return date1 > date2 -1 date1<date2 1 date1==date2 0
     */
    public static int Date2compare(String date1, String date2) {// 从大到小的顺序
        try {
            // 设定时间的模型
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            // 得到指定模范的时间
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            // 比较
            if (d1.getTime() > d2.getTime()) {
                return -1;
            } else if (d1.getTime() < d2.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回正常格时间
     *
     * @param time 2012-10-29T13:53:53:07.25+08:00
     * @return
     */
    public static String getNormalTime(String temp) {
        String time = "";
        time = temp.replace("T", " ");
        time = time.substring(0, 19);
        return time;

    }

    /**
     * 日期选择后的回调
     *
     * @author huim_lin
     */
    public interface GetDateFromPickerListener {
        /**
         * @param date 返回日期，yyyy-MM-dd
         */
        public void onGetDate(String date);

        /**
         * @param year
         * @param month
         * @param day
         */
        public void onGetDate(int year, int month, int day);
    }

    /**
     * 通过日期选择对话框选择日期
     *
     * @param context
     * @param listener 日期选择后的回调
     */
    public static void getDateFromDatePicker(Context context,
                                             final GetDateFromPickerListener listener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(context,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = (monthOfYear + 1 <= 9) ? "0"
                                + (monthOfYear + 1) : "" + (monthOfYear + 1);
                        String day = (dayOfMonth <= 9) ? "0" + (dayOfMonth)
                                : "" + (dayOfMonth);
                        listener.onGetDate(year + "-" + month + "-" + day);
                        listener.onGetDate(year, monthOfYear + 1, dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.setTitle("请选择日期");
        dateDialog.show();
    }

    /**
     * 时间选择后的回调
     *
     * @author huim_lin
     */
    public interface GetTimeFromPickerListener {
        /**
         * @param time 返回时间，hh:mm
         */
        public void onGetTime(String time);

        /**
         * @param hour
         * @param minute
         */
        public void onGetTime(int hour, int minute);
    }

    /**
     * 通过时间选择对话框选择日期
     *
     * @param context
     * @param listener
     */
    public static void getTimeFromTimePicker(Context context,
                                             final GetTimeFromPickerListener listener) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timeDialog = new TimePickerDialog(context,
                new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String h = (hourOfDay <= 9) ? "0" + hourOfDay : ""
                                + hourOfDay;
                        String m = (minute <= 9) ? "0" + minute : "" + minute;
                        listener.onGetTime(h + ":" + m);
                        listener.onGetTime(hourOfDay, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timeDialog.setTitle("请选择时间");
        timeDialog.show();
    }

    /**
     * 本周第一天日期
     *
     * @return
     */
    public static String getFirstDayOfThisWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_WEEK, 2);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 本周最后一天日期
     *
     * @return
     */
    public static String getLastDayOfThisWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_WEEK, 7);//
        lastDate.add(Calendar.DATE, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 某周第一天日期
     *
     * @param days 时间往后为正，否则为负
     * @return
     */
    public static String getFirstDayOfWeek(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_WEEK, 2);//
        lastDate.add(Calendar.DATE, days * 7);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 某周最后一天日期
     *
     * @param days 时间往后为正，否则为负
     * @return
     */
    public static String getLastDayOfWeek(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DAY_OF_WEEK, 7);//
        lastDate.add(Calendar.DATE, 1);
        lastDate.add(Calendar.DATE, days * 7);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 本月第一天日期
     *
     * @return
     */
    public static String getFirstDayOfThisMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 本月最后一天日期
     *
     * @return
     */
    public static String getLastDayOfThisMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, 1);//
        lastDate.add(Calendar.DATE, -1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 本年第一天日期
     *
     * @return
     */
    public static String getFirstDayOfThisYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.set(Calendar.MONTH, 0);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 本年最后一天日期
     *
     * @return
     */
    public static String getLastDayOfThisYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.set(Calendar.MONTH, 0);//
        lastDate.add(Calendar.YEAR, 1);
        lastDate.add(Calendar.DATE, -1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 将英文的周转成中文
     *
     * @param enWeek
     * @return
     */
    public static String changeEnWeekToZnWeek(String enWeek) {
        String week = "周";
        if (enWeek.equalsIgnoreCase("Sunday")) {
            week = "周日";
        } else if (enWeek.equalsIgnoreCase("Monday")) {
            week = "周一";
        } else if (enWeek.equalsIgnoreCase("Tuesday")) {
            week = "周二";
        } else if (enWeek.equalsIgnoreCase("Wednesday")) {
            week = "周三";
        } else if (enWeek.equalsIgnoreCase("Thursday")) {
            week = "周四";
        } else if (enWeek.equalsIgnoreCase("Friday")) {
            week = "周五";
        } else if (enWeek.equalsIgnoreCase("Saturday")) {
            week = "周六";
        }
        return week;
    }

    /**
     * 获取一周中的第几天
     *
     * @return
     */
    public static int getDayInWeek() {
        Calendar lastDate = Calendar.getInstance();
        return lastDate.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取一天中的小时
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getHourInDay() {
        Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
        return curDate.getHours();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDifTime(String firstTime, String lastTime) {
        String time = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = df.parse(lastTime);
            Date d2 = df.parse(firstTime);
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);
            time = hours + "h" + minutes + "m";
            System.out.println(hours + "小时" + minutes + "分");
        } catch (Exception e) {
        }
        return time;
    }


    //获取当前时间
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);//yyyy年MM月dd日  yyyy-MM-dd HH:mm:SS
        String now = dateFormat.format(date);
        return now;
    }

    //获取当前月份
    public static String getCurrentMonthDay(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINESE);
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return month+"月"+day+"日";
    }
}
