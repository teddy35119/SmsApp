package com.view;



import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teddy.smsapp.R;
import com.sms.Preference;
import com.sms.Smser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInitCompoment();
        SmsPreference = new Preference(MainActivity.this);
        mainWork();
        Toast.makeText(this,"OnCreate2",Toast.LENGTH_SHORT).show();

    }

    private ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    private Intent setIntent(){

        //取得螢幕截圖
        getScreenShot();

        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, "com.example.sms.fileprovider", newFile);

        Intent sendIntent = new Intent();
        if(contentUri != null){

            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            sendIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            sendIntent.putExtra(Intent.EXTRA_STREAM, contentUri);

        }
        return sendIntent;

    }
    private void getScreenShot()
    {
        //藉由View來Cache全螢幕畫面後放入Bitmap
        View mView = getWindow().getDecorView();
        mView.setDrawingCacheEnabled(true);
        mView.buildDrawingCache();
        Bitmap mFullBitmap = mView.getDrawingCache();

        //取得系統狀態列高度
        Rect mRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);
        int mStatusBarHeight = mRect.top;

        //取得手機螢幕長寬尺寸
        int mPhoneWidth = getWindowManager().getDefaultDisplay().getWidth();
        int mPhoneHeight = getWindowManager().getDefaultDisplay().getHeight();

        //將狀態列的部分移除並建立新的Bitmap
        Bitmap mBitmap = Bitmap.createBitmap(mFullBitmap, 0, mStatusBarHeight, mPhoneWidth, mPhoneHeight - mStatusBarHeight);
        //將Cache的畫面清除
        mView.destroyDrawingCache();

        //將圖片存入暫存區
        try {

            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                //Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(setIntent(),"分享"));
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
