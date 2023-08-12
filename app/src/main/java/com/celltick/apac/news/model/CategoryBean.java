package com.celltick.apac.news.model;

/**
 * Created by Larryx on 3/7/2018.
 */
public class CategoryBean {

    private String categoryId;
    private String categoryName;
    private String translatedName;
    private String imageUrl;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", translatedName='" + translatedName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
