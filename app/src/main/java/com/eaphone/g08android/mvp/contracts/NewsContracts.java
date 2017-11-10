package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.FAQ;
import com.eaphone.g08android.bean.Info;
import com.eaphone.g08android.bean.InfoComment;
import com.eaphone.g08android.bean.InfoDetail;
import com.eaphone.g08android.entity.NewsType;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:30
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:30
 * 修改备注：
 */
public interface NewsContracts {

    //获取资讯类型
    interface InfoView extends CoreBaseView {
        void getInfo(ResultBase<List<NewsType>> result);

    }

    abstract class InfoPresenter extends CoreBasePresenter<InfoView>{
        public abstract void newsType();
    }

    //资讯列表

    interface InfoTypeView extends CoreBaseView {
        void getInfo(ResultBase<List<Info>> result);

        void getInfoMore(ResultBase<List<Info>> result);
    }

    abstract class InfoTypePresenter extends CoreBasePresenter< InfoTypeView> {
        public abstract void info(String newsTypeId);

        public abstract void infoMore(String newsTypeId);
    }


    //资讯详情

    interface InfoDetailView extends CoreBaseView {
        void getInfoDetail(ResultBase<InfoDetail> result);
    }

    abstract class InfoDetailPresenter extends CoreBasePresenter< InfoDetailView> {
        public abstract void infoDetail(String newsTypeId,String id);
    }

    //评论

    interface InfoCommentView extends CoreBaseView{
        void getInfoComment(ResultBase<List<InfoComment>> result);
        void getInfoMoreComment(ResultBase<List<InfoComment>> result);

        void getZan(ResultBase result);
    }

    abstract class InfoCommentPresenter extends CoreBasePresenter<InfoCommentView>{
        public abstract void infoComment(String id,String pageIndex,String pageSize);
        public abstract void infoMoreComment(String id,String pageIndex,String pageSize);

        public abstract void zan(String newsId,String commentId);
    }


    interface InfoReplyView extends CoreBaseView{
        void getInfoCommentToSomeOne(ResultBase result);
    }

    abstract class InfoReplyPresenter extends CoreBasePresenter<InfoReplyView>{
        public abstract void infoComment(String content,String newsId,String commentId);
    }

    //客服

    interface FAQView extends CoreBaseView{
        void getFAQ(ResultBase<List<FAQ>> result);
        void getFAQDetail(ResultBase<FAQ> result);
    }

    abstract class FAQPresenter extends CoreBasePresenter<FAQView>{

        public abstract void FAQ();
        public abstract void FAQDetail(String id);


    }


}
