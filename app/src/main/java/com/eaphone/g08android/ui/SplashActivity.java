package com.eaphone.g08android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.eaphone.g08android.G08Application;
import com.eaphone.g08android.R;
import com.eaphone.g08android.entity.NewsType;
import com.eaphone.g08android.entity.NewsTypeDao;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.InfoPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

/**
 * Created by zlq on 2017/8/11.
 */

public class SplashActivity extends CoreBaseActivity<InfoPresenter> implements NewsContracts.InfoView {

    private NewsTypeDao mNewsTypeDao;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (PreferencesUtils.getSharePreBoolean(Const.ISFIRST)) {
//            if (TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.TOKEN))) {
//                to(LoginActivity.class);
//            } else {
            to(MainActivity.class);
//            }
        } else {
            to(StartActivity.class);
        }
        mNewsTypeDao = G08Application.getInstances().getDaoSession().getNewsTypeDao();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void to(final Class cls) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, cls);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1500);
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
    public void getInfo(ResultBase<List<NewsType>> result) {
        if (result.isSuccess()) {
            try {

                // mNewsTypeDao.insertInTx(result.getData());
                mNewsTypeDao.insertOrReplaceInTx(result.getData());
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            showToast(result.getMessage());
        }
    }
}
