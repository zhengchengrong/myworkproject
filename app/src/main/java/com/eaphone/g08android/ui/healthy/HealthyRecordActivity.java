package com.eaphone.g08android.ui.healthy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.HeathyRecordAdapter;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.LineBean;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HistoryDataPresenter;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class HealthyRecordActivity extends CoreBaseActivity<HistoryDataPresenter> implements JiankangContracts.HistoryDataView, View.OnClickListener {

    @BindView(R.id.iv_left)
    ImageView mIvLeft;

    @BindView(R.id.iv_right)
    ImageView mIvRight;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.rv_news_list)
    RecyclerView rv_news_list;

    private String sensorType;
    private String date;

    private List<String> listTime;

    private HeathyRecordAdapter mAdapter;

    private BaseQuickAdapter<String, BaseViewHolder> mTimeAdapter;

    private Map<String, List<Healthy>> map;

    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_healthy_record;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        sensorType = getIntent().getExtras().getString("sensorType");
        date = TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_10);
        initBackTitle(DeviceLevelUtils.getSensorType(sensorType) + "测量记录").build();

        initData();
        loadData();
        mTvTime.setText(date);
        swipe_refresh.setEnabled(false);
    }

    private void initData() {
        listTime = new ArrayList<>();
        map = new HashMap<>();
        try {
            userId = getIntent().getExtras().getString("userId");
        } catch (Exception e) {
            userId = "";
        }

        mTimeAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.textview_only_item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                mAdapter = new HeathyRecordAdapter(R.layout.record_item, mContext);
                helper.setText(R.id.tv_time, item);
                RecyclerView viewData = helper.getView(R.id.recyclerview);
                mAdapter.setNewData(map.get(item));
                RecyclerViewHelper.initRecyclerViewV(mContext, viewData, false, mAdapter);
            }
        };

        RecyclerViewHelper.initRecyclerViewV(mContext, rv_news_list, false, mTimeAdapter);

        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mTimeAdapter.setEmptyView(getViewEmpty());
    }

    private View getViewEmpty() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("暂无数据");
        emptyView.setImageResouce(R.mipmap.c47);
        return emptyView.getView();
    }


    private void loadData() {
        String StrStart = TimeUtils.timeTypeChange(date, TimeUtils.TIME_TYPE_10, TimeUtils.TIME_TYPE_1);
        String dateNext = TimeUtils.changeToTime(date, TimeUtils.TIME_TYPE_10, TimeUtils.TIME_TYPE_10, TimeUtils.ChangeTimeType.ADD, TimeUtils.DateType.MONTH);
        String dateEnd = TimeUtils.changeToTime(dateNext, TimeUtils.TIME_TYPE_10, TimeUtils.TIME_TYPE_1, TimeUtils.ChangeTimeType.REDUCE, TimeUtils.DateType.SECOND);
        if (TextUtils.isEmpty(userId))
            mPresenter.historyData(sensorType, StrStart, dateEnd, LoginUtil.isFamilyMember());
        else
            mPresenter.historyData(sensorType, StrStart, dateEnd, userId);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
        mIvRight.setEnabled(true);
        mIvLeft.setEnabled(true);
    }

    @Override
    public void getHistoryData(ResultBase<List<Healthy>> result) {

        dismiss();
        if (result.isSuccess()) {
            map.clear();
            listTime.clear();
            String time;
            //获取当天测量时间
            for (int i = 0; i < result.getData().size(); i++) {
                time = TimeUtils.timeTypeChange(result.getData().get(i).getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_3);
                if (!listTime.contains(time)) {
                    listTime.add(time);
                }

            }
            //通过当天时间获取当天数据重新组合数据
            for (int i = 0; i < listTime.size(); i++) {
                List<Healthy> data = new ArrayList<>();
                for (int j = 0; j < result.getData().size(); j++) {
                    if (listTime.get(i).equals(TimeUtils.timeTypeChange(result.getData().get(j).getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_3))) {

                        Healthy healthy = result.getData().get(j);
                        data.add(healthy);
                    }

                }
                map.put(listTime.get(i), data);
            }
            //通过当天时间获取数据
            mTimeAdapter.setNewData(listTime);

        } else {
            showToast(result.getMessage());
        }

        mIvRight.setEnabled(true);
        mIvLeft.setEnabled(true);
    }

    @Override
    public void getTimeTypeData(ResultBase<List<LineBean>> result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                date = TimeUtils.changeToTime(date, TimeUtils.TIME_TYPE_10, TimeUtils.TIME_TYPE_10, TimeUtils.ChangeTimeType.REDUCE, TimeUtils.DateType.MONTH);
                mTvTime.setText(date);
                loadData();
                mIvLeft.setEnabled(false);
                break;

            case R.id.iv_right:
                date = TimeUtils.changeToTime(date, TimeUtils.TIME_TYPE_10, TimeUtils.TIME_TYPE_10, TimeUtils.ChangeTimeType.ADD, TimeUtils.DateType.MONTH);
                mTvTime.setText(date);
                loadData();
                mIvRight.setEnabled(false);
                break;
        }
    }
}
