package com.joejoe.notificationtest.notificationtest;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopWindowTest extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window_test);

       // Configuration cfg = getResources().getConfiguration();

        View pop = getLayoutInflater().inflate(R.layout.remote, null);
        final PopupWindow popWin = new PopupWindow(pop, 400,300);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popWin.showAsDropDown(v);
                popWin.showAtLocation(v, Gravity.CENTER, 20, 20);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopWindowTest.this, "hellll.", Toast.LENGTH_LONG).show();
            }
        });

        pop.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.dismiss();
            }
        });

    }



}
