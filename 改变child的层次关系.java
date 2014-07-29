package com.joejoe.zipimage.zipimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class Circle extends View {

    private int color, x, y;
    private Context context;
    Paint paint;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Circle(Context ctx, int color, int x, int y) {
        super(ctx);
        this.color = color;
        this.context = ctx;
        this.x = x;
        this.y = y;
        paint = new Paint();
        this.setBackgroundColor(Color.YELLOW);

        // 自定义view的大小默认是撑满全屏的
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(120, 120);
        this.setLayoutParams(layoutParams);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // set view index
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            RelativeLayout root = (RelativeLayout) Circle.this.getParent();
            int count = root.getChildCount();
            root.removeView(this);
            root.addView(Circle.this, count - 1);
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("view size", Circle.this.getWidth() + "====" + Circle.this.getHeight());
            this.setX(event.getRawX() - 60);
            this.setY(event.getRawY() - 60);
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setColor(this.color);
        canvas.drawCircle(x, y, 60, paint);
        invalidate();

    }
}
