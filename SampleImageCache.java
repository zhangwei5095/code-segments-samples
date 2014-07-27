package com.joejoe.gridviewphotoswall.photoswall;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @author  joejoeZ
 */

public class GridViewAdapter extends ArrayAdapter<String> {

    // 任务列表
    private Set<AsyncImageTask> taskSet;

    // 图片缓存类
    private LruCache<String, Bitmap> lruCache;

   // 传过来的gridview
    private GridView gridViewWall;

    // 第一张可见图的下标
    private int firstVisibleItem;

   // 一屏有多少张图片可见
    private int visibleItemCount;

    // 记录是否刚打开应用
    private boolean isFirstOpen = true;

    // 用httpClient请求资源
    private HttpClient httpClient;


    public GridViewAdapter(Context context, int textViewResourceId, String[] objects, GridView gridViewWall) {

        super(context, textViewResourceId, objects);
        this.gridViewWall = gridViewWall;
        setBitmapMemoryCache();
        taskSet = new HashSet<AsyncImageTask>();

        bindscrollEvent();
    }

    private void bindscrollEvent() {

        gridViewWall.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 滑动完成请求图片
                if (scrollState == SCROLL_STATE_IDLE) {
                    loadBitmaps(firstVisibleItem, visibleItemCount);
                } else {
                    cancelDownloadTasks();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                GridViewAdapter.this.firstVisibleItem = firstVisibleItem;
                GridViewAdapter.this.visibleItemCount = visibleItemCount;
                // 首次登录请求图片
                if (isFirstOpen && visibleItemCount > 0) {
                    loadBitmaps(firstVisibleItem, visibleItemCount);
                    isFirstOpen = false;
                }
            }
        });


    }

    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        try {
            for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
                String imageUrl = ImagePathList.imageURLs[i];
                Bitmap bitmap = getBitMapFromCache(imageUrl);
                // 如果缓存里没有
                if (bitmap == null) {
                    AsyncImageTask task = new AsyncImageTask();
                    taskSet.add(task);
                    task.execute(imageUrl);
                } else {
                    ImageView imageView = (ImageView) gridViewWall.findViewWithTag(imageUrl);
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
        } else {
            view = convertView;
        }
        final ImageView photo = (ImageView) view.findViewById(R.id.photo);
        // 设置标识tag
        photo.setTag(url);
        setImageView(url, photo);
        return view;
    }

    private void setImageView(String url, ImageView photo) {

        Bitmap bitmap = getBitMapFromCache(url);

        if (bitmap != null) {
            photo.setImageBitmap(bitmap);
        } else {
            photo.setImageResource(R.drawable.waiting);
        }
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {

        if (getBitMapFromCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    private Bitmap getBitMapFromCache(String key) {
        return lruCache.get(key);
    }

    private void setBitmapMemoryCache() {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize) {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    class AsyncImageTask extends AsyncTask<String, Integer, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadBitmap(params[0]);
            if (bitmap != null) {
                addBitmapToCache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 找到指定tag的view
            ImageView imageView = (ImageView) gridViewWall.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskSet.remove(this);
        }

        /**
         * @param imageUrl 图片的URL地址
         * @return Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            try {
                HttpGet request = new HttpGet(imageUrl);
                BasicHttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, 300);
                HttpConnectionParams.setSoTimeout(httpParameters, 5000);
                httpClient = new DefaultHttpClient(httpParameters);
                HttpResponse response = httpClient.execute(request);

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) { }

    }

    // 取消正在下载的任务
    public void cancelDownloadTasks() {
        if (taskSet != null) {
            for (AsyncImageTask task : taskSet) {
                task.cancel(false);
            }
        }
    }

}

