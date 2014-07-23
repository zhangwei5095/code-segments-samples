package com.joejoe.mywebview.mywebview;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 404) {
                new AlertDialog.Builder(MainActivity.this).setTitle("重新加载")
                        .setMessage("重新加载?????")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();


            }
        }
    };


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        myFragMent myFragMent = new myFragMent(this, handler);
        transaction.add(R.id.root, myFragMent);
        transaction.commit();

        //检测网络
        if (!isNetWorkAvailable(this.getBaseContext())) {
            Toast.makeText(this, "您的网络已断开", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isNetWorkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {

                for (NetworkInfo networkInfo : info) {

                    if (networkInfo.isConnected() && networkInfo.isAvailable()) {

                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.ACTION_DOWN:

                break;
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(MainActivity.this).setTitle("exit")
                        .setMessage("确认退出?")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


}
