package com.bonc.kongdy.significant.utlis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kongdy on 2016/11/25.
 */
public class MainUtils {


    /**
     * 获取 bithmap 主色调 用来做新闻的背景色
     * @param bitmap
     * @return
     */
    public static int getImageColor(Bitmap bitmap){
        Palette palette = Palette.from(bitmap).generate();
        if(palette == null || palette.getDarkMutedSwatch() == null){
            return Color.LTGRAY;
        }
        return palette.getDarkMutedSwatch().getRgb();
    }

    /**
     * 秒 转 时分秒
     * @param secString
     * @return
     */
    public static String second2Time(int secString){
        int last = secString % 60;
        String stringLast;
        if (last <= 9) {
            stringLast = "0" + last;
        } else {
            stringLast = last + "";
        }

        String durationString;
        int minit = secString / 60;
        if (minit < 10) {
            durationString = "0" + minit;
        } else {
            durationString = "" + minit;
        }
        return durationString + "' " + stringLast + '"';
    }
    /**
     * 获取当前日期
     */
    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        return sdf.format(date);
    }

    /***
     * 日期减一
     * @param datetime 日期(20161102)
     * @return 20161101
     */
    public static String getLastDay(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DAY_OF_MONTH, -1);
        date = cl.getTime();
        return sdf.format(date);
    }

    /**
     * 安全的 String 返回
     *
     * @param prefix 默认字段
     * @param obj 拼接字段 (需检查)
     */
    public static String safeText(String prefix, String obj) {
        if (TextUtils.isEmpty(obj)) return "";
        return TextUtils.concat(prefix, obj).toString();
    }

    public static String safeText(String msg) {
        return safeText("", msg);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }
}
