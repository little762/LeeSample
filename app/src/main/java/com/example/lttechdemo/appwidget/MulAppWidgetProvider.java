package com.example.lttechdemo.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.lttechdemo.R;
import com.example.lttechdemo.activity.MainActivity;
import com.example.lttechdemo.data.InitData;

/**
 * Created by litong on 2018/3/2.
 * 桌面小部件Provider 用于更新小部件
 */

public class MulAppWidgetProvider extends AppWidgetProvider
{
    private RemoteViews mRemoteViews;//指定桌面小部件的布局
    private ComponentName mComponentName;//更新所有的 AppWidgetProvider提供的所有的AppWidget实例

    private int[] imgs = new int[]
            {
                    R.mipmap.img1,
                    R.mipmap.img2,
                    R.mipmap.img3,
                    R.mipmap.img4,
                    R.mipmap.img5,
                    R.mipmap.img6
            };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget_provider);//指定布局文件
        mRemoteViews.setImageViewResource(R.id.iv_test, R.mipmap.ic_launcher);//设置图片
        mRemoteViews.setTextViewText(R.id.btn_test, "点击跳转到Activity");//设置Button文字
        Intent skipIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_test, pi);//跳转到MainActivity

        // 通过setRemoteAdapter将 ListView 和ListViewService关联起来，
        Intent lvIntent = new Intent(context, ListViewService.class);
        mRemoteViews.setRemoteAdapter(R.id.lv_test, lvIntent);
        mRemoteViews.setEmptyView(R.id.lv_test, android.R.id.empty);

        // 集合控件(如GridView、ListView等)不能像普通的按钮一样通过setOnClickPendingIntent设置点击事件，必须先通过两步。
        // 1.通过 setPendingIntentTemplate设置intent，这是比不可少的
        // 2.然后在处理该“集合控件”的RemoteViewsFactory类的getViewAt()接口中，通过setOnClickFillInIntent设置“集合控件的某一项的数据”
        Intent toIntent = new Intent(InitData.CHANGE_IMAGE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, toIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);

        mComponentName = new ComponentName(context, MulAppWidgetProvider.class);
        appWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (TextUtils.equals(InitData.CHANGE_IMAGE, intent.getAction()))
        {
            Bundle extras = intent.getExtras();
            int position = extras.getInt(InitData.EXTRA_DATA);
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget_provider);
            mRemoteViews.setImageViewResource(R.id.iv_test, imgs[position]);
            mComponentName = new ComponentName(context, MulAppWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(mComponentName, mRemoteViews);
        }
    }
}
