package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.JianKang;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.JiankangPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;


public class JiankangActivity extends CoreBaseActivity<JiankangPresenter> implements JiankangContracts.JiankangView, View.OnClickListener {

    @BindView(R.id.edt_height)
    EditText edt_height;

    @BindView(R.id.edt_weight)
    EditText edt_weight;

    @BindView(R.id.edt_tag)
    EditText edt_tag;

    @BindView(R.id.edt_ldlc)
    EditText edt_ldlc;

    @BindView(R.id.edt_hdlc)
    EditText edt_hdlc;

    @BindView(R.id.edt_shou)
    EditText edt_shou;

    @BindView(R.id.edt_shu)
    EditText edt_shu;

    @BindView(R.id.edt_heart)
    EditText edt_heart;

    @BindView(R.id.edt_all)
    EditText edt_all;

    @BindView(R.id.tv_age)
    TextView tv_age;

    @BindView(R.id.tv_date)
    TextView tv_date;

    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiankang;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        initBackTitle("健康档案").setRightText("保存").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JianKang jiankang = new JianKang();


                if (!TextUtils.isEmpty(edt_height.getText())) {
                    jiankang.setHeight(Integer.parseInt(edt_height.getText().toString()));
                } else {
                    if (userId.equals(PreferencesUtils.getSharePreStr(Const.USERID))) {
                        showToast("请填写身高");
                        return;
                    }
                }


                if (!TextUtils.isEmpty(edt_weight.getText())) {
                    jiankang.setWeight(Integer.parseInt(edt_weight.getText().toString()));
                } else {
                    if (userId.equals(PreferencesUtils.getSharePreStr(Const.USERID))) {
                        showToast("请填写体重");
                        return;
                    }
                }

                if (!TextUtils.isEmpty(edt_tag.getText())) {
                    jiankang.setTag(Double.parseDouble(edt_tag.getText().toString()));
                }

                if (!TextUtils.isEmpty(edt_hdlc.getText())) {
                    jiankang.setHdlc(Double.parseDouble(edt_hdlc.getText().toString()));
                }

                if (!TextUtils.isEmpty(edt_ldlc.getText())) {
                    jiankang.setLdlc(Double.parseDouble(edt_ldlc.getText().toString()));
                }

                if (!TextUtils.isEmpty(tv_age.getText())) {
                    jiankang.setHypotension_age(Integer.parseInt(tv_age.getText().toString()));
                }

                if (!TextUtils.isEmpty(tv_date.getText())) {
                    jiankang.setHypotension_date(tv_date.getText().toString());
                }

                if (!TextUtils.isEmpty(edt_shou.getText())) {
                    jiankang.setSystolic(Integer.parseInt(edt_shou.getText().toString()));
                }

                if (!TextUtils.isEmpty(edt_shu.getText())) {
                    jiankang.setDiastolic(Integer.parseInt(edt_shu.getText().toString()));
                }

                if (!TextUtils.isEmpty(edt_heart.getText())) {
                    jiankang.setHeart_rate(Integer.parseInt(edt_heart.getText().toString()));
                }

                if (!TextUtils.isEmpty(edt_all.getText())) {
                    jiankang.setChol(Double.parseDouble(edt_all.getText().toString()));
                }
                mPresenter.jiankang(userId, jiankang);

            }
        }).build();


        initData();

    }

    private void initData() {

        userId = getIntent().getExtras().getString("userId");
        mPresenter.jiankang(userId);
        tv_age.setOnClickListener(this);
        tv_date.setOnClickListener(this);
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
    public void getJiankang(ResultBase<JianKang> result) {
        dismiss();
        if (result.isSuccess()) {

            if (result.getData() != null)
                setData(result.getData());

        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getParchJiankang(ResultBase<JianKang> result) {
        if (result.isSuccess()) {
            showToast("健康数据保存成功");
            JiankangActivity.this.finish();
        } else {
            showToast(result.getMessage());
        }
    }

    private void setData(JianKang jiankang) {
        if (jiankang.getHeight() != null)
            edt_height.setText(jiankang.getHeight() + "");

        if (jiankang.getWeight() != null)
            edt_weight.setText(jiankang.getWeight() + "");

        if (jiankang.getTag() != null)
            edt_tag.setText(jiankang.getTag() + "");

        if (jiankang.getChol() != null)
            edt_all.setText(jiankang.getChol() + "");

        if (jiankang.getLdlc() != null)
            edt_ldlc.setText(jiankang.getLdlc() + "");

        if (jiankang.getHdlc() != null)
            edt_hdlc.setText(jiankang.getHdlc() + "");

        if (jiankang.getHypotension_age() != null)
            tv_age.setText(jiankang.getHypotension_age() + "");

        if (!TextUtils.isEmpty(jiankang.getHypotension_date()))
            tv_date.setText(TimeUtils.timeTypeChange(jiankang.getHypotension_date(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_3));

        if (jiankang.getSystolic() != null)
            edt_shou.setText(jiankang.getSystolic() + "");

        if (jiankang.getDiastolic() != null)
            edt_shu.setText(jiankang.getDiastolic() + "");

        if (jiankang.getHeart_rate() != null)
            edt_heart.setText(jiankang.getHeart_rate() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_age:

                NumberPicker picker = new NumberPicker(JiankangActivity.this);
                picker.setRange(1, 99);
                picker.setSelectedItem(50);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_age.setText(option);
                    }
                });
                picker.show();
                break;

            case R.id.tv_date:
                DatePicker datePicker = new DatePicker(JiankangActivity.this);
                datePicker.setRange(1900, Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)));
                datePicker.setSelectedItem(Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)),
                        Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_11)),
                        Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_15)));
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        tv_date.setText(year + "-" + month + "-" + day);
                    }
                });
                datePicker.show();
                break;
        }
    }
}
