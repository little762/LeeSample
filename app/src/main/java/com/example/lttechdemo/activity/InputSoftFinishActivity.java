package com.example.lttechdemo.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.lttechdemo.R;

/**
 * Created by litong on 2018/2/23.
 * 软键盘弹出，直接退出界面
 */

public class InputSoftFinishActivity extends AppCompatActivity implements View.OnClickListener
{
    private ValueAnimator animator;//背景颜色动画
    private View mOutSide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputsoftfinish);

        initViews();
        startAnim();
    }

    private void initViews()
    {
        mOutSide = findViewById(R.id.view_outside);
        mOutSide.setOnClickListener(this);
    }

    //背景颜色渐变动画
    private void startAnim()
    {
        animator = ValueAnimator.ofInt(0x00000000, 0x50000000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int curValue = (int) animation.getAnimatedValue();
                mOutSide.setBackgroundColor(curValue);

            }
        });
        animator.start();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.view_outside://外部背景
                finish();
                break;
        }
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(0, R.anim.comment_dialog_out);
    }

    //监听返回键(有软键盘的情况下想直接返回，需要拦截KeyEvent.ACTION_UP事件)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP)
        {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy()
    {
        if (animator.isStarted())
        {
            animator.cancel();
        }
        super.onDestroy();
    }
}
