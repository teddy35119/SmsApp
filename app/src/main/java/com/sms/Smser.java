package com.sms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.content.SharedPreferences;
/**
 * Created by teddy on 2014/12/31.
 */
public class Smser {
    private Calendar SmsInTime;
    private Calendar SmsOutTime;
    private int ReduceDay;
    private int LifeYear;
    private int LifeDay;
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

        SmsOutTime.add(Calendar.YEAR,LifeYear);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,LifeDay);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,ReduceDay);
    }
    //取得退伍日期
    public Calendar getSmsOutTime(){
        return SmsOutTime;
    }
    //設定減免天數
    public void setReduceDay(int userReduceDay){
        ReduceDay = userReduceDay*-1;
    }
    //取得減免天數
    public int getReduceDay(){

        return ReduceDay*-1;
    }
    //設定役別
    public void setSmsLifeDay(int userYear,int userDay){
        LifeYear = userYear;
        LifeDay = userDay;
    }
    //取得役別天數
    public int getLifeYear(){
        return LifeYear;
    }
    public int getLifeDay(){
        return LifeDay;
    }
    //取得退伍天數
    public  int  getLeaveDay(){
        Calendar Now = Calendar.getInstance();
        //Calendar SetTime = Calendar.getInstance();
        //SetTime.set(2015,6,12,24,0,0);
        long aDayInMilliSecond = 60 * 60 * 24 * 1000;     //一天的毫秒數
        //long aHourInMilliSecond = 60 * 60 * 1000;
        //long aMinInMilliSecond = 60 * 1000 ;
        //long aSecInMilliSecond = 1000 ;

        LeaveDay = (SmsOutTime.getTimeInMillis() - Now.getTimeInMillis()) / aDayInMilliSecond;
        int temp =(int)LeaveDay;
        //long LeaveHour = (SetTime.getTimeInMillis() - Now.getTimeInMillis()) / aHourInMilliSecond;
        //long LeaveMin = (SetTime.getTimeInMillis() - Now.getTimeInMillis()) / aMinInMilliSecond / LeaveHour;
        //long LeaveSec = (SetTime.getTimeInMillis() - Now.getTimeInMillis()) / aSecInMilliSecond / LeaveMin;

        //String LeaveText = LeaveDay +"天"+ LeaveHour +"時"+ LeaveMin + "分" + LeaveSec+"秒";
        //String LeaveText = ""+LeaveSec;
        return temp;
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
