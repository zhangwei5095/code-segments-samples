package com.joejoe.notificationtest.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RemoteViews;


public class MainActivity extends ActionBarActivity {


    Notification nm;
    NotificationManager nmManager;
    RemoteViews contentView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        contentView = new RemoteViews(getPackageName(), R.layout.remote);

        nm = new Notification();
        nm.icon = R.drawable.ic_launcher;
        nm.tickerText = "这是一个测试通知的news";
        nm.defaults = Notification.DEFAULT_SOUND;
        nm.when = System.currentTimeMillis();
        nmManager = (NotificationManager) getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
        nm.contentView = contentView;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmManager.notify(0, nm);
            }
        });
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
