package com.example.lttechdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;
import com.example.lttechdemo.util.ToastUtils;

import java.util.Arrays;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by litong on 2018/2/22.
 * 主页
 */

public class MainActivity extends BaseActivity
{

    private RecyclerView mRecyclerView;//主列表
    private BaseRecyclerAdapter<Item> mAdapter;//主列表适配器

    private enum Item
    {
        RichEditor("RichEditor富文本", RichEditorActivity.class),
        AppBarLayout("AppBarLayout的使用", AppBarLayoutActivity.class),
        StickyHeader("RecyclerView粘性头部", StickyHeaderActivity.class),
        MultiImage("宫格图控件", MultiImageActivity.class),
        Decoration("自定义RecyclerView分割线", DecorationActivity.class),
        Behavior("自定义CoordinatorLayout.Behavior 实现悬浮动画", BehaviorActivity.class),
        InputSoftFinish("软键盘弹出情况下监听返回键直接退出界面", InputSoftFinishActivity.class);

        public String name;
        public Class<?> clazz;

        Item(String name, Class<?> clazz)
        {
            this.name = name;
            this.clazz = clazz;
        }
    }

    /**
     * 创建选项菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_edit:
                        ToastUtils.show("Edit");
                        break;
                    case R.id.action_share:
                        ToastUtils.show("Share");
                        break;
                    case R.id.action_settings:
                        ToastUtils.show("Settings");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initViews()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        mAdapter = new BaseRecyclerAdapter<Item>(this, android.R.layout.simple_list_item_2, Arrays.asList(Item.values()))
        {
            @Override
            public void convert(BaseViewHolder holder, final Item item)
            {
                TextView tvName = holder.getView(android.R.id.text1);
                TextView tvDesc = holder.getView(android.R.id.text2);
                tvName.setText(item.name());
                tvDesc.setText(item.name);
                tvName.setTextColor(getResources().getColor(R.color.colorTextTitle));
                tvDesc.setTextColor(getResources().getColor(R.color.colorTextAssistant));
                holder.setOnItemClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MainActivity.this, item.clazz));
                        if (item.clazz == InputSoftFinishActivity.class)
                        {
                            overridePendingTransition(R.anim.comment_dialog_in, 0);
                        }
                    }
                });
            }
        };
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

}
