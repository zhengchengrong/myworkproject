package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.ZhiBoDetailItemBean;
import com.eaphone.g08android.bean.ZhiboInfo;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 11:15
 * 修改人：Administrator
 改时间：2017/8/29 11:15
 * 修改备注：
 */
public interface LiveApi {

    String LiveiveBASE = "news/v1/";

    @GET(LiveiveBASE + "live/")
    Observable<ResultBase<List<ZhiboInfo>>> loadLiveList();


    @GET(LiveiveBASE + "live/")
    Observable<ResultBase<List<ZhiboInfo>>> loadLiveList(@Query("page_index") String page_index, @Query("page_size") String page_size);


    @GET(LiveiveBASE + "live/{id}/")
    Observable<ResultBase<ZhiBoDetailItemBean>> loadLiveDetailList(@Path("id") String id);


    @GET(LiveiveBASE + "live/{id}/")
    Observable<ResultBase<ZhiBoDetailItemBean>> loadLiveDetailList(@Path("id") String id, @Query("page_index") String page_index, @Query("page_size") String page_size);


}
