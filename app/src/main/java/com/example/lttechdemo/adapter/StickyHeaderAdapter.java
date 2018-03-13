package com.example.lttechdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.activity.StickyHeaderActivity;

import java.util.List;

/**
 * Created by litong on 2018/2/26.
 * 粘性头部适配器
 */

public class StickyHeaderAdapter extends RecyclerView.Adapter<StickyHeaderAdapter.ViewHolder>
{
    private List<StickyHeaderActivity.Model> dataList;
    private Context mContext;

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    public StickyHeaderAdapter(List<StickyHeaderActivity.Model> dataList, Context mContext)
    {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_stickyheader, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        StickyHeaderActivity.Model model = dataList.get(position);
        holder.headerTv.setText(model.getHeader());
        holder.timeTv.setText(model.getTime());
        holder.contentTv.setText(model.getContent());
        holder.itemView.setContentDescription(model.getHeader());
        if (position == 0)
        {
            holder.headerLayout.setVisibility(View.VISIBLE);
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else
        {
            if (model.getHeader().equals(dataList.get(position - 1).getHeader())) //当前Item头部与上一个Item头部相同，则隐藏头部
            {
                holder.headerLayout.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            } else
            {
                holder.headerLayout.setVisibility(View.VISIBLE);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout headerLayout;
        TextView headerTv;
        TextView timeTv;
        TextView contentTv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            headerLayout = itemView.findViewById(R.id.sticky_header);
            headerTv = itemView.findViewById(R.id.header_textview);
            timeTv = itemView.findViewById(R.id.sticky_time_textview);
            contentTv = itemView.findViewById(R.id.sticky_content_textview);
        }
    }
}
