package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.view.View;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;

public class QuestionActivity extends CoreBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_leave_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("留言反馈").setRightImage(R.mipmap.ic_my_question).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MyQuestionActivity.class);
            }
        }).build();
    }
}
