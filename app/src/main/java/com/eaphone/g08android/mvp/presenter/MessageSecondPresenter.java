package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.http.APIUrl.CommonApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.CommonContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/7 12:48
 * 修改人：Administrator
 * 修改时间：2017/9/7 12:48
 * 修改备注：
 */
public class MessageSecondPresenter extends CommonContracts.MessageSystemPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void loadSystemMessage(String type,String size,String index) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadSystemMessage("2",type,size,index)
                .compose(RxUtil.<ResultBase<List<MessageFirstLevel>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<MessageFirstLevel>>>() {
            @Override
            public void call(ResultBase<List<MessageFirstLevel>> listResultBase) {
                mView.getSystemMessage(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void loadMoreSystemMessage(String type, String size, String index) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadSystemMessage("2",type,size,index)
                .compose(RxUtil.<ResultBase<List<MessageFirstLevel>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<MessageFirstLevel>>>() {
            @Override
            public void call(ResultBase<List<MessageFirstLevel>> listResultBase) {
                mView.getMoreSystemMessage(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
