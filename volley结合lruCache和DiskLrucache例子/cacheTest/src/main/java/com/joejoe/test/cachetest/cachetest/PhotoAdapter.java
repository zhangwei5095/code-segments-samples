package com.joejoe.test.cachetest.cachetest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import libcore.io.DiskLruCache;

/**
 * Created by joejoe on 2014/10/13.
 */
public class PhotoAdapter extends BaseAdapter {


    private Context context;
    private LruCache<String, Bitmap> bitmapLruCache;
    private DiskLruCache bitmapDiskLruCache;
    private ListView bitmapListView;
    private RequestQueue volley;
    String[] imgUrls = Images.imageThumbUrls;

    /**
     * 记录每个子项的高度。
     */
    private int mItemHeight = 0;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public PhotoAdapter(Context ctx, ListView listView) {

        volley = Volley.newRequestQueue(ctx);

        this.bitmapListView = listView;
        this.context = ctx;


        // 实例化内存缓存对象
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };


        // 获取图片缓存路径
        try {
            File cacheDir = getDiskCacheDir(ctx, "bitmaps");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            // 创建DiskLruCache实例，初始化缓存数据
            bitmapDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(ctx), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // 缓存图片到内存
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmapToCache(String k, Bitmap b) {
        if (getBitmapFromCache(k) == null) {
            bitmapLruCache.put(k, b);
        }
    }

    // 从内存获取图片
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapFromCache(String k) {
        return bitmapLruCache.get(k);
    }


    /**
     * 获取当前应用程序的版本号。
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        // 如果有SD卡 获取sd卡路径
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            // 获取应用内存路径
            cachePath = context.getCacheDir().getPath();
        }
        //  File.separator平台无关的分割符
        return new File(cachePath + File.separator + uniqueName);
    }


    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        notifyDataSetChanged();
    }


    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    @Override
    public int getCount() {
        return imgUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String bitmapUrl = imgUrls[position];

        final ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_launcher);


        Bitmap cacheBitmap = getBitmapFromCache(bitmapUrl);

        // 如果应用缓存中没有bitmap
        if (cacheBitmap == null) {

            Bitmap diskCacheBitmap = null;
            DiskLruCache.Snapshot snapShot = null;

            // 成成MD5唯一key 作为diskCache的key
            final String key = hashKeyForDisk(bitmapUrl);
            // 查找key对应的缓存
            try {
                snapShot = bitmapDiskLruCache.get(key);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 如果硬盘缓存中没有bitmap
            if (snapShot == null) {

                ImageRequest imageRequest = new ImageRequest(
                        bitmapUrl,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                imageView.setImageBitmap(response);
                                imageView.getLayoutParams().height = response.getHeight();

                                // 存入硬盘缓存
                                addBitmapToDiskCache(response, key);

                                // 存入应用缓存
                                addBitmapToCache(bitmapUrl, response);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.ic_launcher);
                    }
                });
                volley.add(imageRequest);

            } else {
                // 从硬盘缓存中读取
                getBitmapFromDiskCache(snapShot, imageView);
            }
        } else {
            // 从应用缓存中读取
            imageView.setImageBitmap(cacheBitmap);
        }

        return imageView;
    }

    // 获取sd卡中和位图缓存
    private void getBitmapFromDiskCache(DiskLruCache.Snapshot snapShot, ImageView imageView) {

        FileInputStream fileInputStream = null;
        FileDescriptor fileDescriptor = null;
        try {
            fileInputStream = (FileInputStream) snapShot.getInputStream(0);
            fileDescriptor = fileInputStream.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = null;
        if (fileDescriptor != null) {
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            imageView.setImageBitmap(bitmap);
        }
    }

    // 缓存加载的位图到sd
    private void addBitmapToDiskCache(Bitmap bitmap, String key) {

        // 存入硬盘缓存
        DiskLruCache.Editor editor = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        ByteArrayOutputStream baos = null;
        try {
            editor = bitmapDiskLruCache.edit(key);

            if (editor != null) {

                OutputStream outputStream = editor.newOutputStream(0);
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

                in = new BufferedInputStream(inputStream, 8 * 1024);
                out = new BufferedOutputStream(outputStream, 8 * 1024);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                editor.commit();
            }

        } catch (IOException e) {
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (baos != null) {
                    baos.close();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }


}
