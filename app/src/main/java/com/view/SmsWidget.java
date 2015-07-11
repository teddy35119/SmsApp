package com.view;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
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
    private int []appWidgetId;

    public static String OnClickAction = "IntentToActivity";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SmsPreference = new Preference(context);
        WidgetSms = SmsPreference.showWork();
        LeaveDay = WidgetSms.getLeaveDay();


        app_manager = appWidgetManager;
        context_main = context;
        appWidgetId = appWidgetIds;

        //intent service
        Intent intent = new Intent(context_main,WidgetService.class);
        context_main.startService(intent);

        //點擊開啟主頁

        Intent IntentActivity = new Intent(context_main,MainActivity.class);
        //IntentActivity.setAction(OnClickAction);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, IntentActivity, 0);

        RemoteViews views = new RemoteViews(context_main.getPackageName(), R.layout.sms_widget);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        /*final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }*/

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

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


        CharSequence widgetText = context.getString(R.string.SmsOutDay)+LeaveDay;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sms_widget);
        views.setTextViewText(R.id.appwidget_text,  new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( new Date()) + widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


