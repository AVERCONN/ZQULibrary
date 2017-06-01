package com.zqu.library.util;

/**
 * Created by chen on 2016/3/14.
 */
public class LibraryNews {
    private String title;

    private int imageId;

    private String date;

    public LibraryNews(String title,  int imageId,String date) {
        this.title=title;
        this.imageId = imageId;
        this.date = date;
    }
    public void setNews(String title,  int imageId) {
        this.title=title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDate() {
        return date;
    }
}
