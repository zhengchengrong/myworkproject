package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.HealthyWarnDetail;
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
 * 创建时间：2017/9/4 11:41
 * 修改人：Administrator
 * 修改时间：2017/9/4 11:41
 * 修改备注：
 */
public class WarnListPresenter extends JiankangContracts.WarnListPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void warnList( String sensor_type, String page_index, String page_size, String end_time, String begin_time,String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadWarnList(sensor_type, page_index, page_size, end_time, begin_time,userId)
                .compose(RxUtil.<ResultBase<List<HealthyWarnDetail>>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBase<List<HealthyWarnDetail>>>() {
                    @Override
                    public void call(ResultBase<List<HealthyWarnDetail>> resultBase) {
                        mView.getWarnList(resultBase);
                    }
                },new ExceptionUtils(mView)));
    }
}
