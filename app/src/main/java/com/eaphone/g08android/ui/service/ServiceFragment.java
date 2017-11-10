package com.eaphone.g08android.ui.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.http.APIUrl.IApi;
import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.eaphone.g08android.ui.personcenter.WebPayActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseFragment;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:15
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:15
 * 修改备注：
 */
public class ServiceFragment extends CoreBaseFragment {

    @BindView(R.id.iv_quick)
    ImageView iv_quick;

    @BindView(R.id.iv_inquiry)
    ImageView iv_inquiry;

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        super.initData();
        iv_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_APPOINMENT, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebActivity.class, bundle);
            }
        });
        iv_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "");
                bundle.putString("url", IApi.getOpenPlatform(IApi.URL_INQUIRY, PreferencesUtils.getSharePreStr(Const.USERID)));
                startActivity(WebPayActivity.class, bundle);
            }
        });

    }


}
