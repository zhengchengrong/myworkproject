package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.FAQ;
import com.eaphone.g08android.bean.Info;
import com.eaphone.g08android.bean.InfoComment;
import com.eaphone.g08android.bean.InfoDetail;
import com.eaphone.g08android.entity.NewsType;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 11:16
 * 修改人：Administrator
 * 修改时间：2017/8/29 11:16
 * 修改备注：
 */
public interface NewsApi {

    /*--------------------------------news-------------------------------------*/
    String NEWSBASE = "news/v1/";

    //资讯类型
    @GET(NEWSBASE + "newsType")
    Observable<ResultBase<List<NewsType>>> loadNewsType();

    //资讯列表
    @GET(NEWSBASE + "newsType/{newsTypeId}/news")
    Observable<ResultBase<List<Info>>> loadInfo(@Path("newsTypeId") String newsTypeId, @Query("page_index") String page_index, @Query("page_size") String page_size);

    //资讯详情
    @GET(NEWSBASE + "newsType/{newsTypeId}/news/{newsId}")
    Observable<ResultBase<InfoDetail>> loadInfoDetail(@Path("newsTypeId") String newsTypeId, @Path("newsId") String newsId);

    //资讯评论列表
    @GET(NEWSBASE + "news/{info_id}/comments/")
    Observable<ResultBase<List<InfoComment>>> loadInfoListComment(@Path("info_id") String info_id,@Query("page_index") String page_index, @Query("page_size") String page_size);

    //资讯评论
    @POST(NEWSBASE + "news/{info_id}/comments/")
    Observable<ResultBase> loadInfoComment(@Path("info_id") String infoId, @Query("content") String content, @Query("commentsId") String commentsId);

    //点赞
    @POST(NEWSBASE + "news/{newsId}/comments/{commentsId}/zan")
    Observable<ResultBase> loadZan(@Path("newsId") String newsId, @Path("commentsId") String commentsId);

    //取消赞
    @DELETE(NEWSBASE + "news/{newsId}/comments/{commentsId}/zan")
    Observable<ResultBase> loadCancelZan(@Path("newsId") String newsId, @Path("commentsId") String commentsId);


    @GET(NEWSBASE + "FAQ")
    Observable<ResultBase<List<FAQ>>> loadFAQ();

    @GET(NEWSBASE + "FAQ/{id}")
    Observable<ResultBase<FAQ>> loadFAQDetail(@Path("id") String id);
}
