package com.celltick.apac.news.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.adapter.GridViewCricketLeagueAdapter;

public class CricketLeagueDialog implements View.OnClickListener {

    private Context mContext;
    private Callback mCallback;
    private boolean mCanDialogShow;

    private Dialog mLeagueOptionDialog;
    private GridView mCricketLeagueGrid;
    private GridViewCricketLeagueAdapter adapter;
    private String[] leagues = {"CWC","DPL","CSA","IWC","ICC","IPL","BPL","ACA","MTN","MSL","4DS","TWC","BCL","NCL","CAC",};
    private String league_name = "CWC";


    /**
     * 时间选择结果回调接口
     */
    public interface Callback {
        void onCricketLeagueSelected(String league_name);
    }

    public CricketLeagueDialog(Context context, Callback callback) {
        if (context == null || callback == null) {
            mCanDialogShow = false;
            return;
        }
        mContext = context;
        mCallback = callback;
        initView();
        initData();
        mCanDialogShow = true;
    }

    private void initView() {
        mLeagueOptionDialog = new Dialog(mContext, R.style.date_picker_dialog);
        mLeagueOptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLeagueOptionDialog.setContentView(R.layout.dialog_cricket_league_option);

        Window window = mLeagueOptionDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        mLeagueOptionDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        mLeagueOptionDialog.findViewById(R.id.tv_confirm).setOnClickListener(this);

        mCricketLeagueGrid = mLeagueOptionDialog.findViewById(R.id.grid_cricket_league_option);
        adapter = new GridViewCricketLeagueAdapter(mContext);
        adapter.setStr(leagues);
        mCricketLeagueGrid.setAdapter(adapter);
        mCricketLeagueGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                adapter.setSelection(position); //传值更新
                adapter.notifyDataSetChanged(); //每一次点击通知adapter重新渲染
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                break;

            case R.id.tv_confirm:
                if (mCallback != null) {
                    mCallback.onCricketLeagueSelected(adapter.getLeague_name());
                }
                break;
        }

        if (mLeagueOptionDialog != null && mLeagueOptionDialog.isShowing()) {
            mLeagueOptionDialog.dismiss();
        }
    }


    private void initData() {
    }




    public void show(String dateStr) {
        mLeagueOptionDialog.show();
    }

    private boolean canShow() {
        return mCanDialogShow && mLeagueOptionDialog != null;
    }


    public void setCancelable(boolean cancelable) {
        mLeagueOptionDialog.setCancelable(cancelable);
    }

}
