package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.LiveHome;
import com.eaphone.g08android.bean.ZhiBoDetailItemBean;
import com.eaphone.g08android.bean.ZhiBoGroupItemBean;
import com.eaphone.g08android.bean.ZhiboInfo;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
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

    // 加载直播主页
    @GET(LiveiveBASE + "home/")
    Observable<ResultBase<LiveHome>> loadHome();



    @GET(LiveiveBASE + "live/")
    Observable<ResultBase<List<ZhiboInfo>>> loadLiveList();


    @GET(LiveiveBASE + "live/")
    Observable<ResultBase<List<ZhiboInfo>>> loadLiveList(@Query("page_index") String page_index, @Query("page_size") String page_size);


    @GET(LiveiveBASE + "live/{liveId}/")
    Observable<ResultBase<ZhiBoDetailItemBean>> loadLiveDetailList(@Path("liveId") String liveId);


    @GET(LiveiveBASE + "live/{liveId}/")
    Observable<ResultBase<ZhiBoDetailItemBean>> loadLiveDetailList(@Path("liveId") String liveId, @Query("page_index") String page_index, @Query("page_size") String page_size);


    // 加载交流群
    @GET(LiveiveBASE + "group/")
    Observable<ResultBase<ArrayList<ZhiBoGroupItemBean>>> loadLiveGroupList();
    @GET(LiveiveBASE + "group/")
    Observable<ResultBase<ArrayList<ZhiBoGroupItemBean>>> loadLiveGroupList(@Query("page_index") String page_index, @Query("page_size") String page_size);

    // 下载音乐
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

}
