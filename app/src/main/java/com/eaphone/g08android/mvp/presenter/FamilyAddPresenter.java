package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.FamilyAddInvite;
import com.eaphone.g08android.bean.FamilyAddMemberEntity;
import com.eaphone.g08android.bean.FileResult;
import com.eaphone.g08android.bean.SelfMsgEnity;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.APIUrl.CommonApi;
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
 * 创建时间：2017/8/30 15:59
 * 修改人：Administrator
 * 修改时间：2017/8/30 15:59
 * 修改备注：
 */
public class FamilyAddPresenter extends PassportContracts.FamilyAddPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void isExist(String phone) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadIsExist(phone)
                .compose(RxUtil.<ResultBase<Boolean>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<Boolean>>() {
            @Override
            public void call(ResultBase<Boolean> booleanResultBase) {
                mView.getIsExist(booleanResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void sendInvite(FamilyAddInvite invite) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadHandlerMessage(invite)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getSendInvite(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void addCode(String phone) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadVerifyCode(phone)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getAddCode(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void addMember(FamilyAddMemberEntity family) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadAddMember(family.getPhone(), family.getRelationship(), family.getAuthcode(),
                        family.getName(), family.getIdentity(), family.getBirthday(), family.getGender())
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getAddMember(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void upLoadAvager(MultipartBody.Part part) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(CommonApi.class).loadFile(part)
                .compose(RxUtil.<ResultBase<FileResult>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<FileResult>>() {
            @Override
            public void call(ResultBase<FileResult> resultBase) {
                mView.getAvaget(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void upLoadMsg(SelfMsgEnity enity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadUserInfo(enity).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getMsg(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void downLoadMsg(String userId) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadUserInfo(userId)
                .compose(RxUtil.<ResultBase<UserInfo>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<UserInfo>>() {
            @Override
            public void call(ResultBase<UserInfo> userInfoResultBase) {
                mView.getUserMsg(userInfoResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
