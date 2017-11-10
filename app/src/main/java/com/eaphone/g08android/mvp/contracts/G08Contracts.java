package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.G08BindEntity;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 11:09
 * 修改人：Administrator
 * 修改时间：2017/8/29 11:09
 * 修改备注：
 */
public interface G08Contracts {


    interface BindView extends CoreBaseView{
        void getBind(ResultBase<String> result);
        void getBindStatus(ResultBase<String> result);
    }

    abstract class BindPresenter extends CoreBasePresenter<BindView>{
        public abstract void bind(G08BindEntity entity);
        public abstract void bindStatus(String bssid,String phoneAddress,String deviceAddress);
    }

}
