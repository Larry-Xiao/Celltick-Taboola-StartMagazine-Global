package com.celltick.apac.news.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.model.VideoBean;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表的适配器
 *
 */
public class CricketVideoActivityAdapter extends RecyclerView.Adapter<CricketVideoActivityAdapter.ViewHolder> {
    private static final String TAG = CricketVideoActivityAdapter.class.getSimpleName();

    private List<VideoBean> mVideoList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;
    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cricket_video_block;
        TextView cricket_video_title;
        TextView cricket_video_duration;
        SimpleDraweeView cricket_video_photo;
        TextView cricket_video_source;

        public ViewHolder(View view){
            super(view);
            cricket_video_block = view.findViewById(R.id.cricket_video_block);
            cricket_video_title = view.findViewById(R.id.rcv_cricket_video_title);
            cricket_video_duration = view.findViewById(R.id.rcv_cricket_video_duration);
            cricket_video_source = view.findViewById(R.id.rcv_cricket_video_publisher);
            cricket_video_photo = view.findViewById(R.id.rcv_cricket_video_photo);
        }

    }


    public CricketVideoActivityAdapter(List<VideoBean> mVideoList) {
        this.mVideoList = mVideoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cricket_video_item,parent,false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return (ViewHolder) holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        VideoBean cricketVideo= mVideoList.get(position);
        holder.cricket_video_title.setText(cricketVideo.getmTitle());
        holder.cricket_video_duration.setText(TimeUtil.generateTime(Long.valueOf(cricketVideo.getLength())));
        holder.cricket_video_source.setText(cricketVideo.getmSource());
        holder.cricket_video_photo.setImageURI(Uri.parse(cricketVideo.getmImgURL()));
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
        return mVideoList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public void addHeaderItem(List<VideoBean> items){
        mVideoList.addAll(0,items);
        notifyDataSetChanged();
    }

    public void addFooterItem(List<VideoBean> items){
        mVideoList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearList(){
        mVideoList.clear();
        notifyDataSetChanged();
    }

}
