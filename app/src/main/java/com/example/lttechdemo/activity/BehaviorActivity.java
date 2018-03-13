package com.example.lttechdemo.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by litong on 2018/2/23.
 * 自定义CoordinatorLayout的Behavior界面
 */

public class BehaviorActivity extends BaseActivity
{

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<String> mAdapter;

    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_behavior);
    }

    @Override
    protected void initTitle()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);//返回按钮
            actionBar.setDisplayShowTitleEnabled(false);//去掉toolbar标题
        }
    }

    @Override
    protected void initViews()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.behavior_recyclerview);
        mAdapter = new BaseRecyclerAdapter<String>(this, android.R.layout.simple_list_item_1, getDatas())
        {
            @Override
            public void convert(BaseViewHolder holder, String s)
            {
                TextView tvName = holder.getView(android.R.id.text1);
                tvName.setText(s);
                tvName.setTextSize(18);
                tvName.setTextColor(getResources().getColor(R.color.colorTextTitle));
            }
        };
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 模拟数据
     */
    private List<String> getDatas()
    {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            dataList.add("这是第" + (i + 1) + "条数据");
        }
        return dataList;
    }

}
