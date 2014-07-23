package com.joejoe.mywebview.mywebview;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class myFragMent extends Fragment {

    private ProgressDialog dialog;
    private Context context;
    private Handler handler;

    public myFragMent() {
    }

    public myFragMent(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.test, null);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final WebView webView = (WebView) view.findViewById(R.id.web_view);
        initwebViewSetting(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog = ProgressDialog.show(context, "提示", "加载中...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.hide();
            }
        });

        webView.loadUrl("http://www.baidu.com");
    }

    public void initwebViewSetting(WebView webView) {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultFontSize(20);
        webView.setVisibility(View.VISIBLE);
        webView.setBackgroundColor(0);
    }
}
