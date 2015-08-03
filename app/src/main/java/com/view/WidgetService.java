package com.view;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WidgetService extends Service {
    private Handler mHandlerBoss = null;
    private HandlerThread mHandlerEmployee = null;
    private String EmployeeName = "John";
    private Preference SmsPreference;
    private Smser WidgetSms;
    private static int LeaveDay;
    private  RemoteViews view;
    private SharedPreferences WidgetPreferences;
    public WidgetService() {
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
        view = new RemoteViews(getPackageName(), R.layout.sms_widget);
/*
        int Red = WidgetPreferences.getInt("ColorRed",0);
        int Green = WidgetPreferences.getInt("ColorGreen",0);
        int Blue =  WidgetPreferences.getInt("ColorBlue", 0);
        float TextSize = WidgetPreferences.getFloat("Size",12);
        view.setTextColor(R.id.appwidget_text, Color.rgb(Red, Green, Blue));
        view.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, TextSize);
*/
        return super.onStartCommand(intent, flags, startId);
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //每秒跑一次
            buildUpdate();
            mHandlerBoss.postDelayed(this,1000);
        }
    };
    private void buildUpdate()
    {

        Log.v("test","改7");
        //更新Widget
        CharSequence widgetText = this.getString(R.string.SmsOutDay)+LeaveDay;
        view = new RemoteViews(getPackageName(), R.layout.sms_widget);
        view.setTextViewText(R.id.appwidget_text, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + widgetText );

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
