package com.joejoe.zipimage.zipimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.ViewGroup;


public class Rect extends ViewGroup {

    Paint paint;
    int width, height;

    public Rect(Context context) {

        super(context);

        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0xffffff00);

        // 触发重绘
        setWillNotDraw(false);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int length = width / 10 + 1;
        int bound = height / 20;
        for (int i = 0; i < bound; i++) {
            for (int j = 0; j < length; j++) {
                canvas.drawLine(j * length, 0, j * length, height, paint);
                canvas.drawLine(0, i * bound, width, i * bound, paint);

            }

        }
    }


}
