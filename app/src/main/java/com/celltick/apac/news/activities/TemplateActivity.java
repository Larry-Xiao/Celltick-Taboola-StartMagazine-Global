package com.celltick.apac.news.activities;

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
import android.widget.RadioGroup;

import com.celltick.apac.news.BaseActivity;
import com.celltick.apac.news.R;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.SharedPreferencesUtils;

public class TemplateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "TemplateActivity";

    private Toolbar mToolbar;
    private RadioGroup mRGTemplates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepSameColorOfStatusBarAndToolbar();
        setContentView(R.layout.activity_templates);
        findViews();
        initToolbar();

        mRGTemplates = (RadioGroup) findViewById(R.id.rg_templates);
        mRGTemplates.setOnCheckedChangeListener(this);
        setCheckedRadioButton(mRGTemplates);

    }

    private void findViews(){
        mToolbar = (Toolbar)findViewById(R.id.template_toolbar);
    }

    protected void initToolbar() {
        mToolbar.setTitle(R.string.title_template);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplateActivity.this.finish();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_template_default:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"default");
                break;
            case R.id.rb_template2:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template2");
                break;
            case R.id.rb_template3:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template3");
                break;
            case R.id.rb_template4:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template4");
                break;
            case R.id.rb_template5:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template5");
                break;
            case R.id.rb_template6:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template6");
                break;
            case R.id.rb_template7:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template7");
                break;
            case R.id.rb_template8:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template8");
                break;
            case R.id.rb_template9:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template9");
                break;
            case R.id.rb_template10:
                SharedPreferencesUtils.getInstance().putString(Constant.TEMPLATE,"template10");
                break;
            default:
                break;
        }
    }

    private void setCheckedRadioButton(RadioGroup rg) {
        String current_template = SharedPreferencesUtils.getInstance().getString(Constant.TEMPLATE);
        if (current_template == null || current_template.equals("") || current_template.equals("default")) {
            rg.check(R.id.rb_template_default);
        } else if (current_template.equals("template2")){
            rg.check(R.id.rb_template2);
        } else if (current_template.equals("template3")){
            rg.check(R.id.rb_template3);
        }else if (current_template.equals("template4")){
            rg.check(R.id.rb_template4);
        }else if (current_template.equals("template5")){
            rg.check(R.id.rb_template5);
        }else if (current_template.equals("template6")){
            rg.check(R.id.rb_template6);
        }else if (current_template.equals("template7")){
            rg.check(R.id.rb_template7);
        }else if (current_template.equals("template8")){
            rg.check(R.id.rb_template8);
        }else if (current_template.equals("template9")){
            rg.check(R.id.rb_template9);
        }else if (current_template.equals("template10")){
            rg.check(R.id.rb_template10);
        }
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
