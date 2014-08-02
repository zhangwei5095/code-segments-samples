package com.joejoe.layouttest.layoutapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;


/**
 * Created by joejoe on 2014/7/29.
 */
public class WeatherAPP extends ActionBarActivity {

    private ListView list_view;
    private RelativeLayout detail;
    Animation leftIn, leftOut, rightIn, rightOut;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        viewFlipper.addView(getLayoutInflater().inflate(R.layout.list_view, null));
        viewFlipper.addView(getLayoutInflater().inflate(R.layout.details, null));



        leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);

        rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);



    }



    private void showWeathers(ArrayList<WeatherBean> list) {


        MyAdapter adapter = new MyAdapter(list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                viewFlipper.setInAnimation(leftIn);
                viewFlipper.setOutAnimation(leftOut);
                viewFlipper.showNext();
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        ArrayList<WeatherBean> list = null;

        public MyAdapter(ArrayList<WeatherBean> ls) {
            super();
            this.list = ls;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item, null);
            } else {
                view = convertView;
            }


            return view;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }
    }


}
