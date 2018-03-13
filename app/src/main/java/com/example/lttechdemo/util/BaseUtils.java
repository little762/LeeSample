package com.example.lttechdemo.util;

import android.content.Context;

import com.example.lttechdemo.MyApplication;

/**
 * Created by litong on 2018/2/23.
 * 工具类
 */

public class BaseUtils
{
    private static BaseUtils utils;
    private Context context;

    private BaseUtils()
    {
        context = MyApplication.getContext();
    }

    public static synchronized BaseUtils getInstance()
    {
        if (utils == null)
        {
            utils = new BaseUtils();
        }
        return utils;
    }

    /**
     * dp转px
     **/
    public int dip2px(int dipValue)
    {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public int dip2px(float dipValue)
    {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
