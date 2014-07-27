package com.joejoe.zipimage.zipimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.joejoe.zipimage.zipimage.R;

import java.io.IOException;
import java.io.InputStream;

public class GetBitampFromAssets extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bitamp_for_assets);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.root);

        Bitmap bm = null;
        try {
            InputStream stream = getAssets().open("ee.jpg");
            BitmapFactory.Options opt = new BitmapFactory.Options();
            //opt.inJustDecodeBounds = true;
            opt.inSampleSize = 6;
            bm = BitmapFactory.decodeStream(stream, null, opt);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bm);
        layout.addView(imageView);
    }


}
