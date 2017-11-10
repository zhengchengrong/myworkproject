package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.Const;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;


public class AboutActivity extends CoreBaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_function)
    TextView tv_function;

    @BindView(R.id.tv_statement)
    TextView tv_statement;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("关于我们").build();
        tv_function.setOnClickListener(this);
        tv_statement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Bundle bundle;

        switch (view.getId()) {
            case R.id.tv_function:
                bundle = new Bundle();
                bundle.putString("title", "功能介绍");
                bundle.putString("url", Const.FUNCTION_INTRODUCE);
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.tv_statement:
                bundle = new Bundle();
                bundle.putString("title", "声明");
                bundle.putString("url", Const.ABOUT_STATEMENT);
                startActivity(WebActivity.class, bundle);
                break;
        }
    }
}
