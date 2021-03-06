package com.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.util.Calendar;

public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        settingInitCompoment();

        FirstSms = new Smser();
        settingWork();
    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onPause(){
        super.onPause();

    }
    private RadioButton  NormalSms,BodySms,ResearchSms;
    private RadioGroup SmsRG;
    private Button TimeChoose,SaveButton,ResetButton;
    private NumberPicker ReduceDayPicker;
    private int mYear,mMonth,mDay;
    private int LifeYear,LifeDay;
    private DatePickerDialog datePickerDialog;
    private Smser FirstSms;
    private  Preference SettingPreferences;
    public void settingInitCompoment(){

        SmsRG = (RadioGroup)findViewById(R.id.SmsRG);
        NormalSms = (RadioButton) findViewById(R.id.NormalRB);
        BodySms = (RadioButton)findViewById(R.id.BodyRB);
        ResearchSms = (RadioButton)findViewById(R.id.ResearchRB);
        TimeChoose = (Button)findViewById(R.id.TimeChooseB);
        SaveButton = (Button)findViewById(R.id.SaveButton);
        ResetButton = (Button)findViewById(R.id.ResetButton);
        ReduceDayPicker = (NumberPicker)findViewById(R.id.ReduceDayPicker);
        SettingPreferences = new Preference(this);

    }
    public void settingWork(){

        //取出初始化時間
        mYear = SettingPreferences.getYear();
        mMonth = SettingPreferences.getMonth();
        mDay = SettingPreferences.getDay();

        FirstSms.setSmsInTime(mYear, mMonth, mDay);

        //取出服役時間
        LifeYear = SettingPreferences.getLifeYear();
        LifeDay = SettingPreferences.getLifeDay();

        FirstSms.setSmsLifeDay(LifeYear,LifeDay);

        TimeChoose.setText(getText(R.string.SmsInTime) + String.valueOf(mYear) +"/"+ String.valueOf(mMonth+1) +"/"+String.valueOf(mDay) );

        //初始化減免天數Picker
        ReduceDayPicker.setMaxValue(30);
        ReduceDayPicker.setMinValue(0);
        ReduceDayPicker.setValue(SettingPreferences.getReduceDay());
        saveReduceDay(ReduceDayPicker.getValue());

        //初始化RationButton
        SmsRG.check(SettingPreferences.getRationBCheckId());

        //減免天數Picker
        ReduceDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                saveReduceDay(newVal);
            }
        });
        //時間選擇Button
        TimeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
                datePickerDialog.updateDate(mYear, mMonth, mDay);
                TimeChoose.setText(getText(R.string.SmsInTime) + String.valueOf(mYear) +"/"+ String.valueOf(mMonth+1) +"/"+String.valueOf(mDay) );

            }
        });
        //替代役資格設定
        SmsRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.NormalRB:
                        LifeYear = 1;
                        LifeDay = 15;
                        break;
                    case R.id.BodyRB:
                        if(LifeDay == 15){LifeDay = 0;}
                        LifeYear = 1;
                        break;
                    case R.id.ResearchRB:
                        if(LifeDay == 15){LifeDay = 0;}
                        LifeYear = 3;
                        break;

                }

                FirstSms.setSmsLifeDay(LifeYear,LifeDay);
                FirstSms.setSmsOutTime();

            }
        });
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePreferences();
                FirstSms.setSmsOutTime();
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
                dialog.setTitle(getText(R.string.ConfirmData));
                dialog.setMessage(
                                    getText(R.string.SmsInTime) + String.valueOf(mYear) +"/"+ String.valueOf(mMonth+1) +"/"+String.valueOf(mDay) +"\n"+
                                    getText(R.string.SmsInDate) + FirstSms.getLifeYear()+getText(R.string.Year)+ FirstSms.getLifeDay()+getText(R.string.Day)+"\n"+
                                    getText(R.string.ReduceDay)+FirstSms.getReduceDay()
                                 );

                dialog.setPositiveButton(R.string.Confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Intent mIntent = new Intent();
                                mIntent.setClass(SettingActivity.this,MainActivity.class);
                                startActivity(mIntent);
                                SettingActivity.this.finish();
                            }
                        });
                dialog.setNegativeButton(R.string.Cancle,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                }

        });
    }//////////////////////settingWork finish////////////////////////
    //設定入伍時間Picker
    @Override
    protected Dialog onCreateDialog(int id) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                FirstSms.setSmsInTime(mYear, mMonth, mDay);
                TimeChoose.setText(getText(R.string.SmsInTime) + String.valueOf(mYear) +"/"+ String.valueOf(mMonth+1) +"/"+String.valueOf(mDay) );


            }

        }, mYear,mMonth, mDay);

        return datePickerDialog;
    }
    public void sharePreferences(){
        SettingPreferences.saveSetting(FirstSms,SmsRG.getCheckedRadioButtonId());

    }
    public void saveReduceDay(int newVal){
        FirstSms.setReduceDay(newVal);
        FirstSms.setSmsOutTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent SettingIntent = new Intent();
        switch (id){
            case  R.id.action_settings :

                break;
            case R.id.action_widget :
                SettingIntent.setClass(SettingActivity.this,WidgetSettingActivity.class);
                startActivity(SettingIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
