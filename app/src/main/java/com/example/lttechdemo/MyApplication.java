package com.example.lttechdemo;

import android.app.Application;

/**
 * Created by litong on 2018/2/23.
 */

public class MyApplication extends Application
{
    private static MyApplication context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;
    }

    //获取全局Context对象
    public static MyApplication getContext()
    {
        return context;
    }
}
