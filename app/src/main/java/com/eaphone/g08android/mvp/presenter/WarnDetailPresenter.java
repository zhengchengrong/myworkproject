package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.MessageWarnDetail;
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
 * 创建时间：2017/9/11 17:19
 * 修改人：Administrator
 * 修改时间：2017/9/11 17:19
 * 修改备注：
 */
public class WarnDetailPresenter extends JiankangContracts.WarnDetailPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void warnDetail(String id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(JianKangApi.class)
                .loadWarnDetail(id)
                .compose(RxUtil.<ResultBase<MessageWarnDetail>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBase<MessageWarnDetail>>() {
                    @Override
                    public void call(ResultBase<MessageWarnDetail> healthyWarnDetailResultBase) {
                        mView.getWarnDetail(healthyWarnDetailResultBase);
                    }
                },new ExceptionUtils(mView)));
    }
}
