package com.celltick.apac.news.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.MainActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.fragments.FeedFragment;
import com.celltick.apac.news.threads.GetCategoryListThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.NetworkUtil;
import com.celltick.apac.news.util.SharedPreferencesUtils;

import java.util.Locale;

public class LanActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LanActivity";

    private Toolbar mToolbar;
    private Spinner mCountrySpnnier;
    private Spinner mLanguageSpnnier;
    private String mCategoryListStr;
    private Button mSaveRestart;
    private ProgressDialog dialog;
    FeedFragment.OnErrReturnListener onErrReturnListener;
    public Activity mActivity;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            switch (msg.what) {
                case Constant.SUCCESS:
                    dialog.dismiss();
                    mCategoryListStr = msgStr;
                    if (mCategoryListStr != null && !mCategoryListStr.equals("")) {
                        SharedPreferencesUtils.getInstance().clear();
                        SharedPreferencesUtils.getInstance().putString(Constant.COUNTRY_CODE, getSelectedCountryCode());
                        SharedPreferencesUtils.getInstance().putString(Constant.LANGUAGE_CODE, getSelectedLanguageCode());
                        SharedPreferencesUtils.getInstance().putString(Constant.CATEGOTY_LIST, mCategoryListStr);
                        switchLanguage(getSelectedLanguageCode());
                        reStartApp();
                    }else {
                        Log.d(TAG,"request category list failed - finish");
                    }
                    break;

                case Constant.ERR_RETURN:
                    break;
                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:
//                    onErrReturnListener.onErrReturned(msgStr);
                    SharedPreferencesUtils.getInstance().putString(Constant.COUNTRY_CODE, getResources().getConfiguration().locale.getCountry());
                    SharedPreferencesUtils.getInstance().putString(Constant.LANGUAGE_CODE, Locale.getDefault().getLanguage());

//                    Log.d(TAG,"saved countryCode = "+getResources().getConfiguration().locale.getCountry());
//                    Log.d(TAG,"saved languageCode = "+Locale.getDefault().getLanguage());

                    dialog.dismiss();
                    Toast.makeText(LanActivity.this,msgStr,Toast.LENGTH_LONG).show();
                    LanActivity.this.finish();
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_language);
        mActivity = this;
        findViews();
        initToolbar();

        ArrayAdapter<String> mCountrySpinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item);
        String level[] = getResources().getStringArray(R.array.countries);//资源文件
        for (int i = 0; i < level.length; i++) {
            mCountrySpinnerAdapter.add(level[i]);
        }
        mCountrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrySpnnier.setAdapter(mCountrySpinnerAdapter);

        ArrayAdapter<String> mLanguageSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item);
        String levels[] = getResources().getStringArray(R.array.languages);//资源文件
        for (int i = 0; i < levels.length; i++) {
            mLanguageSpinnerAdapter.add(levels[i]);
        }
        mLanguageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLanguageSpnnier.setAdapter(mLanguageSpinnerAdapter);
    }

    private void findViews(){
        mToolbar = (Toolbar)findViewById(R.id.tb1_toolbar);
        mCountrySpnnier = (Spinner)findViewById(R.id.spinner_country);
        mLanguageSpnnier = (Spinner)findViewById(R.id.spinner_language);
        mSaveRestart = (Button) findViewById(R.id.btn_save_restart);
        mSaveRestart.setOnClickListener(this);
        dialog = new ProgressDialog(this);
    }

    protected void initToolbar() {
        mToolbar.setTitle(R.string.title_country_language);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(com.afollestad.materialdialogs.R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.item_save:
//
//                if (NetworkUtil.isNetworkAvailable()) {
//                    String countryCode = getSelectedCountryCode();
//                    String languageCode = getSelectedLanguageCode();
//
//                    SharedPreferencesUtils.getInstance().putString("countryCode", countryCode);
//                    SharedPreferencesUtils.getInstance().putString("languageCode", languageCode);
//                    GetCategoryListThread thread = new GetCategoryListThread(handler);
//                    thread.start();
//                } else {
//                    LanActivity.this.finish();
////                    onErrReturnListener.onErrReturned(getString(R.string.err_return_no_connection));
//                }
//                dialog.show();
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_save_restart){
            if (NetworkUtil.isNetworkAvailable()) {
                String countryCode = getSelectedCountryCode();
                String languageCode = getSelectedLanguageCode();

                SharedPreferencesUtils.getInstance().putString(Constant.COUNTRY_CODE, countryCode);
                SharedPreferencesUtils.getInstance().putString(Constant.LANGUAGE_CODE, languageCode);
                GetCategoryListThread thread = new GetCategoryListThread(handler);
                thread.start();
            } else {
                LanActivity.this.finish();
            }
            dialog.show();
        }

    }

    private String getSelectedCountryCode(){
        String countryStr = mCountrySpnnier.getSelectedItem().toString();
        String countryCode = countryStr.substring(countryStr.indexOf("(")+1, countryStr.lastIndexOf(")"));
        return countryCode;
    }

    private String getSelectedLanguageCode(){
        String languageStr = mLanguageSpnnier.getSelectedItem().toString();
        String languageCode = languageStr.substring(languageStr.indexOf("(")+1, languageStr.lastIndexOf(")"));
        return languageCode;
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
