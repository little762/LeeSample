package com.example.lttechdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.example.lttechdemo.MyApplication;
import com.example.lttechdemo.R;
import com.example.lttechdemo.bean.GroupInfo;
import com.example.lttechdemo.util.BaseUtils;

/**
 * Created by litong on 2018/2/26.
 * RecyclerView粘性头部分割线
 */

public class StickySectionDecoration extends RecyclerView.ItemDecoration
{
    private GroupInfoCallback mCallback;
    private int mHeaderHeight;
    private int mDividerHeight;


    //用来绘制Header上的文字
    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextSize;
    private Paint.FontMetrics mFontMetrics;

    private float mTextOffsetX;

    public StickySectionDecoration(Context context, GroupInfoCallback callback)
    {
        this.mCallback = callback;
        mDividerHeight = context.getResources().getDimensionPixelOffset(R.dimen.header_divider_height);
        mHeaderHeight = context.getResources().getDimensionPixelOffset(R.dimen.header_height);
        mTextSize = context.getResources().getDimensionPixelOffset(R.dimen.header_textsize);

        mHeaderHeight = (int) Math.max(mHeaderHeight, mTextSize);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mTextSize);
        mFontMetrics = mTextPaint.getFontMetrics();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
        mPaint.setColor(MyApplication.getContext().getResources().getColor(R.color.color_material_light));
    }

    //设置每个Item偏移量，给分割线和Header腾出空间
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (mCallback != null)
        {
            GroupInfo groupInfo = mCallback.getGroupInfo(position);

            //如果是组内的第一个则将间距撑开为一个Header的高度，或者就是普通的分割线高度
            if (groupInfo != null && groupInfo.isFirstViewInGroup())
            {
                outRect.top = mHeaderHeight;
            } else
            {
                outRect.top = mDividerHeight;
            }
        }
    }

    //画分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();//屏幕可见ItemView数量

        for (int i = 0; i < childCount; i++)
        {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);


            if (mCallback != null)
            {

                GroupInfo groupinfo = mCallback.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0)
                {
                    if (!groupinfo.isFirstViewInGroup())
                    {
                        //绘制分割线
                        c.drawRect(left, view.getTop() - mDividerHeight, right, view.getTop(), mPaint);
                    }
                }

            }
        }
    }

    //画Header
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();//屏幕可见ItemView数量

        for (int i = 0; i < childCount; i++)
        {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);

            if (mCallback != null)
            {
                GroupInfo groupinfo = mCallback.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0)
                {
                    //只有组内的第一个ItemView之上才绘制
                    if (groupinfo.isFirstViewInGroup())
                    {
                        int top = view.getTop() - mHeaderHeight;

                        int bottom = view.getTop();
                        drawHeaderRect(c, groupinfo, left, top, right, bottom);
                    }

                } else
                {
                    //当 ItemView 是屏幕上第一个可见的View 时，不管它是不是组内第一个View
                    //它都需要绘制它对应的 StickyHeader。

                    // 还要判断当前的 ItemView 是不是它组内的最后一个 View

                    int top = parent.getPaddingTop();


                    if (groupinfo.isLastViewInGroup())
                    {
                        int suggestTop = view.getBottom() - mHeaderHeight;
                        // 当 ItemView 与 Header 底部平齐的时候，判断 Header 的顶部是否小于
                        // parent 顶部内容开始的位置，如果小于则对 Header.top 进行位置更新，
                        //否则将继续保持吸附在 parent 的顶部
                        if (suggestTop < top)
                        {
                            top = suggestTop;
                        }
                    }
                    int bottom = top + mHeaderHeight;
                    drawHeaderRect(c, groupinfo, left, top, right, bottom);
                }

            }
        }
    }

    private void drawHeaderRect(Canvas c, GroupInfo groupinfo, int left, int top, int right, int bottom)
    {
        //绘制Header
        c.drawRect(left, top, right, bottom, mPaint);

        float titleX = BaseUtils.getInstance().dip2px(10);
        float titleY = bottom - BaseUtils.getInstance().dip2px(10);
        //绘制Title
        c.drawText(groupinfo.getTitle(), titleX, titleY, mTextPaint);
    }

    public GroupInfoCallback getCallback()
    {
        return mCallback;
    }

    public void setCallback(GroupInfoCallback callback)
    {
        this.mCallback = callback;
    }

    public interface GroupInfoCallback
    {
        GroupInfo getGroupInfo(int position);
    }
}
