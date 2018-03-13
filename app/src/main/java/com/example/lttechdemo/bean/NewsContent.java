package com.example.lttechdemo.bean;

import java.util.List;

/**
 * Created by litong on 2017/11/14.
 */

public class NewsContent {

    private String iconUrl;
    private String userName;
    private String time;
    private String location;
    private String userType;
    private String content;
    private List<NewsImage> imageurls;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<NewsImage> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<NewsImage> imageurls) {
        this.imageurls = imageurls;
    }

    @Override
    public String toString()
    {
        return "NewsContent{" +
                "iconUrl='" + iconUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", userType='" + userType + '\'' +
                ", content='" + content + '\'' +
                ", imageurls=" + imageurls +
                '}';
    }
}
