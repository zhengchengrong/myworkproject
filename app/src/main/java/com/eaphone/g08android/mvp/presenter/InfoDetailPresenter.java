package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.InfoDetail;
import com.eaphone.g08android.http.APIUrl.NewsApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 14:25
 * 修改人：Administrator
 * 修改时间：2017/8/25 14:25
 * 修改备注：
 */
public class InfoDetailPresenter extends NewsContracts.InfoDetailPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void infoDetail(String newsTypeId,String id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadInfoDetail(newsTypeId,id)
                .compose(RxUtil.<ResultBase<InfoDetail>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<InfoDetail>>() {
            @Override
            public void call(ResultBase<InfoDetail> infoDetailResultBase) {
                mView.getInfoDetail(infoDetailResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
