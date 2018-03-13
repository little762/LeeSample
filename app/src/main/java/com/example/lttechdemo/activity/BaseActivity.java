package com.example.lttechdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by litong on 2018/2/23.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView();
        initTitle();
        initViews();
    }

    //设置布局
    protected abstract void setContentView();

    //初始化标题栏
    protected abstract void initTitle();

    //初始化控件
    protected abstract void initViews();

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home://返回按钮
                finish();
                break;
        }
        return true;
    }
}
