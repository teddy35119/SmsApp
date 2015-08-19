package com.view;


import android.app.Activity;
import android.content.Intent;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInitCompoment();
        SmsPreference = new Preference(MainActivity.this);
        mainWork();
        Toast.makeText(this,"OnCreate5",Toast.LENGTH_SHORT).show();

    }

    private ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Set up ShareActionProvider's default share intent
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        setShareIntent(SetIntent());
        return true;
    }

    private Intent SetIntent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "我剩下" + LeaveDay +"天");
        sendIntent.setType("text/plain");
        return sendIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }else{
            Log.v("test","isNull");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        Intent SettingIntent = new Intent();
        switch (id){
            case  R.id.action_settings :
                SettingIntent.setClass(MainActivity.this, SettingActivity.class);
                startActivity(SettingIntent);

                break;
            case R.id.action_widget :
                SettingIntent.setClass(MainActivity.this,WidgetSettingActivity.class);
                startActivity(SettingIntent);
                break;
            case R.id.menu_item_share :
                /*Toast.makeText(this,"InShare",Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "我剩下"+LeaveDay);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("jp.naver.line.android");
                startActivity(sendIntent);*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mainWork();
        //Toast.makeText(MainActivity.this,"Resume",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mainWork();
        //Toast.makeText(MainActivity.this,"Pause",Toast.LENGTH_LONG).show();

    }
    private TextView SmsInText,SmsOutText,LeaveDayText,RemainPercentText;
    private ProgressBar SmsPorgressBar;
    private Smser ShowSms;
    private Preference SmsPreference;
    public void showInitCompoment(){
        SmsInText = (TextView)findViewById(R.id.InTimeText);
        SmsOutText = (TextView)findViewById(R.id.OutTimeText);
        LeaveDayText = (TextView)findViewById(R.id.LeaveDayText);
        SmsPorgressBar = (ProgressBar)findViewById(R.id.progressBar);
        RemainPercentText = (TextView)findViewById(R.id.RemainPercentText);
    }
    private int LeaveDay;
    public void mainWork(){

        ShowSms = SmsPreference.showWork() ;
        SmsInText.setText(getText(R.string.SmsInDate) + ShowSms.dateFormat(ShowSms.getSmsYear(), ShowSms.getSmsMonth(), ShowSms.getSmsDay()));

        ShowSmsTIME();
        LeaveDay = ShowSms.getLeaveDay();
        LeaveDayText.setText(getText(R.string.SmsOutDate) + String.valueOf(ShowSms.getLeaveDay()));

        //取出當兵的總天數
        int SmsLifeDay = ShowSms.getLifeYear()*365 + ShowSms.getLifeDay();
        SmsPorgressBar.setMax(SmsLifeDay);
        //計算剩餘天數
        int RemainDay = SmsLifeDay-ShowSms.getLeaveDay();
        //設定進度
        SmsPorgressBar.setProgress(RemainDay);
        double RemainPercent = ((double)RemainDay/(double)SmsLifeDay)*100;

        RemainPercentText.setText("退伍進度下載：" + String.format("%.2f", RemainPercent) + "%");

    }
    public void ShowSmsTIME(){
        SmsOutText.setText(getText(R.string.SmsOutDate) + ShowSms.dateFormat(ShowSms.getSmsOutTime().get(Calendar.YEAR),
                ShowSms.getSmsOutTime().get(Calendar.MONTH),
                ShowSms.getSmsOutTime().get(Calendar.DAY_OF_MONTH)) );

    }
}
