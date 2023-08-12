package com.celltick.apac.news.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.celltick.apac.news.R;
import com.celltick.apac.news.activities.LanActivity;
import com.celltick.apac.news.activities.TemplateActivity;
import com.celltick.apac.news.activities.WelcomeActivity;
import com.celltick.apac.news.dialog.MyCommonDialog;
import com.celltick.apac.news.util.APKVersionCodeUtils;
import com.celltick.apac.news.util.Constant;
import com.celltick.apac.news.util.ShareUtils;
import com.celltick.apac.news.util.SharedPreferencesUtils;

import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "SettingsFragment";
    private static final String STORE_PARAM = "param";

    private String mParam;

    private LinearLayout mCountryLanCard;
    private LinearLayout mFavor;
    private LinearLayout mHistory;
    private LinearLayout mShare;
    private LinearLayout mVersion;
    private LinearLayout mTemplates;
    private TextView mVersionTX;
    private Switch mSwitch;

    FeedFragment.OnErrReturnListener onErrReturnListener;

    public static Fragment newInstance(String param) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(STORE_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(STORE_PARAM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onErrReturnListener = (FeedFragment.OnErrReturnListener) getActivity();
        View view = inflater.inflate(R.layout.layout_tab4_frag, null);
        ButterKnife.inject(this, view);
        mVersionTX = view.findViewById(R.id.version);
        setVersionTX(mVersionTX);
        mSwitch = view.findViewById(R.id.mode_change);
        mCountryLanCard = view.findViewById(R.id.layout_card2_country_language);
        mShare = view.findViewById(R.id.layout_share);
        mFavor = view.findViewById(R.id.layout_favor);
        mHistory = view.findViewById(R.id.layout_history);
        mVersion = view.findViewById(R.id.layout_card3_version);
        mTemplates = view.findViewById(R.id.layout_card2_templates);
        mCountryLanCard.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mFavor.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mVersion.setOnClickListener(this);
        mTemplates.setOnClickListener(this);

        if (SharedPreferencesUtils.getInstance().getBoolean(Constant.NIGHT_MODE,false)){
            mSwitch.setChecked(true);
        } else {
            mSwitch.setChecked(false);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferencesUtils.getInstance().putBoolean(Constant.NIGHT_MODE,true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }else {
                    SharedPreferencesUtils.getInstance().putBoolean(Constant.NIGHT_MODE,false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                getActivity().startActivity(new Intent(getActivity(),WelcomeActivity.class));
                getActivity().overridePendingTransition(R.anim.sb__top_in, R.anim.sb__top_out);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        int ID = v.getId();
        switch (ID){
            case R.id.layout_card2_country_language:
                Intent intent1 = new Intent(getActivity(), LanActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_card3_version:
                continuousClick(COUNTS, DURATION);
                break;
            case R.id.layout_share:
                Toast.makeText(getActivity(),"share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_favor:
                Toast.makeText(getActivity(),"favor",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_history:
                Toast.makeText(getActivity(),"history",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_card2_templates:
                Intent intent_template = new Intent(getActivity(), TemplateActivity.class);
                startActivity(intent_template);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void setVersionTX(TextView tx){
        if (tx != null) {
            tx.setText(APKVersionCodeUtils.getVerName(getActivity()));
        }
    }

    /**
        version 5秒内点击10次  调出用户信息
    */
    final static int COUNTS = 10;// 点击次数
    final static long DURATION = 5000;// 规定有效时间
    long[] mHits = new long[COUNTS];
    private void continuousClick(int count, long time) {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组

            final String content = SharedPreferencesUtils.getInstance().getString(Constant.UUID) + "\n"
                    + SharedPreferencesUtils.getInstance().getString(Constant.COUNTRY_CODE) + "\n"
                    + SharedPreferencesUtils.getInstance().getString(Constant.LANGUAGE_CODE);
            new MyCommonDialog(getActivity(), R.style.dialog, content,
                    new MyCommonDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {

                            if(confirm){
                                ShareUtils.shareText(getActivity(),content);
                            }

                        }
                    }).setTitle(getActivity().getResources().getString(R.string.dialog_title)).show();

        }
    }

}
