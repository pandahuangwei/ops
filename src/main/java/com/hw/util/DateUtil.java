/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:18
 */
public class DateUtil {

    public static final int MILLIS_OF_SECOND = 1000;
    public static final int SECOND_OF_MINUTE = 60;
    public static final int MINUTE_OF_HOUR = 60;
    public static final int HOUR_OF_DAY = 24;

    public static final long MILLIS_OF_MINUTE = MILLIS_OF_SECOND * SECOND_OF_MINUTE;

    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat SDFYYMMDD = new SimpleDateFormat(
            "yyMMdd");

    private final static SimpleDateFormat SDFYYMMDDHHMMSS = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    private final static SimpleDateFormat SDFYYMMDDHHMMSS_S = new SimpleDateFormat(
            "yyyyMMddHHmmssS");

    private final static SimpleDateFormat SDFYY = new SimpleDateFormat(
            "yy");

    /**
     * 比较两个日期差是否大于指定分钟数
     * @param maxDate
     * @param minDate
     * @return maxDate>=minDate:true; maxDate<minDate:flase
     */
    public static boolean compareMinutesOfDate(Date maxDate, Date minDate, int minutes){
        if (maxDate == null || minDate == null) {
            return false;
        }
        return maxDate.getTime() / DateUtil.MILLIS_OF_MINUTE - minDate.getTime() / DateUtil.MILLIS_OF_MINUTE > minutes;
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    public static String formatDay(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return dateStr;
        }
        return dateStr.substring(0,10);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(){
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }


    public static String getYY() {
        return SDFYY.format(new Date());
    }

    public static String getYYMMDD() {
        return SDFYYMMDD.format(new Date());
    }

    public static String getYYMMDDHHMMSS() {
        return SDFYYMMDDHHMMSS.format(new Date());
    }
    public static String getYYMMDDHHMMSSMS() {
        return SDFYYMMDDHHMMSS_S.format(new Date())+new Random().nextInt(1000);
    }


    public static int getCurTime() {
        return Integer.parseInt(getYYMMDDHHMMSS());
    }

    public static Date getTm() {
        return new Date();
    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
        return fomatDate(s).getTime() >=fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        try {
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
    public static int getDiffYear(String startTime,String endTime) {
        try {
            long aa=0;
            int years=(int) (((dateFormat.parse(endTime).getTime()-dateFormat.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;

        java.util.Date beginDate = null;
        java.util.Date endDate = null;

        try {
            beginDate = dateFormat.parse(beginDateStr);
            endDate= dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    public static Date fomatTime(String date) {
        try {
            return sdfTime.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static Date getFutureDate() {
        try {
            return dateFormat.parse("2019-12-29");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 组合成新的时间取date的日期，tmstr为时间(yyyymmdd hh:mi:ss,hhmm=> yyyymmdd hh:mi)
     * @param date 日期时间
     * @param tmStr 字符串时间
     * @return 新的日期时间
     */
    public static Date genTm(Date date,String tmStr){
        try {
            String yyyyMMdd = sdfDay.format(date);
            StringBuilder append = new StringBuilder().append(yyyyMMdd).append(" ").append(tmStr).append(":00");
            return  sdfTime.parse(append.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较两个时间是否同一天
     * @param firstTm  第一个时间
     * @param secondTm 第二个时间
     * @return 是否同一日期
     */
    public static boolean isSameDay(Date firstTm,Date secondTm) {
       return DateUtils.isSameDay(firstTm,secondTm);
    }

    public static boolean isInRange(Date srcTm,Date beginTm,Date endTm) {
        return srcTm.after(beginTm) && srcTm.before(endTm);
    }

    public static void main(String[] args) {
        System.out.println(genTm(new Date(),"22:00"));
        System.out.println(isSameDay(new Date(),new Date(System.currentTimeMillis()-15)));
        Date beginTm = genTm(new Date(), "08:00");
        Date endTm = genTm(new Date(), "22:00");

        System.out.println(isInRange(new Date(),beginTm,endTm));
        
       /* System.out.println(getAfterDayWeek("3"));

        System.out.println(compareMinutesOfDate(new Date(System.currentTimeMillis()+11*60*1000),new Date(),12));*/
    }
}
