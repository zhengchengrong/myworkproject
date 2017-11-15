package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.bean.LiveListBean;
import com.eaphone.g08android.http.APIUrl.LiveApi;
import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 11:54
 * 修改人：Administrator
 * 修改时间：2017/8/23 11:54
 * 修改备注：
 */
public class LiveHeathFragmentPresenter extends LiveContracts.LiveHealthFragmentPresenter {

    @Override
    public void onStart() {

    }

    @Override
    public void analysis(HealthyDataEnity data, String id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadLiveList()
                .compose(RxUtil.<LiveListBean>rxSchedulerHelper()).subscribe(new Action1<LiveListBean>() {
            @Override
            public void call(LiveListBean listBean) {
                mView.getLiveList(listBean);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void familyMember() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadFamily()
                .compose(RxUtil.<ResultBase<List<Family>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Family>>>() {
            @Override
            public void call(ResultBase<List<Family>> listResultBase) {
                mView.getFamilyMember(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
