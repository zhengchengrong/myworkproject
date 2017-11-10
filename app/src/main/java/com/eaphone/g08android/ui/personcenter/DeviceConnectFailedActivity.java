package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;

import static com.eaphone.g08android.R.id.btn_restart;


public class DeviceConnectFailedActivity extends CoreBaseActivity implements View.OnClickListener {

    @BindView(btn_restart)
    Button btnRestart;

    @Override
    public int getLayoutId() {
        return R.layout.device_connect_failed_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("连接设备").setLeftImage(0).build();
        btnRestart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_restart:
//                startActivity(DeviceAddActivity.class);
                DeviceConnectFailedActivity.this.finish();
                break;
        }
    }
}
