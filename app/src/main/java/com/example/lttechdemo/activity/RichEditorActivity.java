package com.example.lttechdemo.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lttechdemo.R;
import com.example.lttechdemo.data.InitData;
import com.example.lttechdemo.view.RichTextEditor;

import java.util.List;

/**
 * Created by litong on 2018/2/28.
 * 富文本界面
 */

public class RichEditorActivity extends BaseActivity implements View.OnClickListener
{

    private RichTextEditor richTextEditor;//富文本编辑控件
    private TextView showTv;//预览
    private TextView pictureTv;//图片
    private RelativeLayout webViewRl;//预览布局
    private WebView webView;//用于预览数据
    private String webContent;


    @Override
    protected void setContentView()
    {
        setContentView(R.layout.activity_richeditor);
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
        richTextEditor = (RichTextEditor) findViewById(R.id.richtext_editor);
        showTv = (TextView) findViewById(R.id.richtext_show);
        pictureTv = (TextView) findViewById(R.id.richtext_picture);
        webViewRl = (RelativeLayout) findViewById(R.id.webView_relative);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());

        showTv.setOnClickListener(this);
        pictureTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.richtext_show:
                if ("预览".equals(showTv.getText()))
                {
                    pictureTv.setEnabled(false);
                    showTv.setText("编辑");
                    webViewRl.setVisibility(View.VISIBLE);
                    webContent = "<html><header>" + InitData.LINK_CSS + "</header><body>" + getEditData() + "</body></html>";
                    webView.loadDataWithBaseURL(null, webContent, "text/html", "utf-8", null);
                } else
                {
                    pictureTv.setEnabled(true);
                    showTv.setText("预览");
                    webViewRl.setVisibility(View.GONE);
                }
                break;
            case R.id.richtext_picture:
                insertImage();
                break;
        }
    }

    /**
     * 插入图片
     */
    private void insertImage()
    {
        richTextEditor.insertImage(null, InitData.URL_PIC);
    }

    /**
     * 生成控件中的数据
     */
    private String getEditData()
    {
        List<RichTextEditor.EditData> editList = richTextEditor.buildEditData();
        StringBuilder content = new StringBuilder();
        if (editList.size() > 0)
        {
            content.append("<div class=\"content\">");
            for (RichTextEditor.EditData itemData : editList)
            {
                if (itemData.inputStr != null)
                {
                    //将EditText中的换行符、空格符转换成html
                    String inputStr = itemData.inputStr.replace("\n", "</p><p>").replace(" ", "&nbsp");
                    content.append("<p>").append(inputStr).append("</p>");
                } else if (itemData.imagePath != null)
                {
                    content.append("<p style=\"text-align:center\"><img width=\"100%\" src=\"").append(itemData.imagePath).append("\"/></p>");
                }
            }
            content.append("</div>");
        }
        return content.toString();
    }

}
