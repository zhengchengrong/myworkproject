package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.UpdatePhoneEntity;
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
 * 创建时间：2017/8/29 17:55
 * 修改人：Administrator
 * 修改时间：2017/8/29 17:55
 * 修改备注：
 */
public class UpdatePhonePresenter extends PassportContracts.UpdatePhonePresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void reset(String password, String phone, String authcode) {

        UpdatePhoneEntity entity = new UpdatePhoneEntity(password, phone, authcode);

        mRxManager.add( RxJavaRetrofitService.getInstance()
                .create(PassportApi.class)
                .loadUpdatePhone(entity)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getReset(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void verifyCode(String phone) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(PassportApi.class).loadVerifyCode(phone)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getCode(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
