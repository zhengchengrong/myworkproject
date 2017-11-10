package com.eaphone.g08android.mvp.presenter;

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
 * 创建时间：2017/9/6 11:06
 * 修改人：Administrator
 * 修改时间：2017/9/6 11:06
 * 修改备注：
 */
public class InfoReplyPresenter extends NewsContracts.InfoReplyPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void infoComment(String content, String newId, String commentId) {
        mRxManager.add(RxJavaRetrofitService.getInstance()
                .create(NewsApi.class)
                .loadInfoComment(newId, content, commentId)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase listResultBase) {
                mView.getInfoCommentToSomeOne(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
