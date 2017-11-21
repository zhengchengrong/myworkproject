package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ShareInfoEntity;
import com.eaphone.g08android.http.APIUrl.IApi;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.CenterContracts;
import com.eaphone.g08android.mvp.presenter.CenterPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.widget.CircleImageView;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/4/12.
 * 个人中心
 */
public class PersonCenterFragment extends CoreBaseFragment<CenterPresenter> implements View.OnClickListener,CenterContracts.PersonCenterView{

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.relative_home)
    RelativeLayout relative_home;

    @BindView(R.id.relative_device)
    RelativeLayout relative_device;

    @BindView(R.id.relative_set)
    RelativeLayout relative_set;

    @BindView(R.id.relative_kefu)
    RelativeLayout relative_kefu;

    @BindView(R.id.relative_fenxiang)
    RelativeLayout relative_fenxiang;

    @BindView(R.id.relative_file)
    RelativeLayout relative_file;

    @BindView(R.id.relative_order)
    RelativeLayout relative_order;

    @BindView(R.id.relative_appoint)
    RelativeLayout relative_appoint;

    @BindView(R.id.relative_reocrd)
    RelativeLayout relative_reocrd;

    @BindView(R.id.relative_card)
    RelativeLayout relative_card;


    @Override
    public int getLayoutId() {
        return R.layout.personcenter_fragment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        relative_home.setOnClickListener(this);
        relative_device.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
        relative_set.setOnClickListener(this);
        relative_kefu.setOnClickListener(this);
        relative_file.setOnClickListener(this);
        relative_order.setOnClickListener(this);
        relative_appoint.setOnClickListener(this);
        relative_reocrd.setOnClickListener(this);
        relative_card.setOnClickListener(this);
        relative_fenxiang.setOnClickListener(this);

        ImageLoader.displayImage(Const.getAvater(PreferencesUtils.getSharePreStr(Const.USERID)), iv_avatar);


    }

    @Override
    public void onResume() {
        super.onResume();
        tv_name.setText(PreferencesUtils.getSharePreStr(Const.NAME));
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event != null && event.getCode() == EventCode.F) {
            ImageLoader.displayImage(Const.getAvater(PreferencesUtils.getSharePreStr(Const.USERID)), iv_avatar);
        }
    }

    @Override
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.relative_file:
                bundle = new Bundle();
                bundle.putString("userId", PreferencesUtils.getSharePreStr(Const.USERID));
                startActivity(JiankangActivity.class, bundle);
                break;
            case R.id.relative_home:
                startActivity(FamilyActivity.class);
                break;
            case R.id.relative_device:
                startActivity(DeviceActivity.class);
                break;
            case R.id.iv_avatar:
                bundle = new Bundle();
                bundle.putString(Const.SOURCE, "person");
                bundle.putString(Const.USERID, PreferencesUtils.getSharePreStr(Const.USERID));
                startActivity(SelfMsgActivity.class, bundle);
                break;
            case R.id.relative_set:
                startActivity(SetActivity.class);
                break;
            case R.id.relative_kefu:
//                setFeedBack();
//                FeedbackAPI.openFeedbackActivity();
                startActivity(KeFuActivity.class);
                break;
            case R.id.relative_order:
                bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_MY_ORDER, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebActivity.class, bundle);
                break;

            case R.id.relative_appoint:
                bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_MY_INQUIRY, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebActivity.class, bundle);
                break;

            case R.id.relative_reocrd:
                bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_REGISTION_RECORD, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.relative_card:
                bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_MEDICAL_CARD, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.relative_fenxiang:
                mPresenter.info();
                break;
        }
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void getInfo(ResultBase<ShareInfoEntity> bean) {
        if(bean.isSuccess()){
            String url = bean.getData().getUrl();
            String poster = bean.getData().getPoster();
            String description = bean.getData().getDescription();
            String title = bean.getData().getTitle();
            shareYoumen(url,poster,description,title);
        }else{

        }
    }

    public void  shareYoumen(String url,String poster,String description,String title){
        UMImage thumb =  new UMImage(getActivity(), R.mipmap.ic_logo);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(description);//描述
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        config.setMenuItemBackgroundColor(ShareBoardConfig.BG_SHAPE_NONE);
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);
        config.setTitleText("分享到");
        new ShareAction(mActivity)
                .withText(title).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)//分享平台
                .addButton("复制链接","复制链接","umeng_socialize_share_web","umeng_socialize_share_web")
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
}
