package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 17:33
 * 修改人：Administrator
 * 修改时间：2017/8/29 17:33
 * 修改备注：
 */
public class ResetPasswordPresenter extends PassportContracts.ResetPasswordPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void reset(String old, String last) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadResetPassword(old, last)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getReset(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
