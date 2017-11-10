package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.DeviceName;
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
 * 创建时间：2017/8/29 11:55
 * 修改人：Administrator
 * 修改时间：2017/8/29 11:55
 * 修改备注：
 */
public class DeviceDetailPresenter extends JiankangContracts.DeviceDetailPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void detail(String deviceId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadDeviceDetail(deviceId)
                .compose(RxUtil.<ResultBase<DeviceDetail>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<DeviceDetail>>() {
            @Override
            public void call(ResultBase<DeviceDetail> resultBase) {
                mView.getDetail(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void deviceUnbind(String device_id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class).loadDeviceUnBind(device_id)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getDeviceUnbind(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void deviceRename(String device_id, DeviceName name) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class).loadDeviceRename(device_id,name)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getDeviceRename(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
