package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

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
public class WebLiveHealthActivity extends CoreBaseActivity {

    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.linear)
    LinearLayout linear;

    @BindView(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;

    private TitleBuilder builder;
    private String url;
    private String mTitle;
    private String copy_url;
    @BindView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;
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
        copy_url = getIntent().getExtras().getString("copy_url");
        if(TextUtils.isEmpty(mTitle)){
            rl_titlebar.setVisibility(View.GONE);
        }
        titlebar_iv_right.setVisibility(View.VISIBLE);
        titlebar_iv_right.setImageResource(R.mipmap.share);
        titlebar_iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareHaiBao();
            }
        });
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
        web_view.loadUrl(url);
        web_view.getSettings().setJavaScriptEnabled(true);
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
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebLiveHealthActivity.this, "请检查您的网络设置", Toast.LENGTH_SHORT).show();
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

    private void shareHaiBao() {
        UMImage thumb =  new UMImage(this, R.mipmap.ic_logo);
        final UMWeb web = new UMWeb(url);
        web.setTitle("易风健康");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("易风健康");
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        config.setMenuItemBackgroundColor(ShareBoardConfig.BG_SHAPE_NONE);
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);
        config.setTitleText("分享到");
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.SINA)//分享平台
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            if (share_media == SHARE_MEDIA.SINA){
                                new ShareAction(WebLiveHealthActivity.this).setPlatform(share_media)
                                        .withMedia(web)
                                        .share();
                            }else if (share_media == SHARE_MEDIA.QQ){
                                new ShareAction(WebLiveHealthActivity.this).setPlatform(share_media)
                                        .withMedia(web)
                                        .share();
                            }else if(share_media == SHARE_MEDIA.WEIXIN){
                                new ShareAction(WebLiveHealthActivity.this).setPlatform(share_media)
                                        .withMedia(web)
                                        .share();
                            }else if(share_media == SHARE_MEDIA.WEIXIN_CIRCLE){
                                new ShareAction(WebLiveHealthActivity.this).setPlatform(share_media)
                                        .withMedia(web)
                                        .share();
                            }
                        }
                })
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.i("tag",share_media.toString());
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.i("tag",share_media.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.i("tag",share_media.toString());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.i("tag",share_media.toString());
                    }
                })
                .open(config);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
