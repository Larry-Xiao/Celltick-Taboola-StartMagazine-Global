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

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.adapter.CricketSingleScheduleListAdapter;
import com.celltick.apac.news.model.CricketInfo;
import com.celltick.apac.news.threads.CricketCurrentScheduleRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larryx on 5/27/2018.
 */

public class CricketScheduleListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = CricketScheduleListActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private ProgressBar mProgress;
    private TextView cricket_news_request_error;
    private String mStartDate;
    private String mEndDate;
    private String mCompetition;
    private List<CricketInfo> cricketGameList =new ArrayList<>();
    private CricketScheduleListHandler handler;
    private JsonParseTool mJsonTool;
    private RecyclerView mCricketScheduleListRecy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_cricket_schedule_list);
        mToolbar = (Toolbar)findViewById(R.id.cricket_news_activity_toolbar);

        mStartDate = getIntent().getStringExtra("start_date");
        mEndDate = getIntent().getStringExtra("end_date");
        mCompetition = getIntent().getStringExtra("competition");
        initToolbar(mCompetition);

        mProgress = findViewById(R.id.cricket_schedule_loading_progress);
        mCricketScheduleListRecy = findViewById(R.id.cricket_schedule_list);
        cricket_news_request_error = findViewById(R.id.cricket_schedule_request_error);

        mProgress.setVisibility(View.VISIBLE);
        mCricketScheduleListRecy.setVisibility(View.GONE);
        cricket_news_request_error.setVisibility(View.GONE);

        mJsonTool = new JsonParseTool();
        handler = new CricketScheduleListHandler();

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCricketScheduleListRecy.setLayoutManager(layoutManager);

        CricketCurrentScheduleRequestThread thread = new CricketCurrentScheduleRequestThread(handler,mStartDate,mEndDate,mCompetition);
        thread.start();

    }

    protected void initToolbar(String title) {
        mToolbar.setTitle(title + " Schedules");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CricketScheduleListActivity.this.finish();
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

    }


    public class CricketScheduleListHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            switch (msg.what) {
                case Constant.CRICKET_SUCCESS:
                    Log.d(TAG,"cricket schedule list string: "+ msgStr);
                    mProgress.setVisibility(View.GONE);
                    mCricketScheduleListRecy.setVisibility(View.VISIBLE);
                    cricket_news_request_error.setVisibility(View.GONE);

                    cricketGameList = mJsonTool.parseSportCricketJson(msgStr);
                    if (cricketGameList.isEmpty()){
                        mProgress.setVisibility(View.GONE);
                        mCricketScheduleListRecy.setVisibility(View.GONE);
                        cricket_news_request_error.setVisibility(View.VISIBLE);
                        cricket_news_request_error.setText("no data");
                    }
                    CricketSingleScheduleListAdapter adapter = new CricketSingleScheduleListAdapter(cricketGameList);
                    adapter.setHasStableIds(true);
                    mCricketScheduleListRecy.setAdapter(adapter);

                    adapter.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(int position) {
                            CricketInfo cricket_item = cricketGameList.get(position);
                            startWebActivity(cricket_item.getGamePageURL());
                        }
                    });

                    break;

                case Constant.ERR_RETURN:
                    break;
                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:
                    mProgress.setVisibility(View.GONE);
                    mCricketScheduleListRecy.setVisibility(View.GONE);
                    cricket_news_request_error.setVisibility(View.VISIBLE);
                    break;
                case Constant.ERR_BANNER_DISMISS:
                    break;
            }
        }
    }

    private void startWebActivity(String packages){
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", packages);
        intent.putExtra("title", "");
        intent.putExtra("article_title", "");
        intent.putExtra("cp-logo", "");
        startActivity(intent);

    }


}
