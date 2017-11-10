package com.eaphone.g08android.ui.healthy;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.HandInDataEnity;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HandInPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.CustomHeaderAndFooterPicker;
import com.eaphone.g08android.widget.DataTimePicker;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;


public class HandInDataActivity extends CoreBaseActivity<HandInPresenter> implements View.OnClickListener, JiankangContracts.HandInView {

    private String sensor_type;

    @BindView(R.id.relative_time)
    RelativeLayout mRlTime;

    @BindView(R.id.tv_choose_time)
    TextView mTvChooseTime;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_title2)
    TextView mTvTitle2;

    @BindView(R.id.tv_title3)
    TextView mTvTitle3;

    @BindView(R.id.relative_shou)
    RelativeLayout mRlShou;

    @BindView(R.id.tv_shou)
    TextView mTvShou;

    @BindView(R.id.iv_shou)
    ImageView mIvShou;

    @BindView(R.id.relative_shu)
    RelativeLayout mRlShu;

    @BindView(R.id.iv_shu)
    ImageView mIvShu;

    @BindView(R.id.tv_shu)
    TextView mTvShu;

    @BindView(R.id.relative_status)
    RelativeLayout mRlStatus;

//    @BindView(R.id.iv_status)
//    ImageView mIvStatus;

    @BindView(R.id.tv_status)
    TextView mTvStatus;

    private String mTitle;

    private String[] stringsO = new String[]{Const.BOOLDSUGAR_EMPTY, Const.BOOLDSUGAR_ONE, Const.BOOLDSUGAR_TWO
            , Const.BOOLDSUGAR_THREE, Const.BOOLDSUGAR_FOUR
            , Const.BOOLDSUGAR_FIVE, Const.BOOLDSUGAR_SIX};

    private String[] stringsPress = new String[]{Const.BLODPRESSURE_ONE, Const.BLODPRESSURE_TWO, Const.BLODPRESSURE_THREE
            , Const.BLODPRESSURE_FOUR, Const.BLODPRESSURE_FIVE
            , Const.BLODPRESSURE_SIX};

    @Override
    public int getLayoutId() {
        return R.layout.activity_hand_in_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        sensor_type = getIntent().getExtras().getString("type");
        intiTitleText();
        initBackTitle(mTitle).setRightText("保存").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandInDataEnity enity = new HandInDataEnity();
                HandInDataEnity.Status status = new HandInDataEnity.Status();
                if (TextUtils.isEmpty(mTvChooseTime.getText())) {
                    showToast("请选择测量时间");
                    return;
                }

                if (mRlShou.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(mTvShou.getText())) {
                        showToast("请选择测量数据");
                        return;
                    }
                }

                if (mRlShu.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(mTvShu.getText())) {
                        showToast("请选择测量数据");
                        return;
                    }
                }


                if (mRlStatus.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(mTvStatus.getText())) {
                        showToast("请选择测量状态");
                        return;
                    }
                }

                enity.setTimestamp(TimeUtils.timeTypeChange(mTvChooseTime.getText().toString(), TimeUtils.TIME_TYPE_6, TimeUtils.TIME_TYPE_1));
                if (sensor_type.equals(Const.BLODPRESSURE)) {

                    if(Integer.parseInt(dataShu)>=Integer.parseInt(dataShou)){
                        showToast("您的血压输入有误，请重新输入");
                        mTvShu.setText("");
                        mTvShou.setText("");
                        return;

                    }

                    enity.setValue("[" +dataShu  + "," + dataShou + "]");
                } else {
                    enity.setValue(dataShou);
                }
                if(!TextUtils.isEmpty(mTvStatus.getText())){
                    status.setText("测量状态");
                    status.setValue(mTvStatus.getText().toString());
                    enity.setStatus(status);
                }
                enity.setUserId(LoginUtil.isFamilyMember());
                mPresenter.handIn(sensor_type, enity);
            }
        }).build();

        addAction();
    }


    private void addAction() {
        mRlTime.setOnClickListener(this);
        mRlShou.setOnClickListener(this);
        mRlShu.setOnClickListener(this);
        mRlStatus.setOnClickListener(this);
    }

    private void intiTitleText() {
        StringBuilder sb = new StringBuilder();
        if (Const.ELECTROCARDIOGRAM.equals(sensor_type)) {
            mRlShu.setVisibility(View.GONE);
            sb.append("心电");
            mRlStatus.setVisibility(View.GONE);
        } else if (Const.BLODPRESSURE.equals(sensor_type)) {

            sb.append("血压");
            mIvShou.setImageResource(R.mipmap.ic_shou);
            mIvShu.setImageResource(R.mipmap.ic_shu);
            mTvTitle.setText("收缩压(高压)");
            mTvTitle2.setText("舒张压(低压)");

        } else if (Const.HEARTRATE.equals(sensor_type)) {
            mRlShu.setVisibility(View.GONE);
            sb.append("心率");
            mTvTitle.setText("心率");
            mRlStatus.setVisibility(View.GONE);
        } else if (Const.BODYTEMPERATURE.equals(sensor_type)) {
            sb.append("体温");
            mTvTitle.setText("体温");
            mRlStatus.setVisibility(View.GONE);
            mRlShu.setVisibility(View.GONE);
        } else {
            mRlShu.setVisibility(View.GONE);
            sb.append("血氧");
            mTvTitle.setText("血氧饱和度");
            mRlStatus.setVisibility(View.GONE);
        }
        sb.append("记录");
        mTitle = sb.toString();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_time:
                timePicker();
                break;
            case R.id.relative_shou:
                if (Const.ELECTROCARDIOGRAM.equals(sensor_type)) {


                } else if (Const.BLODPRESSURE.equals(sensor_type)) {
                    showChooseText(FormatUtil.setData(261, 40), mTvShou);

                } else if (Const.HEARTRATE.equals(sensor_type)) {
                    showChooseText(FormatUtil.setData(159, 41), mTvShou);
                } else if (Const.BODYTEMPERATURE.equals(sensor_type)) {

                    showChooseText(FormatUtil.setDataFoat(61, 35), mTvShou);

                } else {
                    showChooseText(FormatUtil.setData(16, 85), mTvShou);

                }


                break;
            case R.id.relative_shu:
                showChooseText(FormatUtil.setData(171, 30), mTvShu);
                break;
            case R.id.relative_status:

                if (Const.ELECTROCARDIOGRAM.equals(sensor_type)) {


                } else if (Const.BLODPRESSURE.equals(sensor_type)) {

                    showChooseText(stringsPress, mTvStatus);

                } else if (Const.HEARTRATE.equals(sensor_type)) {

                } else if (Const.BODYTEMPERATURE.equals(sensor_type)) {


                } else {
                    showChooseText(stringsO, mTvStatus);
                }


                break;

        }
    }

    private String dataShu;
    private String dataShou;

    private void showChooseText(String[] strs, final TextView textView) {
        CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(HandInDataActivity.this, strs);
        picker.setSelectedItem(strs[0]);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {

                String unit = "";
                if (sensor_type.equals(Const.BLODPRESSURE)) {
                    unit = "mmHg";
                } else if (sensor_type.equals(Const.OXYGENATION)) {
                    unit = "%";
                } else if (sensor_type.equals(Const.ELECTROCARDIOGRAM)) {

                } else if (sensor_type.equals(Const.BODYTEMPERATURE)) {
                    unit = "℃";
                } else {
                    unit = "bpm";
                }

                if (textView == mTvShu) {
                    dataShu = option;
                    textView.setText(option + unit);
                } else if (textView == mTvShou) {
                    dataShou = option;
                    textView.setText(option + unit);
                } else {
                    textView.setText(option);
                }

            }
        });
        picker.show();
    }

    private void timePicker() {
        final DatePicker picker1 = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        picker1.setRange(2011, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));//年份范围
        final Calendar calendar = Calendar.getInstance();
        final long currentTimeMillis = System.currentTimeMillis();
        final Date date = new Date(currentTimeMillis);
        final String min = TimeUtils.dateToTime(date, TimeUtils.TIME_TYPE_12);
        picker1.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(final String year, final String month, final String day) {
                //默认选中当前时间
                DataTimePicker picker = new DataTimePicker(HandInDataActivity.this, DataTimePicker.HOUR_OF_DAY);
                picker.setTopLineVisible(false);
                picker.setSelectedItem(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), min);
                picker.setOnTimePickListener(new DataTimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        if (minute.length() == 1) {
                            minute = "00";
                        }
                        String str_time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                        Date date1 = TimeUtils.timeToDate(str_time, TimeUtils.TIME_TYPE_6);
                        long l = date1.getTime();
                        if (l > currentTimeMillis) {
                            showToast("请选择正确日期");
                        } else {
                            mTvChooseTime.setText(str_time);
                        }
                    }
                });
                picker.show();
            }
        });
        picker1.show();
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
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void getHandIn(ResultBase resultBase) {
        if (resultBase.isSuccess()) {
            showToast("数据上传成功");
            CoreEvent event = new CoreEvent(EventCode.A);
            EventBusUtils.sendEvent(event);
            HandInDataActivity.this.finish();
        } else {
            showToast(resultBase.getMessage());
        }
    }
}
