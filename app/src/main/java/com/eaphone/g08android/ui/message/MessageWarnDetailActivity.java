package com.eaphone.g08android.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.MessageWarnDetail;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.WarnDetailPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.CircleImageView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;


public class MessageWarnDetailActivity extends CoreBaseActivity<WarnDetailPresenter> implements JiankangContracts.WarnDetailView {

    private String id;

    @BindView(R.id.iv_avager)
    CircleImageView iv_avager;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.tv_age)
    TextView tv_age;

    @BindView(R.id.tv_sensor)
    TextView tv_sensor;

    @BindView(R.id.tv_shou)
    TextView tv_shou;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private String sensorType = "";

    BaseQuickAdapter<MessageWarnDetail.AbnormalRecordsBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_warn_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        id = getIntent().getExtras().getString("id");
        mPresenter.warnDetail(id);

        initBackTitle("预警消息").build();

        mAdapter = new BaseQuickAdapter<MessageWarnDetail.AbnormalRecordsBean, BaseViewHolder>(R.layout.warn_detail_item) {
            @Override
            protected void convert(BaseViewHolder helper, MessageWarnDetail.AbnormalRecordsBean item) {

                TextView textView = helper.getView(R.id.tv_data);
                String unit = FormatUtil.getUnit(sensorType);
                helper.setText(R.id.tv_time, TimeUtils.timeTypeChange(item.getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_6));
                if (item.getValue().contains("/")) {
                    String[] values = item.getValue().split("/");
                    textView.setText(values[0] + unit + "/" + values[1] + unit);
                } else {
                    textView.setText(item.getValue() + unit);
                }
                helper.setText(R.id.tv_result, item.getText());
            }
        };

        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerview, true, mAdapter);
        show(R.string.loading);
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
    public void getWarnDetail(ResultBase<MessageWarnDetail> result) {
        dismiss();
        if (result.isSuccess()) {
            initView(result.getData());
            mAdapter.setNewData(result.getData().getAbnormalRecords());
        } else {

        }
    }

    private void initView(MessageWarnDetail detail) {
        sensorType = detail.getSensor().getSensorType();
        ImageLoader.displayImage(Const.getAvater(detail.getUserId()), iv_avager);
        tv_title.setText(detail.getTitle());
        tv_name.setText(detail.getUser().getName());
        tv_time.setText(TimeUtils.timeTypeChange(detail.getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_6));
        tv_sex.setText(detail.getUser().getGender());
        if (detail.getUser().getAge() == 0) {
            tv_age.setText("未满一岁");
        } else
            tv_age.setText(detail.getUser().getAge() + "");
        tv_sensor.setText(detail.getSensor().getSensorName());
        if (detail.getSensor().getSensorType().equals(Const.BLODPRESSURE)) {
            tv_shou.setText(detail.getSensor().getNormalValue().replace(";", "\n"));
        } else {
            tv_shou.setText(detail.getSensor().getNormalValue());
        }
    }
}
