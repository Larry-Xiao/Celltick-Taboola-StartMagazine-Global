package com.celltick.apac.news.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.adapter.CricketVideoActivityAdapter;
import com.celltick.apac.news.model.VideoBean;
import com.celltick.apac.news.recyclerview_pkg.EndlessRecyclerOnScrollListener;
import com.celltick.apac.news.recyclerview_pkg.HeaderAndFooterRecyclerViewAdapter;
import com.celltick.apac.news.recyclerview_pkg.utils.RecyclerViewStateUtils;
import com.celltick.apac.news.recyclerview_pkg.weight.LoadingFooter;
import com.celltick.apac.news.threads.CricketVideoRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.NetworkUtil;
import com.celltick.apac.news.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larryx on 5/27/2018.
 */

public class CricketVideoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CricketNewsActivity";

    private Toolbar mToolbar;
    private ProgressBar mProgress;
    private RecyclerView mCricketNewsRcy;
    private CricketVideoActivityAdapter mAdapter;
    //新闻列表数据
    private List<VideoBean> mVideoList;

    private JsonParseTool mJsonTool;
    private CricketVideoHandler handler;
    private CricketVideoRequestThread mCricketVideoReqThrd;
    private TextView cricket_news_request_error;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_cricket_video);
        mToolbar = (Toolbar)findViewById(R.id.cricket_news_activity_toolbar);
        initToolbar();

        mProgress = findViewById(R.id.cricket_video_loading_progress);
        mCricketNewsRcy = findViewById(R.id.cricket_video_list);
        cricket_news_request_error = findViewById(R.id.cricket_video_request_error);

        mProgress.setVisibility(View.VISIBLE);
        mCricketNewsRcy.setVisibility(View.GONE);
        cricket_news_request_error.setVisibility(View.GONE);

        mVideoList = new ArrayList<>();
        mJsonTool = new JsonParseTool();
        handler = new CricketVideoHandler();

        mAdapter = new CricketVideoActivityAdapter(mVideoList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mCricketNewsRcy.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mCricketNewsRcy.setLayoutManager(new LinearLayoutManager(CricketVideoActivity.this));
        mCricketNewsRcy.addOnScrollListener(mOnScrollListener);

        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                VideoBean videoItem = mVideoList.get(position);
                Intent intent = new Intent(CricketVideoActivity.this, PlayerActivity.class);
                intent.putExtra("url", videoItem.getmVideoURL());
                startActivity(intent);
            }
        });

        if (NetworkUtil.isNetworkAvailable()){
            offset = 0;
            mCricketVideoReqThrd = new CricketVideoRequestThread(handler,offset);
            mCricketVideoReqThrd.start();
        } else {
            mProgress.setVisibility(View.GONE);
            mCricketNewsRcy.setVisibility(View.GONE);
            cricket_news_request_error.setVisibility(View.VISIBLE);
        }


    }

    protected void initToolbar() {
        mToolbar.setTitle("Cricket Video");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CricketVideoActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        mProgress.setVisibility(View.VISIBLE);
        mCricketNewsRcy.setVisibility(View.GONE);


    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mCricketNewsRcy);
            if(state == LoadingFooter.State.Loading) {
                return;
            }
            RecyclerViewStateUtils.setFooterViewState(CricketVideoActivity.this, mCricketNewsRcy, 0, LoadingFooter.State.Loading, null);
            if (NetworkUtil.isNetworkAvailable()) {
                CricketVideoRequestThread thread = new CricketVideoRequestThread(handler,offset);
                thread.start();
            } else {
                RecyclerViewStateUtils.setFooterViewState(mCricketNewsRcy, LoadingFooter.State.Normal);
                Toast.makeText(CricketVideoActivity.this,"no connection now!",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoadTopPage(View view) {
            super.onLoadTopPage(view);
        }
    };

    public class CricketVideoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            switch (msg.what) {
                case Constant.SUCCESS:
                    Log.d(TAG,"cricket video string: "+ msgStr);
                    offset = offset+10;
                    mProgress.setVisibility(View.GONE);
                    mCricketNewsRcy.setVisibility(View.VISIBLE);
                    cricket_news_request_error.setVisibility(View.GONE);

                    List<VideoBean> videoList = mJsonTool.parseVideoJson(msgStr);
                    mAdapter.addFooterItem(videoList);
                    RecyclerViewStateUtils.setFooterViewState(mCricketNewsRcy, LoadingFooter.State.Normal);

                    break;

                case Constant.ERR_RETURN:
                    break;
                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:
                    mProgress.setVisibility(View.GONE);
                    mCricketNewsRcy.setVisibility(View.GONE);
                    cricket_news_request_error.setVisibility(View.VISIBLE);
                    break;
                case Constant.ERR_BANNER_DISMISS:
                    break;
            }
        }
    }


}
