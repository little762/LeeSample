package com.example.lttechdemo.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.adapter.BaseRecyclerAdapter;
import com.example.lttechdemo.adapter.BaseViewHolder;
import com.example.lttechdemo.bean.NewsContent;
import com.example.lttechdemo.bean.NewsImage;
import com.example.lttechdemo.bean.PhotoInfo;
import com.example.lttechdemo.view.MultiImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litong on 2018/2/23.
 * 宫格图片界面
 */

public class MultiImageActivity extends BaseActivity
{

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<NewsContent> mAdapter;
    private List<NewsContent> newsContentList = new ArrayList<>();

    private final static String IMG_URL_01 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519468205527&di=5c7f3a21b5b2b09a1a0dc5e0eaef5c27&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fa1%2F70%2Fd%2F81.jpg";
    private final static String IMG_URL_02 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887719&di=358d4fff64dd4105ce9f5bb67d20f90d&imgtype=0&src=http%3A%2F%2Fimages.quanjing.com%2Fcorbis014%2Fhigh%2F42-16421547.jpg";
    private final static String IMG_URL_03 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887716&di=1c5eb56b6bca85703da0527facddb6c7&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201503%2F28%2F20150328180930_tMLJm.jpeg";
    private final static String IMG_URL_04 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887716&di=aa6a6719bd357bebfa13625d1b40da2d&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201510%2F23%2F20151023125100_RiKyV.jpeg";
    private final static String IMG_URL_05 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519468211083&di=561e4210296ddc66e4ab0a520de3e9fd&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201411%2F13%2F20141113142117_N2WWY.jpeg";
    private final static String IMG_URL_06 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887711&di=14580a3717684e9766f2693818952c67&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201408%2F27%2F20140827132657_WMdVw.jpeg";
    private final static String IMG_URL_07 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519468450332&di=c166cd8b85e84004e59d469384ba1c59&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201601%2F29%2F20160129194057_rSTjJ.thumb.700_0.jpeg";
    private final static String IMG_URL_08 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887734&di=31b3a0d972601f2fd9270c7f6baa62aa&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201408%2F14%2F20140814140818_YG8c5.jpeg";
    private final static String IMG_URL_09 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519468450332&di=c166cd8b85e84004e59d469384ba1c59&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201601%2F29%2F20160129194057_rSTjJ.thumb.700_0.jpeg";
    private final static String IMG_URL_10 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519467887733&di=f41a492e49c6e6e41d7be4e27e3da100&imgtype=0&src=http%3A%2F%2Fimg.sc115.com%2Fuploads%2Fsc%2Fjpgs%2F05%2Fxpic4770_sc115.com.jpg";


    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_multiimage);
    }

    @Override
    protected void initTitle()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);//返回按钮
            actionBar.setDisplayShowTitleEnabled(false);//去掉toolbar标题
        }
    }

    @Override
    protected void initViews()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.multiimage_recyclerview);
        mAdapter = new BaseRecyclerAdapter<NewsContent>(this, R.layout.item_multiimage, initData())
        {
            @Override
            public void convert(BaseViewHolder holder, NewsContent newsContent)
            {
                TextView tvUserName = holder.getView(R.id.tv_item_username);
                TextView tvTime = holder.getView(R.id.tv_item_time);
                TextView tvLocation = holder.getView(R.id.tv_item_location);
                TextView tvContent = holder.getView(R.id.tv_item_content);
                MultiImageView multiImagView = holder.getView(R.id.multiImagView);

                tvUserName.setText(newsContent.getUserName());
                tvTime.setText(newsContent.getTime());
                tvLocation.setText(newsContent.getLocation());
                tvContent.setText(newsContent.getContent());

                ArrayList<PhotoInfo> imageInfo = new ArrayList<>();
                List<NewsImage> images = newsContent.getImageurls();
                if (images.size() > 0)
                {
                    for (NewsImage image : images)
                    {
                        PhotoInfo info = new PhotoInfo();
                        info.setUrl(image.getUrl());
                        imageInfo.add(info);
                    }
                    multiImagView.setList(imageInfo);
                }
            }
        };
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 造数据
     */
    private List<NewsContent> initData()
    {
        for (int i = 0; i < 10; i++)
        {
            int num = i + 1;
            NewsContent newsContent = new NewsContent();
            newsContent.setContent("这是第" + num + "条内容");
            newsContent.setLocation("70" + num + "m");
            newsContent.setUserName("Matthias Heiderich-0" + num);
            newsContent.setTime(num + "小时前");
            newsContent.setImageurls(initImages(i));
            newsContentList.add(newsContent);
        }
        return newsContentList;
    }

    private List<NewsImage> initImages(int imageCount)
    {
        switch (imageCount)
        {
            case 0:
                return getImages(imageCount);
            case 1:
                return getImages(imageCount);
            case 2:
                return getImages(imageCount);
            case 3:
                return getImages(imageCount);
            case 4:
                return getImages(imageCount);
            case 5:
                return getImages(imageCount);
            case 6:
                return getImages(imageCount);
            case 7:
                return getImages(imageCount);
            case 8:
                return getImages(imageCount);
            case 9:
                return getImages(imageCount);
        }
        return null;
    }

    private List<NewsImage> getImages(int imageCount)
    {
        List<NewsImage> newsImages = new ArrayList<>();
        if (imageCount == 0)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 1)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_03);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 2)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_05);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_06);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 3)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_07);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_08);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_09);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_10);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 4)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_03);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 4)
                {
                    newsImage.setUrl(IMG_URL_05);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 5)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_03);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 4)
                {
                    newsImage.setUrl(IMG_URL_05);
                } else if (i == 5)
                {
                    newsImage.setUrl(IMG_URL_06);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 6)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_03);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 4)
                {
                    newsImage.setUrl(IMG_URL_05);
                } else if (i == 5)
                {
                    newsImage.setUrl(IMG_URL_06);
                } else if (i == 6)
                {
                    newsImage.setUrl(IMG_URL_07);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 7)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_03);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 4)
                {
                    newsImage.setUrl(IMG_URL_05);
                } else if (i == 5)
                {
                    newsImage.setUrl(IMG_URL_06);
                } else if (i == 6)
                {
                    newsImage.setUrl(IMG_URL_07);
                } else if (i == 7)
                {
                    newsImage.setUrl(IMG_URL_08);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 8)
        {
            for (int i = 0; i < imageCount + 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                } else if (i == 1)
                {
                    newsImage.setUrl(IMG_URL_02);
                } else if (i == 2)
                {
                    newsImage.setUrl(IMG_URL_03);
                } else if (i == 3)
                {
                    newsImage.setUrl(IMG_URL_04);
                } else if (i == 4)
                {
                    newsImage.setUrl(IMG_URL_05);
                } else if (i == 5)
                {
                    newsImage.setUrl(IMG_URL_06);
                } else if (i == 6)
                {
                    newsImage.setUrl(IMG_URL_07);
                } else if (i == 7)
                {
                    newsImage.setUrl(IMG_URL_08);
                } else if (i == 8)
                {
                    newsImage.setUrl(IMG_URL_09);
                }
                newsImages.add(newsImage);
            }
        }
        if (imageCount == 9)
        {
            for (int i = 0; i < 1; i++)
            {
                NewsImage newsImage = new NewsImage();
                if (i == 0)
                {
                    newsImage.setUrl(IMG_URL_01);
                }
                newsImages.add(newsImage);
            }
        }
        return newsImages;
    }
}
