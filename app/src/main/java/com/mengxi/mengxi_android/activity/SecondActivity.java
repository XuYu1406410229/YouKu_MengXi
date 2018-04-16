package com.mengxi.mengxi_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mengxi.mengxi_android.IndexActivity;
import com.mengxi.mengxi_android.MainActivity;
import com.mengxi.mengxi_android.R;
import com.mengxi.mengxi_android.ShowActivity;
import com.mengxi.mengxi_android.util.MengxiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13/013.
 */

public class SecondActivity extends Activity{
    private List<Integer> menuId = new ArrayList<Integer>();// 菜单ID
    private String urlBaseHref;//网站域名
    private WebView myWebView;
    private ImageView storage_menu1,mine_menu1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        storage_menu1 =(ImageView)findViewById(R.id.storage_menu1);
        mine_menu1 =(ImageView)findViewById(R.id.mine_menu1);
        initView();
        initWebView();




        storage_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              switch(v.getId()){
                  case R.id.storage_menu1:
                      startActivity(new Intent(SecondActivity.this,IndexActivity.class));


              }

                //startActivity(new Intent("MApp/StorageAndDelivery"));
            }
        });
        mine_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent("App/PersonalCenter"));
                switch(v.getId()){

                    case R.id.mine_menu1:
                        startActivity(new Intent(SecondActivity.this, MainActivity.class));

                }


            }
        });



    }

    private void initWebView() {
    }

    private void initView() {

        menuId.add(R.id.storage_menu1);
        menuId.add(R.id.treasure_menu1);
        menuId.add(R.id.fullview_menu1);
        menuId.add(R.id.library_menu1);
        menuId.add(R.id.mine_menu1);
    }
    // 设置菜单显示
    private void setMenu(int menu) {
        for (int i = 0; i < menuId.size(); i++) {
            if (i == menu - 1) {
                findViewById(menuId.get(i)).setBackgroundResource(R.color.foot_select);
            } else {
                findViewById(menuId.get(i)).setBackgroundResource(R.color.foot);
            }
        }
    }


    private void webViewLoadurl(String url, boolean addUrlBaseHrefl) {
        String loadUrl = "";
        urlBaseHref = MengxiUtil.sharedPreferences(this, MengxiUtil.URLBASEHREF_KEY, null);
        urlBaseHref = urlBaseHref.equals("") ? MengxiUtil.URLBASEHREF_VALUE : urlBaseHref;
        if (addUrlBaseHrefl) {
            loadUrl = urlBaseHref + url;
        } else {
            loadUrl = url;
        }
        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        Log.e("url", loadUrl);
        myWebView.loadUrl(loadUrl);
    }

}
