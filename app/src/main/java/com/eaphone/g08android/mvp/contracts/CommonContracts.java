package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 11:12
 * 修改人：Administrator
 * 修改时间：2017/8/30 11:12
 * 修改备注：
 */
public interface CommonContracts {

    //消息一级列表

    interface MessageView extends CoreBaseView {
        void getMessage(ResultBase<List<MessageFirstLevel>> resultBase);
        void getPatchMessage(ResultBase<MessageFirstLevel> resultBase);
    }

    abstract class MessagePresenter extends CoreBasePresenter< MessageView> {
        public abstract void loadMessage(String level);
        public abstract void loadPatchMessage(String id, ReadStatusEntity entity);
    }
    //消息二级列表

    interface MessageSystemView extends CoreBaseView {
        void getSystemMessage(ResultBase<List<MessageFirstLevel>> resultBase);
        void getMoreSystemMessage(ResultBase<List<MessageFirstLevel>> resultBase);
    }

    abstract class MessageSystemPresenter extends CoreBasePresenter< MessageSystemView> {
        public abstract void loadSystemMessage(String type,String size,String index);
        public abstract void loadMoreSystemMessage(String type,String size,String index);
    }


    interface MainView extends CoreBaseView{
        void getMsgCount(ResultBase<List<MessageFirstLevel>> resultBase);
    }

    public abstract class MainPresenter extends CoreBasePresenter<MainView>{
       public abstract void msgCount(String path);
    }

}
