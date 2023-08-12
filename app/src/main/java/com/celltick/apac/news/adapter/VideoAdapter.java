package com.celltick.apac.news.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.constant.Const;
import com.celltick.apac.news.model.VideoBean;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.celltick.apac.news.util.TimeUtil;
import com.celltick.apac.news.widget.SmartSimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表的适配器
 *
 */
public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();

    //新闻列表
    private List<VideoBean> mVideoList;

    //context
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnItemClickLitener mOnItemClickLitener;
    private RecyclerView.ViewHolder mViewHolder;
    private View mView;
    private String mPreFirstItemTime;
    private String mNextContentItem;


    public VideoAdapter(Context context, List<VideoBean> mVideoList) {
        this.context = context;
        if (mVideoList == null) {
            this.mVideoList = new ArrayList<>();
        } else {
            this.mVideoList = mVideoList;
        }
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case Const.ARTICLE_TYPE_1:
                mView = mLayoutInflater.inflate(R.layout.item_video_layout, parent, false);
                mViewHolder = new ItemArticleWith1_2ImagesNormalViewHolder(mView);
                return mViewHolder;
        }

    }

    /**
     * 当Item 超出屏幕后，就会重新执行onBindViewHolder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        VideoBean video = mVideoList.get(position);

        if (position == 0){
            mPreFirstItemTime = video.getmFirstItemDate();
        }

        if (holder instanceof ItemArticleWith1_2ImagesNormalViewHolder) {
            ItemArticleWith1_2ImagesNormalViewHolder newHolder = (ItemArticleWith1_2ImagesNormalViewHolder) holder;
            newHolder.rcvVideoPhoto.setImageURI(Uri.parse(video.getmImgURL()));
//            Glide.with(context).load(video.getmImgURL()).transform(new GlideRoundTransform(context,10)).centerCrop().into(newHolder.rcvVideoPhoto);
//            newHolder.videoVendorLogo.setImageURI(Uri.parse(video.getVideoVendorLogoURL()));
//            Glide.with(context).load(video.getVideoVendorLogoURL()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.ic_default_loading).into(newHolder.videoVendorLogo);

            newHolder.rcvVideoTitle.setText(video.getmTitle());
            newHolder.rcvVideoDate.setText(TimeUtil.timeToDate_ms(video.getmPubDate()));
            newHolder.rcvVideoPublisher.setText(video.getmSource());
            newHolder.rcvVideoViews.setText("Views: "+video.getViews());
            newHolder.rcvVideoDuration.setText(TimeUtil.generateTime(Long.valueOf(video.getLength())));

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
    }


    @Override
    public int getItemCount() {
        if (mVideoList != null) {
            return mVideoList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {

        return Const.ARTICLE_TYPE_1;

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class ItemArticleWith1_2ImagesNormalViewHolder extends RecyclerView.ViewHolder {

        SmartSimpleDraweeView rcvVideoPhoto;
        ImageView videoVendorLogo;
        TextView rcvVideoTitle;
        TextView rcvVideoPublisher;
        TextView rcvVideoDate;
        TextView rcvVideoViews;
        TextView rcvVideoDuration;

        public ItemArticleWith1_2ImagesNormalViewHolder(View itemView) {
            super(itemView);

            rcvVideoPhoto = (SmartSimpleDraweeView)itemView.findViewById(R.id.rcv_video_photo);
            videoVendorLogo = (ImageView)itemView.findViewById(R.id.ic_video_vendor);
            rcvVideoTitle = (TextView)itemView.findViewById(R.id.rcv_video_title);
            rcvVideoPublisher = (TextView)itemView.findViewById(R.id.rcv_video_publisher);
            rcvVideoDate = (TextView)itemView.findViewById(R.id.rcv_video_date);
            rcvVideoViews = (TextView)itemView.findViewById(R.id.rcv_video_views);
            rcvVideoDuration = (TextView)itemView.findViewById(R.id.rcv_video_duration);
        }
    }


    public void addHeaderItem(List<VideoBean> items){
        mVideoList.addAll(0,items);
        notifyDataSetChanged();
    }

    public void addFooterItem(List<VideoBean> items){
        mVideoList.addAll(items);
        notifyDataSetChanged();
    }

    public String getPreFirstVideoPublishedDate(){
        return mPreFirstItemTime;
    }
    public String getNextContentItem(){return mNextContentItem;}


}
