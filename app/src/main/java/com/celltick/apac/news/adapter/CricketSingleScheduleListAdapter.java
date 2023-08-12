package com.celltick.apac.news.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.model.CricketInfo;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class CricketSingleScheduleListAdapter extends RecyclerView.Adapter<CricketSingleScheduleListAdapter.ViewHolder>{
    private static final String TAG = "CricketWidgetScheduleListAdapter";
    private List<CricketInfo> mCricketGameList;
    private OnItemClickLitener mOnItemClickLitener;
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout cricket_block;
        TextView cricket_competition_date;
        ImageView cricket_game_live;
        TextView cricket_competition_stage;
        TextView cricket_competition_name;
        SimpleDraweeView cricket_team_A_img;
        TextView cricket_team_A_name;
        TextView cricket_game_status;
        TextView cricket_game_scores_A;
        TextView cricket_game_scores_B;
        SimpleDraweeView cricket_team_B_img;
        TextView cricket_team_B_name;

        public ViewHolder(View view){
            super(view);
            cricket_block = view.findViewById(R.id.cricket_block);
            cricket_competition_date = view.findViewById(R.id.cricket_competition_date);
            cricket_game_live = view.findViewById(R.id.cricket_competition_live);
            cricket_competition_stage = view.findViewById(R.id.cricket_competition_stage);
            cricket_competition_name = view.findViewById(R.id.cricket_competition_name);
            cricket_team_A_img = view.findViewById(R.id.cricket_team_A_img);
            cricket_team_A_name = view.findViewById(R.id.cricket_team_A_name);
            cricket_game_status = view.findViewById(R.id.cricket_game_status);
            cricket_game_scores_A = view.findViewById(R.id.cricket_game_scores_A);
            cricket_game_scores_B = view.findViewById(R.id.cricket_game_scores_B);
            cricket_team_B_img = view.findViewById(R.id.cricket_team_B_img);
            cricket_team_B_name = view.findViewById(R.id.cricket_team_B_name);
        }

    }
    public CricketSingleScheduleListAdapter(List<CricketInfo> mCricketGameList){
        this.mCricketGameList = mCricketGameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cricket_single_activity_item,parent,false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return (ViewHolder) holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CricketInfo cricketGame= mCricketGameList.get(position);
        if (cricketGame.getResult().equals("Match Abandoned")){
            holder.itemView.setBackgroundResource(R.drawable.bg_shape_cricket_item_abandoned);
            holder.itemView.setEnabled(false);
            holder.cricket_game_live.setVisibility(View.INVISIBLE);
        }
        String localReadableTime = TimeUtil.parseUTCAMPM2Local(cricketGame.getStartTime());
        holder.cricket_competition_date.setText(localReadableTime.substring(0,10));
//        holder.cricket_game_live.setImageURI(cricketGame.getStartTime());
        holder.cricket_competition_stage.setText(cricketGame.getStage());
        holder.cricket_competition_name.setText(cricketGame.getCompetition());
        holder.cricket_team_A_img.setImageURI(Uri.parse(cricketGame.getTeams().get(0).getLogoURL()));
        holder.cricket_team_A_name.setText(cricketGame.getTeams().get(0).getName());
        if (cricketGame.getStatus().equals("Future")){
            holder.cricket_game_status.setText(localReadableTime.substring(11,16));
        }else {
            holder.cricket_game_status.setText(cricketGame.getStatus());
        }

        if (!cricketGame.getStatus().equals("Future") && !cricketGame.getResult().equals("Match Abandoned")){
            holder.cricket_game_scores_A.setText(cricketGame.getScores().get(0).getScore());
            holder.cricket_game_scores_B.setText(cricketGame.getScores().get(1).getScore());
        }

        holder.cricket_team_B_img.setImageURI(Uri.parse(cricketGame.getTeams().get(1).getLogoURL()));
        holder.cricket_team_B_name.setText(cricketGame.getTeams().get(1).getName());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(pos);
                }
            });
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        return mCricketGameList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}