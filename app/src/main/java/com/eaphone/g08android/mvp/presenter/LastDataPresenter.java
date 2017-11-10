package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
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
 * 创建时间：2017/9/1 14:17
 * 修改人：Administrator
 * 修改时间：2017/9/1 14:17
 * 修改备注：
 */
public class LastDataPresenter extends JiankangContracts.LastDataPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void lastData(HealthyDataEnity healthy,String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadAnalysis(healthy,userId)
                .compose(RxUtil.<ResultBase<List<Healthy>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Healthy>>>() {
            @Override
            public void call(ResultBase<List<Healthy>> listResultBase) {

                mView.getLastData(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void historyData(String sensor_type, String begin_time, String end_time,String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class).loadHistory(sensor_type, begin_time, end_time,userId)
                .compose(RxUtil.<ResultBase<List<Healthy>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Healthy>>>() {
            @Override
            public void call(ResultBase<List<Healthy>> listResultBase) {
                mView.getHistoryData(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
