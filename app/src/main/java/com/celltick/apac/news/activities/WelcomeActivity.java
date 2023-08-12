package com.celltick.apac.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.MainActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.model.CategoryBean;
import com.celltick.apac.news.threads.GetCategoryListThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.NetworkUtil;
import com.celltick.apac.news.util.SharedPreferencesUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by Larryx on 5/27/2018.
 */

public class WelcomeActivity extends BaseActivity {
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private List<CategoryBean> categoryList;
    private String mCategoryListStr;
    private String mCountryCode;
    private String mLanguageCode;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            switch (msg.what) {
                case Constant.SUCCESS:
                    Log.d(TAG,"Category List = " + msgStr);
                    mCategoryListStr = msgStr;
                    if (mCategoryListStr != null && !mCategoryListStr.equals("")) {
                        Log.d(TAG,"request category list success - save category list");
                        getHome();
                        SharedPreferencesUtils.getInstance().putString(Constant.CATEGOTY_LIST, mCategoryListStr);
                    }else {
                        Log.d(TAG,"request category list failed - finish");
                        WelcomeActivity.this.finish();
                        Toast.makeText(WelcomeActivity.this,"load category list failed",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:
                    Log.d(TAG,"request category list failed - error - finish");
                    Toast.makeText(WelcomeActivity.this,"load category list failed, please retry later",Toast.LENGTH_SHORT).show();
                    WelcomeActivity.this.finish();
                    break;

                case 100:
                    getHome();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        //特殊时期可以更改桌面图标
//        APPLancherChangerUtils mAppIconChange = new APPLancherChangerUtils(getApplicationContext().getPackageManager());
//        ComponentName defaultName = new ComponentName(getBaseContext(), "com.celltick.apac.news.activities.WelcomeActivity");
//        ComponentName springName = new ComponentName(getBaseContext(), "com.celltick.apac.news.spring");
//        mAppIconChange.changeIcon(defaultName,springName);

        mCountryCode = SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE);
        mLanguageCode = SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE);
        mCategoryListStr = SharedPreferencesUtils.getInstance().getString(Constant.CATEGOTY_LIST);

//        Log.d(TAG,"mCountryCode = " +mCountryCode);
//        Log.d(TAG,"mLanguageCode = " +mLanguageCode);
//        Log.d(TAG,"mCategoryListStr = " +mCategoryListStr);

        if (mCountryCode==null || mCountryCode.equals("")
                || mLanguageCode==null || mLanguageCode.equals("")
                || mCategoryListStr==null || mCategoryListStr.equals("")){
//            Log.d(TAG,"read mCountryCode & mLanguageCode & mCategoryListStr failure");
            mCountryCode = getCountryCode();
            mLanguageCode = getLanguageCode();
            SharedPreferencesUtils.getInstance().putString(Constant.COUNTRY_CODE, mCountryCode);
            SharedPreferencesUtils.getInstance().putString(Constant.LANGUAGE_CODE, mLanguageCode);
            if (NetworkUtil.isNetworkAvailable()) {
                GetCategoryListThread thread = new GetCategoryListThread(handler);
                thread.start();
            }else {
                handler.sendEmptyMessageDelayed(0,3000);
            }

//            Log.d(TAG,"saved mCountryCode & mLanguageCode");
//            Log.d(TAG,"mCountryCode == " +mCountryCode);
//            Log.d(TAG,"mLanguageCode == " +mLanguageCode);

        } else {
            handler.sendEmptyMessageDelayed(100,3000);
        }
    }

    public void getHome(){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.putExtra("categoryListName",mCategoryListStr);
        startActivity(intent);
        finish();
    }

    private String getCountryCode () {return getResources().getConfiguration().locale.getCountry();}
    private String getLanguageCode (){return Locale.getDefault().getLanguage();}




}
