package demo.com.test;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyImageView myImageView = (MyImageView) findViewById(R.id.img);

        myImageView.setOnShowListener(new MyImageView.OnShowListener() {
            @Override
            public void onShow(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        });

    }

}
