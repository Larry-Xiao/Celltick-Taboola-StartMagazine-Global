package com.celltick.apac.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.celltick.apac.news.R;
import com.celltick.apac.news.constant.Const;
import com.celltick.apac.news.fragments.VideoFragment;
import com.celltick.apac.news.model.CategoryBean;
import com.celltick.apac.news.util.DialogUtil;
import com.celltick.apac.news.util.JsonParseTool;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = VideoActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private DialogUtil mDialog = null;
    private JsonParseTool mJsonTool;
    private List<CategoryBean> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment_container);

        findViews();
        initToolbar();
        setupViewPager(mViewPager);

    }

    private void findViews(){
        mToolbar = (Toolbar)findViewById(R.id.tb1_toolbar);
        mTabLayout = (TabLayout)this.findViewById(R.id.tablayout_article);
        mViewPager = (ViewPager)this.findViewById(R.id.viewpager_article);

        mDialog = new DialogUtil(VideoActivity.this);
        mJsonTool = new JsonParseTool();

    }

    protected void initToolbar() {
        mToolbar.setTitle("Video");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(mToolbar); // this sentence should be the last

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_video:
                Intent intent = new Intent(this, VideoActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                Toast.makeText(this,"languages", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, LanActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        ArticleFragmentAdapter adapter = new ArticleFragmentAdapter(getSupportFragmentManager());
        for (int m = 0; m< Const.VideoCategory.length; m++) {

//            if (m==1) {
//                adapter.addFragment(FWCFragment.newInstance(Const.Categories[1]), Const.Categories[1]);
//            } else if (m==2) {
//                adapter.addFragment(FWCVideoFragment.newInstance(Const.Categories[2]), Const.Categories[2]);
//            }else if (m==3) {
//                adapter.addFragment(VideoFragment.newInstance(Const.Categories[3]), Const.Categories[3]);
//            } else if (m==4) {
//                adapter.addFragment(HollywoodVideoFragment.newInstance(Const.Categories[4]), Const.Categories[4]);
//            } else if (m==5) {
//                adapter.addFragment(BollywoodVideoFragment.newInstance(Const.Categories[5]), Const.Categories[5]);
//            } else {
//                adapter.addFragment(FeedFragment.newInstance(Const.Categories[m]),Const.Categories[m]);
//            }

            adapter.addFragment(VideoFragment.newInstance(Const.VideoCategory[m]),Const.VideoCategory[m]);

        }
        mViewPager.setAdapter(adapter);

        for (int m=0; m<Const.VideoCategory.length; m++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(Const.VideoCategory[m]));
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class ArticleFragmentAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ArticleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String id) {
            mFragments.add(fragment);
            mFragmentTitles.add(id);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
















}
