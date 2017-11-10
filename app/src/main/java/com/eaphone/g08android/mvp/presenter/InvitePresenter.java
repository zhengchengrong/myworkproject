package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.InviteEnity;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.eaphone.g08android.http.APIUrl.CommonApi;
import com.eaphone.g08android.http.APIUrl.PassportApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 14:34
 * 修改人：Administrator
 * 修改时间：2017/8/30 14:34
 * 修改备注：
 */
public class InvitePresenter extends PassportContracts.InvitePresenter {
    @Override
    public void onStart() {
    }

    @Override
    public void loadInvite(String type,String index, String size) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadSystemMessage("2",type,index, size)
                .compose(RxUtil.<ResultBase<List<MessageFirstLevel>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<MessageFirstLevel>>>() {
            @Override
            public void call(ResultBase<List<MessageFirstLevel>> listResultBase) {
                mView.getInvite(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void loadMoreInvite(String level,String index, String size ) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadSystemMessage("2",level,size, index).subscribe(new Action1<ResultBase<List<MessageFirstLevel>>>() {
            @Override
            public void call(ResultBase<List<MessageFirstLevel>> listResultBase) {
                mView.getMoreInvite(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void loadHandler(String id,InviteEnity invite) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(PassportApi.class)
                .loadHandlerMessage(id,invite)
                .compose(RxUtil.<ResultBase>rxSchedulerHelper()).subscribe(new Action1<ResultBase>() {
            @Override
            public void call(ResultBase resultBase) {
                mView.getHandler(resultBase);
            }
        },new ExceptionUtils(mView)));
    }

    @Override
    public void loadPatchMessage(String id, ReadStatusEntity entity) {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class)
                .loadPatchMessage(id,entity)
                .compose(RxUtil.<ResultBase<MessageFirstLevel>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<MessageFirstLevel>>() {
            @Override
            public void call(ResultBase listResultBase) {
                mView.getPatchMessage(listResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
