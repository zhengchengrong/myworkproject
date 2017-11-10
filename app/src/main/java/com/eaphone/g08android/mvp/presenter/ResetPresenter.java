package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.ResetPwdEnity;
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
 * 创建时间：2017/8/21 17:09
 * 修改人：Administrator
 * 修改时间：2017/8/21 17:09
 * 修改备注：
 */
public class ResetPresenter extends PassportContracts.ResetPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void resetCode(String phone) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadVerifyCode(phone)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getVerifyCode(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void reset(ResetPwdEnity reset) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .resetPwd(reset.getAuthcode(),reset.getPhone(),reset.getNewPassword(),reset.getRole())
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getReset(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
