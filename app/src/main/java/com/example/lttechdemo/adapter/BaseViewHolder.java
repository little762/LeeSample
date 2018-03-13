package com.example.lttechdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by litong on 2018/2/22.
 * RecyclerView通用ViewHolder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder
{
    private SparseArray<View> mViews;//存储itemView子控件
    private View mConvertView;
    private Context mContext;

    public BaseViewHolder(View itemView)
    {
        super(itemView);
        mViews = new SparseArray<>();
        mConvertView = itemView;

        /**
         * 设置水波纹背景
         */
        if (mConvertView.getBackground() == null)
        {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = mConvertView.getContext().getTheme();
            int top = mConvertView.getPaddingTop();
            int bottom = mConvertView.getPaddingBottom();
            int left = mConvertView.getPaddingLeft();
            int right = mConvertView.getPaddingRight();
            if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true))
            {
                mConvertView.setBackgroundResource(typedValue.resourceId);
            }
            mConvertView.setPadding(left, top, right, bottom);
        }
    }

    public static BaseViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemClickListener(View.OnClickListener listener)
    {
        mConvertView.setOnClickListener(listener);
        return this;
    }
}
