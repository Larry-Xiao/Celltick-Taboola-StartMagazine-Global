package com.celltick.apac.news.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.adapter.SearchAdapter;
import com.celltick.apac.news.model.ArticleBean;
import com.celltick.apac.news.threads.SearchArticleRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larryx on 5/27/2018.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SearchActivity";

    private LinearLayout mSearchOrigView;
    private FrameLayout mSearchListView;
    private Toolbar mToolbar;
    private ProgressBar mProgress;
    private RecyclerView mSearchListRcy;
    private SearchAdapter mAdapter;
    //新闻列表数据
    private List<ArticleBean> mArticleList;

    private JsonParseTool mJsonTool;
    private SearchHandler handler;

    private TextView mXi;
    private TextView mTru;
    private TextView mHuawei;
    private TextView mNBA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar)findViewById(R.id.cricket_news_activity_toolbar);
        initToolbar();

        mSearchOrigView = findViewById(R.id.cricket_news_activity_orig_container);
        mSearchListView = findViewById(R.id.cricket_news_list_container);
        mProgress = findViewById(R.id.cricket_news_loading_progress);
        mSearchListRcy = findViewById(R.id.cricket_news_list);

        mXi = findViewById(R.id.Xi);
        mTru = findViewById(R.id.Tru);
        mHuawei = findViewById(R.id.Huawei);
        mNBA = findViewById(R.id.nba);
        mXi.setOnClickListener(this);
        mTru.setOnClickListener(this);
        mHuawei.setOnClickListener(this);
        mNBA.setOnClickListener(this);



        mArticleList = new ArrayList<>();
        mJsonTool = new JsonParseTool();
        handler = new SearchHandler();

        mAdapter = new SearchAdapter(SearchActivity.this, mArticleList);
        mSearchListRcy.setAdapter(mAdapter);
        mSearchListRcy.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                ArticleBean articleItem = mArticleList.get(position);
                Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                String template = SharedPreferencesUtils.getInstance().getString(Constant.TEMPLATE);
                Log.d(TAG,"template = "+template);
                if(template == null || template.equals("") || template.equals("default")){
                    intent.putExtra("url", articleItem.getArticleURL());
                    Log.d(TAG,"url = "+articleItem.getArticleURL());
                } else {
                    intent.putExtra("url", articleItem.getArticleURL()+"&theme="+template);
                    Log.d(TAG,"url = "+articleItem.getArticleURL()+"&theme="+template);
                }

                intent.putExtra("title", articleItem.getPublisherName());
                intent.putExtra("article_title", articleItem.getTitle());
                intent.putExtra("cp-logo", articleItem.getCpLogoURL());
                startActivity(intent);
            }
        });


    }

    protected void initToolbar() {
        mToolbar.setTitle("Search News...");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG,query);
                Toast.makeText(SearchActivity.this,query,Toast.LENGTH_SHORT).show();

                mSearchOrigView.setVisibility(View.GONE);
                mSearchListView.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                mSearchListRcy.setVisibility(View.GONE);

                SearchArticleRequestThread thread = new SearchArticleRequestThread(query,handler);
                thread.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchOrigView.setVisibility(View.VISIBLE);
                mSearchListView.setVisibility(View.GONE);
                return false;
            }
        });
        return true;
//        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_search) {

        } else if (id == R.id.web_share) {

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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        mSearchOrigView.setVisibility(View.GONE);
        mSearchListView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);
        mSearchListRcy.setVisibility(View.GONE);

        switch (id){
            case R.id.Xi:
                SearchArticleRequestThread thread1 = new SearchArticleRequestThread("Q15031",handler);
                thread1.start();
                break;
            case R.id.Tru:
                SearchArticleRequestThread thread2 = new SearchArticleRequestThread("Trump",handler);
                thread2.start();
                break;
            case R.id.Huawei:SearchArticleRequestThread thread3 = new SearchArticleRequestThread("Huawei",handler);
                thread3.start();
                break;
            case R.id.nba:
                SearchArticleRequestThread thread4 = new SearchArticleRequestThread("NBA",handler);
                thread4.start();
                break;
        }

    }

    public class SearchHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            switch (msg.what) {
                case Constant.SUCCESS:
                    Log.d(TAG,"search string: "+ msgStr);

                    mProgress.setVisibility(View.GONE);
                    mSearchListRcy.setVisibility(View.VISIBLE);

                    List<ArticleBean> articleList = mJsonTool.parseStartMagazineJson(msgStr);
                    mAdapter.clearList();
                    mAdapter.addHeaderItem(articleList);

                    break;

                case Constant.ERR_RETURN:
                    break;
                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:

                    break;
                case Constant.ERR_BANNER_DISMISS:
                    break;
            }
        }
    }


}
