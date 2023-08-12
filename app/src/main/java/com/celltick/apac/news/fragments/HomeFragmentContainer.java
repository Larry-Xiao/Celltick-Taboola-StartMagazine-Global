package com.celltick.apac.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.activities.SearchActivity;
import com.celltick.apac.news.model.CategoryBean;
import com.celltick.apac.news.util.JsonParseTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomeFragmentContainer extends Fragment {
    private static final String TAG = HomeFragmentContainer.class.getSimpleName();
    private static final String PARAM = "param";

    private long mExitTime = 0;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TextView mTXSearch;

    private JsonParseTool mJsonTool;
    private List<CategoryBean> categoryList;
    private String mParam;

    public static HomeFragmentContainer newInstance(String param) {
        HomeFragmentContainer fragment = new HomeFragmentContainer();
        Bundle args = new Bundle();
        args.putString(PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.home_fragment_container, null);
        ButterKnife.inject(this, view);
        findViews(view);
        initToolbar();
        initData();

        mTXSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });

        categoryList = mJsonTool.parseCategoryListJson(getActivity().getIntent().getStringExtra("categoryListName"));
        setupViewPager(mViewPager);

        return view;
    }



    private void findViews(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.tb1_toolbar);
        mTabLayout = (TabLayout)view.findViewById(R.id.tablayout_article);
        mViewPager = (ViewPager)view.findViewById(R.id.viewpager_article);
        mTXSearch = (TextView)view.findViewById(R.id.search_tx_id);
    }

    /**
     * initiate ToolBars
     */
    protected void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.mipmap.ic_logo);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    private void initData(){
        mJsonTool = new JsonParseTool();
    }


    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        ArticleFragmentAdapter adapter = new ArticleFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(FeedFragment.newInstance("001"),getResources().getString(R.string.category_top));
        adapter.addFragment(VideoFragment.newInstance("002"),getResources().getString(R.string.category_video));
        for (int m = 0; m< categoryList.size(); m++) {

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

            adapter.addFragment(FeedFragment.newInstance(categoryList.get(m).getCategoryId()),categoryList.get(m).getTranslatedName());

        }
        mViewPager.setAdapter(adapter);
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.category_top)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.category_video)));
        for (int m=0; m<categoryList.size(); m++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(categoryList.get(m).getTranslatedName()));
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

    //重写setMenuVisibility方法，不然会出现叠层的现象
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
        Log.d(TAG,"setMenuVisibility");
    }

}
