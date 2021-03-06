package com.sms;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by teddy on 2015/2/15.
 */
public  class   Preference {
    private SharedPreferences SmsPreferences;
    private Smser ShowSms = new Smser();
    private  int year,month,day,LifeYear,LifeDay,ReduecDay;;
    public Preference(Context context){
        SmsPreferences = context.getSharedPreferences("setting",0);
    }
    public  void saveSetting(Smser FirstSms,int checkedId){
        SmsPreferences.edit().putInt("ReduceDay",FirstSms.getReduceDay())
                .putInt("LifeYear",FirstSms.getLifeYear())
                .putInt("LifeDay",FirstSms.getLifeDay())
                .putInt("SmsYear",FirstSms.getSmsYear())
                .putInt("SmsMonth",FirstSms.getSmsMonth())
                .putInt("SmsDay",FirstSms.getSmsDay())
                .putInt("RGcheckedId",checkedId).commit();
    }

    public Smser showWork(){
        getYear();
        getMonth();
        getDay();
        getLifeYear();
        getLifeDay();
        ReduecDay = SmsPreferences.getInt("ReduceDay", 0);

        ShowSms.setSmsInTime(year,month,day);
        ShowSms.setSmsLifeDay(LifeYear,LifeDay);
        ShowSms.setReduceDay(ReduecDay);
        ShowSms.setSmsOutTime();
        return ShowSms;
    }
    public int getReduceDay(){
        return SmsPreferences.getInt("ReduceDay", 0);
    }
    public int getRationBCheckId(){
        return SmsPreferences.getInt("RGcheckedId",-1);
    }
    public int getLifeYear(){
        return  LifeYear = SmsPreferences.getInt("LifeYear",0);
    }
    public int getLifeDay(){
        return  LifeDay = SmsPreferences.getInt("LifeDay",0);
    }

    public int getYear(){
        return year = SmsPreferences.getInt("SmsYear", ShowSms.getSmsInTime().get(Calendar.YEAR));
    }
    public int getMonth(){
        return  month = SmsPreferences.getInt("SmsMonth", ShowSms.getSmsInTime().get(Calendar.MONTH));
    }
    public int getDay(){
        return  day = SmsPreferences.getInt("SmsDay", ShowSms.getSmsInTime().get(Calendar.DAY_OF_MONTH));
    }
}
