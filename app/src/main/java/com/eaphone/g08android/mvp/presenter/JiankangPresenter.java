package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.JianKang;
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
 * 创建时间：2017/9/20 12:06
 * 修改人：Administrator
 * 修改时间：2017/9/20 12:06
 * 修改备注：
 */
public class JiankangPresenter extends JiankangContracts.JiankangPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void jiankang(String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadJianKang(userId)
                .compose(RxUtil.<ResultBase<JianKang>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<JianKang>>() {
            @Override
            public void call(ResultBase<JianKang> jianKangResultBase) {
                mView.getJiankang(jianKangResultBase);
            }
        }, new ExceptionUtils(mView)));
    }

    @Override
    public void jiankang(String userId, JianKang jianKang) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadJianKang(userId,jianKang)
                .compose(RxUtil.<ResultBase<JianKang>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<JianKang>>() {
            @Override
            public void call(ResultBase<JianKang> jianKangResultBase) {
                mView.getParchJiankang(jianKangResultBase);
            }
        }, new ExceptionUtils(mView)));
    }
}
