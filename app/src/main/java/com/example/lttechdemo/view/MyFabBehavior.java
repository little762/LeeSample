package com.example.lttechdemo.view;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * Created by litong on 2018/2/23.
 * 自定义Behavior
 */

public class MyFabBehavior extends CoordinatorLayout.Behavior<View>
{
    private float viewY;//控件距离coordinatorLayout底部距离
    private boolean isHideAnimate;//隐藏动画是否在进行
    private boolean isShowAnimate;//显示动画是否在进行

    private boolean isChildTop = true;//判断控件是否回到原始位置

    private ViewPropertyAnimator animator;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public MyFabBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 滑动前指定Behavior关注的滑动方向
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes)
    {
        if (child.getVisibility() == View.VISIBLE && viewY == 0)
        {
            //获取控件距离父布局（coordinatorLayout）底部距离
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }

    /**
     * 用来监听滑动状态，对象消费滚动距离前回调
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed)
    {
        //速度大于40，则执行动画（速度判断）
        if (Math.abs(dy) > 40)
        {
            //dy大于0是向上滚动 dy小于0是向下滚动
            if (dy >= 0 && !isHideAnimate && child.getVisibility() == View.VISIBLE)//如果向上滚动，隐藏动画已结束并且控件可见，则启动隐藏动画
            {
                hide(child);
            } else if (dy < 0 && !isShowAnimate && !isChildTop)//如果向下滚动，显示动画已结束并且控件未回到原始位置，则启动显示动画
            {
                show(child);
            } else if (dy >= 0 && isShowAnimate)//如果向上滚动，并且显示动画未结束，则先取消显示动画再启动隐藏动画
            {
                animator.cancel();
            } else if (dy < 0 && isHideAnimate)//如果向下滚动，并且显示隐藏未结束，则先取消隐藏动画再启动显示动画
            {
                animator.cancel();
            }
        }
    }


    /**
     * 隐藏时的动画
     */
    private void hide(final View view)
    {
        animator = view.animate().translationY(viewY).setInterpolator(INTERPOLATOR).setDuration(300);

        animator.setListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {
                isHideAnimate = true;
            }

            //每次cancel也会走onAnimationEnd方法
            @Override
            public void onAnimationEnd(Animator animator)
            {
                //如果隐藏动画正常结束，而非取消后结束，则将布局隐藏
                if (isHideAnimate)
                {
//                    view.setVisibility(View.GONE);
                    isHideAnimate = false;
                }
                isChildTop = false;
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {
                isHideAnimate = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        animator.start();
    }

    /**
     * 显示时的动画
     */
    private void show(final View view)
    {
        animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(300);
        animator.setListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {
                view.setVisibility(View.VISIBLE);
                isShowAnimate = true;
            }

            //每次cancel也会走onAnimationEnd方法
            @Override
            public void onAnimationEnd(Animator animator)
            {
                //如果显示动画正常结束，而非取消后结束，则isChildTop = true，表示控件回到原来位置
                if (isShowAnimate)
                {
                    isShowAnimate = false;
                    isChildTop = true;
                }
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {
                isShowAnimate = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        animator.start();
    }
}
