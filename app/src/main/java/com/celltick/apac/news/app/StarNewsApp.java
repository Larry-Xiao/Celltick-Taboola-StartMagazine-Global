package com.celltick.apac.news.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.webkit.WebView;

import com.activeandroid.ActiveAndroid;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.SharedPreferencesUtils;
import com.celltick.apac.news.util.UUIDGenerator;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * 全局应用程序上下文
 * 方便 Preference 或 Sqlite 获取 Context
 */

public class StarNewsApp extends Application {


    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    private String UUID;
    private String template;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        //Fresco是facebook的android图片处理库
        Fresco.initialize(getApplicationContext());
        new WebView(this).destroy();

        //初始化ActiveAndroid 方便操作Sqlite
        ActiveAndroid.initialize(this);

        Logger.init(Constant.LOGGER_TAG);

//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush

        //UUID
        UUID = SharedPreferencesUtils.getInstance().getString(Constant.UUID);
        if (UUID != null && !UUID.equals("")) {
            Log.d("UUID","uuid = "+UUID);
        } else {
            UUID = UUIDGenerator.getUUID32();
            SharedPreferencesUtils.getInstance().putString(Constant.UUID,UUID);
        }

        //template
        template = SharedPreferencesUtils.getInstance().getString(Constant.TEMPLATE);
        if (template != null && !template.equals("")) {

        } else {
            SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"default");
        }

        /**
         * 默认设置一直使用夜间模式
         *
         * 这里AppCompatDelegate.setDefaultNightMode()方法可以接受的参数值有4个：
         * MODE_NIGHT_NO. Always use the day (light) theme(一直应用日间(light)主题).
         * MODE_NIGHT_YES. Always use the night (dark) theme(一直使用夜间(dark)主题).
         * MODE_NIGHT_AUTO. Changes between day/night based on the time of day(根据当前时间在day/night主题间切换).
         * MODE_NIGHT_FOLLOW_SYSTEM(默认选项). This setting follows the system's setting, which is essentially MODE_NIGHT_NO(跟随系统，通常为MODE_NIGHT_NO).
         */
        if (SharedPreferencesUtils.getInstance().getBoolean(Constant.NIGHT_MODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    /**
     * clear 类库
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Fresco.shutDown();
        ActiveAndroid.dispose();
    }
}
