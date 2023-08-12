package com.celltick.apac.news;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.celltick.apac.news.app.StarNewsApp;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.PrefUtils;
import com.celltick.apac.news.util.SharedPreferencesUtils;
import com.celltick.apac.news.util.ThemeTool;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isFirstRun()) {
            switchLanguage(SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE, "en"));
            setLocale();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个颜色给系统栏
        if (PrefUtils.isDarkMode()) {
            tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDarkDarkTheme));
        } else {
            tintManager.setTintColor(getResources().getColor(R.color.primary_dark));
        }
        ThemeTool.changeTheme(this);
    }

    /**
     * 通过Id得到view的实例
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

    /**
     * toast消息
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出对话框
     *
     * @param msg
     */
    protected void showDialog(String msg) {

    }

    /**
     * 关闭对话框
     */
    protected void dismissDialog() {

    }

    /**
     * 通过类名启动activity
     *
     * @param clazz
     */
    protected void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    /**
     * 通过类名启动activity
     *
     * @param context
     * @param clazz
     */
    protected void openActivity(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        openActivity(intent);
    }

    /**
     * 通过类名带参启动Activity
     *
     * @param clazz
     * @param bundle
     */
    protected void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        openActivity(intent);
    }

    /**
     * 启动Activity
     *
     * @param intent
     */
    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 通过action名启动activity
     *
     * @param action
     */
    protected void openActivity(String action) {
        openActivity(action, null);
    }

    /**
     * 通过action名带参启动activity
     *
     * @param action
     * @param bundle
     */
    protected void openActivity(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        openActivity(intent);
    }

    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        } else if (language.equals("es")) {
            config.locale = new Locale("es");
        } else if (language.equals("fr")) {
            config.locale = Locale.FRENCH;
        } else if (language.equals("de")) {
            config.locale = Locale.GERMAN;
        }else if (language.equals("it")) {
            config.locale = Locale.ITALIAN;
        } else if (language.equals("pt")) {
            config.locale = new Locale("pt");
        }else if (language.equals("pl")) {
            config.locale = new Locale("pl");
        }else if (language.equals("ru")) {
            config.locale = new Locale("ru");
        }else if (language.equals("tr")) {
            config.locale = new Locale("tr");
        }else if (language.equals("uk")) {
            config.locale = new Locale("uk");
        }else if (language.equals("id")) {
            config.locale = new Locale("id");
        }else if (language.equals("bn")) {
            config.locale = new Locale("bn");
        }else if (language.equals("he")) {
            config.locale = new Locale("he");
        }else if (language.equals("vi")) {
            config.locale = new Locale("vi");
        }else if (language.equals("hi")) {
            config.locale = new Locale("hi");
        }else if (language.equals("ne")) {
            config.locale = new Locale("ne");
        }else if (language.equals("ar")) {
            config.locale = new Locale("ar");
        }else if (language.equals("ja")) {
            config.locale = Locale.JAPANESE;
        }else if (language.equals("zh")) {
            config.locale = Locale.TRADITIONAL_CHINESE;
        }else if (language.equals("ps")) {
            config.locale = new Locale("ps");
        }else if (language.equals("nl")) {
            config.locale = new Locale("nl");
        } else if (language.equals("hr")) {
            config.locale = new Locale("hr");
        }else if (language.equals("el")) {
            config.locale = new Locale("el");
        }else if (language.equals("ko")) {
            config.locale = new Locale("ko");
        }else if (language.equals("ms")) {
            config.locale = new Locale("ms");
        }else if (language.equals("th")) {
            config.locale = new Locale("th");
        }else if (language.equals("ur")) {
            config.locale = new Locale("ur");
        }else if (language.equals("af")) {
            config.locale = new Locale("af");
        }else if (language.equals("km")) {
            config.locale = new Locale("km");
        }else if (language.equals("ta")) {
            config.locale = new Locale("ta");
        }else {
            config.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(config, dm);

        //保存设置语言的类型
        SharedPreferencesUtils.getInstance().putString(Constant.LANGUAGE_CODE, language);
    }

    public static void setLocale() {
        Locale locale = new Locale(SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE));
        Configuration config = StarNewsApp.getContext().getResources().getConfiguration();
        Locale.setDefault(locale);
        config.locale = locale;
        StarNewsApp.getContext()
                .getResources()
                .updateConfiguration(config, StarNewsApp.getContext().getResources().getDisplayMetrics());
    }

    protected boolean isFirstRun(){
        boolean isFirstRun = SharedPreferencesUtils.getInstance().getBoolean(Constant.IS_FIRST_RUN,true);
        if(isFirstRun){//第一次
            SharedPreferencesUtils.getInstance().putBoolean(Constant.IS_FIRST_RUN,false);
            return true;
        }else{
            return false;
        }
    }

}
