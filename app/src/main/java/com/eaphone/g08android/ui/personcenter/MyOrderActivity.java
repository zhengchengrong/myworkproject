package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;

public class MyOrderActivity extends CoreBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle(getIntent().getExtras().getString("title")).build();
    }
}
