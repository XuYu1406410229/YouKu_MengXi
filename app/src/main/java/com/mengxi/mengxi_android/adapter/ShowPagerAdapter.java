package com.mengxi.mengxi_android.adapter;

import java.util.List;
import com.mengxi.mengxi_android.R;
import com.mengxi.mengxi_android.util.MengxiUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowPagerAdapter extends PagerAdapter {
	private List<View> views;
	private Context context;
	
	public ShowPagerAdapter(List<View> views,Context context){
		this.views=views;
		this.context=context;
	}

	@Override
	public int getCount() {
		if(views!=null){
			return views.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0==arg1);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(views.get(position));
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(views.get(position), 0);
		if(position==views.size()-1){
			Button button=(Button)container.findViewById(R.id.goIndexBut);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					goIndex();
				}
			});
		}
		
		return views.get(position);
	}

	
	private void goIndex(){
		MengxiUtil.sharedPreferences(context, MengxiUtil.ISFIRSTIN_KEY, MengxiUtil.ISFIRSTIN_VALUE);
		Intent intent=new Intent("com.mengxi.mengxi_android.Index");
		 context.startActivity(intent);
		 ((Activity)context).finish();
	}
	
}
