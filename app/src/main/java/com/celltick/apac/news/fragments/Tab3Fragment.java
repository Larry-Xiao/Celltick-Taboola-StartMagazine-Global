package com.celltick.apac.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.activities.CricketNewsActivity;
import com.celltick.apac.news.activities.CricketScheduleActivity;
import com.celltick.apac.news.activities.CricketVideoActivity;
import com.celltick.apac.news.activities.WebViewActivity;
import com.celltick.apac.news.adapter.CricketWidgetScheduleListAdapter;
import com.celltick.apac.news.adapter.SoccerWidgetScheduleListAdapter;
import com.celltick.apac.news.customizedview.MyGridView;
import com.celltick.apac.news.model.CricketInfo;
import com.celltick.apac.news.model.SoccerInfo;
import com.celltick.apac.news.threads.CricketCurrentScheduleRequestThread;
import com.celltick.apac.news.threads.NBACurrentScheduleRequestThread;
import com.celltick.apac.news.threads.SoccerCurrentScheduleRequestThread;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.JsonParseTool;
import com.celltick.apac.news.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tab3Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Tab3Fragment";

    private static final String STORE_PARAM = "param";

    private MyGridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    private List<CricketInfo> cricketGameList =new ArrayList<>();
    private List<SoccerInfo> soccerGameList =new ArrayList<>();
    private SportHandler handler;
    private JsonParseTool mJsonTool;
    private RecyclerView mCricketRecy;
    private RecyclerView mSoccerRecy;
    private RecyclerView mNBARecy;

    private ImageView mCktRefresh;
    private ImageView mSoccerfresh;
    private ImageView mNBARefresh;

    private TextView mCktScheReqErr;
    private TextView mSoccerScheReqErr;
    private TextView mNBAScheReqErr;

    private ProgressBar mCrktProgress;
    private ProgressBar mSoccerProgress;
    private ProgressBar mNBAProgress;

    private Button cricket_schedules,soccer_schedules,nba_schedules;
    private Button cricket_news,soccer_news,nba_news;
    private Button cricket_video,soccer_video,nba_video;

    private ImageView mSoccerBanner;
    private ImageView mCricketBanner;
    private ImageView mBeautyBanner;
    private ImageView mHealthBanner;
    private ImageView mFoodrBanner;
    private ImageView mCelebrityBanner;

    private int[] icon = { R.mipmap.soccer,
            R.mipmap.cricket,
            R.mipmap.beauty,
            R.mipmap.health,
            R.mipmap.food,
            R.mipmap.celebrity};
    private String[] iconName = { "Soccer", "Cricket", "Beauty", "Health", "Food", "Celebrity"};

    public static Fragment newInstance(String param) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        args.putString(STORE_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab3_frag, null);
        mCrktProgress = view.findViewById(R.id.cricket_schedule_loading_progress);
        mCricketRecy = (RecyclerView) view.findViewById(R.id.id_recycler_view);
        mCktScheReqErr = view.findViewById(R.id.cricket_schedule_request_error);
        mCktRefresh = view.findViewById(R.id.cricket_refresh);
        mCktRefresh.setOnClickListener(this);

        mSoccerProgress = view.findViewById(R.id.soccer_schedule_loading_progress);
        mSoccerRecy = (RecyclerView) view.findViewById(R.id.id_recycler_view_soccer);
        mSoccerScheReqErr = view.findViewById(R.id.soccer_schedule_request_error);
        mSoccerfresh = view.findViewById(R.id.soccer_refresh);
        mSoccerfresh.setOnClickListener(this);

        mNBAProgress = view.findViewById(R.id.nba_schedule_loading_progress);
        mNBARecy = (RecyclerView) view.findViewById(R.id.id_recycler_view_nba);
        mNBAScheReqErr = view.findViewById(R.id.nba_schedule_request_error);
        mNBARefresh = view.findViewById(R.id.NBA_refresh);
        mNBARefresh.setOnClickListener(this);

        mJsonTool = new JsonParseTool();
        handler = new SportHandler();

        mCrktProgress.setVisibility(View.VISIBLE);
        mCricketRecy.setVisibility(View.GONE);
        mCktScheReqErr.setVisibility(View.GONE);
        CricketCurrentScheduleRequestThread thread1 = new CricketCurrentScheduleRequestThread(handler);
        thread1.start();

        mSoccerProgress.setVisibility(View.VISIBLE);
        mSoccerRecy.setVisibility(View.GONE);
        mSoccerScheReqErr.setVisibility(View.GONE);
        SoccerCurrentScheduleRequestThread thread2 = new SoccerCurrentScheduleRequestThread(handler);
        thread2.start();

        mNBAProgress.setVisibility(View.VISIBLE);
        mNBARecy.setVisibility(View.GONE);
        mNBAScheReqErr.setVisibility(View.GONE);
        NBACurrentScheduleRequestThread thread3 = new NBACurrentScheduleRequestThread(handler);
        thread3.start();


        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCricketRecy.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(getActivity());
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSoccerRecy.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3=new LinearLayoutManager(getActivity());
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mNBARecy.setLayoutManager(layoutManager3);






        initSpecialCards(view);
        gview = (MyGridView) view.findViewById(R.id.gridView);
        data_list = new ArrayList<Map<String, Object>>();
        getData();
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.tab3_grid_item, from, to);
        gview.setAdapter(sim_adapter);

        return view;
    }

    public List<Map<String, Object>> getData(){
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }


    private void initSpecialCards(View v){
        mSoccerBanner = v.findViewById(R.id.iv_soccer);
        mCricketBanner= v.findViewById(R.id.iv_cricket);
        mBeautyBanner= v.findViewById(R.id.iv_beauty);
        mHealthBanner= v.findViewById(R.id.iv_health);
        mFoodrBanner= v.findViewById(R.id.iv_food);
        mCelebrityBanner= v.findViewById(R.id.iv_celebrity);

        mSoccerBanner.setOnClickListener(this);
        mCricketBanner.setOnClickListener(this);
        mBeautyBanner.setOnClickListener(this);
        mHealthBanner.setOnClickListener(this);
        mFoodrBanner.setOnClickListener(this);
        mCelebrityBanner.setOnClickListener(this);

        cricket_schedules = v.findViewById(R.id.cricket_schedule);
        cricket_news = v.findViewById(R.id.cricket_news);
        cricket_video = v.findViewById(R.id.cricket_video);
        cricket_schedules.setOnClickListener(this);
        cricket_news.setOnClickListener(this);
        cricket_video.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startWebActivity(Constant.SOCCER);
                        break;
                    case 1:
                        startWebActivity(Constant.CRICKET);
                        break;
                    case 2:
                        startWebActivity(Constant.BEAUTY);
                        break;
                    case 3:
                        startWebActivity(Constant.HEALTH);
                        break;
                    case 4:
                        startWebActivity(Constant.FOOD);
                        break;
                    case 5:
                        startWebActivity(Constant.CELEBRITY);
                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        switch (ID){
            case R.id.iv_cricket:
                startWebActivity(Constant.CRICKET);
            break;
            case R.id.iv_soccer:
                startWebActivity(Constant.SOCCER);
            break;
            case R.id.iv_health:
                startWebActivity(Constant.HEALTH);
            break;
            case R.id.iv_food:
                startWebActivity(Constant.FOOD);
            break;
            case R.id.iv_beauty:
                startWebActivity(Constant.BEAUTY);
            break;
            case R.id.iv_celebrity:
                startWebActivity(Constant.CELEBRITY);
            break;
            case R.id.cricket_schedule:
                Intent intent1 = new Intent(getActivity(),CricketScheduleActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.cricket_news:
                Intent intent2 = new Intent(getActivity(),CricketNewsActivity.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.cricket_video:
                Intent intent3 = new Intent(getActivity(),CricketVideoActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.cricket_refresh:
                mCrktProgress.setVisibility(View.VISIBLE);
                mCricketRecy.setVisibility(View.GONE);
                mCktScheReqErr.setVisibility(View.GONE);
                CricketCurrentScheduleRequestThread thread = new CricketCurrentScheduleRequestThread(handler);
                thread.start();
                break;
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {super.onDestroyView();}

    private void startWebActivity(String packages){
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", packages);
        intent.putExtra("title", "");
        intent.putExtra("article_title", "");
        intent.putExtra("cp-logo", "");
        getActivity().startActivity(intent);

    }

    public class SportHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String msgStr = (String) msg.obj;
            int flag = msg.arg1;
            switch (msg.what) {
                case Constant.CRICKET_SUCCESS:
                    Log.d(TAG,"成功收到板球数据列表");
                    cricketGameList = mJsonTool.parseSportCricketJson(msgStr);
                    CricketWidgetScheduleListAdapter adapter = new CricketWidgetScheduleListAdapter(cricketGameList);
                    adapter.setHasStableIds(true);
                    mCricketRecy.setAdapter(adapter);


                    adapter.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(int position) {
                            CricketInfo cricket_item = cricketGameList.get(position);
                            startWebActivity(cricket_item.getGamePageURL());
                        }
                    });

                    mCrktProgress.setVisibility(View.GONE);
                    mCricketRecy.setVisibility(View.VISIBLE);
                    mCktScheReqErr.setVisibility(View.GONE);

                    break;
                case Constant.SOCCER_SUCCESS:
                    Log.d(TAG,"成功收到SOCCER数据列表");
                    Log.d(TAG,"Soccer 数据： " + msgStr);

                    soccerGameList = mJsonTool.parseSportSoccerJson(msgStr);
                    SoccerWidgetScheduleListAdapter adapter_soccer = new SoccerWidgetScheduleListAdapter(soccerGameList);
                    adapter_soccer.setHasStableIds(true);
                    mSoccerRecy.setAdapter(adapter_soccer);


                    adapter_soccer.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(int position) {
                            SoccerInfo soccer_item = soccerGameList.get(position);
                            startWebActivity(soccer_item.getGamePageURL());
                        }
                    });

                    mSoccerProgress.setVisibility(View.GONE);
                    mSoccerRecy.setVisibility(View.VISIBLE);
                    mSoccerScheReqErr.setVisibility(View.GONE);

                    break;
                case Constant.NBA_SUCCESS:
                    Log.d(TAG,"成功收到NBA数据列表");
                    cricketGameList = mJsonTool.parseSportCricketJson(msgStr);
                    CricketWidgetScheduleListAdapter adapter_nba = new CricketWidgetScheduleListAdapter(cricketGameList);
                    adapter_nba.setHasStableIds(true);
                    mNBARecy.setAdapter(adapter_nba);


                    adapter_nba.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(int position) {
                            CricketInfo cricket_item = cricketGameList.get(position);
                            startWebActivity(cricket_item.getGamePageURL());
                        }
                    });

                    mNBAProgress.setVisibility(View.GONE);
                    mNBARecy.setVisibility(View.VISIBLE);
                    mNBAScheReqErr.setVisibility(View.GONE);

                    break;



                case Constant.ERR_RETURN:
                    if (flag == Constant.HEADER_LOAD) {

                    } else if (flag == Constant.BOTTOM_LOAD){

                    }
                    break;
                case Constant.CRICKET_ERR_RETURN_NO_CONNECTION:
                case Constant.CRICKET_ERR_RETURN_TIMEOUT:
                case Constant.CRICKET_ERR_RETURN_UNKNOWN:
                    mCrktProgress.setVisibility(View.GONE);
                    mCricketRecy.setVisibility(View.GONE);
                    mCktScheReqErr.setVisibility(View.VISIBLE);
                    break;
                case Constant.SOCCER_ERR_RETURN_NO_CONNECTION:
                case Constant.SOCCER_ERR_RETURN_TIMEOUT:
                case Constant.SOCCER_ERR_RETURN_UNKNOWN:
                    mSoccerProgress.setVisibility(View.GONE);
                    mSoccerRecy.setVisibility(View.GONE);
                    mSoccerScheReqErr.setVisibility(View.VISIBLE);
                    break;
                case Constant.NBA_ERR_RETURN_NO_CONNECTION:
                case Constant.NBA_ERR_RETURN_TIMEOUT:
                case Constant.NBA_ERR_RETURN_UNKNOWN:
                    mNBAProgress.setVisibility(View.GONE);
                    mNBARecy.setVisibility(View.GONE);
                    mNBAScheReqErr.setVisibility(View.VISIBLE);
                    break;
                case Constant.ERR_BANNER_DISMISS:
                    break;
            }
        }
    }

}
