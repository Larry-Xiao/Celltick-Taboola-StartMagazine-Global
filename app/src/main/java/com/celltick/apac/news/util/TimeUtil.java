package com.celltick.apac.news.util;

import android.util.Log;

import com.celltick.apac.news.R;
import com.celltick.apac.news.app.StarNewsApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Larryx on 4/17/2018.
 */
public class TimeUtil {

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    //stamp to date   *1000 ms
    public static String timeToDate_ms(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(time)*1000);
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

        String date = sf.format(calendar.getTime());
        return date;

    }

    //stamp to date
    public static String timeToDate(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

        String date = sf.format(calendar.getTime());
        return date;

    }

    /*
 * 将时间戳转换为时间date
 */
    public static Date stampToDate(long timeMillis){
        Date date = new Date(timeMillis*1000);
        return date;
    }


    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try{
            date = sdf.parse(time);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String getGMTTamp(){
        String currentTime = getCurrentDate();
        String timestamp = "";
        try {
            timestamp = TimeUtil.dateToStamp(currentTime);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return timestamp;
    }



    //比较时间先后 - 时间戳
    public static int compare_date(String DATE1, String DATE2) {

        Long date1 = Long.valueOf(DATE1);
        Long date2 = Long.valueOf(DATE2);
        if (date1 > date2) {
            Log.d("TimeUtil","dt1离现在近--");
            return 1;
        } else if (date1 < date2) {
            Log.d("TimeUtil","dt2离现在近--");
            return -1;
        } else {
            return 0;
        }







//        SimpleDateFormat  df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        try {
//            String dt1 = timeToDate_ms(DATE1);
//            String dt2 = timeToDate_ms(DATE2);
//            Date dt11 = df.parse(dt1);
//            Date dt22 = df.parse(dt2);
//            Log.d("TimeUtil","dt1 = " + dt11);
//            Log.d("TimeUtil","dt2 = " + dt22);
//            if (dt11.getTime() > dt22.getTime()) {
//                Log.d("TimeUtil","dt1 在dt2前");
//                return 1;
//            } else if (dt11.getTime() < dt22.getTime()) {
//                Log.d("TimeUtil","dt1在dt2后");
//                return -1;
//            } else {
//                return 0;
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            Log.d("TimeUtile", "error = " + exception.getMessage());
//        }
//        return 0;
    }


    /**
     * 将毫秒转时分秒
     *
     * @param time
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }


    /**
     * return a human time like 'just now', 'i mins ago' etc..
     */
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_year);
        }
        if (diff > month) {
            r = (diff / month);
            return r + " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_month);
        }
        if (diff > day) {
            r = (diff / day);
            return r + " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_day);
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_hour);
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_min);
        }
        return " " + StarNewsApp.getContext().getResources().getString(R.string.publish_date_now);
    }

    public static Date utcToLocal(String utcTime){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }

    /*
    将Jun 3, 2019 9:30:00 AM  转化成   Mon Jun 10 09:30:00 GMT+08:00 2019
    再转化成06/03/2019 09:30:00
     */
    public static String parse2ReadableTime(String englishDate){
        SimpleDateFormat sdf1;
        if (englishDate.contains("AM")){
            Log.d("time","AM - MMM d, yyyy hh:mm:ss a");
            sdf1 = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a",Locale.ENGLISH);
        }else if (englishDate.contains("PM")){
            Log.d("time","PM - MMM d, yyyy hh:mm:ss aa");
            sdf1 = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aa",Locale.ENGLISH);
        }else {
            sdf1 = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a",Locale.ENGLISH);
        }
        Date d2 = null;
        try {
            d2 = sdf1.parse(englishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String s = d2.toString();
//        String s = "Sun Sep 02 2012 08:00:00 GMT+08:00";
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
//        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String result = sdf3.format(date);

        return result;
    }

    //Mon Jun 10 09:30:00 GMT+08:00 2019转化成06/03/2019 09:30:00
    public static String parseGMT2ReadableTime(String time){
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String result = sdf3.format(date);

        return result;
    }

    /*
    将Jun 3, 2019 9:30:00 AM 转化成06/03/2019 09:30:00
     */


    public static String parseUTCAMPM2Local(String ampmUTC){
        //先将带有AM PM的UTC 时间转化成24小时制
        SimpleDateFormat sdf1 = null;
        if (ampmUTC.contains("AM")){
            sdf1 = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a",Locale.ENGLISH);
        }else if (ampmUTC.contains("PM")){
            sdf1 = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aa",Locale.ENGLISH);
        }
        Date date = null;
        try {
            date = sdf1.parse(ampmUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.d("time","ampmUTCDate = "+date);
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String ampmUTCStr = sdf2.format(date);
//        Log.d("time","ampmUTCStr = "+ampmUTCStr);

        //再将MM/dd/yyyy HH:mm:ss UTC转化成当地时间
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(ampmUTCStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate.toString();
    }




}
