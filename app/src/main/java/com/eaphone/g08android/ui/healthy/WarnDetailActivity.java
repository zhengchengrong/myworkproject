package com.eaphone.g08android.ui.healthy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.HealthyWarnDetail;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.WarnListPresenter;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class WarnDetailActivity extends CoreBaseActivity<WarnListPresenter> implements JiankangContracts.WarnListView {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.rv_news_list)
    RecyclerView rv_news_list;

    private String sensorType;
    private String timeType;
    private String userId;


    private BaseQuickAdapter<HealthyWarnDetail, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        sensorType = getIntent().getExtras().getString("sensorType");
        timeType = getIntent().getExtras().getString("timeType");
        userId = getIntent().getExtras().getString("userId");
        getWarnTextList(timeType);
        initBackTitle(DeviceLevelUtils.getSensorType(sensorType) + "预警详情").build();

        mAdapter = new BaseQuickAdapter<HealthyWarnDetail, BaseViewHolder>(R.layout.warn_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, HealthyWarnDetail item) {
                helper.setText(R.id.tv_warm, DeviceLevelUtils.getSensorType(sensorType) + "指标告警");
                helper.setText(R.id.tv_time, TimeUtils.displayTime(item.getTime()));
                helper.setText(R.id.tv_content, item.getTitle());
            }
        };
        swipe_refresh.setEnabled(false);
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
    public void getWarnList(ResultBase<List<HealthyWarnDetail>> resultBase) {
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


    public void getParseTime(long date) {

        long today = System.currentTimeMillis();
        long firstDay = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * date);
        String endTime = TimeUtils.getDateToTime(new Date(today), TimeUtils.TIME_TYPE_3);
        String beginTime = TimeUtils.getDateToTime(new Date(firstDay), TimeUtils.TIME_TYPE_3);
        if (TextUtils.isEmpty(userId))
            mPresenter.warnList(sensorType, "1", "10", endTime, beginTime, LoginUtil.isFamilyMember());
        else
            mPresenter.warnList(sensorType, "1", "10", endTime, beginTime, userId);
    }

    public void getWarnTextList(String time_type) {
        switch (time_type) {
            case "week":
                getParseTime(7);
                break;
            case "month":
                getParseTime(30);
                break;
            case "year":
                getParseTime(365);
                break;
            default:

                break;
        }
    }
}
