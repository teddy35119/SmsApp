package com.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.teddy.smsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by teddy on 2015/8/17.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bData = intent.getExtras();
        mContext = context;
        if(bData.get("Alarm").equals("Notification"))
        {
            Log.v("test", "in");
            int LeaveDay = bData.getInt("LeaveDay",0);
            Toast.makeText(context, new SimpleDateFormat("HH:mm:ss").format(new Date()), Toast.LENGTH_LONG).show();

            switch (LeaveDay){
                case 69:
                    Notification(LeaveDay,"恭喜你剩下：");
                    break;
                case 60:
                    Notification(LeaveDay,"你只剩2個月了!!");
                    break;
                case 57:
                    Notification(LeaveDay,"測試一下");
                    break;
                case 56:
                    Notification(LeaveDay,"測試一下");
                    break;
                case 55:
                    Notification(LeaveDay,"測試一下");
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
        }
    }

    public void Notification(int LeaveDay,String CustomString){

        //取得Notification服務
        NotificationManager NotificationManager=(NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //設定當按下這個通知之後要執行的activity
        Intent notifyIntent = new Intent(mContext,MainActivity.class);
        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent=PendingIntent.getActivity(mContext,0,notifyIntent,0);

        Notification.Builder NotificationBuilder = new Notification.Builder(mContext);
        //設定出現在狀態列的圖示
        long [] VibratePattern = {100,400,500,400};

        NotificationBuilder
                .setSmallIcon(R.drawable.ic_action_clanendar)
                .setContentTitle("剩餘退伍天數"+LeaveDay)
                .setContentText( CustomString +"\n"+ LeaveDay+"天後退伍")
                .setContentInfo("資訊")
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher))
                .setVibrate(VibratePattern)
                .setContentIntent(appIntent)
                .setAutoCancel(true);

        NotificationManager.notify(0, NotificationBuilder.build());



    }

}
