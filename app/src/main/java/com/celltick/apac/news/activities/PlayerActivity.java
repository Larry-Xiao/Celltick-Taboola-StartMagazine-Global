package com.celltick.apac.news.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.celltick.apac.news.R;

/**
 * Created by Larryx on 5/27/2018.
 */

public class PlayerActivity extends AppCompatActivity {

    protected WebView mWebView;
    // 用来显示视频的布局
    private FrameLayout mLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_player);

        mWebView = (WebView) findViewById(R.id.webview_player);
        mLayout = (FrameLayout) findViewById(R.id.fl_video);

        initWebView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if(url!=null){
            mWebView.loadUrl(url);
        }
    }

    /**
     * 设置webView 相关属性
     */

    private void initWebView() {
        WebSettings setting= mWebView.getSettings();
        setting.setJavaScriptEnabled(true);// 设置支持javascript脚本
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存模式
        mWebView.setVerticalScrollBarEnabled(false); // 取消Vertical ScrollBar显示
        mWebView.setHorizontalScrollBarEnabled(false); // 取消Horizontal ScrollBar显示
        //设置自适应屏幕，两者合用
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        setting.setAllowFileAccess(true);// 允许访问文件
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setFocusable(false); // 去掉超链接的外边框
        setting.setDefaultTextEncodingName("GBK");//设置文本编码（根据页面要求设置）
        /**
         * Make sure to apply this on the webview settings.
         * Required for auto play without user gesture in some scenarios.
         */
        setting.setMediaPlaybackRequiresUserGesture(false);
        mWebView.setWebChromeClient(new MyWebChromeClient());

    }

    private class MyWebChromeClient extends WebChromeClient {

        private CustomViewCallback mCustomViewCallback;
        //  横屏时，显示视频的view
        private View mCustomView;

        // 点击全屏按钮时，调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

            //如果view 已经存在，则隐藏
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomView = view;
            mCustomView.setVisibility(VISIBLE);
            mCustomViewCallback = callback;
            mLayout.addView(mCustomView);
            mLayout.setVisibility(VISIBLE);
            mLayout.bringToFront();

            //设置横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // 取消全屏调用的方法
        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(GONE);
            mLayout.removeView(mCustomView);
            mCustomView = null;
            mLayout.setVisibility(GONE);
            try {
                mCustomViewCallback.onCustomViewHidden();
            } catch (Exception e) {
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        }
    }
    /**
     * 横竖屏切换监听
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
    }

}
