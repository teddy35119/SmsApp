package com.view;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import com.example.teddy.smsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WidgetService extends Service {
    private Handler mHandlerBoss = null;
    private HandlerThread mHandlerEmployee = null;
    private String EmployeeName = "John";
    public WidgetService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandlerEmployee = new HandlerThread(EmployeeName);
        mHandlerEmployee.start();
        mHandlerBoss = new Handler(mHandlerEmployee.getLooper());
        mHandlerBoss.post(mRunnable);
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
        //更新Widget
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.sms_widget);
        view.setTextViewText(R.id.appwidget_text, new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( new Date()));

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
