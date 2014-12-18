package demo.com.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by joejoe on 2014/12/13.
 * 自定义一个事件监听器
 *
 */
public class MyImageView extends ImageView implements View.OnClickListener{

    private OnShowListener onShowListener;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public void setOnShowListener(OnShowListener listener) {
        this.onShowListener = listener;
    }

    @Override
    public void onClick(View v) {
        onShowListener.onShow("hello");
    }

    public static interface OnShowListener {
        void onShow(String str);
    }

}
