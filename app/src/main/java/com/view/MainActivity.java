package com.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;

import com.example.teddy.smsapp.R;
import com.sms.Smser;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitCompoment();
        Work();
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
            setContentView(R.layout.setting);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    TextView SmsInText,SmsOutText,LeaveDayText;
    RadioButton  NormalSms,BodySms,ResearchSms;
    RadioGroup SmsRG;
    Button TimeChoose;
    NumberPicker RuduceDayPicker;
    int mYear,mMonth,mDay;
    DatePickerDialog datePickerDialog;
    public Smser FirstSms;

    public void InitCompoment(){
        SmsInText = (TextView)findViewById(R.id.InTimeText);
        SmsOutText = (TextView)findViewById(R.id.OutTimeText);
        LeaveDayText = (TextView)findViewById(R.id.LeaveDayText);
        SmsRG = (RadioGroup)findViewById(R.id.SmsRG);
        NormalSms = (RadioButton) findViewById(R.id.NormalRB);
        BodySms = (RadioButton)findViewById(R.id.BodyRB);
        ResearchSms = (RadioButton)findViewById(R.id.ResearchRB);
        TimeChoose = (Button)findViewById(R.id.TimeChooseB);
        RuduceDayPicker = (NumberPicker)findViewById(R.id.ReduceDayPicker);
    }
    public void Work(){
        FirstSms = new Smser();
        mYear = FirstSms.getSmsInTime().get(Calendar.YEAR);
        mMonth = FirstSms.getSmsInTime().get(Calendar.MONTH);
        mDay = FirstSms.getSmsInTime().get(Calendar.DAY_OF_MONTH);
        RuduceDayPicker.setMaxValue(30);
        RuduceDayPicker.setMinValue(0);
        RuduceDayPicker.setValue(10);
        RuduceDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                FirstSms.setReduceDay(newVal);
                FirstSms.setSmsOutTime();
                ShowSmsTIME();
                LeaveDayText.setText(""+ FirstSms.getLeaveDay());
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
                ShowSmsTIME();

            }
        });
    }
    //設定時間
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
                SmsInText.setText(""+ FirstSms.ShowSmsInTime(mYear,mMonth,mDay));

            }

        }, mYear,mMonth, mDay);

        return datePickerDialog;
    }
    public void ShowSmsTIME(){
        SmsOutText.setText(""+FirstSms.ShowSmsInTime(FirstSms.getSmsOutTime().get(Calendar.YEAR),
                FirstSms.getSmsOutTime().get(Calendar.MONTH),
                FirstSms.getSmsOutTime().get(Calendar.DAY_OF_MONTH)));

    }
}
