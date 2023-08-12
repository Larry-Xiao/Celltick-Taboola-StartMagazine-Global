package com.celltick.apac.news.model;

/**
 * Created by Larryx on 3/7/2018.
 */
public class VideoBean {

    private String mTitle;
    private String mImgURL;
    private String mVideoURL;
    private String mSource;
    private String mPubDate;
    private String mID;
    private String mFirstItemDate;
    private String length;
    private String views;
    private String videoVendorLogoURL;

    public String getVideoVendorLogoURL() {
        return videoVendorLogoURL;
    }

    public void setVideoVendorLogoURL(String videoVendorLogoURL) {
        this.videoVendorLogoURL = videoVendorLogoURL;
    }

    public String getmPubDate() {
        return mPubDate;
    }

    public void setmPubDate(String mPubDate) {
        this.mPubDate = mPubDate;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getmVideoURL() {
        return mVideoURL;
    }

    public void setmVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    public String getmFirstItemDate() {
        return mFirstItemDate;
    }

    public void setmFirstItemDate(String mFirstItemDate) {
        this.mFirstItemDate = mFirstItemDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "mTitle='" + mTitle + '\'' +
                ", mImgURL='" + mImgURL + '\'' +
                ", mVideoURL='" + mVideoURL + '\'' +
                ", mSource='" + mSource + '\'' +
                ", mPubDate='" + mPubDate + '\'' +
                ", mID='" + mID + '\'' +
                ", mFirstItemDate='" + mFirstItemDate + '\'' +
                ", length='" + length + '\'' +
                ", views='" + views + '\'' +
                ", videoVendorLogoURL='" + videoVendorLogoURL + '\'' +
                '}';
    }
}
