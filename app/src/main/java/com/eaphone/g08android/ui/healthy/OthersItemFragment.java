package com.eaphone.g08android.ui.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.HealthyServiceFragmentAdapter;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyServiceList;
import com.eaphone.g08android.bean.LineBean;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HistoryDataPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.LineChartUtils;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.CustomHeaderAndFooterPicker;
import com.eaphone.g08android.widget.MListView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
import cn.qqtheme.framework.picker.OptionPicker;

public class OthersItemFragment extends CoreBaseFragment<HistoryDataPresenter> implements JiankangContracts.HistoryDataView {

    @BindView(R.id.lineChart)
    LineChart lc_day;

    @BindView(R.id.list_item)
    MListView mListItem;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.tv_unit)
    TextView tv_unit;

    @BindView(R.id.tv_time_x)
    TextView tv_time_x;

    @BindView(R.id.btn_date)
    Button mBtnDate;

    private String sensor_type;
    private String userId;
    private String timeType;//日周月年

    private String time;

    private static int DEFULT_TIME = 2011;
    private HealthyServiceFragmentAdapter mAdapter;
    private List<HealthyServiceList> mHealthyServiceList;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_others_item;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        super.initData();
//        show(R.string.loading);
        sensor_type = getArguments().getString("type");
        timeType = getArguments().getString("timeType");
        userId = getArguments().getString("userId");


        upLoad();

        tv_unit.setText(FormatUtil.getUnit(sensor_type));

        mTvTime.setText("测量时间：" + time);

        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeType.equals("日")) {
                    setPickerWeekAndDay("day");

                } else if (timeType.equals("周")) {
                    setPickerWeekAndDay("week");
                } else if (timeType.equals("月")) {
                    setPickerMonth();
                } else if (timeType.equals("年")) {
                    setPickerYear();

                }
            }
        });
        mHealthyServiceList = new ArrayList<>();
        setButtomAdapter();
        setIfHasData("--", "--");
    }


    private void setButtomAdapter() {
        mAdapter = new HealthyServiceFragmentAdapter(mHealthyServiceList, getActivity());
        mListItem.setAdapter(mAdapter);
    }


    private void upLoad() {
        if (timeType.equals("日")) {
            time = TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_3);
            loadData("day", "");
            tv_time_x.setText("小时");
        } else if (timeType.equals("周")) {
            time = TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_3);
            loadData("unnatural_week", "");
            tv_time_x.setText("日");
        } else if (timeType.equals("月")) {
            time = TimeUtils.timeTypeChange(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_3), TimeUtils.TIME_TYPE_4, TimeUtils.TIME_TYPE_3);//将获取时间修改为当月头一天
            loadData("month", "");
            tv_time_x.setText("日");
        } else if (timeType.equals("年")) {
            time = TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7);
            loadData("unnatural_year", "");
            tv_time_x.setText("月");
        }
    }

    private void loadData(String type, String time) {

        if (TextUtils.isEmpty(userId)) {

            mPresenter.timeTypeData(sensor_type, type, time, LoginUtil.isFamilyMember());
        } else {
            mPresenter.timeTypeData(sensor_type, type, time, userId);
        }

    }


    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event.getCode() == EventCode.A) {
            upLoad();
        }
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getHistoryData(ResultBase<List<Healthy>> result) {
        if (result.isSuccess()) {
//            showLine(result.getData(), Calendar.HOUR_OF_DAY);
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getTimeTypeData(ResultBase<List<LineBean>> result) {

        if (result.isSuccess()) {
            if (result.getData().isEmpty()) return;
            showLine(result.getData(), timeType);
            if (!timeType.equals("日")) {
                setTime(result.getData().get(0).getTimestamp(), result.getData().get(result.getData().size() - 1).getTimestamp());
            }
        } else {
            showToast(result.getMessage());
        }
    }

    private void setTime(String firstDay, String endDay) {

        if (timeType.equals("周") || timeType.equals("月")) {
            firstDay = TimeUtils.timeTypeChange(firstDay, TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_3);
            endDay = TimeUtils.timeTypeChange(endDay, TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_3);
        } else if (timeType.equals("年")) {
            firstDay = TimeUtils.timeTypeChange(firstDay, TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_4);
            endDay = TimeUtils.timeTypeChange(endDay, TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_4);
        }

        mTvTime.setText("测量时间：" + firstDay + "~" + endDay);
    }


    LineDataSet set1;
    LineDataSet set2;

    private void showLine(final List<LineBean> dataList, final String dayTime) {

        LineChartUtils.setLinesChart(lc_day, dataList, dayTime, sensor_type);

        if (lc_day.getData() != null) {

            set1 = lc_day.getData().getDataSetByIndex(0);
            Entry entry1 = set1.getEntryForXIndex(0);
            String data2 = "";
            if (sensor_type.equals(Const.BLODPRESSURE)) {
                set2 = lc_day.getData().getDataSetByIndex(1);
                Entry entry2 = set2.getEntryForXIndex(0);
                if (entry2 != null)
                    data2 = String.valueOf(entry2.getVal()).replace(".0", "");
            }

            if (entry1 != null) {
                String data = String.valueOf(entry1.getVal()).replace(".0", "");
                setIfHasData(data2, data);
            } else {
                setIfHasData("--", "--");
            }
        }

        lc_day.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {

                if (!sensor_type.equals(Const.BLODPRESSURE))
                    setIfHasData("", dataList.get(entry.getXIndex()).getText());
                else {
                    String[] data = dataList.get(entry.getXIndex()).getText().split("/");
                    setIfHasData(data[0], data[1]);
                }
                if (dayTime.equals("日")) {
                    setTimeText(entry.getXIndex(), TimeUtils.TIME_TYPE_6);
                } else if (dayTime.equals("年")) {
                    setTimeText(entry.getXIndex(), TimeUtils.TIME_TYPE_4);
                } else {
                    setTimeText(entry.getXIndex(), TimeUtils.TIME_TYPE_3);
                }


            }

            private void setTimeText(int pos, String timeType) {
                mTvTime.setText("测量时间："+TimeUtils.timeTypeChange(dataList.get(pos).getTimestamp(), TimeUtils.TIME_TYPE_1, timeType));
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setIfHasData(String data, String data2) {
        mHealthyServiceList.clear();
        HealthyServiceList helthy1 = new HealthyServiceList();
        HealthyServiceList helthy2 = new HealthyServiceList();

        if (sensor_type.equals(Const.BLODPRESSURE)) {
            helthy1.setTitle("收缩压（高压）");
            helthy1.setData(data + "mmHg");

            helthy2.setTitle("舒张压（低压）");
            helthy2.setData(data2 + "mmHg");

            mHealthyServiceList.add(helthy1);
            mHealthyServiceList.add(helthy2);
        } else if (sensor_type.equals(Const.HEARTRATE)) {
            helthy1.setTitle("心率值");
            helthy1.setData(data2 + "bpm");
            mHealthyServiceList.add(helthy1);

        } else if (sensor_type.equals(Const.ELECTROCARDIOGRAM)) {

        } else if (sensor_type.equals(Const.BODYTEMPERATURE)) {
            helthy1.setTitle("体温值");
            helthy1.setData(data2 + "℃");
            mHealthyServiceList.add(helthy1);

        } else if (sensor_type.equals(Const.OXYGENATION)) {
            helthy1.setTitle("血氧");
            helthy1.setData(data2 + "%");
            mHealthyServiceList.add(helthy1);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void setPickerYear() {
        String[] data = null;
        int thisYear = Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7));
        data = new String[thisYear - DEFULT_TIME];
        for (int i = 0; i < data.length; i++) {
            data[i] = String.valueOf(thisYear--);
        }
        CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(getActivity(), data);
        picker.setSelectedItem(data[data.length - 1]);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                mTvTime.setText("测量时间：" + option);
                time = TimeUtils.timeTypeChange(option, TimeUtils.TIME_TYPE_7, TimeUtils.TIME_TYPE_3);
                loadData("unnatural_year", option + "-" + TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_11) + "-" + TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_15));
            }
        });
        picker.show();

    }

    private void setPickerMonth() {
        DatePicker picker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH);
        picker.setRange(DEFULT_TIME, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));//年份范围
        Calendar calendar = Calendar.getInstance();
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {

            @Override
            public void onDatePicked(String year, String month) {
                String testTime = year + "-" + month + "-01";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtils.TIME_TYPE_3);
                try {
                    Date parse = simpleDateFormat.parse(testTime);
                    if (parse.getTime() < System.currentTimeMillis()) {
                        mTvTime.setText("测量时间：" + year + "-" + month);
                        time = year + "-" + month + "-01";
                        loadData("month", TimeUtils.getMonthLastDay(year + "-" + month));
                    } else {
                        showToast("请选择正确日期");
                    }
                } catch (ParseException e) {
                }
            }
        });
        picker.show();
    }

    private void setPickerWeekAndDay(final String monthOrDay) {
        DatePicker picker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH_DAY);
        picker.setRange(DEFULT_TIME, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));//年份范围
        Calendar calendar = Calendar.getInstance();
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String testTime = year + "-" + month + "-01";
                String s = year + "-" + month + "-" + day;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtils.TIME_TYPE_3);
                try {
                    Date parse = simpleDateFormat.parse(testTime);
                    if (parse.getTime() < System.currentTimeMillis()) {
                        mTvTime.setText("测量时间：" + s);
                        time = year + "-" + month + "-" + day;
                        if (monthOrDay.equals("day")) {
                            loadData("day", time);
                        } else {
                            loadData("unnatural_week", time);
                        }

                    } else {
                        showToast("请选择正确日期");
                    }
                } catch (ParseException e) {
                }

            }
        });
        picker.show();
    }

}
