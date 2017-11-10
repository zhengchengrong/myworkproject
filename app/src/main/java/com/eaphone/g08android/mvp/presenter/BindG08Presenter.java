package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.G08BindEntity;
import com.eaphone.g08android.http.APIUrl.G08Api;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.G08Contracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/31 10:18
 * 修改人：Administrator
 * 修改时间：2017/8/31 10:18
 * 修改备注：
 */
public class BindG08Presenter extends G08Contracts.BindPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void bind(G08BindEntity entity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(G08Api.class)
                .loadBindG08(entity)
                .compose(RxUtil.<ResultBase<String>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<String>>() {
                    @Override
                    public void call(ResultBase<String> resultBase) {
                        mView.getBind(resultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void bindStatus(String bssid, String phoneAddress, String deviceAddress) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(G08Api.class).loadBindStatus(bssid, phoneAddress, deviceAddress)
                .compose(RxUtil.<ResultBase<String>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<String>>() {
                    @Override
                    public void call(ResultBase<String> resultBase) {
                        mView.getBindStatus(resultBase);
                    }
                }, new ExceptionUtils(mView)));
    }
}
