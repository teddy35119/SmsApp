package com.sms;

import java.util.Calendar;

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
        SmsInTime.set(InYeay,InMonth,InDay);
    }
    //取得入伍日期
    public Calendar getSmsInTime(){

        return SmsInTime;
    }
    //設定退伍時間
    public void setSmsOutTime(){
        SmsOutTime.set(InYeay,InMonth,InDay);
        SmsOutTime.add(Calendar.YEAR,SmsYear);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,SmsDay);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,DiscountDay);
    }
    //取得退伍日期
    public Calendar getSmsOutTime(){
        return SmsOutTime;
    }
    //設定役別
    public void setSmsLifeDay(int userYear,int userDay){
            SmsYear = userYear;
            SmsDay = userDay;
    }
    //取得退伍天數
    public long getLeaveDay(){
        long aDayInMilliSecond = 60 * 60 * 24 * 1000;     //一天的毫秒數
        LeaveDay = (SmsOutTime.getTimeInMillis() - SmsInTime.getTimeInMillis()) / aDayInMilliSecond;
        return LeaveDay;
    }
    //日期格式
    public String ShowSmsInTime(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }
}
