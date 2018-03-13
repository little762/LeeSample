package com.example.lttechdemo.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;
import com.example.lttechdemo.view.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by litong on 2018/2/23.
 * 自定义RecyclerView.ItemDecoration界面
 */

public class DecorationActivity extends BaseActivity
{
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<Model> mAdapter;//列表适配器

    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_decoration);
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
        mRecyclerView = (RecyclerView) findViewById(R.id.decoration_recyclerview);
        mAdapter = new BaseRecyclerAdapter<Model>(this, R.layout.item_decoration, loadModels())
        {
            @Override
            public void convert(BaseViewHolder holder, final Model item)
            {
                TextView nameTv = holder.getView(R.id.name_textview);
                TextView sellTv = holder.getView(R.id.sell_textview);
                TextView buyTv = holder.getView(R.id.buy_textview);
                TextView percentTv = holder.getView(R.id.percent_textview);
                nameTv.setText(item.name);
                sellTv.setText(item.sell);
                buyTv.setText(item.buy);
                percentTv.setText(item.percent);
            }
        };
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 模拟数据
     */
    private class Model
    {
        String name;
        String sell;
        String buy;
        String percent;
    }

    private List<Model> loadModels()
    {
        return Arrays.asList(
                new Model()
                {{
                    this.name = "现货黄金";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }}, new Model()
                {{
                    this.name = "现货白银";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "美原油";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "现货黄金";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "现货白银";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "美原油";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "现货黄金";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }},
                new Model()
                {{
                    this.name = "现货白银";
                    this.sell = "1250.68";
                    this.buy = "-4.18";
                    this.percent = "-0.23%";
                }});
    }
}
