package com.eaphone.g08android.mvp.presenter;

import com.eaphone.g08android.bean.Family;
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
 * 创建时间：2017/8/22 12:44
 * 修改人：Administrator
 * 修改时间：2017/8/22 12:44
 * 修改备注：
 */
public class FamilyPresenter extends PassportContracts.FamilyPresenter {
    @Override
    public void onStart() {
        loadFamily();
    }

    @Override
    public void loadFamily() {
        mRxManager.add( RxJavaRetrofitService.getInstance().create(PassportApi.class).loadFamily()
                .compose(RxUtil.<ResultBase<List<Family>>>rxSchedulerHelper()).subscribe(new Action1<ResultBase<List<Family>>>() {
            @Override
            public void call(ResultBase<List<Family>> familyResultBase) {
                mView.getFamily(familyResultBase);
            }
        },new ExceptionUtils(mView)));
    }
}
