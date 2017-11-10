package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.InfoComment;
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
 * 创建时间：2017/8/25 16:57
 * 修改人：Administrator
 * 修改时间：2017/8/25 16:57
 * 修改备注：
 */
public class InfoCommentPresenter extends NewsContracts.InfoCommentPresenter {


    @Override
    public void infoComment(String id, String pageIndex, String pageSize) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadInfoListComment(id, pageIndex, pageSize)
                .compose(RxUtil.<ResultBase<List<InfoComment>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<InfoComment>>>() {
                    @Override
                    public void call(ResultBase<List<InfoComment>> listResultBase) {
                        mView.getInfoComment(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void infoMoreComment(String id, String pageIndex, String pageSize) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadInfoListComment(id, pageIndex, pageSize)
                .compose(RxUtil.<ResultBase<List<InfoComment>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<InfoComment>>>() {
                    @Override
                    public void call(ResultBase<List<InfoComment>> listResultBase) {
                        mView.getInfoMoreComment(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void zan(String newsId, String commentId) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(NewsApi.class).loadZan(newsId, commentId)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
                    @Override
                    public void call(ResultBase resultBase) {
                        mView.getZan(resultBase);
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
