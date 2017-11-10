package com.eaphone.g08android.ui.healthy;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.BindUserNextPresenter;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.widget.CustomHeaderAndFooterPicker;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;
import cn.qqtheme.framework.picker.OptionPicker;


public class BindUserNextActivity extends CoreBaseActivity<BindUserNextPresenter> implements View.OnClickListener, PassportContracts.BindUserNextView {

    @BindView(R.id.tv_relation)
    TextView mTvRelation;

    @BindView(R.id.rl_relation)
    RelativeLayout mRlRelation;

    @BindView(R.id.edt_name)
    EditText mEdtName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_user_next;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("绑定成员").setRightText("下一步").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEdtName.getText())) {
                    showToast("请输入手机号码");
                    return;
                } else if (!FormatUtil.isMobileNO(mEdtName.getText().toString())) {
                    showToast("请输入正确手机号码");
                    return;
                } else {
                    mPresenter.isExits(mEdtName.getText().toString());
                }
            }
        }).build();

        addAction();

    }

    private void addAction() {
        mRlRelation.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_relation:
                CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(BindUserNextActivity.this, new String[]{"祖辈", "父母", "夫妻", "子女", "兄弟姐妹", "孙辈"});
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        mTvRelation.setText(option);
                    }
                });
                picker.show();
                break;
        }
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
    public void getIsExits(ResultBase<Boolean> result) {
        if (result.getData()) {

        } else {
            mPresenter.vercode(mEdtName.getText().toString());
        }
    }

    @Override
    public void getVerCode(ResultBase result) {
        if (result.isSuccess()) {
//            startActivity();
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getSendInvite(ResultBase resultBase) {
        if (resultBase.isSuccess()) {
            BindUserNextActivity.this.finish();
        }
        showToast(resultBase.getMessage());
    }
}
