package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.bean.LiveListBean;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/10 14:47
 * 修改人：Administrator
 * 修改时间：2017/11/10 14:47
 * 修改备注：
 */
public interface LiveContracts { // 定义一个模块的接口类，里面是P和V的接口集合。

    // 1.定义个接口View,作为泛型传递给P
     interface LiveHealthView extends CoreBaseView{

    }


     abstract class LiveHealthPresenter extends CoreBasePresenter<LiveHealthView> {
    }


    interface LiveZhiBoView extends CoreBaseView{

    }
    abstract class LiveZhiBoPresenter extends CoreBasePresenter<LiveZhiBoView> {
    }

    // 直播首页
    interface LiveHealthFragmentView extends CoreBaseView {
       void getLiveList(LiveListBean result);
       void getFamilyMember(ResultBase<List<Family>> result);
    }
    abstract class LiveHealthFragmentPresenter extends CoreBasePresenter<LiveHealthFragmentView> {
        public abstract void analysis(HealthyDataEnity data, String userId);

        public abstract void familyMember();
    }

}
