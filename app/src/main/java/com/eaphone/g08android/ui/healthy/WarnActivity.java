package com.eaphone.g08android.ui.healthy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.HealthyWarn;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.WarnPresenter;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;


public class WarnActivity extends CoreBaseActivity<WarnPresenter> implements JiankangContracts.WarnView {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.rv_news_list)
    RecyclerView rv_news_list;

    private String sensorType;

    private String userId;

    private BaseQuickAdapter<HealthyWarn, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        sensorType = getIntent().getExtras().getString("sensorType");
        try {
            userId = getIntent().getExtras().getString("userId");
        } catch (Exception e) {
            userId = "";
        }
        initBackTitle(DeviceLevelUtils.getSensorType(sensorType) + "预警情况").build();
        swipe_refresh.setEnabled(false);

        if (TextUtils.isEmpty(userId))
            mPresenter.healthyWarn(sensorType, LoginUtil.isFamilyMember());
        else
            mPresenter.healthyWarn(sensorType, userId);

        mAdapter = new BaseQuickAdapter<HealthyWarn, BaseViewHolder>(R.layout.warn_item) {
            @Override
            protected void convert(BaseViewHolder helper, final HealthyWarn item) {

                helper.setText(R.id.tv_title, item.getTitle());
                ImageView image = helper.getView(R.id.iv_icon);
                if (item.getTitle().contains("周")) {
                    image.setImageResource(R.mipmap.ic_week);
                } else if (item.getTitle().contains("月")) {
                    image.setImageResource(R.mipmap.ic_month);
                } else {
                    image.setImageResource(R.mipmap.ic_year);
                }
                String text_warn_num = String.valueOf(item.getAlarmCount());
                String test_num = String.valueOf(item.getMeasureCount());
                SpannableString styledText = new SpannableString("测量" + test_num + "次，预警" + text_warn_num + "次");//包尾不包头
                styledText.setSpan(new TextAppearanceSpan(WarnActivity.this, R.style.data_warn0), 2, test_num.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(WarnActivity.this, R.style.data_warn0), test_num.length() + 6, test_num.length() + 6 + text_warn_num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                TextView tv = helper.getView(R.id.tv_warn);
                tv.setText(styledText, TextView.BufferType.SPANNABLE);

                helper.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String timeType;

                        if (item.getTitle().contains("周")) {
                            timeType = "week";
                        } else if (item.getTitle().contains("月")) {
                            timeType = "month";
                        } else {
                            timeType = "year";
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("sensorType", sensorType);
                        bundle.putString("timeType", timeType);
                        bundle.putString("userId", userId);
                        startActivity(WarnDetailActivity.class, bundle);
                    }
                });
            }
        };

        RecyclerViewHelper.initRecyclerViewV(mContext, rv_news_list, true, mAdapter);


    }

    private View getViewEmpty() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("暂无数据");
        emptyView.setImageResouce(R.mipmap.c47);
        return emptyView.getView();
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
    public void getHealthyWarn(ResultBase<List<HealthyWarn>> resultBase) {
        dismiss();
        if (resultBase.isSuccess()) {
            if (resultBase.getData().size() == 0) {
                mAdapter.setEmptyView(getViewEmpty());
            } else
                mAdapter.setNewData(resultBase.getData());
        } else {
            showToast(resultBase.getMessage());
        }

    }
}
