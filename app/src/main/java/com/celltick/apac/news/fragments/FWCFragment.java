package com.celltick.apac.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.celltick.apac.news.R;
import com.celltick.apac.news.activities.WebViewActivity;
import com.celltick.apac.news.adapter.FWCAdapter;
import com.celltick.apac.news.model.ArticleBean;
import com.celltick.apac.news.recyclerview_pkg.EndlessRecyclerOnScrollListener;
import com.celltick.apac.news.recyclerview_pkg.HeaderAndFooterRecyclerViewAdapter;
import com.celltick.apac.news.recyclerview_pkg.utils.RecyclerViewStateUtils;
import com.celltick.apac.news.recyclerview_pkg.weight.LoadingFooter;
import com.celltick.apac.news.threads.FWCBottomArticleRequestThread;
import com.celltick.apac.news.threads.FWCHeaderArticleRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FWCFragment extends Fragment {

    private static final String TAG = "FWCFragment";

    private FWCAdapter mAdapter;
    //新闻列表数据
    private List<ArticleBean> mArticleList;

    private JsonParseTool mJsonTool;
    private FWCHandler handler;
    private String mTitle;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private String preFirstArticleTime = "";

    @InjectView(R.id.list)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static FWCFragment newInstance(String title) {
        FWCFragment fragment = new FWCFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = this.getArguments().get("title").toString();
        Log.d(TAG,"当前标题：" +mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_feed, container, false);
        ButterKnife.inject(this, view);
        mArticleList = new ArrayList<>();
        mJsonTool = new JsonParseTool();
        handler = new FWCHandler();

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preFirstArticleTime = mAdapter.getPreFirstArticlePublishedDate();
                Log.d(TAG,"preFirstArticleTime = " + preFirstArticleTime);
                FWCHeaderArticleRequestThread thread = new FWCHeaderArticleRequestThread(mTitle,handler,Constant.NUM_SM_NORMAL_REQUEST);
                thread.start();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                FWCHeaderArticleRequestThread thread = new FWCHeaderArticleRequestThread(mTitle,handler);
                thread.start();
            }
        });

        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                ArticleBean articleItem = mArticleList.get(position);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", articleItem.getArticleURL());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    private void initData(){

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setRefreshing(true);

        mAdapter = new FWCAdapter(getActivity(), mArticleList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }



    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            String nextContentItem = mAdapter.getNextContentItem();

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }

            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 0, LoadingFooter.State.Loading, null);
            FWCBottomArticleRequestThread thread = new FWCBottomArticleRequestThread(mTitle,handler,nextContentItem);
            thread.start();

        }

        @Override
        public void onLoadTopPage(View view) {
            super.onLoadTopPage(view);

            Log.d(TAG,"=======onLoadTopPage========");

        }


    };


    public class FWCHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            int flag = msg.arg1;
            switch (msg.what) {
                case Constant.SUCCESS:
                    if (flag == Constant.HEADER_LOAD) {
                        Log.d(TAG,"下拉刷新加载的数据：" + msgStr);
                        List<ArticleBean> articleList = mJsonTool.parseFWCJson(msgStr);
                        List<ArticleBean> newList = new ArrayList<>();

                        if (preFirstArticleTime == null || preFirstArticleTime == "") {
                            mAdapter.addHeaderItem(articleList);
                        } else {
                            for (int i=0; i<articleList.size(); i++) {
                                Log.d(TAG,"PublishedDate = " + articleList.get(i).getPublishedDate());
                                if (TimeUtil.compare_date(articleList.get(i).getPublishedDate(),preFirstArticleTime) == 1) {
                                    newList.add(articleList.get(i));
                                }

                            }
                            if (newList.size() != 0) {
                                mAdapter.addHeaderItem(newList);
                            }
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(),newList.size()+" items updated",Toast.LENGTH_SHORT).show();
                            }


                        }
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else if (flag == Constant.BOTTOM_LOAD){
                        Log.d(TAG,"底部加载的数据成功：" + msgStr);
                        List<ArticleBean> articleList = mJsonTool.parseFWCJson(msgStr);
                        if (articleList.size() == 0 || articleList == null) {
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
                        }else {
                            mAdapter.addFooterItem(articleList);
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                        }

                    }
                    break;

                case Constant.ERR_RETURN:
                    if (flag == Constant.HEADER_LOAD) {

                    } else if (flag == Constant.BOTTOM_LOAD){

                    }
                    break;
            }
        }
    }


}
