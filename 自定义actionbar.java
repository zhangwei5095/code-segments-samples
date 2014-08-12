

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**

 *
 * @author joejoe
 */

public class MainActivity extends ActionBarActivity {



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 自定义actionbar
        ActionBar actionBar = getActionBar();
		
        actionBar.setDisplayShowCustomEnabled(true);
		
		// 隐藏应用的图标
        actionBar.setDisplayShowHomeEnabled(false);

        actionBar.setCustomView(R.layout.actionbar);

        View v = actionBar.getCustomView();

        v.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "btn1...", Toast.LENGTH_LONG).show();
            }
        });

        v.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "btn2...", Toast.LENGTH_LONG).show();
            }
        });

        TextView tv = (TextView) v.findViewById(R.id.txt1);
        tv.setText("change title....");

        findViews();

    }

    private void findViews() {


    }


}
