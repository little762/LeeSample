package com.example.lttechdemo.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;
import com.example.lttechdemo.adapter.StickyHeaderAdapter;
import com.example.lttechdemo.bean.GroupInfo;
import com.example.lttechdemo.util.ToastUtils;
import com.example.lttechdemo.view.StickySectionDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by litong on 2018/2/24.
 * 粘性头部界面
 */

public class StickyHeaderActivity extends BaseActivity
{

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private StickyHeaderAdapter mAdapter;
    private BaseRecyclerAdapter<String> baseRecyclerAdapter;
    private LinearLayout headerView;
    private TextView headerViewText;

    @Override

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_stickyheader, menu);
        return true;
    }

    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_stickyheader);
    }

    @Override
    protected void initTitle()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.custom_anim:
                        headerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView1.setVisibility(View.GONE);
                        break;
                    case R.id.decoration_anim:
                        headerView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        mRecyclerView1.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);//返回按钮
            actionBar.setDisplayShowTitleEnabled(false);//去掉toolbar标题
        }
    }

    @Override
    protected void initViews()
    {
        headerView = (LinearLayout) findViewById(R.id.sticky_header);
        headerViewText = (TextView) findViewById(R.id.header_textview);
        headerViewText.setText(getDatas().get(0).getHeader());
        initDatas();
        initRecycler();
        initRecycler1();
    }

    /**
     * 初始化自定义动画的RecyclerView
     */
    private void initRecycler()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.stickyheader_recyclerview);
        mAdapter = new StickyHeaderAdapter(getDatas(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RvScrollListener());
    }

    /**
     * 初始化ItemDecoration的RecyclerView
     */
    private void initRecycler1()
    {
        mRecyclerView1 = (RecyclerView) findViewById(R.id.stickyheader_recyclerview1);

        StickySectionDecoration.GroupInfoCallback callback = new StickySectionDecoration.GroupInfoCallback()
        {
            @Override
            public GroupInfo getGroupInfo(int position)
            {
                //分组逻辑，这里为了测试每5个数据为一组
                //大家可以在实际开发中替换为真正的需求逻辑
                int groupId = position / 5;
                int index = position % 5;
                GroupInfo groupInfo = new GroupInfo(groupId, groupId + "");
                groupInfo.setGroupLength(5);
                groupInfo.setPosition(index);
                return groupInfo;
            }
        };
        baseRecyclerAdapter = new BaseRecyclerAdapter<String>(StickyHeaderActivity.this, android.R.layout.simple_list_item_1, data)
        {
            @Override
            public void convert(BaseViewHolder holder, String s)
            {
                TextView textView = holder.getView(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(StickyHeaderActivity.this, R.color.colorPrimary));
                textView.setText(s);
            }
        };
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(StickyHeaderActivity.this));
        mRecyclerView1.setAdapter(baseRecyclerAdapter);
        mRecyclerView1.addItemDecoration(new StickySectionDecoration(StickyHeaderActivity.this, callback));
    }

    /**
     * 监听RecyclerView滚动，实现粘性头部
     */
    private class RvScrollListener extends RecyclerView.OnScrollListener
    {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);

            View stickyInfoView = recyclerView.getChildAt(0);//获取头部View
            if (stickyInfoView != null && stickyInfoView.getContentDescription() != null)
            {
                headerView.setVisibility(View.VISIBLE);
                headerViewText.setText(String.valueOf(stickyInfoView.getContentDescription()));
            }
            View transInfoView = recyclerView.findChildViewUnder(headerView.getMeasuredWidth() / 2
                    , headerView.getMeasuredHeight() + 1);//位于headerView下方的itemView（该坐标是否在itemView内）
            if (transInfoView != null && transInfoView.getTag() != null)
            {
                int tag = (int) transInfoView.getTag();
                int deltaY = transInfoView.getTop() - headerView.getMeasuredHeight();
                if (tag == StickyHeaderAdapter.HAS_STICKY_VIEW)//当Item包含粘性头部一类时
                {
                    if (transInfoView.getTop() > 0)//当Item还未移动出顶部时
                    {
                        headerView.setTranslationY(deltaY);
                    } else//当Item移出顶部，粘性头部复原
                    {
                        headerView.setTranslationY(0);
                    }
                } else//当Item不包含粘性头部时
                {
                    headerView.setTranslationY(0);
                }
            }
        }
    }

    /**
     * 模拟数据（自定义动画）
     */
    public class Model
    {
        private String header;
        private String content;
        private String time;

        public String getHeader()
        {
            return header;
        }

        public void setHeader(String header)
        {
            this.header = header;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public String getTime()
        {
            return time;
        }

        public void setTime(String time)
        {
            this.time = time;
        }
    }

    private List<Model> getDatas()
    {
        List<Model> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            Model model = new Model();
            model.setHeader("2018.01.01");
            model.setContent("下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦");
            model.setTime("18:00");
            dataList.add(model);
        }
        for (int i = 0; i < 5; i++)
        {
            Model model = new Model();
            model.setHeader("2018.02.02");
            model.setContent("上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了");
            model.setTime("08:00");
            dataList.add(model);
        }
        for (int i = 0; i < 5; i++)
        {
            Model model = new Model();
            model.setHeader("2018.03.03");
            model.setContent("下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦");
            model.setTime("18:00");
            dataList.add(model);
        }
        for (int i = 0; i < 5; i++)
        {
            Model model = new Model();
            model.setHeader("2018.04.04");
            model.setContent("上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了上班了苦逼了");
            model.setTime("08:00");
            dataList.add(model);
        }
        for (int i = 0; i < 5; i++)
        {
            Model model = new Model();
            model.setHeader("2018.05.05");
            model.setContent("下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦下班啦吃饭啦");
            model.setTime("18:00");
            dataList.add(model);
        }
        return dataList;
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
