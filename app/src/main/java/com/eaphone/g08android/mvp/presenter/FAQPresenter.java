package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.FAQ;
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
 * 创建时间：2017/9/6 9:36
 * 修改人：Administrator
 * 修改时间：2017/9/6 9:36
 * 修改备注：
 */
public class FAQPresenter extends NewsContracts.FAQPresenter {
    @Override
    public void onStart() {
        FAQ();
    }

    @Override
    public void FAQ() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class)
                .loadFAQ()
                .compose(RxUtil.<ResultBase<List<FAQ>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<FAQ>>>() {
            @Override
            public void call(ResultBase<List<FAQ>> listResultBase) {
                mView.getFAQ(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void FAQDetail(String id) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class)
                .loadFAQDetail(id)
                .compose(RxUtil.<ResultBase<FAQ>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<FAQ>>() {
            @Override
            public void call(ResultBase<FAQ> faqResultBase) {
                mView.getFAQDetail(faqResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
