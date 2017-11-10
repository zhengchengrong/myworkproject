package com.eaphone.g08android.ui.personcenter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseActivity;

public class DeviceAddUserActivity extends CoreBaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.device_add_user_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("添加成员").setRightText("保存").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).build();
    }

    private void initBindDialog(String phone) {
        AlertDialog dialog =initAlertDialog(null,"验证码已发送至"+phone+"，请注意查收。")
                .setNegativeButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
