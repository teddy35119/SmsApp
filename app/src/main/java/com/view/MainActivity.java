package com.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.text.NumberFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInitCompoment();
        SmsPreference = new Preference(MainActivity.this);
        mainWork();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent SettingIntent = new Intent();
            SettingIntent.setClass(MainActivity.this,SettingActivity.class);
            startActivity(SettingIntent);
            //setContentView(R.layout.setting);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mainWork();
        Toast.makeText(MainActivity.this,"Resume",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mainWork();
        Toast.makeText(MainActivity.this,"Pause",Toast.LENGTH_LONG).show();

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
    public void mainWork(){

        ShowSms = SmsPreference.showWork() ;
        SmsInText.setText(getText(R.string.SmsInDate) + ShowSms.dateFormat(ShowSms.getSmsYear(), ShowSms.getSmsMonth(), ShowSms.getSmsDay()));

        ShowSmsTIME();
        LeaveDayText.setText(getText(R.string.SmsOutDate) + String.valueOf(ShowSms.getLeaveDay()));

        int SmsLifeDay = ShowSms.getLifeYear()*365 + ShowSms.getLifeDay();
        SmsPorgressBar.setMax(SmsLifeDay);
        int RemainDay = SmsLifeDay-(int)ShowSms.getLeaveDay();
        SmsPorgressBar.setProgress(RemainDay);
        double RemainPercent = ((double)RemainDay/(double)SmsLifeDay)*100;

        RemainPercentText.setText("退伍進度下載：" + String.format("%.2f", RemainPercent) + "%");

    }
    public void ShowSmsTIME(){
        SmsOutText.setText(getText(R.string.SmsOutDate) + ShowSms.dateFormat(ShowSms.getSmsOutTime().get(Calendar.YEAR),
                ShowSms.getSmsOutTime().get(Calendar.MONTH),
                ShowSms.getSmsOutTime().get(Calendar.DAY_OF_MONTH)) + "-" + ShowSms.TimeFormat(ShowSms.getSmsOutTime()));

    }
}
