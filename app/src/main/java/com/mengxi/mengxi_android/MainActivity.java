package com.mengxi.mengxi_android;

import com.mengxi.mengxi_android.util.MengxiUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends Activity {



    /*    private static final int GO_INDEX = 1000;
   private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;*/

    private static final String IS_FRIST="is_frist";
    private static Activity activity;

/*    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            *//*switch (msg.what) {
                case GO_INDEX:
                    intent = new Intent("com.mengxi.mengxi_android.Index");
                    break;
                case GO_GUIDE:
                    intent = new Intent("com.mengxi.mengxi_android.Show");
                    break;
            }*//*
            Intent intent = new Intent("com.mengxi.mengxi_android.Index");
            activity.startActivity(intent);
            activity.finish();
        };
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(MengxiUtil.fetchBoolean(MainActivity.this,IS_FRIST,false)){
                    startActivity(new Intent(MainActivity.this,IndexActivity.class));
                    MengxiUtil.putBoolean(MainActivity.this,IS_FRIST,true);
                }else{
                    startActivity(new Intent(MainActivity.this,ShowActivity.class));
                }
                MengxiUtil.putBoolean(MainActivity.this,IS_FRIST,true);
                return true;

            }
        }).sendEmptyMessageDelayed(0,3000);

                /*        String isFirstIn = MengxiUtil.sharedPreferences(this, MengxiUtil.ISFIRSTIN_KEY, null);
                if (isFirstIn.equals(MengxiUtil.ISFIRSTIN_VALUE)) {
                    handler.sendEmptyMessageDelayed(GO_INDEX, SPLASH_DELAY_MILLIS);
                } else {
                    handler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
                }*/
                 activity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
