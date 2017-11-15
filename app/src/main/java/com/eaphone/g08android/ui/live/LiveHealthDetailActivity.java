package com.eaphone.g08android.ui.live;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.InfoDetail;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.InfoDetailPresenter;
import com.eaphone.g08android.ui.info.InfoCommentActivity;
import com.eaphone.g08android.ui.info.InfoReplyActivity;
import com.eaphone.g08android.ui.live.livepic.ShowImageWebView;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 11:00
 * 修改人：Administrator
 * 修改时间：2017/8/25 11:00
 * 修改备注：
 */
public class LiveHealthDetailActivity extends CoreBaseActivity<InfoDetailPresenter> implements NewsContracts.InfoDetailView {

    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_come_from)
    TextView tv_come_from;

    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.web_view)
    ShowImageWebView web_view;

    @BindView(R.id.tv_comment)
    TextView tv_comment;

    @BindView(R.id.tv_reply)
    TextView tv_reply;

    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;

    @BindView(R.id.iv_reply)
    ImageView iv_reply;

    @BindView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;

    private String id = "";
    private String newsTypeId = "";

    @Override
    public int getLayoutId() {
        return R.layout.live_health_detail_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("资讯详细").build();
        titlebar_iv_right.setVisibility(View.VISIBLE);
        titlebar_iv_right.setImageResource(R.mipmap.share);
        show(R.string.loading);
        id = getIntent().getExtras().getString("id");
        newsTypeId = getIntent().getExtras().getString("newsTypeId");
        mPresenter.infoDetail(newsTypeId, id);

        iv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtil.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    startActivity(InfoCommentActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LoginUtil.isLogin()){
                    Bundle bundle = new Bundle();
                    bundle.putString("newsId",id);
                    bundle.putString("commentId","");
                    startActivity(InfoReplyActivity.class,bundle);
                }else{
                    startActivity(LoginActivity.class);
                }


            }
        });
        titlebar_iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n  = Build.VERSION.SDK_INT;
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(LiveHealthDetailActivity.this,mPermissionList,123);
                }else{
                    shareYoumen();
                }

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        shareYoumen();
    }
    public void  shareYoumen(){
        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig               config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);//设置位置
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        config.setMenuItemBackgroundColor(ShareBoardConfig.BG_SHAPE_NONE);
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);
        config.setTitleText("分享到");

        new ShareAction(LiveHealthDetailActivity.this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
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
    protected boolean isRegistEventBus() {
        return true;
    }
    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event != null && event.getCode() == EventCode.B) {
            mPresenter.infoDetail(newsTypeId, id);
        }
    }
    @Override
    public Context getContext() {
        return mContext;
    }
    @Override
    public void showError(String msg) {
        showToast(msg);
    }
    @Override
    public void getInfoDetail(ResultBase<InfoDetail> result) {
        dismiss();
        if (result.isSuccess()) {
            tv_title_name.setText(result.getData().getTitle());
            tv_time.setText(TimeUtils.displayTime(result.getData().getCreateTime()));
            tv_come_from.setText("来源：" + result.getData().getCreateBy());
            if (TextUtils.isEmpty(result.getData().getContent())) {
                web_view.setVisibility(View.GONE);
            } else {
                web_view.setVisibility(View.VISIBLE);
                web_view.loadDataWithBaseURL("", result.getData().getContent(), "text/html", "UTF-8", "");
            }

         //  Set<String> imgs = WebUtil.getImgStr(result.getData().getContent());

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
                    Toast.makeText(LiveHealthDetailActivity.this, "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                }
            });



            if (TextUtils.isEmpty(result.getData().getBanner())) {
                iv_image.setVisibility(View.GONE);
            } else {
                iv_image.setVisibility(View.GONE);
                ImageLoader.displayImage(result.getData().getBanner(), iv_image);
            }
            tv_comment.setText(result.getData().getCommentCount() + "");
        } else {
            showToast(result.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
