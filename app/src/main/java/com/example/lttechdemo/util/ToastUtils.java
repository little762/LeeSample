package com.example.lttechdemo.util;

import android.view.Gravity;
import android.widget.Toast;

import com.example.lttechdemo.MyApplication;


/**
 * Created by XLX on 2017/6/6.
 * 吐司工具类
 */

public class ToastUtils
{
    private static Toast TOAST;
    private static Toast TOASTCENTER;

    //自定义时长吐司
    public static void show(String text)
    {
        if (TOAST == null)
        {
            TOAST = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        TOAST.setText(text);
        TOAST.setDuration(Toast.LENGTH_SHORT);
        TOAST.show();
    }

    //居中显示toast
    public static void showCenter(String text)
    {
        if (TOASTCENTER == null)
        {
            TOASTCENTER = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        TOASTCENTER.setText(text);
        TOASTCENTER.setGravity(Gravity.CENTER, 0, 0);
        TOASTCENTER.setDuration(Toast.LENGTH_SHORT);
        TOASTCENTER.show();
    }

    //取消Toast显示
    public static void cancel()
    {
        if (TOAST != null)
        {
            TOAST.cancel();
        }
    }
}
