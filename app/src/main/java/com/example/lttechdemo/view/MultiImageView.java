package com.example.lttechdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lttechdemo.R;
import com.example.lttechdemo.bean.PhotoInfo;
import com.example.lttechdemo.util.BaseUtils;

import java.util.List;

/**
 * Created by litong on 2018/2/23.
 * 宫格图控件
 */

public class MultiImageView extends LinearLayout
{
    private int pxOneMaxWandH; //单张图最大允许宽高
    private int pxMoreWandH = 0; //多张图的宽高
    private int MAX_PER_ROW_COUNT = 3; //每行显示最大数
    private int MAX_WIDTH = 0; //控件最大宽度
    private int pxImagePadding = BaseUtils.getInstance().dip2px(3); //图片间的间距

    private LayoutParams onePicPara; //一张图的布局参数
    private LayoutParams morePara, moreParaColumnFirst; //多张图的布局参数（非第一列、第一列）
    private LayoutParams rowPara; //行布局参数

    private List<PhotoInfo> imagesList; //照片的Url列表
    private OnItemClickListener mOnItemClickListener; //图片点击监听

    public MultiImageView(Context context)
    {
        super(context);
    }

    public MultiImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (MAX_WIDTH == 0)
        {
            int width = MeasureSpec.getSize(widthMeasureSpec);
//            int width = measureWidth(widthMeasureSpec);
            if (width > 0)
            {
                MAX_WIDTH = width;
                if (imagesList != null && imagesList.size() > 0)
                {
                    setList(imagesList);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置图片URL
     */
    public void setList(List<PhotoInfo> lists) throws IllegalArgumentException
    {
        if (lists == null)
        {
            throw new IllegalArgumentException("imageList is null...");
        }
        imagesList = lists;

        if (MAX_WIDTH > 0)
        {
            pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3; //解决右侧图片和内容对不齐问题
            pxOneMaxWandH = MAX_WIDTH * 2 / 3;
            initImageLayoutParams();
        }
        initView();
    }

    /**
     * 测量控件宽度
     */
    private int measureWidth(int measureSpec)
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        } else
        {
            if (specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 根据imageView的数量初始化不同的View布局
     */
    private void initView()
    {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0)
        {
            //为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }

        if (imagesList == null || imagesList.size() == 0)
        {
            return;
        }

        if (imagesList.size() == 1) //单图
        {
            addView(createImageView(0, false));
        } else //多图
        {
            int allCount = imagesList.size();
            if (allCount == 4) //四张图每行显示两张
            {
                MAX_PER_ROW_COUNT = 2;
            } else //其他每行显示三张
            {
                MAX_PER_ROW_COUNT = 3;
            }
            int rowCount = allCount / MAX_PER_ROW_COUNT
                    + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0); //行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++)
            {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                rowLayout.setLayoutParams(rowPara);
                if (rowCursor != 0)
                {
                    rowLayout.setPadding(0, pxImagePadding, 0, 0);
                }

                int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT
                        : allCount % MAX_PER_ROW_COUNT; //最后一行的列数
                if (rowCursor != rowCount - 1) //非最后一行的列数
                {
                    columnCount = MAX_PER_ROW_COUNT;
                }
                addView(rowLayout);

                int rowOffset = rowCursor * MAX_PER_ROW_COUNT; //行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++)
                {
                    int position = columnCursor + rowOffset; //图片位置
                    rowLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    /**
     * 创建ImageView
     */
    private ImageView createImageView(int position, final boolean isMultiImage)
    {
        PhotoInfo photoInfo = imagesList.get(position);
        MyImageView imageView = new MyImageView(getContext());
        if (isMultiImage) //多图
        {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? moreParaColumnFirst : morePara);//第一列图片不设置MarginLeft
        } else //单张图
        {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //imageView.setMaxHeight(pxOneMaxWandH);

            int expectW = photoInfo.w;
            int expectH = photoInfo.h;

            if (expectW == 0 || expectH == 0)
            {
                imageView.setLayoutParams(onePicPara);
            } else
            {
                int actualW = 0;
                int actualH = 0;
                float scale = ((float) expectH) / ((float) expectW);
                if (expectW > pxOneMaxWandH)
                {
                    actualW = pxOneMaxWandH;
                    actualH = (int) (actualW * scale);
                } else if (expectW < pxMoreWandH)
                {
                    actualW = pxMoreWandH;
                    actualH = (int) (actualW * scale);
                } else
                {
                    actualW = expectW;
                    actualH = expectH;
                }
                imageView.setLayoutParams(new LayoutParams(actualW, actualH));
            }
        }

        imageView.setId(photoInfo.url.hashCode());
        imageView.setOnClickListener(new ImageOnClickListener(position));
        imageView.setBackgroundColor(getResources().getColor(R.color.im_font_color_text_hint));
        Glide.with(getContext()).load(photoInfo.url)
                .asBitmap()
                .dontAnimate()
                .placeholder(R.mipmap.ic_img_default)
                .error(R.mipmap.ic_img_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
        return imageView;
    }

    /**
     * 初始化布局参数
     */
    private void initImageLayoutParams()
    {
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;

        onePicPara = new LayoutParams(wrap, wrap);

        moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);

        rowPara = new LayoutParams(match, wrap);
    }

    private class ImageOnClickListener implements OnClickListener
    {

        private int position;

        public ImageOnClickListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View view)
        {
            if (mOnItemClickListener != null)
            {
                mOnItemClickListener.onItemClick(view, position);
            }
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    //获取列数
    public int getColumn()
    {
        return MAX_PER_ROW_COUNT;
    }

    //获取图片间的间距
    public int getPxImagePadding()
    {
        return pxImagePadding;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }
}
