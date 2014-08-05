package com.joejoe.myviewflipper.viewfilppertest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    ViewFlipper viewFlipper;

    Animation leftIn, leftOut, rightIn, rightOut;

    /**
     * flipper的子项
     */
    RelativeLayout subView, subView2, subView3;

    ArrayList<String> arrayList = new ArrayList<String>();

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初使化一些测试数据
        arrayList.add("item1");
        arrayList.add("item2");
        arrayList.add("item3");


        leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);

        rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);


        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);


        subView = (RelativeLayout) getLayoutInflater().inflate(R.layout.details, null);
        subView2 = (RelativeLayout) getLayoutInflater().inflate(R.layout.details2, null);
        subView3 = (RelativeLayout) getLayoutInflater().inflate(R.layout.details3, null);

        listView = (ListView) getLayoutInflater().inflate(R.layout.list_view, null);

        // 默认加载列表项
        viewFlipper.addView(listView);


        MyAdapter adapter = new MyAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 如果有第2个子对象  则清除掉一个
                if (viewFlipper.getChildAt(1) != null) {
                    viewFlipper.removeViewAt(1);
                }

                if (position == 0) {
                    viewFlipper.addView(subView);
                }

                if (position == 1) {
                    viewFlipper.addView(subView2);
                }
                if (position == 2) {
                    viewFlipper.addView(subView3);
                }

                // 展现动画
                viewFlipper.setInAnimation(leftIn);
                viewFlipper.setOutAnimation(leftOut);
                viewFlipper.showNext();//向右滑动
            }
        });




    }


    /**
     * 点击恢复到正常的状态
     * @param v
     */
    public void fadeBack(View v) {
        viewFlipper.setInAnimation(rightIn);
        viewFlipper.setOutAnimation(rightOut);
        viewFlipper.showPrevious();
    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.item, null);
            TextView txt = (TextView) view.findViewById(R.id.title);
            txt.setText(arrayList.get(position));
            return view;


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
