package com.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WidgetSettingActivity extends ActionBarActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private TextView RedText,BlueText,GreenText;
    private SeekBar RedSeekBar,BlueSeekBar,GreenSeekBar,SizeSeekBar;
    private TextView DisplayText,ShowSizeText;
    private Button SaveButton;
    private int RedColor = 0,GreenColor = 0, BlueColor = 0;
    private float TextSize = 0;
    private SharedPreferences prefs;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_setting);
        mContext = this;
        prefs = mContext.getSharedPreferences("SettingWidget", 0);
        initalView();
        initalSeekBar();
        DisplayText.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + getString(R.string.SmsOutDay));
        setSeekBar();


        SaveButton.setOnClickListener(SaveOnClickListener);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            //Toast.makeText(this,"SetId",Toast.LENGTH_SHORT).show();
        }
        //If they gave us an intent without the widget id, just bail.
        /*if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            //finish();
            Toast.makeText(this,"Id"+mAppWidgetId,Toast.LENGTH_SHORT).show();
        }*/



    }

    View.OnClickListener SaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //將顏色及字形丟入prefs
            prefs.edit().putFloat("Size", TextSize)
                        .putInt("ColorRed", RedColor)
                        .putInt("ColorGreen", GreenColor)
                        .putInt("ColorBlue", BlueColor).apply();


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            RemoteViews viewAppWidget = new RemoteViews(getPackageName(),R.layout.sms_widget);
           //點擊開啟主頁
            Intent IntentActivity = new Intent(mContext,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, IntentActivity, 0);
            viewAppWidget.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            //設定顏色及字體大小
            viewAppWidget.setTextColor(R.id.appwidget_text,Color.rgb(RedColor, GreenColor, BlueColor));
            viewAppWidget.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, TextSize);

            //更新widget
            appWidgetManager.updateAppWidget(new ComponentName(mContext.getPackageName(), SmsWidget.class.getName()), viewAppWidget);

            Toast.makeText(mContext,"update widget",Toast.LENGTH_SHORT).show();

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };
    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        final float OriginalSize = pixelsToSp(getApplicationContext(), DisplayText.getTextSize());
        private SeekBar mSeekBar;
        public OnSeekBarChangeListener(SeekBar seekBar){
            mSeekBar = seekBar;
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int ProgressSize, boolean fromUser) {

            if(mSeekBar.equals(SizeSeekBar)){
                TextSize = OriginalSize + ProgressSize;
                DisplayText.setTextSize(TextSize);

                ShowSizeText.setText(TextSize + "sp");
            }
            if (mSeekBar.equals(RedSeekBar)){RedColor = ProgressSize;}
            if (mSeekBar.equals(GreenSeekBar)){GreenColor = ProgressSize;}
            if (mSeekBar.equals(BlueSeekBar)){BlueColor = ProgressSize;}
            DisplayText.setTextColor(Color.rgb(RedColor, GreenColor, BlueColor));
            DisplayText.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + getString(R.string.SmsOutDay));
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
    //轉換 px to sp
    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }
    public void setSeekBar(){

        float initalSize = prefs.getFloat("Size", 0);
        //Toast.makeText(this,"float pre"+(int)initalSize,Toast.LENGTH_SHORT).show();
        int Red = prefs.getInt("ColorRed", 0);
        int Green = prefs.getInt("ColorGreen",0);
        int Blue = prefs.getInt("ColorBlue", 0);
        try {

            SizeSeekBar.setProgress((int)initalSize-10);
            RedSeekBar.setProgress(Red);
            GreenSeekBar.setProgress(Green);
            BlueSeekBar.setProgress(Blue);
            DisplayText.setTextColor(Color.rgb(Red, Green, Blue));

            DisplayText.setTextSize(TextSize);
            ShowSizeText.setText(TextSize + "sp");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void initalSeekBar(){
        SizeSeekBar.setMax(16);
        RedSeekBar.setMax(255);
        GreenSeekBar.setMax(255);
        BlueSeekBar.setMax(255);



        SizeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(SizeSeekBar) {});
        RedSeekBar.setOnSeekBarChangeListener(new  OnSeekBarChangeListener(RedSeekBar){});
        GreenSeekBar.setOnSeekBarChangeListener(new  OnSeekBarChangeListener(GreenSeekBar){});
        BlueSeekBar.setOnSeekBarChangeListener(new  OnSeekBarChangeListener(BlueSeekBar){});
    }
    public void initalView(){
        RedText = (TextView)findViewById(R.id.RedText);
        BlueText = (TextView)findViewById(R.id.BlueText);
        GreenText = (TextView)findViewById(R.id.GreenText);
        DisplayText = (TextView)findViewById(R.id.DisplayText);
        ShowSizeText = (TextView)findViewById(R.id.ShowSizeText);
        SaveButton = (Button)findViewById(R.id.SaveButton);

        RedSeekBar = (SeekBar)findViewById(R.id.RedSeekBar);
        BlueSeekBar = (SeekBar)findViewById(R.id.BlueSeekBar);
        GreenSeekBar = (SeekBar)findViewById(R.id.GreenSeekBar);
        SizeSeekBar = (SeekBar)findViewById(R.id.SizeSeekBar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayText.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + getString(R.string.SmsOutDay));
    }

    @Override
    protected void onStart() {
        super.onStart();
        DisplayText.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + getString(R.string.SmsOutDay));
        setSeekBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent SettingIntent = new Intent();
        switch (id){
            case  R.id.action_settings :
                SettingIntent.setClass(WidgetSettingActivity.this,SettingActivity.class);
                startActivity(SettingIntent);
                break;
            case R.id.action_widget :

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
