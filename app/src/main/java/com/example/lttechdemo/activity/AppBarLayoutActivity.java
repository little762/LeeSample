package com.example.lttechdemo.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;
import com.example.lttechdemo.inter.OnAppbarExpandListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litong on 2018/2/27.
 * AppBarLayout的使用界面
 */

public class AppBarLayoutActivity extends BaseActivity implements OnAppbarExpandListener
{

    private AppBarLayout appBarLayout;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<String> mAdapter;

    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_appbarlayout);
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
        initDatas();
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.appbar_recyclerview);
        mAdapter = new BaseRecyclerAdapter<String>(AppBarLayoutActivity.this, android.R.layout.simple_list_item_1, data)
        {
            @Override
            public void convert(BaseViewHolder holder, String s)
            {
                TextView textView = holder.getView(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(AppBarLayoutActivity.this, R.color.colorTextContent));
                textView.setText(s);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AppBarLayoutActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RvScrollListener(this));
    }

    /**
     * 判断RecyclerView是否滚动到顶部,从而对AppBarLayout进行伸展
     */
    class RvScrollListener extends RecyclerView.OnScrollListener
    {
        OnAppbarExpandListener onAppbarExpandListener;
        int scrollY = 0;

        public RvScrollListener(OnAppbarExpandListener onAppbarExpandListener)
        {
            this.onAppbarExpandListener = onAppbarExpandListener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            super.onScrollStateChanged(recyclerView, newState);
            //判断快速滚动：当速度大于10且滚到顶部时，让AppBarLayout展开
            if (newState == RecyclerView.SCROLL_STATE_IDLE)
            {
                if (!recyclerView.canScrollVertically(-1) && scrollY < -10)
                {
                    onAppbarExpandListener.onExpand();//通知AppBarLayout伸展
                    scrollY = 0;
                }
            }
            //判断慢速滚动：当滚动到顶部时靠手指拖动后的惯性让RecyclerView处于Fling状态时的速度大于5时，让AppBarLayout展开
            if (newState == RecyclerView.SCROLL_STATE_SETTLING)
            {
                if (!recyclerView.canScrollVertically(-1) && scrollY < -5)
                {
                    onAppbarExpandListener.onExpand();
                    scrollY = 0;
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);
            scrollY = dy;
        }
    }

    @Override
    public void onExpand()
    {
        //AppBarLayout展开
        appBarLayout.setExpanded(true, true);
    }

    /**
     * 初始化测试数据（ItemDecoration）
     */
    List<String> data;

    private void initDatas()
    {
        data = new ArrayList<>();
        for (int i = 0; i < 56; i++)
        {
            data.add(i + " test ");
        }
    }
}
