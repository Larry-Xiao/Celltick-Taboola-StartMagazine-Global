package com.celltick.apac.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.celltick.apac.news.fragments.FeedFragment;
import com.celltick.apac.news.fragments.HomeFragmentContainer;
import com.celltick.apac.news.fragments.SettingsFragment;
import com.celltick.apac.news.fragments.Tab3Fragment;
import com.celltick.apac.news.fragments.VideoFragment;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.NetworkUtil;
import com.celltick.apac.news.util.NightModeHelper;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity
        implements FeedFragment.OnErrReturnListener {
//        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final String savedTab = "savedTab";
    private static final String Log_FILTER = "param";
    public static Activity mActivity;


    private RelativeLayout rlLayout;
    private FrameLayout mHomeContent;
    private RadioGroup mHomeRadioGroup;
    private RadioButton mHomeHomeRb;
    private RadioButton mHomeVideoRb;
    private RadioButton mHomeFunnyRb;
    private RadioButton mHomeSettingsRb;
    static final int NUM_TABS = 3;//一共3个fragment
    private TextView mTxErrNotice;
    private Toolbar mToolbar;
    private boolean mFirstLaunch = false;

    private long mExitTime = 0;
    private long mFreshTime1 = 0;
    private long mFreshTime2 = 0;
    private boolean isNightMode = true;
    private ActionBarDrawerToggle toggle;//代替监听器
    private UiModeManager uiManager;

    private int mThemeId;

    private NightModeHelper mNightModeHelper;

    private Handler hander = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.ERR_BANNER_DISMISS:
                    dismissErrTxtBanner();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_main);
        mActivity = this;
        mFirstLaunch = true;

        initView();

        if (savedInstanceState == null) {
            //默认将第一个RadioButton设为选中
            mHomeHomeRb.performClick();
        } else {
            RadioButton radioButton = (RadioButton) findViewById(savedInstanceState.getInt(savedTab));
            radioButton.performClick();
        }

    }



    /**
       控制toolbar的menu显示与否
        false - 不显示
        true - 显示
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    //设置搜索框
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 回退键弹窗
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > Constant.EXIT_SURPASS_TIME) {
            Toast.makeText(this, R.string.exit_notice, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(savedTab, mHomeRadioGroup.getCheckedRadioButtonId());
        RadioButton radioButton = (RadioButton) findViewById(mHomeRadioGroup.getCheckedRadioButtonId());
        //删除下面这行，不然容易发生重影
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume()");
        JPushInterface.onResume(this);
        mFreshTime2 = System.currentTimeMillis();
        //如果黑屏大于10分钟 就重启应用
        if (!mFirstLaunch && (mFreshTime2 - mFreshTime1) > Constant.RESTART_SURPASS_TIME) {
            new Handler().postDelayed(new Runnable(){
                public void run() {
//                    reStartApp();
                }
            }, 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause()");
        JPushInterface.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop()");
        mFreshTime1 = System.currentTimeMillis();
        mFirstLaunch = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy()");
//        ButterKnife.reset(this);
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

    protected void initView() {
        rlLayout = (RelativeLayout) findViewById(R.id.rlLayout);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTxErrNotice = (TextView) findViewById(R.id.tx_err_notice);
         mHomeContent = (FrameLayout) findViewById(R.id.fragment_container); //tab上方的区域
         mHomeRadioGroup = (RadioGroup) findViewById(R.id.main_radios);  //底部的四个tab
         mHomeHomeRb = (RadioButton) findViewById(R.id.rb_tab_home);
         mHomeVideoRb = (RadioButton) findViewById(R.id.rb_tab_video);
         mHomeFunnyRb = (RadioButton) findViewById(R.id.rb_tab_funny);
         mHomeSettingsRb = (RadioButton) findViewById(R.id.rb_tab_settings);

         //监听事件：为底部的RadioGroup绑定状态改变的监听事件
         mHomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
                 int index = 0;
                 switch (checkedId) {
                         case R.id.rb_tab_home:
                                 index = 0;
                                 break;
                         case R.id.rb_tab_video:
                                 index = 1;
                                 break;
                         case R.id.rb_tab_funny:
                                index = 2;
                                break;
                         case R.id.rb_tab_settings:
                                 index = 3;
                                 break;
                     }
                 //通过fragments这个adapter还有index来替换帧布局中的内容
                 Fragment fragment = (Fragment) fragments.instantiateItem(mHomeContent, index);
                 //一开始将帧布局中 的内容设置为第一个
                 fragments.setPrimaryItem(mHomeContent, 0, fragment);
                 fragments.finishUpdate(mHomeContent);

          }
      });
    }


    //用adapter来管理四个Fragment界面的变化。注意，我这里用的Fragment都是v4包里面的
      FragmentPagerAdapter fragments = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return NUM_TABS;//一共有四个Fragment
        }

        //进行Fragment的初始化
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0://howe
                    fragment = HomeFragmentContainer.newInstance("FirstFragment");
//                    fragment = Tab1Fragment.newInstance("FirstFragment");
                    break;
                 case 1://video
                     fragment = VideoFragment.newInstance("SecondFragment");
//                     fragment = Tab2Fragment.newInstance("SecondFragment");
                     break;
                case 2://funny
                    fragment = Tab3Fragment.newInstance("ThirdFragment");
//                     fragment = Tab2Fragment.newInstance("SecondFragment");
                    break;
                 case 3://settings
                     fragment = SettingsFragment.newInstance("ForthFragment");
//                     fragment = Tab3Fragment.newInstance("ThirdFragment");
                     break;

            }

            return fragment;
        }
     };

    @Override
    public void onErrReturned(String newItem) {
        // 步骤7：实现该接口，并使用其中的功能
        displayErrTxtBanner(newItem);
        if (!NetworkUtil.isNetworkAvailable()){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                hander.sendEmptyMessage(Constant.ERR_BANNER_DISMISS);
            }
        }).start();
    }

    @Override
    public void onSucReturned (String newItem){
        if (NetworkUtil.isNetworkAvailable()){
            dismissErrTxtBanner();
        }
    }


    private void dismissErrTxtBanner(){
         if (mTxErrNotice != null && mTxErrNotice.getVisibility() == View.VISIBLE){
             mTxErrNotice.setVisibility(View.GONE);
         }
    }

    private void displayErrTxtBanner(String txt){
        if (mTxErrNotice != null && mTxErrNotice.getVisibility() == View.GONE){
            mTxErrNotice.setVisibility(View.VISIBLE);
            mTxErrNotice.setText(txt);
        }
    }

    private void reStartApp(){
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        MainActivity.mActivity.finish();
        System.exit(0);

    }

}
