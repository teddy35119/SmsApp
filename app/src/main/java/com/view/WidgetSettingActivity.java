package com.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.teddy.smsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WidgetSettingActivity extends ActionBarActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private TextView RedText,BlueText,GreenText;
    private SeekBar RedSeekBar,BlueSeekBar,GreenSeekBar,SizeSeekBar;
    private TextView DisplayText,ShowSizeText;
    private Button SaveButton;
    private int RedColor = 0,GreenColor = 0, BlueColor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_setting);
        initalView();
        initalSeekBar();
        DisplayText.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n" + getString(R.string.SmsOutDay));

        SaveButton.setOnClickListener(SaveOnClickListener);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        //If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

    }
    View.OnClickListener SaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Context context = WidgetSettingActivity.this;




            // Push widget update to surface with newly set prefix
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            SmsWidget.updateAppWidget(context, appWidgetManager,mAppWidgetId);

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
      //RedText.setText(ProgressSize);
            if(mSeekBar.equals(SizeSeekBar)){
                float TotalSize = OriginalSize + ProgressSize;
                DisplayText.setTextSize(TotalSize);
                ShowSizeText.setText(TotalSize + "sp");
            }
            if (mSeekBar.equals(RedSeekBar)){RedColor = ProgressSize;}
            if (mSeekBar.equals(GreenSeekBar)){GreenColor = ProgressSize;}
            if (mSeekBar.equals(BlueSeekBar)){BlueColor = ProgressSize;}
            DisplayText.setTextColor(Color.rgb(RedColor, GreenColor, BlueColor));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            Log.v("Test",""+RedColor+"_"+GreenColor+"_"+BlueColor);
        }
    }
    //Convert px to sp
    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }
    public void initalSeekBar(){
        SizeSeekBar.setMax(36);

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
