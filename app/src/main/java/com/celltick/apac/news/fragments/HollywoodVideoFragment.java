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
import com.celltick.apac.news.activities.PlayerActivity;
import com.celltick.apac.news.adapter.VideoAdapter;
import com.celltick.apac.news.model.VideoBean;
import com.celltick.apac.news.recyclerview_pkg.EndlessRecyclerOnScrollListener;
import com.celltick.apac.news.recyclerview_pkg.HeaderAndFooterRecyclerViewAdapter;
import com.celltick.apac.news.recyclerview_pkg.utils.RecyclerViewStateUtils;
import com.celltick.apac.news.recyclerview_pkg.weight.LoadingFooter;
import com.celltick.apac.news.threads.HollywoodVideoBottomRequestThread;
import com.celltick.apac.news.threads.HollywoodVideoHeaderRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HollywoodVideoFragment extends Fragment {

    private static final String TAG = "FWCVideoFragment";

    private VideoAdapter mAdapter;
    //新闻列表数据
    private List<VideoBean> mVideoList;

    private JsonParseTool mJsonTool;
    private VideoHandler handler;
    private String mTitle;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private String preFirstItemTime = "";
    private int num_endless_swipe;

    @InjectView(R.id.list)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static HollywoodVideoFragment newInstance(String title) {
        HollywoodVideoFragment fragment = new HollywoodVideoFragment();
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
        mVideoList = new ArrayList<>();
        mJsonTool = new JsonParseTool();
        handler = new VideoHandler();

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preFirstItemTime = mAdapter.getPreFirstVideoPublishedDate();
                Log.d(TAG,"preFirstItemTime = " + preFirstItemTime);
                HollywoodVideoHeaderRequestThread thread = new HollywoodVideoHeaderRequestThread(mTitle,handler,Constant.NUM_SM_NORMAL_REQUEST);
                thread.start();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                HollywoodVideoHeaderRequestThread thread = new HollywoodVideoHeaderRequestThread(mTitle,handler);
                thread.start();
            }
        });

        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                VideoBean videoItem = mVideoList.get(position);
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("url", videoItem.getmVideoURL());
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

        mAdapter = new VideoAdapter(getActivity(), mVideoList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }



    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            int end_fresh_num = num_endless_swipe++;
            int offset = end_fresh_num*Constant.NUM_SM_NORMAL_REQUEST+Constant.NUM_SM_NORMAL_REQUEST;

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }

            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 0, LoadingFooter.State.Loading, null);
            HollywoodVideoBottomRequestThread thread = new HollywoodVideoBottomRequestThread(mTitle,handler,offset);
            thread.start();

        }

        @Override
        public void onLoadTopPage(View view) {
            super.onLoadTopPage(view);

            Log.d(TAG,"=======onLoadTopPage========");

        }


    };


    public class VideoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            int flag = msg.arg1;
            switch (msg.what) {
                case Constant.SUCCESS:
                    if (flag == Constant.HEADER_LOAD) {
                        Log.d(TAG,"下拉刷新加载的数据：" + msgStr);
                        List<VideoBean> videoList = mJsonTool.parseVideoJson(msgStr);
                        List<VideoBean> newList = new ArrayList<>();

                        if (preFirstItemTime == null || preFirstItemTime == "") {
                            mAdapter.addHeaderItem(videoList);
                        } else {
                            for (int i=0; i<videoList.size(); i++) {
                                Log.d(TAG,"PublishedDate = " + videoList.get(i).getmPubDate());
                                if (TimeUtil.compare_date(videoList.get(i).getmPubDate(), preFirstItemTime) == 1) {
                                    newList.add(videoList.get(i));
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
                        List<VideoBean> videoList = mJsonTool.parseVideoJson(msgStr);
                        if (videoList.size() == 0 || videoList == null) {
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
                        }else {
                            mAdapter.addFooterItem(videoList);
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
