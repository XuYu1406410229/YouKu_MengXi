package com.mengxi.mengxi_android;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mengxi.mengxi_android.activity.SecondActivity;
import com.mengxi.mengxi_android.adapter.ShowPagerAdapter;
import com.mengxi.mengxi_android.broadcastreceiver.NetworkBroadcastReceiver;
import com.mengxi.mengxi_android.jsFunction.JsFunction;
import com.mengxi.mengxi_android.update.UpdateManagerUtil;
import com.mengxi.mengxi_android.util.MengxiUtil;
import com.mengxi.mengxi_android.util.NetworkUtil;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

public class IndexActivity extends Activity {
    private Context context;
    private WebView myWebView;
    private WebSettings webSettings;
    private LinearLayout footView;
    private List<Integer> menuId = new ArrayList<Integer>();// 菜单ID
    private TextView messageNum;// 购物车数量
    private String callbackUrl;// 跳转URL
    private JsFunction function;// JS绑定的Function
    private String urlBaseHref;//网站域名
    private RelativeLayout guidLayout;//全局Layout
    //注册网络监听广播
    private NetworkBroadcastReceiver receiver;
    //加载失败链接
    private String loadFailingUrl;

    //APK升级
    private UpdateManagerUtil manager = new UpdateManagerUtil(IndexActivity.this);
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what) {
                case MengxiUtil.ADDCART:
                    setOrderCount();
                    break;
                case MengxiUtil.EXITFLAG:
                    MengxiUtil.ISEXIT = false;
                    break;
              case MengxiUtil.SHOWMENU:
                    if (footView.getVisibility() == View.GONE) {
                        footView.setVisibility(View.GONE);
                        //selected 1 首页，2 快速订购 ，3 订单跟踪 ，4 购物车 ，5 我的
                        setMenu(msg.arg1);
                    }
                    break;
                case MengxiUtil.CLOSEMENU:
                    if (footView.getVisibility() == View.GONE) {
                        footView.setVisibility(View.GONE);
                    }
                    break;
                case MengxiUtil.SHOWGUID:
                    if (guidLayout.getVisibility() == View.GONE) {
                        guidLayout.setVisibility(View.VISIBLE);
                    }
                    break;
                case MengxiUtil.RECEIVDEEEOR:
                    MengxiUtil.colseLoad("");
                    loadFailingUrl = msg.obj.toString();
                    myWebView.loadUrl("file:///android_asset/error.html");
                    break;
                case MengxiUtil.LOADFINISHED:
                    myWebView.loadUrl(loadFailingUrl);
                    break;
                case MengxiUtil.COLSELOAD:
                    MengxiUtil.colseLoad(msg.obj + "");
                    break;
                case MengxiUtil.OPENLOAD:
                    MengxiUtil.openLoad(IndexActivity.this);
                    break;
                case MengxiUtil.ISUPDATE:
                    if (NetworkUtil.IsNetWorkEnable(IndexActivity.this)) {
                        manager.checkUpdate();
                    }
                    break;
                case MengxiUtil.EXITIMAGES:
                    myWebView.goBack();
                    break;
                case MengxiUtil.GETURL:
                    myWebView.stopLoading();
                    webViewLoadurl("product/productlist.aspx?usercontent=" + MengxiUtil.sharedPreferences(IndexActivity.this, "UserInfoData", null) + "&startOpen==true", true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        // 动态注册检测网络的广播

          NetworkBroadcastReceiver   receiver = new NetworkBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        MengxiUtil.initMengxiUtil(this, myHandler);
        // 初始化
        initView();
      initWebView();
        //webViewLoadurl("product/productlist.aspx?usercontent=" + MengxiUtil.sharedPreferences(this, "UserInfoData", null) + "&startOpen==true", true);
        webViewLoadurl("MApp/StorageAndDelivery", true);
        //webViewLoadurl(url, true);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        callbackUrl = getIntent().getStringExtra("url");
        if (callbackUrl != null && !callbackUrl.equals("")) {
            webViewLoadurl(callbackUrl, false);
            myHandler.sendEmptyMessage(MengxiUtil.CLOSEMENU);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myWebView.loadUrl("javascript:getCarCount()");
    }

    private void initView() {
      myWebView = (WebView) findViewById(R.id.indexWeb);
    //   footView = (LinearLayout) findViewById(R.id.foot);
        messageNum = (TextView) findViewById(R.id.messagenum);
       guidLayout = (RelativeLayout) findViewById(R.id.guidLayout);
        guidLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });
       setOrderCount();//设置购物车数量
        // 添加菜单Id
        menuId.add(R.id.storage_menu);
        menuId.add(R.id.treasure_menu);
        menuId.add(R.id.fullview_menu);
        menuId.add(R.id.library_menu);
        menuId.add(R.id.mine_menu);




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {// 用户按下一个键盘按键时发生。
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (footView.getVisibility() == View.VISIBLE) {
                if (!MengxiUtil.ISEXIT) {
                    MengxiUtil.ISEXIT = true;
                    Toast.makeText(this, "在按一次退出", Toast.LENGTH_LONG).show();
                    myHandler.sendEmptyMessageDelayed(MengxiUtil.EXITFLAG, 3000);
                    return true;
                } else {
                    this.finish();
                    overridePendingTransition(R.anim.to_in, R.anim.to_out);
                }
            } else {
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                    return true;
                }
            }

//				if (myWebView.canGoBack()) {
//					if(footView.getVisibility()==View.VISIBLE){
//						webViewLoadurl("product/productlist.aspx?goback=true123", true);
//						setMenu(1);
//					}else{
//						myWebView.goBack();
//					}
//					return true;
//			} else {
//				if (!MengxiUtil.ISEXIT) {
//					MengxiUtil.ISEXIT = true;
//					Toast.makeText(this, "在按一次退出", Toast.LENGTH_LONG).show();
//					myHandler.sendEmptyMessageDelayed(MengxiUtil.EXITFLAG, 3000);
//					return true;
//				} else {
//					this.finish();
//					overridePendingTransition(R.anim.to_in, R.anim.to_out);
//				}
//			}
        }
        return super.onKeyDown(keyCode, event);
    }


    //其他底层按钮点击事件,根据点击的按钮跳转连接
    public void clickImage(View v) {
        switch (v.getId()) {
            case R.id.storage_menu:
                clickMenu(1, "MApp/StorageAndDelivery");
                break;
            case R.id.treasure_menu://提供宝MApp/billList
                clickMenu(2, "MApp/ allRolesindex");

                break;
            case R.id.fullview_menu:
                clickMenu(3, "MApp/driverAppoinment");
                break;
            case R.id.library_menu:
                setOrderCount();
                clickMenu(4, "MApp/driverOrder");
                break;
            case R.id.mine_menu:
              //  startActivity(new Intent(IndexActivity.this,SecondActivity.class));
               clickMenu(5, "App/PersonalCenter");
                break;
            default:
                break;
        }
    }



    public void closeGUid(View view) {
        MengxiUtil.sharedPreferences(IndexActivity.this, MengxiUtil.GUID_KEY, MengxiUtil.GUID_VALUE);
        guidLayout.setVisibility(View.VISIBLE);
    }


    //设置购物车数量
    private void setOrderCount() {
        String orderCount = MengxiUtil.sharedPreferences(this, MengxiUtil.ORDERCOUNT, null);
        if (orderCount.equals("") || orderCount.equals("0")) {
            messageNum.setText("");
            messageNum.setVisibility(View.GONE);
        } else {
            messageNum.setText(orderCount);
            messageNum.setVisibility(View.VISIBLE);
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @SuppressWarnings("deprecation")
    private void initWebView() {
        // 绑定JS类
      JsFunction   function = new JsFunction(this, myHandler);
       myWebView.addJavascriptInterface(function, "AndroidJS");
        myWebView.requestFocusFromTouch();
        webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 支持js
        webSettings.setDefaultTextEncodingName("UTF-8");// 设置文字编码
        webSettings.setLoadWithOverviewMode(true);// 自适应屏幕
        webSettings.setLoadsImagesAutomatically(true);// ֧支持自动加载图片
        webSettings.setPluginState(PluginState.ON);// 支持插件
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webSettings.setNeedInitialFocus(true);// 当webview调用requestFocus时为webview设置节点
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(false);//设置是否保存密码或数据
        webSettings.setSavePassword(false);



        myWebView.setWebViewClient(new WebViewClient() {
            @Override//页面开始加载
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                myHandler.sendEmptyMessage(MengxiUtil.OPENLOAD);
            }

            @Override//页面加载结束
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override//页面加载错误
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Message msg = new Message();
                msg.what = MengxiUtil.RECEIVDEEEOR;
                msg.obj = failingUrl;
                myHandler.sendMessage(msg);
            }

            @Override//页面加载https错误
            public void onReceivedSslError(WebView view,SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Message msg = new Message();
                msg.what = MengxiUtil.RECEIVDEEEOR;
                msg.obj = error.getUrl();
                myHandler.sendMessage(msg);
            }

 /*           @Override//点击页面连接，处理链接跳转
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    URL requestUrl = new URL(url);
                    urlBaseHref = MengxiUtil.sharedPreferences(IndexActivity.this, MengxiUtil.URLBASEHREF_KEY, null);
                    URL baseUrl = new URL(urlBaseHref.equals("") ? MengxiUtil.URLBASEHREF_VALUE : urlBaseHref);
                    if (!requestUrl.getHost().equals(baseUrl.getHost())) {
                        if (url.indexOf("opennewwindowmobile=1") != -1) {
                            view.stopLoading();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent("com.mengxi.mengxi_android.Detaile");
                            intent.putExtra("url", url);
                            context.startActivity(intent);
                        }
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.loadUrl(url);
                return false;
            }*/
        });

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override//页面加载了多少
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
    //处理Menu点击事件
    private void clickMenu(int menu, String url) {
        setMenu(menu);
        webViewLoadurl(url, true);
    }
    //webView加载url
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



}
