package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Bind3rdInfoEnity;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.MD5Encryption;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/21 10:55
 * 修改人：Administrator
 * 修改时间：2017/8/21 10:55
 * 修改备注：
 */
public class BindInfoPresenter extends PassportContracts.Bind3rdInfoPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void bind3rdInfo(ResultQQMsg info) {

        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class).load3rdInfo(getQQmsg(info))
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
                    @Override
                    public void call(ResultBase resultBase) {
                        mView.getBindInfo(resultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    private Bind3rdInfoEnity getQQmsg(ResultQQMsg mMsg) {
        Bind3rdInfoEnity enity = new Bind3rdInfoEnity();
        if ("qq".equals(mMsg.getSource())) {

            enity.setAvatar(mMsg.getFigureurl_qq_1());
        } else {
            enity.setAvatar(mMsg.getHeadimgurl());
        }
        enity.setName(mMsg.getNickname());
        enity.setPassword(MD5Encryption.MD5(mMsg.getPassword()));
        enity.setPhone(mMsg.getPhone());
        enity.setRole("user");
        enity.setAddress(mMsg.getProvince() + mMsg.getCity());
        enity.setUserId(PreferencesUtils.getSharePreStr(Const.USERID));
        return enity;
    }
}
