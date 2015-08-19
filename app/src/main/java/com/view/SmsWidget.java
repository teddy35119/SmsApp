package com.view;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Date;
import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.SimpleDateFormat;



/**
 * Implementation of App Widget functionality.
 */
public class SmsWidget extends AppWidgetProvider {
    private Preference SmsPreference;
    private Smser WidgetSms;
    private static Long LeaveDay;

    private Context context_main ;
    private AppWidgetManager app_manager;
    private int[] appWidgetId;
    private Intent intentService;
    private SharedPreferences WidgetPreferences;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SmsPreference = new Preference(context);
        WidgetPreferences = context.getSharedPreferences("SettingWidget",0);
        WidgetSms = SmsPreference.showWork();
        //LeaveDay = WidgetSms.getLeaveDay();

        app_manager = appWidgetManager;
        context_main = context;
        appWidgetId = appWidgetIds;


        for (int i = 0; i <  appWidgetIds.length; i++) {

            //intent service

            context_main.startService(new Intent(context_main,WidgetService.class));

            //點擊開啟主頁

            Intent IntentActivity = new Intent(context_main,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, IntentActivity, 0);
            RemoteViews views = new RemoteViews(context_main.getPackageName(), R.layout.sms_widget);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);



            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            Toast.makeText(context,"OnUpdateWidget",Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Toast.makeText(context,"OnReceiveWidget",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context,"onEnabledWidget",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
//        context_main.stopService(intentService);
        Toast.makeText(context,"onDeletedWidget",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
       //context_main.stopService(intentService);
        Toast.makeText(context,"onDisabledWidget",Toast.LENGTH_SHORT).show();
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.SmsOutDay)+LeaveDay;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sms_widget);
        views.setTextViewText(R.id.appwidget_text,  new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( new Date()) + widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


