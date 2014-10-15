package com.joejoe.test.cachetest.cachetest;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private ListView bitmapListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bitmapListView = (ListView) findViewById(R.id.bitmap_list_view);

        final PhotoAdapter photoAdapter = new PhotoAdapter(this, bitmapListView);

        bitmapListView.setAdapter(photoAdapter);


        bitmapListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                photoAdapter.setItemHeight(100);
            }
        });

        bitmapListView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

            }
        });

        bitmapListView.getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {
                Log.d("ttttt", "scroll....................");
            }
        });

    }



}
