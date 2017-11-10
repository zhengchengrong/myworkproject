package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.HandInDataEnity;
import com.eaphone.g08android.http.APIUrl.JianKangApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/2 17:08
 * 修改人：Administrator
 * 修改时间：2017/9/2 17:08
 * 修改备注：
 */
public class HandInPresenter extends JiankangContracts.HandInPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void handIn(String sensorType, HandInDataEnity enity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadHandIdData(sensorType, enity)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getHandIn(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
