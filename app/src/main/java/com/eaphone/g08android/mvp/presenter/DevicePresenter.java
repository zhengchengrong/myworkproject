package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Device;
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
 * 创建时间：2017/8/25 10:23
 * 修改人：Administrator
 * 修改时间：2017/8/25 10:23
 * 修改备注：
 */
public class DevicePresenter extends JiankangContracts.DevicePresenter {
    @Override
    public void onStart() {
        analysis();
    }

    @Override
    public void analysis() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class).loadDevice()
                .compose(RxUtil.<ResultBase<List<Device>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Device>>>() {
            @Override
            public void call(ResultBase<List<Device>> listResultBase) {
                mView.getAnalysis(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
