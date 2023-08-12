package com.celltick.apac.news.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celltick.apac.news.R;
import com.celltick.apac.news.constant.Const;
import com.celltick.apac.news.model.ArticleBean;
import com.celltick.apac.news.util.OnItemClickLitener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表的适配器
 *
 */
public class FWCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FWCAdapter.class.getSimpleName();

    //新闻列表
    private List<ArticleBean> mArticleList;

    //context
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnItemClickLitener mOnItemClickLitener;
    private RecyclerView.ViewHolder mViewHolder;
    private View mView;
    private String mPreFirstArticleTime;
    private String mNextContentItem;

    public FWCAdapter(Context context, List<ArticleBean> mArticleList) {
        this.context = context;
        if (mArticleList == null) {
            this.mArticleList = new ArrayList<>();
        } else {
            this.mArticleList = mArticleList;
        }
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case Const.ARTICLE_TYPE_1:
                mView = mLayoutInflater.inflate(R.layout.item_article_1or2images_normal, parent, false);
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

        ArticleBean article = mArticleList.get(position);
        mNextContentItem = article.getNextContentItem();

        if (position == 0){
            mPreFirstArticleTime = article.getPriviousFirstArticleDate();
        }

        if (holder instanceof ItemArticleWith1_2ImagesNormalViewHolder) {
            ItemArticleWith1_2ImagesNormalViewHolder newHolder = (ItemArticleWith1_2ImagesNormalViewHolder) holder;
            newHolder.rcvArticlePhoto.setImageURI(Uri.parse(article.getMainImageThumbnailURL()));
            newHolder.rcvArticleTitle.setText(article.getTitle());
            newHolder.rcvArticleDate.setText(article.getPublishedDate());
            newHolder.rcvArticlePublisher.setText(article.getPublisherName());

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
        if (mArticleList != null) {
            return mArticleList.size();
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

        SimpleDraweeView rcvArticlePhoto;
        TextView rcvArticleTitle;
        TextView rcvArticlePublisher;
        TextView rcvArticleDate;

        public ItemArticleWith1_2ImagesNormalViewHolder(View itemView) {
            super(itemView);

            rcvArticlePhoto = (SimpleDraweeView)itemView.findViewById(R.id.rcv_video_photo);
            rcvArticleTitle = (TextView)itemView.findViewById(R.id.rcv_video_title);
            rcvArticlePublisher = (TextView)itemView.findViewById(R.id.rcv_video_publisher);
            rcvArticleDate = (TextView)itemView.findViewById(R.id.rcv_video_date);
        }
    }


    public void addHeaderItem(List<ArticleBean> items){
        mArticleList.addAll(0,items);
        notifyDataSetChanged();
    }

    public void addFooterItem(List<ArticleBean> items){
        mArticleList.addAll(items);
        notifyDataSetChanged();
    }

    public String getPreFirstArticlePublishedDate(){
        return mPreFirstArticleTime;
    }
    public String getNextContentItem(){return mNextContentItem;}


}
