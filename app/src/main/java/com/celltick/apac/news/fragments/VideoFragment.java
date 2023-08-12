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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.activities.PlayerActivity;
import com.celltick.apac.news.adapter.VideoAdapter;
import com.celltick.apac.news.model.VideoBean;
import com.celltick.apac.news.recyclerview_pkg.EndlessRecyclerOnScrollListener;
import com.celltick.apac.news.recyclerview_pkg.HeaderAndFooterRecyclerViewAdapter;
import com.celltick.apac.news.recyclerview_pkg.utils.RecyclerViewStateUtils;
import com.celltick.apac.news.recyclerview_pkg.weight.LoadingFooter;
import com.celltick.apac.news.threads.VideoBottomRequestThread;
import com.celltick.apac.news.threads.VideoHeaderRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.NetworkUtil;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";

    private VideoAdapter mAdapter;
    //video列表数据
    private List<VideoBean> mVideoList;
    private Toolbar mToolbar;

    private JsonParseTool mJsonTool;
    private VideoHandler handler;
    private String mTitle;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private String preFirstItemTime = "";
    private int num_endless_swipe;
    private TextView mTXNumNotice;

    @InjectView(R.id.list)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    FeedFragment.OnErrReturnListener onErrReturnListener;

    public static VideoFragment newInstance(String title) {
        VideoFragment fragment = new VideoFragment();
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
        onErrReturnListener = (FeedFragment.OnErrReturnListener) getActivity();
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        mTXNumNotice = view.findViewById(R.id.tx_num_notice);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        mRecyclerView = view.findViewById(R.id.list);
        ButterKnife.inject(this, view);
        mVideoList = new ArrayList<>();
        mJsonTool = new JsonParseTool();
        handler = new VideoHandler();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtil.isNetworkAvailable()){
                    preFirstItemTime = mAdapter.getPreFirstVideoPublishedDate();
                    Log.d(TAG,"preFirstItemTime = " + preFirstItemTime);
                    VideoHeaderRequestThread thread = new VideoHeaderRequestThread(mTitle,handler,Constant.NUM_SM_NORMAL_REQUEST);
                    thread.start();
                } else {
                    onErrReturnListener.onErrReturned(getString(R.string.err_return_no_connection));
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });

        if (NetworkUtil.isNetworkAvailable()){
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    VideoHeaderRequestThread thread = new VideoHeaderRequestThread(mTitle,handler);
                    thread.start();
                }
            });
        } else {
            onErrReturnListener.onErrReturned(getString(R.string.err_return_no_connection));
            swipeRefreshLayout.setRefreshing(false);
        }

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

    //重写setMenuVisibility方法，不然会出现叠层的现象
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
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
            if (NetworkUtil.isNetworkAvailable()) {
                VideoBottomRequestThread thread = new VideoBottomRequestThread(mTitle,handler,offset);
                thread.start();
            } else {
                RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                onErrReturnListener.onErrReturned(getString(R.string.err_return_no_connection));
            }

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
                    onErrReturnListener.onSucReturned("");
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
                                    Log.d(TAG,"刷新 有 更新" + videoList.get(i).getmPubDate());
                                    newList.add(videoList.get(i));
                                }

                            }
                            if (newList.size() != 0) {
                                mAdapter.addHeaderItem(newList);
                            }
                            if (getActivity() != null) {
//                                Toast.makeText(getActivity(),newList.size()+" items updated",Toast.LENGTH_SHORT).show();
                                displayNumBanner(newList.size()+" updated");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        handler.sendEmptyMessage(Constant.ERR_BANNER_DISMISS);
                                    }
                                }).start();
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
                case Constant.ERR_RETURN_NO_CONNECTION:
                case Constant.ERR_RETURN_TIMEOUT:
                case Constant.ERR_RETURN_UNKNOWN:
                    // 步骤5：创建某种场景，使用该接口回调方法
                    // 步骤5：创建某种场景，使用该接口回调方法
                    onErrReturnListener.onErrReturned(msgStr);
                    //dismiss the swipefresh
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading){
                        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                    }
                    break;
                case Constant.ERR_BANNER_DISMISS:
                    dismissNumBanner();
                    break;
            }
        }
    }

    private void dismissNumBanner(){
        if (mTXNumNotice != null && mTXNumNotice.getVisibility() == View.VISIBLE){
            mTXNumNotice.setVisibility(View.GONE);
        }
    }

    private void displayNumBanner(String txt){
        if (mTXNumNotice != null && mTXNumNotice.getVisibility() == View.GONE){
            mTXNumNotice.setVisibility(View.VISIBLE);
            mTXNumNotice.setText(txt);
        }
    }


}
