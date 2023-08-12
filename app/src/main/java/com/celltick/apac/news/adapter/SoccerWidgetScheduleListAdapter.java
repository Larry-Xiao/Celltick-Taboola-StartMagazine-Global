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
import com.celltick.apac.news.model.SoccerInfo;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class SoccerWidgetScheduleListAdapter extends RecyclerView.Adapter<SoccerWidgetScheduleListAdapter.ViewHolder>{
    private static final String TAG = SoccerWidgetScheduleListAdapter.class.getSimpleName();
    private List<SoccerInfo> mSoccerGameList;
    private OnItemClickLitener mOnItemClickLitener;
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout soccer_block;
        TextView soccer_competition_date;
        ImageView soccer_game_live;
        TextView soccer_competition_stage;
        TextView soccer_competition_name;
        SimpleDraweeView soccer_team_A_img;
        TextView soccer_team_A_name;
        TextView soccer_game_status;
        TextView soccer_game_scores_A;
        TextView soccer_game_scores_B;
        SimpleDraweeView soccer_team_B_img;
        TextView soccer_team_B_name;

        public ViewHolder(View view){
            super(view);
            soccer_block = view.findViewById(R.id.soccer_block);
            soccer_competition_date = view.findViewById(R.id.soccer_competition_date);
            soccer_game_live = view.findViewById(R.id.soccer_competition_live);
            soccer_competition_stage = view.findViewById(R.id.soccer_competition_stage);
            soccer_competition_name = view.findViewById(R.id.soccer_competition_name);
            soccer_team_A_img = view.findViewById(R.id.soccer_team_A_img);
            soccer_team_A_name = view.findViewById(R.id.soccer_team_A_name);
            soccer_game_status = view.findViewById(R.id.soccer_game_status);
            soccer_game_scores_A = view.findViewById(R.id.soccer_game_scores_A);
            soccer_game_scores_B = view.findViewById(R.id.soccer_game_scores_B);
            soccer_team_B_img = view.findViewById(R.id.soccer_team_B_img);
            soccer_team_B_name = view.findViewById(R.id.soccer_team_B_name);
        }

    }
    public SoccerWidgetScheduleListAdapter(List<SoccerInfo> mSoccerGameList){
        this.mSoccerGameList = mSoccerGameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soccer_widget_item,parent,false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return (ViewHolder) holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SoccerInfo soccerGame= mSoccerGameList.get(position);
        holder.soccer_competition_date.setText(soccerGame.getGameTime().substring(0,9));
        holder.soccer_competition_name.setText(soccerGame.getStage());
        holder.soccer_team_A_img.setImageURI(Uri.parse(soccerGame.getTeams().get(0).getTeamLogo()));
        holder.soccer_team_A_name.setText(soccerGame.getTeams().get(0).getTeamName());
        if (soccerGame.getStatus().equals("FUTURE")){
            holder.soccer_game_status.setText(soccerGame.getGameTime().substring(11,15));
        }else {
            holder.soccer_game_status.setText(soccerGame.getStatus());
        }

        holder.soccer_game_scores_A.setText(soccerGame.getScores().get(0).getScr());
        holder.soccer_game_scores_B.setText(soccerGame.getScores().get(1).getScr());

        holder.soccer_team_B_img.setImageURI(Uri.parse(soccerGame.getTeams().get(1).getTeamLogo()));
        holder.soccer_team_B_name.setText(soccerGame.getTeams().get(1).getTeamName());

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
        return mSoccerGameList.size();
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