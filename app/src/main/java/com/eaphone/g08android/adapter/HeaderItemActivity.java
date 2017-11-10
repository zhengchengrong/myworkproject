package com.eaphone.g08android.adapter;

import android.content.Context;
import android.os.Bundle;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.HealthyPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.widget.AutoLocateHorizontalView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeaderItemActivity extends CoreBaseActivity<HealthyPresenter> implements JiankangContracts.HealthyView {

    @BindView(R.id.horizontalView)
    AutoLocateHorizontalView horizontalView;

    private BarAdapter mAdapter;

    private List<Family> list;


    @Override
    public int getLayoutId() {
        return R.layout.activity_header_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        list = new ArrayList<>();
        mPresenter.familyMember();

//        mAdapter = new BarAdapter(mContext);

    }


    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter.getItemCount()>0){
            if(getPosition() == -1){
                horizontalView.moveToPosition(0);
            }else{
                horizontalView.moveToPosition(getPosition());
            }
        }


    }

    private int getPosition(){
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getName().equals(LoginUtil.isFamilyName())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getAnalysis(ResultBase<List<Healthy>> result) {

    }

    @Override
    public void getFamilyMember(ResultBase<List<Family>> result) {
        if (result.isSuccess()) {
            list.clear();
            Family family = new Family();
            family.setName("添加成员");

            Family family2 = new Family();
            family2.setName(PreferencesUtils.getSharePreStr(Const.NAME));
            family2.setUserId(PreferencesUtils.getSharePreStr(Const.USERID));
            list.add(family2);
            list.addAll(result.getData());
            list.add(family);
            mAdapter.addNewData(list);

            horizontalView.setAdapter(mAdapter);
        }
    }
}
