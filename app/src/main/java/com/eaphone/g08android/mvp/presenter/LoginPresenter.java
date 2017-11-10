package com.eaphone.g08android.mvp.presenter;

import android.text.TextUtils;

import com.eaphone.g08android.bean.LoginEnity;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.bean.TokenResult;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.bean.WXMsg;
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
 * 创建时间：2017/8/15 17:10
 * 修改人：Administrator
 * 修改时间：2017/8/15 17:10
 * 修改备注：
 */
public class LoginPresenter extends PassportContracts.LoginPresenter {

    @Override
    public void onStart() {

    }

    @Override
    public void login(LoginEnity enity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).userLogin(enity)
                .compose(RxUtil.<ResultBase<TokenResult>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<TokenResult>>() {
                    @Override
                    public void call(ResultBase<TokenResult> result) {
                        if (result.isSuccess()) {
                            if (!TextUtils.isEmpty(result.getData().getToken())) {
                                PreferencesUtils.putSharePre(Const.USERID, result.getData().getUserId());
                                PreferencesUtils.putSharePre(Const.TOKEN, result.getData().getToken());
                            }
                        }
                        mView.getLoginResult(result);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void wxRes(String appid, String secret, String code, String grant_type) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadWXMSG("https://api.weixin.qq.com/sns/oauth2/access_token", appid, secret, code, grant_type)
                .compose(RxUtil.<WXMsg>rxSchedulerHelper()).subscribe(new Action1<WXMsg>() {
                    @Override
                    public void call(WXMsg result) {

                        wxUserInfo(result.getAccess_token(), result.getOpenid());

                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void wxUserInfo(String access_token, final String openid) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadWXUserMsg("https://api.weixin.qq.com/sns/userinfo", access_token, openid)
                .compose(RxUtil.<ResultQQMsg>rxSchedulerHelper()).subscribe(new Action1<ResultQQMsg>() {
                    @Override
                    public void call(ResultQQMsg resultQQMsg) {
                        resultQQMsg.setOpenId(openid);
                        resultQQMsg.setSource("weixin");
                        isBind3rd(openid, "weixin", "user", resultQQMsg);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void isBind3rd(String openId, String type, String role, final ResultQQMsg resultQQMsg) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadIsBind3rd(openId, type, role)
                .compose(RxUtil.<ResultBase<TokenResult>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<TokenResult>>() {
                    @Override
                    public void call(ResultBase<TokenResult> result) {
                        if (result.isSuccess()) {
                            PreferencesUtils.putSharePre(Const.TOKEN, result.getData().getToken());
                            PreferencesUtils.putSharePre(Const.USERID, result.getData().getUserId());
                        }
                        mView.getIsBindResult(result, resultQQMsg);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void userInfo(String userId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).loadUserInfo(userId)
                .compose(RxUtil.<ResultBase<UserInfo>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<UserInfo>>() {
                    @Override
                    public void call(ResultBase<UserInfo> info) {
                        if (info.isSuccess()) {
                            PreferencesUtils.putSharePre(Const.NAME, info.getData().getName());
                            PreferencesUtils.putSharePre(Const.SEX, info.getData().getSex());
                            PreferencesUtils.putSharePre(Const.BIRTHDAY, info.getData().getBirthday());
                            if (!TextUtils.isEmpty(info.getData().getBirthday())) {
                                String bornYear = TimeUtils.timeTypeChange(info.getData().getBirthday(), TimeUtils.TIME_TYPE_3, TimeUtils.TIME_TYPE_7);
                                int age = Integer.parseInt(TimeUtils.getCurrentTime(TimeUtils.TIME_TYPE_7)) - Integer.parseInt(bornYear);
                                PreferencesUtils.putSharePre(Const.AGE, String.valueOf(age));
                            }
                            PreferencesUtils.putSharePre(Const.ADDRESS, info.getData().getAddress());
                            PreferencesUtils.putSharePre(Const.IDENTITY, info.getData().getIdentity());
                            PreferencesUtils.putSharePre(Const.CAREER, info.getData().getCareer());
                            PreferencesUtils.putSharePre(Const.CONTACT_PHONE, info.getData().getContactPhone());
                            PreferencesUtils.putSharePre(Const.PHONE, info.getData().getPhone());
                        }
                        mView.getUserInfo(info);
                    }
                }, new ExceptionUtils(mView)));
    }
}
