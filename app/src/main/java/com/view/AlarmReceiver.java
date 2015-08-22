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

                case 57:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 56:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 55:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 54:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 53:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 52:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 51:
                    Notification(LeaveDay,context.getString(R.string.ForTest));
                    break;
                case 50:
                    Notification(LeaveDay,context.getString(R.string.For50));
                    break;
                case 40:
                    Notification(LeaveDay,context.getString(R.string.For40));
                    break;
                case 30:
                    Notification(LeaveDay,context.getString(R.string.For30));
                    break;
                case 15:
                    Notification(LeaveDay,context.getString(R.string.For15));
                    break;
                case 10:
                    Notification(LeaveDay,context.getString(R.string.For10));
                    break;
                case 5:
                    Notification(LeaveDay,context.getString(R.string.For5));
                    break;
                case 1:
                    Notification(LeaveDay,context.getString(R.string.For1));
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
                .setContentTitle(mContext.getString(R.string.NotiLeaveDay)+LeaveDay)
                .setContentText( CustomString +"\n"+ LeaveDay+mContext.getString(R.string.NotiWord))
                .setContentInfo(mContext.getString(R.string.NotiInform))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher))
                .setVibrate(VibratePattern)
                .setContentIntent(appIntent)
                .setAutoCancel(true);

        NotificationManager.notify(0, NotificationBuilder.build());



    }

}
