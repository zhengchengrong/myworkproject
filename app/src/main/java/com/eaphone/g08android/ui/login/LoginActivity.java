package com.eaphone.g08android.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.LoginEnity;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.bean.TokenResult;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.LoginPresenter;
import com.eaphone.g08android.ui.MainActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.ErrorCoreUtils;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.MD5Encryption;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.StatusBarUtils;
import com.google.gson.Gson;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;
import com.hpw.mvpframe.utils.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by zlq on 2017/8/11.
 */

public class LoginActivity extends CoreBaseActivity<LoginPresenter> implements PassportContracts.LoginView, IUiListener, View.OnClickListener {

    @BindView(R.id.et_username)
    EditText mUserName;

    @BindView(R.id.et_password)
    EditText mPWD;

    @BindView(R.id.btn_login)
    Button mLogin;

    @BindView(R.id.tv_registe)
    TextView tv_registe;

    @BindView(R.id.tv_forget)
    TextView tv_forget;

    @BindView(R.id.ll_qq)
    LinearLayout mLoginQQ;
    @BindView(R.id.ll_weixin)
    LinearLayout mLoginWX;

    private Tencent mTencent;
    private UserInfo mInfo;
    public static String QQ_APP_ID = "1106130236";

    public static String WX_APP_ID = "wx45904aba60ff29eb";
    public static String WX_APP_SECRET = "e417a8ae4edb5bfff5a886aed943067e";
    public IWXAPI mWeiXin;

    private IntentFilter filter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String code = intent.getStringExtra("code");
            Log.e(TAG, code);
            mPresenter.wxRes(WX_APP_ID, WX_APP_SECRET, code, "authorization_code");
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.setColor(LoginActivity.this, getResources().getColor(R.color.white), 0);
        initQQWX();
        if (!TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.TOKEN))) {
            startActivity(MainActivity.class);
            LoginActivity.this.finish();
        }

        filter = new IntentFilter(Const.WEIXIN_LOGIN);
        registerReceiver(receiver, filter);


        mLogin.setOnClickListener(this);
        mLoginQQ.setOnClickListener(this);
        mLoginWX.setOnClickListener(this);
        tv_registe.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();//处理未登录时由于点击“我的”之后该选项卡从登陆页面返回被选中的问题
        CoreEvent event = new CoreEvent(EventCode.C);
        EventBusUtils.sendEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(mUserName.getText().toString())) {
                    showToast(getResources().getString(R.string.input_phone));
                    return;
                }
                if (!FormatUtil.isMobileNO(mUserName.getText().toString())) {
                    showToast(getResources().getString(R.string.input_right_phone));
                    return;
                }
                if (TextUtils.isEmpty(mPWD.getText().toString())) {
                    showToast(getResources().getString(R.string.input_pwd));
                    return;
                }

                if (mPWD.getText().toString().length() < 6) {
                    showToast(getResources().getString(R.string.wrong_pwd));
                    return;
                }
                show(R.string.loading_up);
                LoginEnity enity = new LoginEnity("user", mUserName.getText().toString(), MD5Encryption.MD5(mPWD.getText().toString()));
                mPresenter.login(enity);
                break;
            case R.id.ll_qq:
                if (!mTencent.isSessionValid()) {
                    mTencent.login(LoginActivity.this, "all", this);
                }
                break;
            case R.id.ll_weixin:

                if(!mWeiXin.isWXAppInstalled()){
                    showToast("微信未安装，请安装后使用");
                    return;
                }

                mWeiXin.registerApp(WX_APP_ID);

                SendAuth.Req req = new SendAuth.Req();
                //授权读取用户信息
                req.scope = "snsapi_userinfo";
                //自定义信息
                req.state = "wechat_sdk_test";
                //向微信发送请求
                mWeiXin.sendReq(req);
                break;

            case R.id.tv_registe:
                startActivity(RegisteActivity.class);
                break;
            case R.id.tv_forget:
                startActivity(ResetNextActivity.class);
                break;
        }
    }

    private void initQQWX() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(QQ_APP_ID, this);
        }

        mWeiXin = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
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
    public void getLoginResult(ResultBase<TokenResult> result) {
        if (result.isSuccess()) {
            mPresenter.userInfo(result.getData().getUserId());
        } else {
           showToast(ErrorCoreUtils.dispalyCode(result.getErrcode()));
        }

        PreferencesUtils.putSharePre(Const.PASSWORD, MD5Encryption.MD5(mPWD.getText().toString()));
    }

    @Override
    public void getIsBindResult(ResultBase<TokenResult> result, ResultQQMsg resultQQMsg) {
        Log.e(TAG, "   " + result.toString());
        if (result.isSuccess()) {//用户已绑定过第三方后直接进入主页
            if (!TextUtils.isEmpty(result.getData().getToken())) {
                mPresenter.userInfo(result.getData().getUserId());
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bindResult", resultQQMsg);
                startActivity(Bind3rdActivity.class, bundle);
            }
        } else {
            ToastUtils.showToast(mContext, result.getMessage());
        }
    }

    @Override
    public void getUserInfo(ResultBase<com.eaphone.g08android.bean.UserInfo> info) {
        dismiss();
        if (info.isSuccess()) {
            startActivity(MainActivity.class);
            LoginActivity.this.finish();
        }
    }

    @Override
    public void onComplete(Object o) {
        if (null == o) {
            return;
        }
        JSONObject jsonResponse = (JSONObject) o;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            return;
        }
        initOpenidAndToken(jsonResponse);
        updateUserInfo();
    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    Log.e(TAG, response.toString());
                    ResultQQMsg qqMsg = new Gson().fromJson(response.toString(), ResultQQMsg.class);
                    qqMsg.setSource("qq");
                    qqMsg.setOpenId(mTencent.getOpenId());
                    mPresenter.isBind3rd(mTencent.getOpenId(), "qq", "user", qqMsg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        } else {
//            mUserInfo.setText("");
//            mUserInfo.setVisibility(android.view.View.GONE);
//            mUserLogo.setVisibility(android.view.View.GONE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("LoginActivity", "requestCode=" + requestCode + "    resultCode=" + resultCode);
        if (requestCode == 11101 && resultCode == -1) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
