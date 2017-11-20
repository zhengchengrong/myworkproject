package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.ZhiBoGroupItemBean;
import com.eaphone.g08android.http.APIUrl.LiveApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.ArrayList;

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
public class LiveZhiBoGroupPresenter extends LiveContracts.LiveZhiBoGroupPresenter {
    private int page = 1;
    private int page_size = 10;

    @Override
    public void onStart() {
    }


    @Override
    public void info() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadLiveGroupList()
                .compose(RxUtil.<ResultBase<ArrayList<ZhiBoGroupItemBean>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<ArrayList<ZhiBoGroupItemBean>>>() {
                    @Override
                    public void call(ResultBase<ArrayList<ZhiBoGroupItemBean>> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));
    }

    @Override
    public void infoMore() {
        page++;
        mRxManager.add(RxJavaRetrofitService.getInstance().create(LiveApi.class).loadLiveGroupList(String.valueOf(page), String.valueOf(page_size))
                .compose(RxUtil.<ResultBase<ArrayList<ZhiBoGroupItemBean>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<ArrayList<ZhiBoGroupItemBean>>>() {
                    @Override
                    public void call(ResultBase<ArrayList<ZhiBoGroupItemBean>> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));

    }
}
