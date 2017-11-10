package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.DeviceBindEntity;
import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.http.APIUrl.JianKangApi;
import com.eaphone.g08android.http.APIUrl.PassportApi;
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
 * 创建时间：2017/9/7 10:20
 * 修改人：Administrator
 * 修改时间：2017/9/7 10:20
 * 修改备注：
 */
public class DeviceBindUserPresenter extends JiankangContracts.DeviceBindUserPresenter {
    @Override
    public void onStart() {
        familyMember();
    }

    @Override
    public void familyMember() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadFamily()
                .compose(RxUtil.<ResultBase<List<Family>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Family>>>() {
                    @Override
                    public void call(ResultBase<List<Family>> listResultBase) {
                        mView.getFamilyMember(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void deviceBind(DeviceBindEntity entity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadDeviceBind(entity)
                .compose(RxUtil.<ResultBase<DeviceDetail>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<DeviceDetail>>() {
                    @Override
                    public void call(ResultBase<DeviceDetail> deviceResultBase) {
                        mView.getBindDevice(deviceResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void deviceDelete(String deviceId, String channelName) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadDeleteDevice(deviceId, channelName)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
                    @Override
                    public void call(ResultBase resultBase) {
                        mView.getDeviceDelete(resultBase);
                    }
                }, new ExceptionUtils(mView)));
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
                }, new ExceptionUtils(mView)));
    }
}
