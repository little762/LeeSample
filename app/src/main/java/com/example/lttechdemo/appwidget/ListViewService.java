package com.example.lttechdemo.appwidget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.lttechdemo.data.InitData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litong on 2018/3/2.
 * 更新ListVIew视图
 */

public class ListViewService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    /**
     * 具体实现逻辑
     */
    private class ListRemoteViewsFactory implements RemoteViewsFactory
    {

        private Context mContext;

        private List<String> mList = new ArrayList<>();

        public ListRemoteViewsFactory(Context mContext)
        {
            this.mContext = mContext;
        }

        @Override
        public void onCreate()
        {
            mList.add("一");
            mList.add("二");
            mList.add("三");
            mList.add("四");
            mList.add("五");
            mList.add("六");
        }

        @Override
        public void onDataSetChanged()
        {

        }

        @Override
        public void onDestroy()
        {
            mList.clear();
        }

        @Override
        public int getCount()
        {
            return mList.size();
        }

        @Override
        public RemoteViews getViewAt(int position)
        {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);//itemView
            views.setTextViewText(android.R.id.text1, "item：" + mList.get(position));
            Bundle extras = new Bundle();
            extras.putInt(InitData.EXTRA_DATA, position);
            Intent changeIntent = new Intent();
            changeIntent.setAction(InitData.CHANGE_IMAGE);
            changeIntent.putExtras(extras);
            views.setOnClickFillInIntent(android.R.id.text1, changeIntent);
            return views;
        }

        /**
         * 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
         * 如果返回null 显示默认界面
         * 否则 加载自定义的，返回RemoteViews
         */
        @Override
        public RemoteViews getLoadingView()
        {
            return null;
        }

        @Override
        public int getViewTypeCount()
        {
            return 1;
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public boolean hasStableIds()
        {
            return false;
        }
    }
}
