package com.joejoe.layouttest.layoutapp;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SplashActivity extends Activity {

    private ViewPager viewPager;


    List<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);


        views.add(new MyViews());
        views.add(new MyViews());
        views.add(new MyViews());

        MyPagerAdapter adapter = new MyPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);



        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.d("onPageScrolled", "onPageScrolled");

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.d("onPageScrollStateChanged", "onPageScrollStateChanged");
            }
        });



    }

    /**
     * view pager适配器
     *
     * @author yaguang.wang
     */
    private class MyPagerAdapter extends PagerAdapter {
        private List<View> lists;


        public MyPagerAdapter(List<View> lists) {
            super();
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //实例化页面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = lists.get(position);
            container.addView(view);
            return view;
        }

        //删除页面
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

    }

}
