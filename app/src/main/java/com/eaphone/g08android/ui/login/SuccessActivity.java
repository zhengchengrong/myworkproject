package com.eaphone.g08android.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/4/14.
 */
public class SuccessActivity extends CoreBaseActivity {

    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_sccuss)
    TextView tv_sccuss;
    private String title;


    private void initData() {
        title =  getIntent().getExtras().getString("title").toString();
        initBackTitle(title).build();
        tv_sccuss.setText( getIntent().getExtras().getString("content"));
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityCollector.finishAll();
//                Map<String, Object> map = new HashMap<>();
//                map.put(Const.ISSCAN, "0");
                startActivity(LoginActivity.class);
//                IntentUtils.turnTo(SuccessActivity.this, LoginActivity.class, true, map);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_success_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initData();
    }
}
