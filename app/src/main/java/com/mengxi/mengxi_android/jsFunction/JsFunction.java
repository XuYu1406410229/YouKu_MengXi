package com.mengxi.mengxi_android.jsFunction;



import com.mengxi.mengxi_android.R;
import com.mengxi.mengxi_android.util.MengxiUtil;
import com.mengxi.mengxi_android.util.NetworkUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class JsFunction {
	private Context context;
	private Handler handler;

	public JsFunction(Context context,Handler handler) {
		this.context=context;
		this.handler=handler;
	}

	/** 	
	 * 初始化
	 * @return 返回成功或者失败
	 */
	@JavascriptInterface
	public String  init(String strAdr) {
		
		try {
			return "1";
		} catch (Exception e) {
			return "-1";
		}
		
	}

	/**
	 *  	保存用户
	 * @param strUserName 字符串，用户名称
	 * @param strLoginName 字符串，用户登录名
	 * @param strPWD 字符串，用户登陆密码
	 * @param nUserType 整型，用户的类型  1，个人用户；2，企业用户，3，个人经销商；4，企业经销商 
	 * @param nLoginStatus 整型；1，成功登陆；0，没有登陆
	 * @return 返回成功或者失败
	 */
	@JavascriptInterface
	public String saveUser(String strUserName, String strLoginName,
			String strPWD, int nUserType, int nStatus, int nLoginStatus) {
		try {
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "保存用户失败", Toast.LENGTH_SHORT).show();
			return "-1";
		}
	}

	/**
	 *  	增加购物车
	 * @param nProductId 整型，产品编号
	 * @param nNumber 整型，加入购物车的数量
	 * @return  返回成功或者失败 
	 */
	@JavascriptInterface
	public String addTocart(int nProductId, String nNumber) {
		try {
			MengxiUtil.sharedPreferences(context, MengxiUtil.ORDERCOUNT, nNumber);
			handler.sendEmptyMessage(MengxiUtil.ADDCART);
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "增加购物车失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}

	/**
	 *  	保存Session的Token
	 * @param strToken 字符串，用户从服务器获取的Token; 
	 * @return 返回成功或者失败
	 */
	@JavascriptInterface
	public String saveToken(String strToken) {
		
    try {
		return "1";
	} catch (Exception e) {
		Toast.makeText(context, "保存数据失败", Toast.LENGTH_LONG).show();
		return "-1";
	}
		
	}

	/**
	 *  	保存数据
	 * @param strKey 要保存数据的Key 
	 * @param strValue 要保存的数据 
	 * @param nType 保存数据的类型；1，Session过程数据；2，本地数据
	 * @return
	 */
	@JavascriptInterface
	public String saveData(String strKey, String strValue, int nType) {
		try {
			Log.e("保存数据", strKey + "===" + strValue);
	    	MengxiUtil.sharedPreferences(context, strKey, strValue);
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "保存数据失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}
	
	/**
	 *  	获取数据
	 * @param strKey 要获取数据的Key
	 * @param nType 要获取数据的类型；1，Session过程数据；2，本地数据
	 * @return 返回对应的值，字符串
	 */
	@JavascriptInterface
	public String getData(String strKey, int nType){
		try {
			return MengxiUtil.sharedPreferences(context, strKey, null);
		} catch (Exception e) {
			Toast.makeText(context, "获取数据失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}

	/**
	 *  	获取网络状态
	 * @return
	 * 	返回当前网络的状态：0，没有网络；1，GSM网络；2,2G网络；3.3G网络；4,4G网络；100：宽带连接
	 */
	@JavascriptInterface
	public int getNetwork(){
		int networkType=0;
		try {
			if(NetworkUtil.IsNetWorkEnable(context)){
				networkType=NetworkUtil.getCurrentNetworkType(context);
			}
		} catch (Exception e) {
			Toast.makeText(context, "获取网络状态失败", Toast.LENGTH_LONG).show();
		}
		return networkType;
	}
	
	/**
	 *  	增加消息通知
	 * @param nMsgId 消息编号，整型
	 * @param strMsgTitle 消息Title,字符串
	 * @param strDate 发布消息的时间，字符串
	 * @param nMsgType 消息类型。具体待定义
	 * @return 	返回成功或者失败
	 */
	@JavascriptInterface
	public String addMessage(int nMsgId, String strMsgTitle, String strDate, int nMsgType){
		try {
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "增加消息通知失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}
	
	
	
	/**
	 *  	增加新消息
	 * @param nNum  新消息数量，整型
	 * @return 	返回成功或者失败
	 */	
	@JavascriptInterface
	public String addNewmsg(int nNum){
		try {
	    	
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "增加新消息失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}
	
	/**
	 * 获取无网络情况下提示
	 * @param url
	 */
	@JavascriptInterface
	public void showNetworkTips(String url){
		try {
			MengxiUtil.showNetwoekTips(context);
		} catch (Exception e) {
			Toast.makeText(context, "获取无网络情况下提示失败", Toast.LENGTH_LONG).show();
		}
	}
	
	
	/**
	 * 跳转Activity
	 * @param url  需要跳转的链接
	 * @param type 显示Activity类型  0，调回到没有菜单的activity  1，跳转到有菜单的activity
	 * @param menu 需要跳转的menu 1 首页，2 咨询 ，3 快速订购，4 购物车 ，5 我的
	 * @param animaType 跳转Activity动画类型 0，默认打开 ;1、向左滑动 ; 2，向右滑动;
	 */
	@JavascriptInterface
	public void setNewActivity(String url, int type,int menu,int animaType){
		try {
			String urlBaseHref=MengxiUtil.sharedPreferences(context, MengxiUtil.URLBASEHREF_KEY, null);
			url=urlBaseHref.equals("")?MengxiUtil.URLBASEHREF_VALUE+url:urlBaseHref+url;
			Intent intent=new Intent();
			switch (type) {
			case 0:
				intent.setAction("com.mengxi.mengxi_android.Detaile");
				break;
			case 1:
				intent.setAction("com.mengxi.mengxi_android.Index");
				intent.putExtra("menu", menu);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				break;
			}
			 intent.putExtra("url", url);
			 context.startActivity(intent);
			 switch (animaType) {
			case 1:
				((Activity) context).overridePendingTransition(R.anim.from_in, R.anim.from_out);
				break;
			case 2:
				((Activity) context).overridePendingTransition(R.anim.to_in, R.anim.to_out);
				break;
			}
			 if(type==1){
			 ((Activity)context).finish();
			 ((Activity) context).overridePendingTransition(R.anim.to_in, R.anim.to_out);
			 }
		} catch (Exception e) {
			Toast.makeText(context, "跳转Activity失败", Toast.LENGTH_LONG).show();
		}
		
	}
	
	/**
	 * 去设置网络
	 */
	@JavascriptInterface
	public void setNetwork(){
		try {
			Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
			context.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(context, "设置网络失败", Toast.LENGTH_LONG).show();
		}
	}
	
	
	/**
	 * 用户退出
	 * @param strLoginName 用户登入名
	 * @param nLoginStatus  用户登入状态
	 */
	@JavascriptInterface
	public String loginout(String strLoginName,int nLoginStatus ){
		try {
//			manager.updateLoginstatus(strLoginName, nLoginStatus);
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "用户退出失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}
	
	
	/**
	 * 获取网站域名
	 * @param urlBaseHref
	 */
	@JavascriptInterface
	public String getUrlBaseHref(String urlBaseHref){
		try {
			MengxiUtil.sharedPreferences(context, MengxiUtil.URLBASEHREF_KEY, urlBaseHref);
			handler.sendEmptyMessage(MengxiUtil.GETURL);
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "获取网站域名失败", Toast.LENGTH_LONG).show();
			return "-1";
		}
	}
	/**
	 * 获取已经登入的用户信息
	 */
	@JavascriptInterface
	public String getUser(){
		try {
			return "1";
		} catch (Exception e) {
			Toast.makeText(context, "获取信息失败", Toast.LENGTH_LONG).show();
		}
		return "-1";
	}
	
	
	/**
	 * @param selected 1 首页，2 咨询 ，3 快速订购，4 购物车 ，5 我的
	 */
	@JavascriptInterface
	public String showMenuList(int type, int silde, int selected) {
		try {
			Message message=new Message();
				switch (type) {
				case -1:
					message.what=MengxiUtil.CLOSEMENU;
					break;
				case 1:
					message.what=MengxiUtil.SHOWMENU;
					break;
				}
				message.arg1=selected;
				handler.sendMessage(message);
			return "1";
		} catch (Exception e) {
			
			Toast.makeText(context, "菜单的显示隐藏失败", Toast.LENGTH_LONG).show();
			return "-1";
		}

	}
	
	@JavascriptInterface
	public String loadUrl(){
		try {
			handler.sendEmptyMessage(MengxiUtil.LOADFINISHED);
			return "1";
		} catch (Exception e) {
			return "-1";
		}
	}
	
	
	@JavascriptInterface
	public String returnHtml(String type){
		try {
			handler.sendEmptyMessage(MengxiUtil.EXITIMAGES);
			return "1";
		} catch (Exception e) {
			return "-1";
		}
	}
	@JavascriptInterface
	public String getSDK(){
		try {
			return android.os.Build.VERSION.SDK_INT+"";
		} catch (Exception e) {
			return "-1";
		}
	}

	/**
	 * 当返回的saveType=1时，为保存成功。如果返回savaType=0时，保存失败
	 * */
	@JavascriptInterface
	public String savaData(String key, String values){
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		String saveType = "";

		SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();

		if(values.contains("&")){
			String[] array = values.split("&");
			try{
				for (int i=0; i<array.length; i++){
					String k = array[i].split("=")[0];
					String v = array[i].split("=")[1];
					jsonObject.put(k, v);
				}
				edit.putString(key, jsonObject.toString());
				edit.commit();
				saveType = "1";
			} catch (JSONException e){
				saveType = "0";
			}
		} else {
			if(values.contains("=")){
				int begin = values.indexOf("=");
				int end = values.lastIndexOf("=");
				if(begin == end){
					try{
						String k = values.split("=")[0];
						String v = values.split("=")[1];
						jsonObject.put(k, v);
						edit.putString(key, jsonObject.toString());
						edit.commit();
						saveType = "1";
					} catch (JSONException e){
						saveType = "0";
					}
				} else {
					saveType = "0";
				}
			} else {
				edit.putString(key, values);
				edit.commit();
				saveType = "1";
			}
		}
		return saveType;
	}

	/**
	 * 当能够正确读取数据时，返回一个JSON对象
	 * */
	@JavascriptInterface
	public String getData(String key){
		SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
		String value = sp.getString(key, "");
		String result = "";
		if(!TextUtils.isEmpty(value)){
			org.json.JSONObject jsonObject = null;
			try {
				jsonObject = new org.json.JSONObject(value);
				result = jsonObject.toString();
			} catch (JSONException e) {
				//e.printStackTrace();
				result = value;
			}
		}
		return result;
	}
	
}
