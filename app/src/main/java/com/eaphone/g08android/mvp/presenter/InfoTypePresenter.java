package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Info;
import com.eaphone.g08android.http.APIUrl.NewsApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:46
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:46
 * 修改备注：
 */
public class InfoTypePresenter extends NewsContracts.InfoTypePresenter {
    private int page = 1;
    private int page_size = 10;

    @Override
    public void onStart() {
    }

    @Override
    public void info(String newsTypeId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadInfo(newsTypeId, String.valueOf(page), String.valueOf(page_size))
                .compose(RxUtil.<ResultBase<List<Info>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Info>>>() {
                    @Override
                    public void call(ResultBase<List<Info>> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void infoMore(String newsTypeId) {
        page++;
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadInfo(newsTypeId, String.valueOf(page), String.valueOf(page_size))
                .subscribe(new Action1<ResultBase<List<Info>>>() {
                    @Override
                    public void call(ResultBase<List<Info>> listResultBase) {

                        mView.getInfoMore(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }
}
