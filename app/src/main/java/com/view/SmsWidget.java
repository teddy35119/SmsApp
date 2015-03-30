package com.view;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;

import android.content.Context;


import android.content.Intent;
import android.os.HandlerThread;
import android.widget.RemoteViews;



import java.util.Date;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.SimpleDateFormat;
import android.os.Handler;


/**
 * Implementation of App Widget functionality.
 */
public class SmsWidget extends AppWidgetProvider {
    private Preference SmsPreference;
    private Smser WidgetSms;
    private static Long LeaveDay;

    Context context_main ;
    AppWidgetManager app_manager;
    int []appWidgetId;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SmsPreference = new Preference(context);
        WidgetSms = SmsPreference.showWork();
        LeaveDay = WidgetSms.getLeaveDay();


        app_manager = appWidgetManager;
        context_main = context;
        appWidgetId = appWidgetIds;

        Intent intent = new Intent(context_main,WidgetService.class);
        context_main.startService(intent);


        /*final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }*/

    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Intent intent = new Intent(context_main,WidgetService.class);
        context_main.stopService(intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text)+LeaveDay;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sms_widget);
        views.setTextViewText(R.id.appwidget_text,  new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( new Date()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


