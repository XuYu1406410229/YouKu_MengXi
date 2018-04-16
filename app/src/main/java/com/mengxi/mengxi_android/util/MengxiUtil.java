package com.mengxi.mengxi_android.util;

import com.mengxi.mengxi_android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MengxiUtil {
	private static Dialog loaddialog;
	private static Dialog showdialog;
	public static final String PREFERENCE_NAME = "mengxi_pref";
	public static final String ISFIRSTIN_KEY = "isFirstIn";
	public static final String ISFIRSTIN_VALUE = "isFirstIn";
	public static final String GUID_KEY="isGUid";
	public static final String GUID_VALUE="true";
	public static final String URLBASEHREF_KEY = "urlBaseHref";
	public static final String ORDERCOUNT = "orderCount";
	public static final int ADDCART = 1001;// 添加购物车
	public static final int EXITFLAG = -1001;// 退出Handler
	public static boolean ISEXIT = false;// 是否退出

	public static final int SHOWMENU = 1;// 显示菜单
	public static final int CLOSEMENU = -1;// 隐藏菜单
	public static final int SHOWGUID=1002;//显示指示页面
	public static final int RECEIVDEEEOR=1003;//加载错误
	public static final int LOADFINISHED=1004;//加载结束
	public static final int EXITIMAGES=1005;//图片activities退出
	public static final int COLSELOAD=1006;//关闭Load
	public static final int OPENLOAD=1007;//打开Load
	public static final int ISUPDATE=1008;//检查更新
	public static final int GETURL=1009;//更换域名
//	public static final String URLBASEHREF_VALUE = "http://58.246.172.150/MobileApp/AndroidApp/";
//	public static final String VERSION_URL = "http://58.246.172.150/MobileApp/download/version.xml";
	//public static final String URLBASEHREF_VALUE = "https://www.emengxi.com/MobileApp/AndroidApp/";
	//public static final String VERSION_URL = "https://www.emengxi.com/MobileApp/download/version.xml";
	public static final String URLBASEHREF_VALUE = "http://192.168.2.50:33333/";
	public static final String VERSION_URL = "https://www.etanking.com/MobileApp/download/version.xml";
	private static Handler MyHandler;
	private static Context MyContext;
	
	
	
	public static void initMengxiUtil(Context context,Handler handler) {
		MyHandler = handler;
		MyContext = context;
	}
	// open loading
	/**
	 * 
	 * @param context
	 * @param message
	 * @param type
	 */
	public static void openLoad(Context context) {
		if(loaddialog==null){
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = null;
			RelativeLayout layout = null;
			view = inflater.inflate(R.layout.load_dialog, null);
			layout = (RelativeLayout) view.findViewById(R.id.load_view);
			ImageView imageView = (ImageView) view.findViewById(R.id.loadimage);
			Animation animation = AnimationUtils.loadAnimation(context,R.anim.load_adimation);
			imageView.setAnimation(animation);
			loaddialog = new Dialog(context, R.style.dialog);
			loaddialog.setContentView(layout);
			loaddialog.setCancelable(false);
			loaddialog.show();
		}
		
	}

	/**
	 * 打开网络提示
	 * 
	 * @param context
	 * @param webView
	 * @param url
	 */
	public static void showNetwoekTips(final Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = null;
		LinearLayout layout = null;
		view = inflater.inflate(R.layout.shownetworkset, null);
		layout = (LinearLayout) view.findViewById(R.id.shownetworklayout);
		Button setNetwork = (Button) layout.findViewById(R.id.setNetwork);
		setNetwork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				context.startActivity(intent);
			}
		});

		Button network_back = (Button) layout.findViewById(R.id.network_back);
		network_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				colseShow();
			}
		});

		showdialog = new Dialog(context, R.style.dialog);
		showdialog.setContentView(layout, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		showdialog.setCancelable(false);
		showdialog.show();

	}

	// colse loading
	public static void colseLoad(String url) {
		if (loaddialog != null && loaddialog.isShowing()) {
			loaddialog.cancel();
			loaddialog.dismiss();
			loaddialog = null;
		}
		
		if(url.indexOf("startOpen==true")!=-1){
			if(sharedPreferences(MyContext, GUID_KEY, null).equals("")){
				MyHandler.sendEmptyMessage(SHOWGUID);
			}
			MyHandler.sendEmptyMessage(ISUPDATE);
		}
	}

	/**
	 * 关闭网络提示
	 */
	public static void colseShow() {
		if (showdialog != null && showdialog.isShowing()) {
			showdialog.cancel();
			showdialog.dismiss();
			showdialog = null;
		}
	}


	/**
	 * webView加载缓存
	 * 
	 * @param context
	 * @param webSettings
	 * @param webViewSousce
	 */

	@SuppressWarnings("deprecation")
	public static void loadCache(Context context, WebSettings webSettings,
			String webViewSousce) {
		webSettings.setRenderPriority(RenderPriority.HIGH);

		// 判断是否有网络，有网络的情况下使用LOAD_DEFAULT，
		// 无网络的情况下使用LOAD_CACHE_ELSE_NETWORK
		if (NetworkUtil.IsNetWorkEnable(context)) {
			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		} else {
			webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		webSettings.setDomStorageEnabled(true);// 开启DOM storage API 功能
		webSettings.setDatabaseEnabled(true);// 开启database storage API功能
		webSettings.setAppCacheEnabled(true);// 开启Application Cache功能
	}

	/**
	 * 
	 * @param context
	 * @param key 需要存储的Key
	 * @param newValue  需要存储的Value, null 表示只查询是否有该值
	 * @return
	 */
	public static String sharedPreferences(Context context, String key, String newValue) {
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		String oldValue = preferences.getString(key, "");
		if (newValue != null && !oldValue.equals(newValue)) {
			Editor editor = preferences.edit();
			editor.putString(key, newValue);
			editor.commit();
			oldValue = newValue;
		}
		return oldValue;
	}


	//用于保存数据
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("util",Context.MODE_PRIVATE);
	}
	public static void putBoolean( Context context,String key,boolean value){
		SharedPreferences sharedPreferences =getSharedPreferences(context);
		SharedPreferences.Editor editor =sharedPreferences.edit();
		editor.putBoolean(key,value);
		editor.commit();
	}
	//获取它的数据
	public static boolean fetchBoolean(Context context,String key,boolean defValue){
		return getSharedPreferences(context).getBoolean(key,defValue);
	}


	/*public static String saveObj(Context context, String key, String... values) {
		JSONObject jsonObject = new JSONObject();
		String saveType = "";
		try{
			for (int i=0; i<values.length; i++){
				jsonObject.put(values[i], values[i]);
			}
			//得到SharedPreferences对象
			SharedPreferences sp = context.getSharedPreferences(key, 0);
			SharedPreferences.Editor edit = sp.edit();
			edit.putString("key", jsonObject.toString());
			edit.commit();
			saveType = "1";
		} catch (JSONException e){
			saveType = "0";
		}
		return saveType;
	}
	//用于获取保存的数据
	public static String getObject(Context context, String key){
		SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
		String value = sp.getString(key, "");
		String result = "";
		if(!TextUtils.isEmpty(value)){
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(value);
				result = jsonObject.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}


		}
		return result;
	}

*/

}
