package com.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private RadioButton  NormalSms,BodySms,ResearchSms;
    private RadioGroup SmsRG;
    private Button TimeChoose,SaveButton,ResetButton;
    private NumberPicker ReduceDayPicker;
    private int mYear,mMonth,mDay;
    private DatePickerDialog datePickerDialog;
    private Smser FirstSms;
    public void settingInitCompoment(){

        SmsRG = (RadioGroup)findViewById(R.id.SmsRG);
        NormalSms = (RadioButton) findViewById(R.id.NormalRB);
        BodySms = (RadioButton)findViewById(R.id.BodyRB);
        ResearchSms = (RadioButton)findViewById(R.id.ResearchRB);
        TimeChoose = (Button)findViewById(R.id.TimeChooseB);
        SaveButton = (Button)findViewById(R.id.SaveButton);
        ResetButton = (Button)findViewById(R.id.ResetButton);
        ReduceDayPicker = (NumberPicker)findViewById(R.id.ReduceDayPicker);
    }
    public void settingWork(){

        //取出初始化時間
        mYear = FirstSms.getSmsInTime().get(Calendar.YEAR);
        mMonth = FirstSms.getSmsInTime().get(Calendar.MONTH);
        mDay = FirstSms.getSmsInTime().get(Calendar.DAY_OF_MONTH);

        //初始化減免天數Picker
        ReduceDayPicker.setMaxValue(30);
        ReduceDayPicker.setMinValue(0);
        ReduceDayPicker.setValue(10);
        ReduceDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                FirstSms.setReduceDay(newVal);
                FirstSms.setSmsOutTime();


            }
        });
        TimeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
                datePickerDialog.updateDate(mYear, mMonth, mDay);

            }
        });
        //替代役資格設定
        SmsRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int LifeYear = 0;
                int LifeDay = 0;
                switch (checkedId){
                    case R.id.NormalRB:
                        LifeYear = 1;
                        LifeDay = 15;
                        break;
                    case R.id.BodyRB:
                        LifeYear = 1;
                        break;
                    case R.id.ResearchRB:
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
                dialog.setTitle("確認資料");
                dialog.setMessage("Ly" + FirstSms.getLifeYear() + "Ld" + FirstSms.getLifeDay()+"RD"+FirstSms.getReduceDay());
                dialog.setPositiveButton(R.string.abc_action_bar_home_description,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
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
                //SmsInText.setText(""+ FirstSms.ShowSmsInTime(mYear,mMonth,mDay));

            }

        }, mYear,mMonth, mDay);

        return datePickerDialog;
    }
    public void sharePreferences(){
        Preference SettingPreferences = new Preference(this);
        SettingPreferences.saveSetting(FirstSms);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
