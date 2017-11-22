package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eaphone.g08android.R;
import com.eaphone.g08android.ui.live.livepic.ShowImageWebView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.TitleBuilder;

import butterknife.BindView;


/**
 * 项目名称：心相随 患者端
 * 类描述：
 * 创建人：wjq
 * 创建时间：2016/7/14 9:27
 * 修改人：user
 * 修改时间：2016/7/14 9:27
 * 修改备注：
 */
public class WebGroupActivity extends CoreBaseActivity {

    @BindView(R.id.web_view)
    ShowImageWebView web_view;

    @BindView(R.id.linear)
    LinearLayout linear;

    @BindView(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;

    private TitleBuilder builder;
    private String url;
    private String mTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_web;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

        url = getIntent().getExtras().getString("url");
        mTitle = getIntent().getExtras().getString("title");
        web_view = (ShowImageWebView) findViewById(R.id.web_view);
        if(TextUtils.isEmpty(mTitle)){
            rl_titlebar.setVisibility(View.GONE);
        }
        builder = initBackTitle(mTitle).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (web_view.canGoBack()) {
//                    web_view.goBack();// 返回前一个页面
//                } else {
                    finish();
//                }
            }
        });
        builder.build();
       // mWebUtil = new WebUtil(WebGroupActivity.this, web_view, url);
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // web 页面加载完成，添加监听图片的点击 js 函数
                web_view.setImageClickListner();
                //解析 HTML
                web_view.parseHTML(view);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebGroupActivity.this, "请检查您的网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (web_view != null) {
            web_view.removeAllViews();
            try {
                web_view.destroy();
            } catch (Throwable t) {
            }
            web_view = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }
}
