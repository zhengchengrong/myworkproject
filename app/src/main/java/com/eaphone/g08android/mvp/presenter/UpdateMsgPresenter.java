package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.SelfMsgEnity;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import okhttp3.MultipartBody;
import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/26 16:33
 * 修改人：Administrator
 * 修改时间：2017/8/26 16:33
 * 修改备注：
 */
public class UpdateMsgPresenter extends PassportContracts.UpdateMsgPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void loadmsg(SelfMsgEnity enity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadUserInfo(enity)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getMsg(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void upLoadAvager(MultipartBody.Part part,String userId) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(PassportApi.class).upLoadAvater(userId, part)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getAvager(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void downLoadUserInfo(String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadUserInfo(userId)
                .compose(RxUtil.<ResultBase<UserInfo>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<UserInfo>>() {
            @Override
            public void call(ResultBase<UserInfo> resultBase) {
                mView.getUserInfo(resultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
