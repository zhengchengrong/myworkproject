package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.http.APIUrl.IApi;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.SetPresenter;
import com.eaphone.g08android.ui.MainActivity;
import com.eaphone.g08android.ui.live.LiveConstats;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FileUtils;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.AppManager;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.AppUtils;
import com.hpw.mvpframe.utils.ToastUtils;
import com.tencent.tauth.Tencent;

import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class SetActivity extends CoreBaseActivity<SetPresenter> implements PassportContracts.SetView, View.OnClickListener {


    @BindView(R.id.btn_logout)
    Button mBtnLogout;

    @BindView(R.id.tv_reset)
    TextView mTvReset;


    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    @BindView(R.id.linear_changephone)
    LinearLayout mLLChange;

    @BindView(R.id.linear_update)
    LinearLayout mLLUpdate;

    @BindView(R.id.tv_about)
    TextView mTvAbout;

    @BindView(R.id.tv_new_mark)
    TextView mTvNewMark;

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @BindView(R.id.tv_clear)
    TextView tv_clear;

    private Tencent mTencent;
    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("设置").build();
        mBtnLogout.setOnClickListener(this);
        mTvReset.setOnClickListener(this);
        mLLChange.setOnClickListener(this);
        mTvAbout.setOnClickListener(this);
        mLLUpdate.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        if (IApi.MAIN_URL.equals(IApi.TEST)) {

            mTvVersion.setText(AppUtils.getAppVersionName(this) + "内网");
        } else {
            mTvVersion.setText(AppUtils.getAppVersionName(this));
        }
        mTvPhone.setText(FormatUtil.getSecretPhone(PreferencesUtils.getSharePreStr(Const.PHONE)));
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
    public void getLogout(ResultBase result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                AlertDialog dialog = initAlertDialog(null, "是否退出登录")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.logout();
                                mHandler.sendMessage(mHandler.obtainMessage(100));
                                PreferencesUtils.removeSharePreStr(Const.TOKEN);
                                PreferencesUtils.removeSharePreStr(Const.USERID);
                                PreferencesUtils.removeSharePreStr(Const.IDENTITY);
                                PreferencesUtils.removeSharePreStr(Const.SEX);
                                if(mTencent == null){
                                    mTencent = Tencent.createInstance(LoginActivity.QQ_APP_ID,getApplicationContext());
                                }
                                mTencent.logout(SetActivity.this);
                                AppManager.getAppManager().finishAllActivity();
                                startActivity(MainActivity.class);
                            }
                        }).create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                break;
            case R.id.tv_reset:
                startActivity(SetResetActivity.class);
                break;
            case R.id.linear_changephone:
                startActivity(SetUpdatePhoneActivity.class);
                break;
            case R.id.tv_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.linear_update:
//                lastVersion();
                break;

            case R.id.tv_clear:
                // 清除缓存
                FileUtils.deleteDir(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+LiveConstats.JYJK);
                ToastUtils.showToast(this,"已清除缓存");
                break;
        }
    }

    private void lastVersion() {
        AlertDialog dialog = initAlertDialog("", "当前为最新版本").setNegativeButton("好的", null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showNewVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_version, null);
        builder.setView(view);
        builder.create().show();
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int i, String s, Set<String> set) {
            switch (i) {
                case 0:
                    PreferencesUtils.putSharePre(Const.ALIAS_SUCESS, false);
                    break;
                case 6002:
                    break;


            }
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Log.e("PUSH", "Set alias in handler." + (String) msg.obj);
                    // 退出登录时将极光注销
                    JPushInterface.setAliasAndTags(SetActivity.this,
                            "", null,
                            mAliasCallback);

                    break;
                default:
            }
        }
    };
}
