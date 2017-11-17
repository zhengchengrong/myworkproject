package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.ZhiBoDetailItemBean;
import com.eaphone.g08android.http.APIUrl.LiveApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 16:57
 * 修改人：Administrator
 * 修改时间：2017/8/25 16:57
 * 修改备注：
 */
// 定义的P层的接口LiveZhiBoPresenter
public class LiveZhiBoDetailPresenter extends LiveContracts.LiveZhiBoDetailPresenter {
    private int page = 1;
    private int page_size = 10;

    @Override
    public void onStart() {
    }


    @Override
    public void info(String id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadLiveDetailList(id)
                .compose(RxUtil.<ResultBase<ZhiBoDetailItemBean>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<ZhiBoDetailItemBean>>() {
                    @Override
                    public void call(ResultBase<ZhiBoDetailItemBean> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void infoMore(String id) {
        page++;
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadLiveDetailList(id,String.valueOf(page), String.valueOf(page_size))
                .compose(RxUtil.<ResultBase<ZhiBoDetailItemBean>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<ZhiBoDetailItemBean>>() {
                    @Override
                    public void call(ResultBase<ZhiBoDetailItemBean> listResultBase) {
                        mView.getInfoMore(listResultBase);
                    }
                }, new ExceptionUtils(mView)));


    }
}
