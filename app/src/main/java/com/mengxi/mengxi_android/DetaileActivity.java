package com.mengxi.mengxi_android;


import com.mengxi.mengxi_android.broadcastreceiver.NetworkBroadcastReceiver;
import com.mengxi.mengxi_android.jsFunction.JsFunction;
import com.mengxi.mengxi_android.util.MengxiUtil;
import com.mengxi.mengxi_android.util.NetworkUtil;

import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;

public class DetaileActivity extends Activity {
    private WebView detaileWebView;
    private WebSettings webSettings;
    private String detaileUrl;
    private Activity activity;
    //注册网络监听广播
    private NetworkBroadcastReceiver receiver;
    private String loadFailingUrl;
    private JsFunction function;

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MengxiUtil.OPENLOAD:
                    MengxiUtil.openLoad(activity);
                    break;
                case MengxiUtil.RECEIVDEEEOR:
                    MengxiUtil.colseLoad("");
                    loadFailingUrl = msg.obj.toString();
                    detaileWebView.loadUrl("file:///android_asset/error.html");
                    break;
                case MengxiUtil.LOADFINISHED:
                    detaileWebView.loadUrl(loadFailingUrl);
                    break;
                case MengxiUtil.COLSELOAD:
                    MengxiUtil.colseLoad("");
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaile);
        activity = this;
        //动态注册广播
        NetworkBroadcastReceiver  receiver = new NetworkBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        MengxiUtil.initMengxiUtil(activity, myHandler);
        detaileUrl = getIntent().getStringExtra("url");
        detaileWebView = (WebView) findViewById(R.id.detaileWebview);
        webSettings = detaileWebView.getSettings();
        if (NetworkUtil.IsNetWorkEnable(this)) {
            detaileWebView.loadUrl(detaileUrl);
        } else {
            MengxiUtil.showNetwoekTips(this);
        }
        initWebView(detaileWebView);
    }

    @SuppressWarnings("deprecation")
    private void initWebView(WebView webView) {
        // 绑定JS类
        function = new JsFunction(this, myHandler);
        detaileWebView.addJavascriptInterface(function, "AndroidJS");
        webView.requestFocusFromTouch();
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 支持js
        webSettings.setDefaultTextEncodingName("UTF-8");// 设置文字编码
        webSettings.setLoadWithOverviewMode(true);// 自适应屏幕
        webSettings.setLoadsImagesAutomatically(true);// ֧支持自动加载图片
        webSettings.setPluginState(PluginState.ON);// 支持插件
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webSettings.setNeedInitialFocus(true);// 当webview调用requestFocus时为webview设置节点
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(false);// 设置是否保存密码或数据
        webSettings.setSavePassword(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                myHandler.sendEmptyMessage(MengxiUtil.OPENLOAD);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Message msg = new Message();
                msg.what = MengxiUtil.RECEIVDEEEOR;
                msg.obj = failingUrl;
                myHandler.sendMessage(msg);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Message msg = new Message();
                msg.what = MengxiUtil.RECEIVDEEEOR;
                msg.obj = error.getUrl();
                myHandler.sendMessage(msg);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if (url.indexOf("MobileRechargeNotify.aspx") != -1) {
                    view.stopLoading();
                    Intent intent = new Intent("com.mengxi.mengxi_android.Index");
                    intent.putExtra("url", url);
                    activity.startActivity(intent);
                    activity.finish();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    Message msg = new Message();
                    msg.what = MengxiUtil.COLSELOAD;
                    msg.obj = view.getUrl();
                    myHandler.sendMessage(msg);
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    public void goBack(View view) {
        detaileWebView.stopLoading();
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (detaileWebView.canGoBack()) {
                detaileWebView.goBack();
            } else {
                detaileWebView.stopLoading();
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
