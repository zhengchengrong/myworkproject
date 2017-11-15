package com.eaphone.g08android.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.MainFragmentPagerAdapter;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.mvp.contracts.CommonContracts;
import com.eaphone.g08android.mvp.presenter.MainPresenter;
import com.eaphone.g08android.ui.healthy.HealthyFragment;
import com.eaphone.g08android.ui.live.LiveHealthFragment;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.ui.market.MarketFragment;
import com.eaphone.g08android.ui.message.MessageActivity;
import com.eaphone.g08android.ui.personcenter.PersonCenterFragment;
import com.eaphone.g08android.ui.service.ServiceFragment;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.StatusBarUtils;
import com.eaphone.g08android.widget.NoScrollViewPager;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.TitleBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends CoreBaseActivity<MainPresenter> implements CommonContracts.MainView {
    @BindView(R.id.content)
    NoScrollViewPager mViewPager;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;

    private List<Fragment> list;
    private MainFragmentPagerAdapter mAdapter;
    private TitleBuilder builder;

    @BindView(R.id.rb_home)
    RadioButton rb_home;

    @BindView(R.id.rb_health)
    RadioButton rb_health;

    @BindView(R.id.rb_doctor)
    RadioButton rb_doctor;

    @BindView(R.id.rb_hospital)
    RadioButton rb_hospital;


    private HealthyFragment healthyFragment;

    private ServiceFragment serviceFragment;
    private LiveHealthFragment infoFragment;

    private MarketFragment marketFragment;
    private PersonCenterFragment centerFragment;

    private IntentFilter filter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "RELOAD_LOGIN":
                    startActivity(LoginActivity.class);
                    PreferencesUtils.removeSharePreStr(Const.TOKEN);
                    PreferencesUtils.removeSharePreStr(Const.USERID);
                    MainActivity.this.finish();
                    break;
                case "NEW_MESSAGE":
                    builder.setRightImage(R.mipmap.ic_msg_red);

                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.setColor(MainActivity.this, getResources().getColor(R.color.main_color), 0);
        initTab();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MainActivity.this.sendBroadcast(intent.setAction("NEW_MESSAGE"));
    }

    private void initTab() {
        filter = new IntentFilter();
        filter.addAction("NEW_MESSAGE");
        filter.addAction("RELOAD_LOGIN");
        registerReceiver(receiver, filter);
        list = new ArrayList<>();
        healthyFragment = new HealthyFragment();
        serviceFragment = new ServiceFragment();
        infoFragment = new LiveHealthFragment();
        marketFragment = new MarketFragment();
        centerFragment = new PersonCenterFragment();
        list.add(healthyFragment);
        list.add(serviceFragment);
        list.add(infoFragment);
        list.add(marketFragment);
        list.add(centerFragment);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setNoScroll(true);

        builder = new TitleBuilder(MainActivity.this);

        builder = initBackTitle("健康").setLeftImage(0).setRightImage(R.mipmap.ic_msg);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);
                        builder.setTitleText("健康").setLeftImage(0);
                        //startActivity(EsptouchDemoActivity.class);
                        break;
                    case R.id.rb_health:
                        mViewPager.setCurrentItem(1);
                        builder.setTitleText("服务").setLeftImage(0);
                        break;
                    case R.id.rb_doctor:
                        mViewPager.setCurrentItem(2);
                        builder.setTitleText("资讯").setLeftImage(0);
                        break;
                    case R.id.rb_hospital:
                        mViewPager.setCurrentItem(3);
                        builder.setTitleText("商城").setLeftImage(0);
                        break;
                    case R.id.rb_self:
                        if (LoginUtil.isLogin()) {
                            mViewPager.setCurrentItem(4);
                            builder.setTitleText("我的").setLeftImage(0);
                        } else {
                            startActivity(LoginActivity.class);
                        }

                        break;
                }
            }
        });

        builder.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtil.isLogin()) {
//                    startActivity(HeaderItemActivity.class);
                    startActivity(MessageActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });
        builder.build();

        registJpush();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER);
        PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER_NAME);
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event != null && event.getCode() == EventCode.C) {
//处理未登录时由于点击“我的”之后该选项卡从登陆页面返回被选中的问题
            String title = builder.getTvTitle().getText().toString();
            if ("健康".equals(title)) {
                mViewPager.setCurrentItem(0);
                rb_home.setChecked(true);
            } else if ("服务".equals(title)) {
                mViewPager.setCurrentItem(1);
                rb_health.setChecked(true);
            } else if ("资讯".equals(title)) {
                mViewPager.setCurrentItem(2);
                rb_doctor.setChecked(true);
            } else if ("商城".equals(title)) {
                mViewPager.setCurrentItem(3);
                rb_hospital.setChecked(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtil.isLogin()) {
            mPresenter.msgCount("1");
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {

        if (msg.equals("invalid token")) {
            startActivity(LoginActivity.class);
        } else {
            showToast(msg);
        }


    }


    //未给jpush设置别名的话 则 设置
    private void registJpush() {
        if (!PreferencesUtils.getSharePreBoolean(Const.ALIAS_SUCESS) && LoginUtil.isLogin()) {
            mHandler.sendMessage(mHandler.obtainMessage(100, PreferencesUtils.getSharePreStr(Const.USERID)));
        }

    }


    //极光登录设置别名
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int i, String s, Set<String> set) {
            String log;
            switch (i) {
                case 0:

                    log = "SUCCESS";
                    Log.e("PUSH", log);
                    PreferencesUtils.putSharePre(Const.ALIAS_SUCESS, true);
                    break;
                case 6002:
                    log = "FAILE";
                    Log.e("PUSH", log);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(100, PreferencesUtils.getSharePreStr(Const.USERID)), 1000 * 60);
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
                    Log.e("PUSH", "Set alias in handler." );
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(MainActivity.this,
                            (String) msg.obj, null,
                            mAliasCallback);

                    break;
                default:
                    Log.e("PUSH", "Unhandled msg - " + msg.what);
            }
        }
    };

    @Override
    public void getMsgCount(ResultBase<List<MessageFirstLevel>> resultBase) {
        if (resultBase.isSuccess()) {
            boolean isRead = true;
            for (int i = 0; i < resultBase.getData().size(); i++) {
                if (resultBase.getData().get(i).getReadStatus().equals("unread")) {
                    isRead = false;
                }
            }
            if (isRead) {
                builder.setRightImage(R.mipmap.ic_msg);
            } else {
                builder.setRightImage(R.mipmap.ic_msg_red);
            }


            builder.build();
        } else {
            showToast(resultBase.getMessage());
        }
    }
}
