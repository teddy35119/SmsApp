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
    private long LeaveDay;

    public void Smser()
    {
        //this.SmsInTime = SmsInTime;

    }
    public void setSmsInTime(Calendar userSmsInTime){
        SmsInTime = userSmsInTime;
    }
    public Calendar getSmsInTime(){
        return SmsInTime;
    }
    //取得退伍時間
    public Calendar getSmsOutTime(){
        SmsOutTime.add(Calendar.YEAR,SmsYear);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,SmsDay);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,DiscountDay);
        return SmsOutTime;
    }
    //設定役別
    public void setSmsLifeDay(int userYear,int userDay){
        if(userYear >= 0 && userDay >= 0)
        {
            SmsYear = userYear;
            SmsDay = userDay;
        }
    }
    //取得退伍天數
    public long getLeaveDay(){
        long aDayInMilliSecond = 60 * 60 * 24 * 1000;     //一天的毫秒數
        LeaveDay = (SmsOutTime.getTimeInMillis() - SmsInTime.getTimeInMillis()) / aDayInMilliSecond;
        return LeaveDay;
    }

}
