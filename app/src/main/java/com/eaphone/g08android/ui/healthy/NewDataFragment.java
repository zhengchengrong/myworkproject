package com.eaphone.g08android.ui.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.LastDataPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;


public class NewDataFragment extends CoreBaseFragment<LastDataPresenter> implements JiankangContracts.LastDataView {

    @BindView(R.id.tv_source)
    TextView mTvSource;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.btn_date)
    Button mBtnDate;

    @BindView(R.id.tv_data)
    TextView mTvData;

    @BindView(R.id.tv_unit)
    TextView mTvUnit;

    @BindView(R.id.tv_unit_ch)
    TextView mTvUnitCh;

    @BindView(R.id.tv_result)
    TextView mTvResult;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @BindView(R.id.tv_trend)
    TextView mTvTrend;


    private String time;//所选中查询的时间
    private String sensor_type;
    private String begin_time;
    private String end_time;

    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event.getCode() == EventCode.A) {
            initLastData();
        }
    }

    @Override
    public void initData() {
        super.initData();
        sensor_type = getArguments().getString("type");
        userId = getArguments().getString("userId");
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker picker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH_DAY);
                picker.setRange(2008, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));//年份范围
                Calendar calendar = Calendar.getInstance();
                picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        String testTime = year + "-" + month + "-01";
                        String s = year + "年" + month + "月" + day + "日";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtils.TIME_TYPE_3);
                        try {
                            Date parse = simpleDateFormat.parse(testTime);
                            if (parse.getTime() < System.currentTimeMillis()) {
                                mTvTime.setText( s);
                                time = year + "-" + month + "-" + day;
                                parseTime();
                            } else {
                                showToast("请选择正确日期");
                            }
                        } catch (ParseException e) {
//                            ExceptionCatchUtil.exceptionToprint(e);
                        }

                    }
                });
                picker.show();
            }
        });
        initLastData();
        mTvTrend.setText(DeviceLevelUtils.getSensorType(sensor_type) + "趋势图");
        onStartAnimation();
        initViewData(null);
        show(R.string.loading);
    }

    private void initLastData() {
        HealthyDataEnity enity = new HealthyDataEnity();
        HealthyDataEnity.DataBean dataBean = new HealthyDataEnity.DataBean();
        dataBean.setSensorType(sensor_type);
        dataBean.setType("last");
        List<HealthyDataEnity.DataBean> list = new ArrayList<>();
        list.add(dataBean);
        enity.setData(list);

        if(TextUtils.isEmpty(userId))
        mPresenter.lastData(enity, LoginUtil.isFamilyMember());
        else
            mPresenter.lastData(enity, userId);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getLastData(ResultBase<List<Healthy>> result) {
        setResult(result);
    }

    @Override
    public void getHistoryData(ResultBase<List<Healthy>> result) {
        setResult(result);
    }

    private void setResult(ResultBase<List<Healthy>> result) {
        dismiss();
        if (result.isSuccess()) {
            if (!result.getData().isEmpty()) {
                mTvSource.setText(result.getData().get(0).getSource().getText());
                mTvTime.setText( TimeUtils.timeTypeChange(result.getData().get(0).getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_2));

                initViewData(result.getData().get(0).getText());
            } else {
                initViewData("");
            }
        }
    }

    private void parseTime() {
        begin_time = TimeUtils.dateToTime(TimeUtils.timeToDate(time, TimeUtils.TIME_TYPE_3), TimeUtils.TIME_TYPE_1);
        end_time = TimeUtils.dateToTime(new Date(TimeUtils.timeToDate(time, TimeUtils.TIME_TYPE_3).getTime() + 24 * 60 * 60 * 1000), TimeUtils.TIME_TYPE_1);
        if(TextUtils.isEmpty(userId))
        mPresenter.historyData(sensor_type, begin_time, end_time, LoginUtil.isFamilyMember());
        else
            mPresenter.historyData(sensor_type, begin_time, end_time, userId);
    }

    private void initViewData(String value) {
        switch (sensor_type) {
            case Const.HEARTRATE:
                setBPM(value);
                break;
            case Const.ELECTROCARDIOGRAM:
                setECG(value);
                break;
            case Const.BLODPRESSURE:
                setBloodPressure(value);
                break;
            case Const.BODYTEMPERATURE:
                jugdeBodytemperature(value);
                break;
            case Const.OXYGENATION:
                jugdeOxygenation(value);
                break;
        }
    }

    //血氧
    private void jugdeOxygenation(String data) {
        mTvTrend.setText("血氧趋势");
        mTvUnit.setText("%");
        mTvUnitCh.setText("血氧");
        if (TextUtils.isEmpty(data)) {
            mTvData.setText("--");
            mTvContent.setText("暂无建议");
            mTvResult.setText("");
            mTvSource.setText("");
            return;
        }
            int level = DeviceLevelUtils.getBloodOxygen(Float.parseFloat(data));
            if (level == 1) {
                mTvResult.setText("正常");

                mTvContent.setText(R.string.warn_oxygen_n);
            } else if (level == 2) {
                mTvResult.setText("偏低");
                mTvContent.setText(R.string.warn_oxygen_l);
            }
            mTvData.setText(data.replace(".0", ""));
    }

    //体温
    private void jugdeBodytemperature(String data) {
        mTvTrend.setText("体温趋势");
        mTvUnit.setText("℃");
        mTvUnitCh.setText("体温");
        if (TextUtils.isEmpty(data)) {
            mTvData.setText("--");
            mTvContent.setText("暂无建议");
            mTvResult.setText("");
            mTvSource.setText("");
            return;
        }
            int level = DeviceLevelUtils.getTemperatureLevel(Float.parseFloat(data));
            if (level == 1) {
                mTvResult.setText("正常");

                mTvContent.setText(R.string.warn_thermometer_n);
            } else if (level == 2) {
                mTvResult.setText("低热");
                mTvContent.setText(R.string.warn_thermometer_l);
            } else if (level == 3) {
                mTvResult.setText("中热");
                mTvContent.setText(R.string.warn_thermometer_l);
            } else if (level == 4) {

                mTvResult.setText("高热");
                mTvContent.setText(R.string.warn_thermometer_h);
            } else if (level == 5) {

                mTvResult.setText("超高热");
                mTvContent.setText(R.string.warn_thermometer_h);
            } else {

                mTvResult.setText("低温");
                mTvContent.setText(R.string.warn_thermometer_flat);
            }
            mTvData.setText(data);
    }

    private void setBPM(String data) {

        mTvTrend.setText("心率趋势");
        mTvUnit.setText("bpm");
        mTvUnitCh.setText("心率");

        if (TextUtils.isEmpty(data)) {
            mTvData.setText("--");
            mTvContent.setText("暂无建议");
            mTvResult.setText("");
            mTvSource.setText("");
            return;
        }
            int level = DeviceLevelUtils.getHRVLevel(Float.parseFloat(data));

            if (level == 1) {
                mTvResult.setText("正常");
                mTvContent.setText(R.string.warn_heart_n);

            } else if (level == 2) {
                mTvResult.setText("过快");

                mTvContent.setText(R.string.warn_heart_h);
            } else {
                mTvResult.setText("过缓");
                mTvContent.setText(R.string.warn_heart_l);
            }
            mTvData.setText(data.replace(".0", ""));


    }

    private void setECG(String data) {

        mTvTrend.setText("心电趋势");
        mTvUnit.setText("BPM");
        mTvUnitCh.setText("心电");
        if (TextUtils.isEmpty(data)) {
            mTvData.setText("--");
            mTvContent.setText("暂无建议");
            mTvResult.setText("");
            mTvSource.setText("");
            return;
        }
        if (!data.equals("")) {

            mTvData.setText(data);
        }
    }

    private void setBloodPressure(String data) {
        mTvTrend.setText("血压趋势");
        mTvUnit.setText("mmHg");
        mTvUnitCh.setText("收缩压/舒张压");
        if (TextUtils.isEmpty(data)) {
            mTvData.setText("--/--");
            mTvContent.setText("暂无建议");
            mTvResult.setText("");
            mTvSource.setText("");
            return;
        }
        String[] split = data.split("/");
        int value = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[0]), Const.FLOAT_2));
        int value1 = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[1]), Const.FLOAT_2));
        mTvData.setText(value + "/" + value1);

        switch (DeviceLevelUtils.getBloodPressLevel(value, value1)) {//1正常 2轻度 3中度 4重度  5低压
            case 1:
                mTvResult.setText("正常");

                mTvContent.setText(R.string.warn_press_n);
                break;
            case 2:
                mTvResult.setText("轻度高血压");
                mTvContent.setText(R.string.warn_press_h_low_grade);
                break;
            case 3:
                mTvResult.setText("中度高血压");
                mTvContent.setText(R.string.warn_press_h_moder_grade);
                break;
            case 4:
                mTvResult.setText("高度高血压");
                mTvContent.setText(R.string.warn_press_h_high_grade);
                break;
            case 5:
                mTvResult.setText("低血压");
                mTvContent.setText(R.string.warn_press_l);
                break;
            default:
                mTvResult.setText("");
                mTvContent.setText("暂无建议");
                break;
        }
    }

    private void onStartAnimation() {
        AnimationSet animatorSet = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.arrows_down);
        mTvTrend.startAnimation(animatorSet);
    }

}
