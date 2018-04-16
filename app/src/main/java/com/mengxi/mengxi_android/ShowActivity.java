package com.mengxi.mengxi_android;

import java.util.ArrayList;
import java.util.List;

import com.mengxi.mengxi_android.adapter.ShowPagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class ShowActivity extends Activity {
    private List<View> views = null;//页面List
    private ViewPager viewPager = null; //加载页面用的ViewPager
    //	private ImageView [] dots=null;//底部小点图标
    private LayoutInflater inflater = null;//加载页面
//	private ImageView imageView=null;//加载小图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
    }
    private void initViews() { //初始化需要显示的页面
        LayoutInflater  inflater = LayoutInflater.from(this);
        ArrayList<View>  views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.show_layout5, null));
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        viewPager.setAdapter(new ShowPagerAdapter(views, this));
//		initDots();
//		viewPager.setOnPageChangeListener(new ShowPageChangeListener(dots));
    }

//	private void initDots(){
//		LinearLayout layout=(LinearLayout)findViewById(R.id.showPage);
//		dots=new ImageView[views.size()];
//		for(int i=0;i<dots.length;i++){
//			imageView=new ImageView(this);
//			imageView.setLayoutParams(new LayoutParams(20, 20));
//			imageView.setPadding(20, 0, 20, 0);
//			if(i==0){
//				imageView.setBackgroundResource(R.drawable.page_indicator_focused);
//			}else{
//				imageView.setBackgroundResource(R.drawable.page_indicator);
//			}
//			dots[i]=imageView;
//			layout.addView(dots[i]);
//		}
//	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.show, menu);
        return true;
    }

}
