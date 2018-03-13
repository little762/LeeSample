package com.example.lttechdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.util.BaseUtils;

/**
 * Created by XLX on 2017/11/17.
 * 图片加边框
 */

public class MyImageView extends ImageView
{
    private Context mContext;
    private int mBorderThickness;//边框宽度
    private int mBorderColor;//边框颜色
    private int defaultThickness = BaseUtils.getInstance().dip2px(0.5f);//边框默认宽度
    private int defaultColor = 0xFFE9E9E9;//边框默认颜色

    public MyImageView(Context context)
    {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs)
    {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyImageView);
        //边框的宽度
        mBorderThickness = a.getDimensionPixelSize(R.styleable.MyImageView_border_thickness, defaultThickness);
        //边框的颜色
        mBorderColor = a.getColor(R.styleable.MyImageView_border_outside_color, defaultColor);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(mBorderColor);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    //图片边框宽度
    public void setmBorderThickness(int mBorderThickness)
    {
        this.mBorderThickness = mBorderThickness;
    }

    //图片边框颜色
    public void setmBorderColor(int mBorderColor)
    {
        this.mBorderColor = mBorderColor;
    }
}
