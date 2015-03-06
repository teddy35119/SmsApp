package com.sms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.content.SharedPreferences;
/**
 * Created by teddy on 2014/12/31.
 */
public class Smser {
    private Calendar SmsInTime;
    private Calendar SmsOutTime;
    private int DiscountDay;
    private int SmsYear;
    private int SmsDay;
    private int InYeay;
    private int InMonth;
    private int InDay;
    private long LeaveDay;

    //建構子
    public Smser()
    {
        this.SmsInTime = Calendar.getInstance();
        this.SmsOutTime = Calendar.getInstance();


    }
    //設定入伍日期
    public void setSmsInTime(int Year,int Month,int Day){
        InYeay = Year;
        InMonth = Month;
        InDay = Day;
        SmsInTime.set(InYeay,InMonth,InDay,0,0,0);
    }
    public int getSmsYear(){
        return InYeay;
    }
    public int getSmsMonth(){
        return InMonth;
    }
    public int getSmsDay(){
        return InDay;
    }
    //取得入伍日期
    public Calendar getSmsInTime(){

        return SmsInTime;
    }
    //設定退伍時間
    public void setSmsOutTime(){
        SmsOutTime.set(InYeay,InMonth,InDay,0,0,0);

        SmsOutTime.add(Calendar.YEAR,SmsYear);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,SmsDay);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,DiscountDay);
    }
    //取得退伍日期
    public Calendar getSmsOutTime(){
        return SmsOutTime;
    }
    //設定減免天數
    public void setReduceDay(int ReduceDay){
        DiscountDay = ReduceDay*-1;
    }
    //取得減免天數
    public int getReduceDay(){

        return DiscountDay*-1;
    }
    //設定役別
    public void setSmsLifeDay(int userYear,int userDay){
            SmsYear = userYear;
            SmsDay = userDay;
    }
    //取得役別天數
    public int getLifeYear(){
        return SmsYear;
    }
    public int getLifeDay(){
        return SmsDay;
    }
    //取得退伍天數
    public  long  getLeaveDay(){
        Calendar Now = Calendar.getInstance();
        long aDayInMilliSecond = 60 * 60 * 24 * 1000;     //一天的毫秒數
        LeaveDay = (SmsOutTime.getTimeInMillis() - Now.getTimeInMillis()) / aDayInMilliSecond;
        return LeaveDay;
    }
    //日期格式
    public static String dateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }
    public static String TimeFormat(Calendar SmsOutTime){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String TimeOut = df.format(SmsOutTime.getTime());
        return TimeOut;

    }
}
