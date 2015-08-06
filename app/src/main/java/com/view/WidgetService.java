package com.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static RemoteViews view ;
    private SharedPreferences WidgetPreferences;
    public WidgetService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //view = new RemoteViews(getPackageName(), R.layout.sms_widget);
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

        switch (LeaveDay){
            case 69:
                Notification(LeaveDay,"恭喜你剩下：");
                break;
            case 60:
                Notification(LeaveDay,"你只剩2個月了!!");
                break;
            case 50:
                Notification(LeaveDay,"不到2個月，撐下去吧!!");
                break;
            case 40:
                Notification(LeaveDay,"邁入40，加油!!");
                break;
            case 30:
                Notification(LeaveDay,"你只剩1個月了!!");
                break;
            case 15:
                Notification(LeaveDay,"你只剩半個月了，準備離開!");
                break;
            case 10:
                Notification(LeaveDay,"終於要最後倒數");
                break;
            case 5:
                Notification(LeaveDay,"一個禮拜不到囉!!");
                break;
            case 1:
                Notification(LeaveDay,"恭喜!!!!! 要退伍了");
                break;

        }

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

        Log.v("test","改1");
        //更新Widget
        CharSequence widgetText = this.getString(R.string.SmsOutDay)+LeaveDay;
        view = new RemoteViews(getPackageName(), R.layout.sms_widget);
        view.setTextViewText(R.id.appwidget_text, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + widgetText);

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
    public void Notification(int Leaveday,String CustomString){
        //取得Notification服務
        NotificationManager NotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //設定當按下這個通知之後要執行的activity
        Intent notifyIntent = new Intent(this,MainActivity.class);
        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent=PendingIntent.getActivity(this,0,notifyIntent,0);

        Notification.Builder NotificationBuilder = new Notification.Builder(this);
        //設定出現在狀態列的圖示
        long [] VibratePattern = {100,400,500,400};

        NotificationBuilder
                .setSmallIcon(R.drawable.ic_action_clanendar)
                .setContentTitle("剩餘退伍天數："+Leaveday)
                .setContentText( CustomString +"\n"+ Leaveday+"天後退伍")
                .setContentInfo("資訊")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setVibrate(VibratePattern)
                .setContentIntent(appIntent)
                .setAutoCancel(true);

        NotificationManager.notify(0,NotificationBuilder.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
