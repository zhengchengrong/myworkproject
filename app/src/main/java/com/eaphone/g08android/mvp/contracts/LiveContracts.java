package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.LiveHome;
import com.eaphone.g08android.bean.ZhiBoDetailItemBean;
import com.eaphone.g08android.bean.ZhiBoGroupItemBean;
import com.eaphone.g08android.bean.ZhiboInfo;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
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
// 管理一个模块的P和V的接口
public interface LiveContracts {
    // 定义一个模块的接口类，里面是P和V的接口集合。

    // 1.定义个接口View,作为泛型传递给P
     interface LiveHealthView extends CoreBaseView{
        void getInfo(ResultBase<LiveHome> bean);
    }


     abstract class LiveHealthPresenter extends CoreBasePresenter<LiveHealthView> {
         // 逻辑操作，比如访问网络
         public abstract void info();
    }


    interface LiveZhiBoView extends CoreBaseView{
        // 结果获取,更新界面
        void getInfo(ResultBase<List<ZhiboInfo>> bean);
        void getInfoMore(ResultBase<List<ZhiboInfo>> bean);

    }
    abstract class LiveZhiBoPresenter extends CoreBasePresenter<LiveZhiBoView> {
        // 逻辑操作，比如访问网络
        public abstract void info();
        public abstract void infoMore();
    }

    interface LiveZhiBoDetailView extends CoreBaseView{
        // 结果获取,更新界面
        void getInfo(ResultBase<ZhiBoDetailItemBean> bean);
        void getInfoMore(ResultBase<ZhiBoDetailItemBean> bean);

    }
    abstract class LiveZhiBoDetailPresenter extends CoreBasePresenter<LiveZhiBoDetailView> {
        // 逻辑操作，比如访问网络
        public abstract void info(String id);
        public abstract void infoMore(String id);
    }
    interface LiveZhiBoGroupView extends CoreBaseView{
        // 结果获取,更新界面
        void getInfo(ResultBase<ArrayList<ZhiBoGroupItemBean>> bean);
        void getInfoMore(ResultBase<ArrayList<ZhiBoGroupItemBean>> bean);

    }

    abstract class LiveZhiBoGroupPresenter extends CoreBasePresenter<LiveZhiBoGroupView> {
        // 逻辑操作，比如访问网络
        public abstract void info();
        public abstract void infoMore();
    }

}
