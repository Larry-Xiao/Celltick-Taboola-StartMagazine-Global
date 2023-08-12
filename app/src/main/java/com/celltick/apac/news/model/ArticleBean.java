package com.celltick.apac.news.model;

import java.util.List;

/**
 * Created by Larryx on 3/7/2018.
 */
public class ArticleBean {

    private String uniqueKey;
    private String title;
    private String publishedDate;
    private String categoryName;
    private String articleURL;
    private String publisherName;
    private int id;
    private List<String> imageUrls;
    private String mainImageURL;
    private String mainImageThumbnailURL;
    private List<String> additionalImages;
    private String priviousFirstArticleDate;
    private int views;
    private String nextContentItem;
    private String cpLogoURL;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public int getId() {
        return id;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getMainImageURL() {
        return mainImageURL;
    }

    public void setMainImageURL(String mainImageURL) {
        this.mainImageURL = mainImageURL;
    }

    public String getMainImageThumbnailURL() {
        return mainImageThumbnailURL;
    }

    public void setMainImageThumbnailURL(String mainImageThumbnailURL) {
        this.mainImageThumbnailURL = mainImageThumbnailURL;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }

    public String getPriviousFirstArticleDate() {
        return priviousFirstArticleDate;
    }

    public void setPriviousFirstArticleDate(String priviousFirstArticleDate) {
        this.priviousFirstArticleDate = priviousFirstArticleDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getNextContentItem() {
        return nextContentItem;
    }

    public void setNextContentItem(String nextContentItem) {
        this.nextContentItem = nextContentItem;
    }

    public String getCpLogoURL() {
        return cpLogoURL;
    }

    public void setCpLogoURL(String cpLogoURL) {
        this.cpLogoURL = cpLogoURL;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "uniqueKey='" + uniqueKey + '\'' +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", articleURL='" + articleURL + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", id=" + id +
                ", imageUrls=" + imageUrls +
                ", mainImageURL='" + mainImageURL + '\'' +
                ", mainImageThumbnailURL='" + mainImageThumbnailURL + '\'' +
                ", additionalImages=" + additionalImages +
                ", priviousFirstArticleDate='" + priviousFirstArticleDate + '\'' +
                ", views=" + views +
                ", nextContentItem='" + nextContentItem + '\'' +
                ", cpLogoURL='" + cpLogoURL + '\'' +
                '}';
    }
}
