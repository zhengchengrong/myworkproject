package com.eaphone.g08android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.BindMsgResult;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.Bind3rdPresenter;
import com.eaphone.g08android.ui.MainActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.TimeDown;
import com.eaphone.g08android.widget.CircleImageView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ToastUtils;
import com.tencent.tauth.Tencent;

import butterknife.BindView;


/**
 * 项目名称：瘤盾
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/5/5 16:11
 * 修改人：Administrator
 * 修改时间：2017/5/5 16:11
 * 修改备注：
 */
public class Bind3rdActivity extends CoreBaseActivity<Bind3rdPresenter> implements PassportContracts.Bind3rdView {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.edt_code)
    EditText edt_code;

    @BindView(R.id.btn_code)
    Button btn_code;

    @BindView(R.id.iv_avager)
    CircleImageView iv_avager;

    @BindView(R.id.btn_next)
    Button btn_next;

    private ResultQQMsg qqMsg;

    private Tencent mTencent;

    @Override
    public int getLayoutId() {
        return R.layout.bind_3rd_acitivity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        initBackTitle("绑定手机号").build();
        qqMsg = (ResultQQMsg) bundle.getSerializable("bindResult");
        if("qq".equals(qqMsg.getSource()))
        ImageLoader.displayImage(qqMsg.getFigureurl_qq_1(),iv_avager);
        else  ImageLoader.displayImage(qqMsg.getHeadimgurl(),iv_avager);
        addAction();
    }

    private void addAction() {
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.showToast(mContext, "请输入手机号码");
                } else if (FormatUtil.isMobileNO(et_phone.getText().toString())) {
                    mPresenter.verifyCode(et_phone.getText().toString());
                } else {
                    ToastUtils.showToast(mContext, "请输入正确的手机号码");
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_phone.getText())) {
                    show(R.string.loading_up);
                    mPresenter.bind3rd(et_phone.getText().toString(), qqMsg.getOpenId(), qqMsg.getSource(), edt_code.getText().toString(), "user");
                }
            }
        });
    }
    private TimeDown timeDown;
    private void time() {
        timeDown = new TimeDown(60 * 1000, 1000, btn_code);
        timeDown.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDown != null)
            timeDown.cancel();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(mContext,msg);
    }

    @Override
    public void getVerifyCode(ResultBase resultBase) {
        time();
    }

    @Override
    public void getUserInfo(ResultBase<UserInfo> info) {
        dismiss();
        if(info.isSuccess()){
            startActivity(MainActivity.class);
        }else{
            ToastUtils.showToast(mContext,info.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("qq".equals(qqMsg.getSource())){
            if(mTencent == null){
                mTencent = Tencent.createInstance(LoginActivity.QQ_APP_ID,getApplicationContext());
            }
            mTencent.logout(Bind3rdActivity.this);
        }
    }

    @Override
    public void getBind3rd(ResultBase<BindMsgResult> resultBase) {
        dismiss();
        if (resultBase.isSuccess()) {
            switch (resultBase.getData().getStatus()) {
                case "new_user":
                    PreferencesUtils.putSharePre(Const.TOKEN, resultBase.getData().getLogin().getToken());
                    PreferencesUtils.putSharePre(Const.USERID, resultBase.getData().getLogin().getUserId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",qqMsg);
                    bundle.putString("phone",et_phone.getText().toString().trim());
                    startActivity(Bind3rdSetPwdActivity.class,bundle);
                    break;
                case "bind_success":
                    PreferencesUtils.putSharePre(Const.TOKEN, resultBase.getData().getLogin().getToken());
                    PreferencesUtils.putSharePre(Const.USERID, resultBase.getData().getLogin().getUserId());
                    PreferencesUtils.putSharePre(Const.PHONE,et_phone.getText().toString().trim());
                    mPresenter.userInfo(resultBase.getData().getLogin().getUserId());
                    break;
                case "bind_exists":
                    ToastUtils.showToast(mContext,"该用户已绑定其他第三方账号，请更换手机号码");
                    break;
                case "auth_fail":
                    ToastUtils.showToast(mContext,"验证码不正确，请重新出入");
                    break;
            }
        } else {
            switch (resultBase.getData().getStatus()) {
                case "bind_exists":
                    ToastUtils.showToast(mContext,"该用户已绑定其他第三方账号，请更换手机号码");
                    break;
                case "auth_fail":
                    ToastUtils.showToast(mContext,"验证码不正确，请重新出入");
                    break;
            }
        }
    }
}
