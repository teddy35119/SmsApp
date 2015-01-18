package com.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.teddy.smsapp.R;


public class MainActivity extends ActionBarActivity {
    TextView SmsInText,SmsOutText;
    RadioButton  NormalSms,BodySms,ResearchSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitCompoment();
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
    public void InitCompoment(){
        SmsInText = (TextView)findViewById(R.id.InTimeText);
        SmsOutText = (TextView)findViewById(R.id.OutTimeText);
        NormalSms = (RadioButton) findViewById(R.id.NormalRB);
        BodySms = (RadioButton)findViewById(R.id.BodyRB);
        ResearchSms = (RadioButton)findViewById(R.id.ResearchRB);

    }
}
