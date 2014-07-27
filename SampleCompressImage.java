package com.joejoe.zipimage.zipimage;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 示例压缩图片
 */

public class SampleCompressImage extends ActionBarActivity implements View.OnTouchListener {

    ImageView imageView;
    static int screenWid, screenHei;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        //获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWid = dm.widthPixels;
        screenHei = dm.heightPixels;

        imageView = new ImageView(this);
        Bitmap bb = BitmapFactory.decodeResource(getResources(), R.drawable.mm);
        Log.d("压缩前", bb.getWidth() + "=======>" + bb.getHeight() + "大小===>" + bb.getByteCount());
        Bitmap bm = compressBitmap(getResources(), R.drawable.mm, screenWid, screenHei);
        Log.d("压缩后", bm.getWidth() + "=======>" + bm.getHeight() + "大小===>" + bm.getByteCount());


        imageView.setImageBitmap(bm);
        root.addView(imageView);
        root.setOnTouchListener(this);
    }

    public static int getSampleSize(BitmapFactory.Options options,
                                    int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round(height / reqHeight);
            final int widthRatio = Math.round(width / reqWidth);
            inSampleSize = Math.round((heightRatio + widthRatio) / 2);
        }
        return inSampleSize;
    }

    public static Bitmap compressBitmap(Resources res, int resId,
                                        int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = getSampleSize(options, screenWid, screenHei);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("move", event.getX() + "===" + event.getY() + "raw" + event.getRawX() + "raw" + event.getRawY());
                imageView.setX(event.getRawX() - imageView.getWidth() / 2);
                imageView.setY(event.getRawY() - imageView.getHeight() / 2);
                break;
        }

        return true;
    }
}
