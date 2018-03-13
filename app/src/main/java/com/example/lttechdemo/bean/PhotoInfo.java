package com.example.lttechdemo.bean;

/**
 * Created by litong on 2016/11/17.
 * 九宫格图片实体类
 */
public class PhotoInfo
{

    public String url;
    public int w;
    public int h;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getW()
    {
        return w;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    @Override
    public String toString()
    {
        return "PhotoInfo{" +
                "url='" + url + '\'' +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
