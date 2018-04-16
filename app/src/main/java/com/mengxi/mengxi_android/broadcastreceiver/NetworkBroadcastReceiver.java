package com.mengxi.mengxi_android.broadcastreceiver;

import com.mengxi.mengxi_android.util.MengxiUtil;
import com.mengxi.mengxi_android.util.NetworkUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
					if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
						if(NetworkUtil.IsNetWorkEnable(context)){
							switch (NetworkUtil.getCurrentNetworkType(context)) {
							case 0:
								MengxiUtil.showNetwoekTips(context);
								break;
							case 100:
								break;
							case 2:
							case 3:
							case 4:
//								Toast.makeText(context, "网络切换为数据流量，请注意使用！", Toast.LENGTH_LONG).show();
								break;
							}
						}else{
//							MengxiUtil.showNetwoekTips(context);
							Toast.makeText(context, "网络连接失败！", Toast.LENGTH_LONG).show();
						}
					}
	}

}
