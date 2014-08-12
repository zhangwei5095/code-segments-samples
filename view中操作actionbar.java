

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MyLinearLayout_back extends LinearLayout {


    Context ctx;
    MainActivity activity;

    public MyLinearLayout_back(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        activity = (MainActivity) ctx;

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        LinearLayout actionView = (LinearLayout) activity.getActionBar().getCustomView();


        TextView left = (TextView) actionView.getChildAt(0);
        TextView center = (TextView) actionView.getChildAt(1);
        TextView right = (TextView) actionView.getChildAt(2);

        center.setText("aaaaa");

        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.dynamicFragment.fadeBack(v);
            }
        });


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        activity = (MainActivity) ctx;

        View v = ((MainActivity) ctx).getLayoutInflater().inflate(R.layout.actionbar2, null);

        activity.getActionBar().setCustomView(v);
    }
}
