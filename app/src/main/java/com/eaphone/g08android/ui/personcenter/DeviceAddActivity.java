package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eaphone.g08android.R;
import com.eaphone.g08android.airkiss.EsptouchDemoActivity;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;

public class DeviceAddActivity extends CoreBaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_add)
    Button btn_add;

    @Override
    public int getLayoutId() {
        return R.layout.device_add_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("添加设备").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).build();

        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Bundle bundle = new Bundle();
                bundle.putString("source","bind");
                startActivity(EsptouchDemoActivity.class,bundle);
                break;
        }
    }
}
