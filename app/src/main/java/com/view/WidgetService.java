package com.view;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;

import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WidgetService extends Service {
    private Handler mHandlerBoss = null;
    private HandlerThread mHandlerEmployee = null;
    private String EmployeeName = "John";
    private static Preference SmsPreference;
    private static Smser WidgetSms;
    private static int LeaveDay;
    private static RemoteViews view ;
    private static SharedPreferences WidgetPreferences;
    public WidgetService() {
        Log.v("Test", "Constrouct");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SmsPreference = new Preference(this);
        WidgetPreferences = this.getSharedPreferences("SettingWidget", 0);
        WidgetSms = SmsPreference.showWork();
        LeaveDay = WidgetSms.getLeaveDay();
        //註冊監聽
        SetAlarm();
        Log.v("Test","OnCreate");

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mHandlerBoss.removeCallbacks(mRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandlerEmployee = new HandlerThread(EmployeeName);
        mHandlerEmployee.start();
        mHandlerBoss = new Handler(mHandlerEmployee.getLooper());
        mHandlerBoss.post(mRunnable);

        SmsPreference = new Preference(this);
        WidgetPreferences = this.getSharedPreferences("SettingWidget", 0);
        WidgetSms = SmsPreference.showWork();
        LeaveDay = WidgetSms.getLeaveDay();
        Date dt = new Date(WidgetSms.getLeaveMillis());

        return START_STICKY;
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //每秒跑一次
            buildUpdate();
            mHandlerBoss.postDelayed(this,1000);
        }
    };

    public void SetAlarm()
    {
        Calendar cal = Calendar.getInstance();
        //設定啟動時間
        cal.set(Calendar.HOUR_OF_DAY,8);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("Alarm", "Notification");
        intent.putExtra("LeaveDay",LeaveDay);

        PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        //重複啟動
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pi);
    }
    private void buildUpdate()
    {


        //更新Widget
        CharSequence widgetText = this.getString(R.string.SmsOutDay)+ "\n" +LeaveDay + this.getString(R.string.Date);
        view = new RemoteViews(getPackageName(), R.layout.sms_widget);
        view.setTextViewText(R.id.appwidget_text, widgetText +"\n"+ new SimpleDateFormat("HH:mm:ss").format(new Date(WidgetSms.getLeaveMillis())));

        int Red = WidgetPreferences.getInt("ColorRed",0);
        int Green = WidgetPreferences.getInt("ColorGreen",0);
        int Blue =  WidgetPreferences.getInt("ColorBlue", 0);
        float TextSize = WidgetPreferences.getFloat("Size",12);
        view.setTextColor(R.id.appwidget_text, Color.rgb(Red, Green, Blue));
        view.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, TextSize);

        ComponentName thisWidget = new ComponentName(this, SmsWidget.class);

        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        manager.updateAppWidget(thisWidget, view);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
