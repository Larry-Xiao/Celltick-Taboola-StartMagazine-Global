package com.celltick.apac.news.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.progresswebview.ProgressWebview;
import com.celltick.apac.news.util.ShareUtils;
import com.celltick.apac.news.widget.SmartSimpleDraweeView;

/**
 * Created by Larryx on 5/27/2018.
 */

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";


    protected ProgressWebview mWebView;
    private Toolbar mToolbar;
    private SmartSimpleDraweeView mCPLogo;
    private String url;
    private String mArticleTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_webview);
        mWebView = (ProgressWebview) findViewById(R.id.baseweb_webview);
        mWebView.setBackgroundColor(getResources().getColor(R.color.bg_webview));
        mToolbar = (Toolbar)findViewById(R.id.web_toolbar);
        mCPLogo = findViewById(R.id.web_cp_logo);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        mArticleTitle = intent.getStringExtra("article_title");
        String subTitle = intent.getStringExtra("title");
        String cpLogo = intent.getStringExtra("cp-logo");
        mCPLogo.setImageURI(Uri.parse(cpLogo));
        Log.d(TAG,"CPLOGO = "+cpLogo);

        initToolbar(subTitle,cpLogo);

        if(url!=null){
            mWebView.loadUrl(url);
        }

    }

    protected void initToolbar(String subTitle,String cpLogo) {
        if (cpLogo.equals("") || cpLogo==null) {
            mToolbar.setTitle(subTitle);
        } else {
            mToolbar.setTitle("");
        }
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.web_favor) {

        } else if (id == R.id.web_share) {
            String share_content = mArticleTitle+"\n\n"+url+"\n\n"+"From: Start Magazine";
            ShareUtils.shareText(this,share_content);

        }

        return super.onOptionsItemSelected(item);
    }
    private void keepSameColorOfStatusBarAndToolbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //获取样式中的属性值
            TypedValue typedValue = new TypedValue();
            this.getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
            int[] attribute = new int[] { android.R.attr.colorPrimary };
            TypedArray array = this.obtainStyledAttributes(typedValue.resourceId, attribute);
            int color = array.getColor(0, Color.TRANSPARENT);
            array.recycle();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                window.setStatusBarColor(color);
            }

        }
        //解决状态栏和toolbar重叠  toolbar被顶上去只显示一部分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//设置固定状态栏常驻，不覆盖app布局
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));//设置状态栏颜色
        }
    }


}
