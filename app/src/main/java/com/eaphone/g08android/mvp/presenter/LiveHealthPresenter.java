package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.LiveHome;
import com.eaphone.g08android.http.APIUrl.LiveApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 16:57
 * 修改人：Administrator
 * 修改时间：2017/8/25 16:57
 * 修改备注：
 */
public class LiveHealthPresenter extends LiveContracts.LiveHealthPresenter {



    @Override
    public void onStart() {

    }


    @Override
    public void info() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadHome()
                .compose(RxUtil.<ResultBase<LiveHome>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<LiveHome>>() {
                    @Override
                    public void call(ResultBase<LiveHome> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }
}
