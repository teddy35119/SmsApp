package com.sms;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by teddy on 2015/2/15.
 */
public  class   Preference {
    private SharedPreferences SmsPreferences;
    private Smser ShowSms;
    public Preference(Context context){
        SmsPreferences = context.getSharedPreferences("setting",0);
    }
    public  void saveSetting(Smser FirstSms){
        SmsPreferences.edit().putInt("ReduceDay",FirstSms.getReduceDay())
                .putInt("LifeYear",FirstSms.getLifeYear())
                .putInt("LifeDay",FirstSms.getLifeDay())
                .putInt("SmsYear",FirstSms.getSmsYear())
                .putInt("SmsMonth",FirstSms.getSmsMonth())
                .putInt("SmsDay",FirstSms.getSmsDay()).commit();
    }
    public Smser showWork(){
        ShowSms = new Smser();
        int year,month,day,LifeYear,LifeDay,ReduecDay;
        year = SmsPreferences.getInt("SmsYear", ShowSms.getSmsInTime().get(Calendar.YEAR));
        month = SmsPreferences.getInt("SmsMonth", ShowSms.getSmsInTime().get(Calendar.MONTH));
        day = SmsPreferences.getInt("SmsDay", ShowSms.getSmsInTime().get(Calendar.DAY_OF_MONTH));
        LifeYear = SmsPreferences.getInt("LifeYear", 0);
        LifeDay = SmsPreferences.getInt("LifeDay",0);
        ReduecDay = SmsPreferences.getInt("ReduceDay", 0);

        ShowSms.setSmsInTime(year,month,day);
        ShowSms.setSmsLifeDay(LifeYear,LifeDay);
        ShowSms.setReduceDay(ReduecDay);
        ShowSms.setSmsOutTime();
        return ShowSms;
    }
}
