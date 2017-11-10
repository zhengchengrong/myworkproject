package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
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
 * 创建时间：2017/8/30 11:26
 * 修改人：Administrator
 * 修改时间：2017/8/30 11:26
 * 修改备注：
 */
public class MessagePresenter extends CommonContracts.MessagePresenter {
    @Override
    public void onStart() {
        loadMessage("1");
    }

    @Override
    public void loadMessage(String level) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadMessage(level,"1","10")
                .compose(RxUtil.<ResultBase<List<MessageFirstLevel>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<MessageFirstLevel>>>() {
            @Override
            public void call(ResultBase<List<MessageFirstLevel>> listResultBase) {
                mView.getMessage(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void loadPatchMessage(String id, ReadStatusEntity entity) {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadPatchMessage(id,entity)
                .compose(RxUtil.<ResultBase<MessageFirstLevel>>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase listResultBase) {
                mView.getPatchMessage(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
