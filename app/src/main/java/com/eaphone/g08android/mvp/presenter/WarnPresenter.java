package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.HealthyWarn;
import com.eaphone.g08android.http.APIUrl.JianKangApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/4 10:47
 * 修改人：Administrator
 * 修改时间：2017/9/4 10:47
 * 修改备注：
 */
public class WarnPresenter extends JiankangContracts.WarnPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void healthyWarn(String sersonType, String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadHealthyWarn(sersonType, userId)
                .compose(RxUtil.<ResultBase<List<HealthyWarn>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<HealthyWarn>>>() {
            @Override
            public void call(ResultBase<List<HealthyWarn>> resultBase) {
                mView.getHealthyWarn(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
