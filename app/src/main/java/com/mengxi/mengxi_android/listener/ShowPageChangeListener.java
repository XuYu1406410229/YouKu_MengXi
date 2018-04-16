package com.mengxi.mengxi_android.listener;


import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mengxi.mengxi_android.R;

/**
 * 该文件暂时不用
 * */
public class ShowPageChangeListener implements OnPageChangeListener {
	
	private ImageView[] dots;
	private WebView indexWeb;
	private LinearLayout footlayout;
	
	public ShowPageChangeListener(ImageView[] dots){
		this.dots=dots;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
/*		for (int i = 0; i < dots.length; i++) {
			if(i==arg0){
				dots[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				dots[i].setBackgroundResource(R.drawable.page_indicator);
			}
			
		}*/

		if(arg0==2) {
			footlayout.setVisibility(View.GONE);
		}else{
			footlayout.setVisibility(View.GONE);
		}
	}


}
