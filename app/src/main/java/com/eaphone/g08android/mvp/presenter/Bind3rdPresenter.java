package com.eaphone.g08android.mvp.presenter;

import android.text.TextUtils;

import com.eaphone.g08android.bean.BindMsgResult;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/18 16:07
 * 修改人：Administrator
 * 修改时间：2017/8/18 16:07
 * 修改备注：
 */
public class Bind3rdPresenter extends PassportContracts.Bind3rdPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void verifyCode(String phone) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadVerifyCode(phone)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper())
                .subscribe(new Action1<ResultBase>() {
                    @Override
                    public void call(ResultBase resultBase) {
                        mView.getVerifyCode(resultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void bind3rd(String phone, String openId, String type, String authcode, String role) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadBind3rd(phone, openId, type, authcode, role)
                .compose(RxUtil.<ResultBase<BindMsgResult>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<BindMsgResult>>() {
                    @Override
                    public void call(ResultBase<BindMsgResult> bindMsgResultResultBase) {
                        mView.getBind3rd(bindMsgResultResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void userInfo(String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadUserInfo(userId)
                .compose(RxUtil.<ResultBase<UserInfo>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<UserInfo>>() {
                    @Override
                    public void call(ResultBase<UserInfo> user_info) {

                        if (user_info.isSuccess()) {
                            PreferencesUtils.putSharePre(Const.NAME, user_info.getData().getName());
                            PreferencesUtils.putSharePre(Const.SEX, user_info.getData().getSex());
                            PreferencesUtils.putSharePre(Const.BIRTHDAY, user_info.getData().getBirthday());
                            if (!TextUtils.isEmpty(user_info.getData().getBirthday())) {
                                String bornYear = TimeUtils.timeTypeChange(user_info.getData().getBirthday(), TimeUtils.TIME_TYPE_3, TimeUtils.TIME_TYPE_7);
                                int age = Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)) - Integer.parseInt(bornYear);
                                PreferencesUtils.putSharePre(Const.AGE, String.valueOf(age));
                            }
                            PreferencesUtils.putSharePre(Const.ADDRESS, user_info.getData().getAddress());
                            PreferencesUtils.putSharePre(Const.IDENTITY, user_info.getData().getIdentity());
                            PreferencesUtils.putSharePre(Const.CAREER, user_info.getData().getCareer());
                            PreferencesUtils.putSharePre(Const.CONTACT_PHONE, user_info.getData().getContactPhone());
                            PreferencesUtils.putSharePre(Const.PHONE, user_info.getData().getPhone());
                        } else {
                            PreferencesUtils.removeSharePreStr(Const.TOKEN);//请求失败，把数据清除
                        }

                        mView.getUserInfo(user_info);
                    }
                }, new ExceptionUtils(mView)));
    }
}
