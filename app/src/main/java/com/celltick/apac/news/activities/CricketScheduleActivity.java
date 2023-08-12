package com.celltick.apac.news.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.datepicker.CustomDatePicker;
import com.celltick.apac.news.datepicker.DateFormatUtils;
import com.celltick.apac.news.dialog.CricketLeagueDialog;

/**
 * Created by Larryx on 5/27/2018.
 */

public class CricketScheduleActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CricketScheduleActivity";

    private Toolbar mToolbar;
    private TextView mStartDate, mEndDate,mLeagueName;
    private CustomDatePicker mStartDatePicker, mEndDatePicker;
    private CricketLeagueDialog mCktDlg;
    private String mStrLeagueName = "CWC";
    private Button mCktSchlListReqBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_cricket_schedules);
        mToolbar = (Toolbar)findViewById(R.id.cricket_news_activity_toolbar);
        initToolbar();

        findViewById(R.id.ll_cricket_start_date).setOnClickListener(this);
        mStartDate = (TextView) findViewById(R.id.tv_selected_start_date);
        findViewById(R.id.ll_cricket_end_date).setOnClickListener(this);
        mEndDate = (TextView) findViewById(R.id.tv_selected_end_date);
        initDatePicker();

        findViewById(R.id.ll_cricket_leagues).setOnClickListener(this);
        mLeagueName = (TextView) findViewById(R.id.tv_selected_league);

        mCktDlg = new CricketLeagueDialog(this, new CricketLeagueDialog.Callback() {
            @Override
            public void onCricketLeagueSelected(String league_name) {
                mStrLeagueName = league_name;
                mLeagueName.setText(league_name);
            }
        });
        mCktDlg.setCancelable(false);

        mCktSchlListReqBtn = (Button) findViewById(R.id.btn_ckt_schdl_req);
        mCktSchlListReqBtn.setOnClickListener(this);


    }

    protected void initToolbar() {
        mToolbar.setTitle("Cricket Schedules");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(com.afollestad.materialdialogs.R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CricketScheduleActivity.this.finish();
            }
        });

    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        mStartDate.setText(DateFormatUtils.long2Str(endTimestamp, false));
        mEndDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mStartDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mStartDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        mEndDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mEndDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);

        // 不允许点击屏幕或物理返回键关闭
        mStartDatePicker.setCancelable(false);
        mEndDatePicker.setCancelable(false);
        // 不显示时和分
        mStartDatePicker.setCanShowPreciseTime(false);
        mEndDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mStartDatePicker.setScrollLoop(false);
        mEndDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mStartDatePicker.setCanShowAnim(false);
        mEndDatePicker.setCanShowAnim(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        switch (v.getId()) {
            case R.id.ll_cricket_start_date:
                // 日期格式为yyyy-MM-dd
                mStartDatePicker.show(mStartDate.getText().toString());
                break;

            case R.id.ll_cricket_end_date:
                // 日期格式为yyyy-MM-dd HH:mm
                mEndDatePicker.show(mEndDate.getText().toString());
                break;

            case R.id.ll_cricket_leagues:
                mCktDlg.show("");
                break;
            case R.id.btn_ckt_schdl_req:
                Intent intent = new Intent(this, CricketScheduleListActivity.class);
                intent.putExtra("competition", mStrLeagueName);
                intent.putExtra("start_date", mStartDate.getText().toString());
                intent.putExtra("end_date", mEndDate.getText().toString());
                startActivity(intent);
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStartDatePicker.onDestroy();
        mEndDatePicker.onDestroy();
    }


}
