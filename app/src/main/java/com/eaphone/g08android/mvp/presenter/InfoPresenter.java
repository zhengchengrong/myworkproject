package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.entity.NewsType;
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
 * 创建时间：2017/8/26 11:10
 * 修改人：Administrator
 * 修改时间：2017/8/26 11:10
 * 修改备注：
 */
public class InfoPresenter extends NewsContracts.InfoPresenter {
    @Override
    public void newsType() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadNewsType()
                .compose(RxUtil.<ResultBase<List<NewsType>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<NewsType>>>() {
            @Override
            public void call(ResultBase<List<NewsType>> listResultBase) {
                mView.getInfo(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    // 在这里调用了资讯
    @Override
    public void onStart() {
        newsType();
    }
}
