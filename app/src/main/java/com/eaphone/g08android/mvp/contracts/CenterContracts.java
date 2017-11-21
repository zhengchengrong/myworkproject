package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.ShareInfoEntity;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/10 14:47
 * 修改人：Administrator
 * 修改时间：2017/11/10 14:47
 * 修改备注：
 */
// 管理一个模块的P和V的接口
public interface CenterContracts {

    // 1.定义个接口View,作为泛型传递给P
    interface PersonCenterView extends CoreBaseView {
        void getInfo(ResultBase<ShareInfoEntity> bean);
    }

    abstract class PersonCenterPresenter extends CoreBasePresenter<PersonCenterView> {
        // 逻辑操作，比如访问网络
        public abstract void info();
    }
}
