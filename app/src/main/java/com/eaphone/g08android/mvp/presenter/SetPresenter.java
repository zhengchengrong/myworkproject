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
 * 创建时间：2017/8/28 12:58
 * 修改人：Administrator
 * 修改时间：2017/8/28 12:58
 * 修改备注：
 */
public class SetPresenter extends PassportContracts.SetPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void logout() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).logout()
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getLogout(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
