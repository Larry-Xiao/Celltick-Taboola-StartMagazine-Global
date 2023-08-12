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
import com.celltick.apac.news.util.TimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表的适配器
 *
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SearchAdapter.class.getSimpleName();

    //新闻列表
    private List<ArticleBean> mArticleList;

    //context
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnItemClickLitener mOnItemClickLitener;
    private RecyclerView.ViewHolder mViewHolder;
    private View mView0,mView1,mView2,mView3,mView4;
    private String mPreFirstArticleTimeStmp;

    public SearchAdapter(Context context, List<ArticleBean> mArticleList) {
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
            case Const.ARTICLE_TYPE_0:
                mView0 = mLayoutInflater.inflate(R.layout.item_article_0image, parent, false);
                mViewHolder = new ItemArticleWith0ImageViewHolder(mView0);
                return mViewHolder;
            case Const.ARTICLE_TYPE_1:
                mView1 = mLayoutInflater.inflate(R.layout.item_article_1or2images_normal, parent, false);
                mViewHolder = new ItemArticleWith1_2ImagesNormalViewHolder(mView1);
                return mViewHolder;
            case Const.ARTICLE_TYPE_2:
                mView2 = mLayoutInflater.inflate(R.layout.item_article_1or2images_normal, parent, false);
                mViewHolder = new ItemArticleWith1_2ImagesNormalViewHolder(mView2);
                return mViewHolder;
            case Const.ARTICLE_TYPE_3:
                mView3 = mLayoutInflater.inflate(R.layout.item_article_3images, parent, false);
                mViewHolder = new ItemArticleWith3ImagesViewHolder(mView3);
                return mViewHolder;
            case Const.ARTICLE_TYPE_4:
                mView4 = mLayoutInflater.inflate(R.layout.item_article_1or2images_big, parent, false);
                mViewHolder = new ItemArticleWith1_2ImagesBigViewHolder(mView4);
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
        List<String> imageUrls = article.getAdditionalImages();

        if (position == 0){
            mPreFirstArticleTimeStmp = article.getPriviousFirstArticleDate();
        }

        if (holder instanceof ItemArticleWith0ImageViewHolder) {
            ItemArticleWith0ImageViewHolder newHolder = (ItemArticleWith0ImageViewHolder) holder;
            newHolder.mArticle0ImageTitle.setText(article.getTitle());
//            newHolder.mArticle0ImageDate.setText(article.getPublishedDate());
            newHolder.mArticle0ImageDate.setText(TimeUtil.getTimeFormatText(TimeUtil.stampToDate(Long.valueOf(article.getPublishedDate()))));
            newHolder.mArticle0ImageAuthorName.setText(article.getPublisherName());

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

        } else if (holder instanceof ItemArticleWith1_2ImagesNormalViewHolder) {
            ItemArticleWith1_2ImagesNormalViewHolder newHolder = (ItemArticleWith1_2ImagesNormalViewHolder) holder;
            newHolder.rcvArticlePhoto.setImageURI(Uri.parse(article.getMainImageThumbnailURL()));
            newHolder.rcvArticleTitle.setText(article.getTitle());
//            newHolder.rcvArticleDate.setText(TimeUtil.timeToDate_ms(article.getPublishedDate()));
            newHolder.rcvArticleDate.setText(TimeUtil.getTimeFormatText(TimeUtil.stampToDate(Long.valueOf(article.getPublishedDate()))));
            newHolder.rcvArticlePublisher.setText(article.getPublisherName());
            newHolder.rcvArticleViews.setText(article.getViews()+"");

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

        } else if (holder instanceof ItemArticleWith1_2ImagesBigViewHolder) {
            ItemArticleWith1_2ImagesBigViewHolder newHolder = (ItemArticleWith1_2ImagesBigViewHolder) holder;
            newHolder.rcvArticlePhoto.setImageURI(Uri.parse(article.getMainImageURL()));
//            newHolder.rcvArticlePhoto.setImageURI(Uri.parse(article.getMainImageThumbnailURL()));
//            newHolder.rcvArticlePhoto.setImageURI(Uri.parse(article.getAdditionalImages().get(0)));
            newHolder.rcvArticleTitle.setText(article.getTitle());
//            newHolder.rcvArticleDate.setText(TimeUtil.timeToDate_ms(article.getPublishedDate()));
            newHolder.rcvArticleDate.setText(TimeUtil.getTimeFormatText(TimeUtil.stampToDate(Long.valueOf(article.getPublishedDate()))));
            newHolder.rcvArticlePublisher.setText(article.getPublisherName());
            newHolder.rcvArticleViews.setText(article.getViews()+"");

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

        } else if (holder instanceof ItemArticleWith3ImagesViewHolder) {
            ItemArticleWith3ImagesViewHolder newHolder = (ItemArticleWith3ImagesViewHolder) holder;
            newHolder.articleTitle.setText(article.getTitle());
            newHolder.articlePic1.setImageURI(Uri.parse(imageUrls.get(0)));
            newHolder.articlePic2.setImageURI(Uri.parse(imageUrls.get(1)));
            newHolder.articlePic3.setImageURI(Uri.parse(imageUrls.get(2)));

//            newHolder.mItemArticle3ImagesDate.setText(article.getPublishedDate());
            newHolder.mItemArticle3ImagesDate.setText(TimeUtil.getTimeFormatText(TimeUtil.stampToDate(Long.valueOf(article.getPublishedDate()))));
            newHolder.mItemArticle3ImagesPublisher.setText(article.getPublisherName());

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

        ArticleBean article = mArticleList.get(position);
        int images_num = article.getAdditionalImages().size();
        if (images_num == 0) {
            return Const.ARTICLE_TYPE_1;
        } else if (images_num == 1 || images_num == 2) {
            if (article.getViews() >= 500) {
                return Const.ARTICLE_TYPE_4;
            }else {
                return Const.ARTICLE_TYPE_1;
            }
        } else if (images_num >= 3) {
            return Const.ARTICLE_TYPE_3;
        } else {
            return -1;
        }

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class ItemArticleWith0ImageViewHolder extends RecyclerView.ViewHolder {

        TextView mArticle0ImageTitle;
        TextView mArticle0ImageAuthorName;
        TextView mArticle0ImageDate;

        public ItemArticleWith0ImageViewHolder(View itemView) {
            super(itemView);
            mArticle0ImageTitle = (TextView)itemView.findViewById(R.id.item_article_0_image_title);
            mArticle0ImageAuthorName = (TextView)itemView.findViewById(R.id.item_article_0_image_publisher);
            mArticle0ImageDate = (TextView)itemView.findViewById(R.id.item_article_0_image_date);


        }
    }

    class ItemArticleWith1_2ImagesNormalViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView rcvArticlePhoto;
        TextView rcvArticleTitle;
        TextView rcvArticlePublisher;
        TextView rcvArticleDate;
        TextView rcvArticleViews;

        public ItemArticleWith1_2ImagesNormalViewHolder(View itemView) {
            super(itemView);

            rcvArticlePhoto = (SimpleDraweeView)itemView.findViewById(R.id.rcv_video_photo);
            rcvArticleTitle = (TextView)itemView.findViewById(R.id.rcv_video_title);
            rcvArticlePublisher = (TextView)itemView.findViewById(R.id.rcv_video_publisher);
            rcvArticleDate = (TextView)itemView.findViewById(R.id.rcv_video_date);
            rcvArticleViews = (TextView)itemView.findViewById(R.id.rcv_video_views);
        }
    }

    class ItemArticleWith1_2ImagesBigViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView rcvArticlePhoto;
        TextView rcvArticleTitle;
        TextView rcvArticlePublisher;
        TextView rcvArticleDate;
        TextView rcvArticleViews;

        public ItemArticleWith1_2ImagesBigViewHolder(View itemView) {
            super(itemView);

            rcvArticlePhoto = (SimpleDraweeView)itemView.findViewById(R.id.rcv_video_photo);
            rcvArticleTitle = (TextView)itemView.findViewById(R.id.rcv_video_title);
            rcvArticlePublisher = (TextView)itemView.findViewById(R.id.rcv_video_publisher);
            rcvArticleDate = (TextView)itemView.findViewById(R.id.rcv_video_date);
            rcvArticleViews = (TextView)itemView.findViewById(R.id.rcv_video_views);
        }
    }

    class ItemArticleWith3ImagesViewHolder extends RecyclerView.ViewHolder {


        TextView articleTitle;
        SimpleDraweeView articlePic1;
        SimpleDraweeView articlePic2;
        SimpleDraweeView articlePic3;
        TextView mItemArticle3ImagesPublisher;
        TextView mItemArticle3ImagesDate;

        public ItemArticleWith3ImagesViewHolder(View itemView) {
            super(itemView);
            articleTitle = (TextView)itemView.findViewById(R.id.article_title);
            articlePic1 = (SimpleDraweeView)itemView.findViewById(R.id.article_pic1);
            articlePic2 = (SimpleDraweeView)itemView.findViewById(R.id.article_pic2);
            articlePic3 = (SimpleDraweeView)itemView.findViewById(R.id.article_pic3);
            mItemArticle3ImagesPublisher = (TextView)itemView.findViewById(R.id.item_article_3_images_publisher);
            mItemArticle3ImagesDate = (TextView)itemView.findViewById(R.id.item_article_3_images_date);
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

    public void clearList(){
        mArticleList.clear();
        notifyDataSetChanged();
    }

    public String getPreFirstArtivleStmp(){
        return mPreFirstArticleTimeStmp;
    }


}
