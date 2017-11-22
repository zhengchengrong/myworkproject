package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.WebUtil;
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
public class WebActivity extends CoreBaseActivity {

    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.linear)
    LinearLayout linear;

    @BindView(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;
    private WebUtil mWebUtil;

    private TitleBuilder builder;
    private String url;
    private String mTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

        url = getIntent().getExtras().getString("url");
        mTitle = getIntent().getExtras().getString("title");
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
        mWebUtil = new WebUtil(WebActivity.this, web_view, url);
        web_view.addJavascriptInterface(new JavaScriptObject(), "eaphone");

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
    public void onBackPressed() {
        mWebUtil.isCanGoBack();
    }
    class JavaScriptObject {

        @JavascriptInterface
        public void goBack() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   mWebUtil.isCanGoBack();

                }
            });
        }

        @JavascriptInterface
        public void closeWindow() {
            finish();
        }

    }


//    @Override
//    public void onBackPressed() {
////        mWebUtil.isCanGoBack();
////        web_view.destroy();
////        WebActivity.this.finish();
//    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void destroy() {
        if (web_view != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = web_view.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(web_view);
            }

            web_view.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            web_view.getSettings().setJavaScriptEnabled(false);
            web_view.clearHistory();
            web_view.clearView();
            web_view.removeAllViews();

            try {
                web_view.destroy();
            } catch (Throwable ex) {

            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
