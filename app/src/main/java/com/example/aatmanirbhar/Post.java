package com.example.aatmanirbhar;

import java.io.Serializable;

public class Post implements Serializable {
    String title;
    String desc;
    String applink;
    String url;

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", applink='" + applink + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getApplink() {
        return applink;
    }

    public void setApplink(String applink) {
        this.applink = applink;
    }
}
