package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.FamilyDetailPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.widget.CircleImageView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/22 16:44
 * 修改人：Administrator
 * 修改时间：2017/8/22 16:44
 * 修改备注：
 */
public class FamilyDetailActivity extends CoreBaseActivity<FamilyDetailPresenter> implements PassportContracts.FamilyDetailView {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_relation)
    TextView tv_relation;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.rl_item)
    RelativeLayout mRlItem;

    @BindView(R.id.relative_file)
    RelativeLayout relative_file;

    @BindView(R.id.relative_assessment)
    RelativeLayout relative_assessment;
    @BindView(R.id.relative_advice)
    RelativeLayout relative_advice;

    private Family mFamily;

    @Override
    public int getLayoutId() {
        return R.layout.family_detail_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFamily = (Family) getIntent().getExtras().getSerializable("data");
        initBackTitle("成员信息").setRightText("取消关注").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = initAlertDialog(null, "是否取消对该成员的关注")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.loadDelete(mFamily.getUserId());
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("否", null).create();
                dialog.show();
            }
        }).build();


        tv_name.setText(mFamily.getName());
        tv_relation.setText("关系："+mFamily.getRelationship());
        tv_phone.setText("电话："+ FormatUtil.getSecretPhone(mFamily.getPhone()));
        ImageLoader.displayImage(Const.getAvater(mFamily.getUserId()), iv_avatar);
        mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Const.SOURCE, Const.SOURCE_FAMILY_MEMBER);
                bundle.putString("userId", mFamily.getUserId());
                startActivity(SelfMsgActivity.class, bundle);
            }
        });

        relative_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mFamily.getUserId());
                startActivity(JiankangActivity.class, bundle);
            }
        });

        relative_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name",mFamily.getName());
                bundle.putString("userId",mFamily.getUserId());
                startActivity(FamilyDataActivity.class,bundle);
            }
        });

        relative_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "就诊记录");
                startActivity(MyOrderActivity.class, bundle);
            }
        });
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
    public void getDelete(ResultBase result) {
        if (result.isSuccess()) {
            setResult(2);
            if (LoginUtil.isFamilyMember().equals(mFamily.getUserId())) {//如果取消关注对象为健康页选中对象，则将选中设置为自己
                PreferencesUtils.putSharePre(Const.CURRENT_MEMBER, PreferencesUtils.getSharePreStr(Const.USERID));
                PreferencesUtils.putSharePre(Const.CURRENT_MEMBER_NAME, PreferencesUtils.getSharePreStr(Const.NAME));
            }
            FamilyDetailActivity.this.finish();
        } else {
            showToast(result.getMessage());
        }
    }
}
