package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.FAQ;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.FAQPresenter;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;

public class KeFuDetailActivity extends CoreBaseActivity<FAQPresenter> implements NewsContracts.FAQView {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kefu_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        initBackTitle("详细内容").build();
        id = getIntent().getExtras().getString("id");
        mPresenter.FAQDetail(id);
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
    public void getFAQ(ResultBase<List<FAQ>> result) {

    }

    @Override
    public void getFAQDetail(ResultBase<FAQ> result) {
        dismiss();
        if (result.isSuccess()) {
            mTvTitle.setText(result.getData().getQuestion());
            mTvContent.setText(result.getData().getQuestion());
        } else {
            showToast(result.getMessage());
        }
    }
}
