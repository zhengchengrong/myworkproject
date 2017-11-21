package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.ShareInfoEntity;
import com.eaphone.g08android.http.APIUrl.CommonApi;
import com.eaphone.g08android.http.RxJavaRetrofitService;
import com.eaphone.g08android.mvp.contracts.CenterContracts;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ExceptionUtils;
import com.hpw.mvpframe.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

import static com.hpw.mvpframe.utils.RxUtil.rxSchedulerHelper;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 16:57
 * 修改人：Administrator
 * 修改时间：2017/8/25 16:57
 * 修改备注：
 */
public class PersonCenterPresenter extends CenterContracts.PersonCenterPresenter {

    @Override
    public void onStart() {
    }

    @Override
    public void info() {
        mRxManager.add(RxJavaRetrofitService.getInstance().create(CommonApi.class).loadShareInfo()
                .compose(RxUtil.<ResultBase<ShareInfoEntity>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<ShareInfoEntity>>() {
                    @Override
                    public void call(ResultBase<ShareInfoEntity> listResultBase) {
                        mView.getInfo(listResultBase);
                    }
                }, new ExceptionUtils(mView)));

    }
}
