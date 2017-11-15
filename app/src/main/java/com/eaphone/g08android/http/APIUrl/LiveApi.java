package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.LiveListBean;

import retrofit2.http.POST;
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

    @POST(LiveiveBASE + "live")
    Observable<LiveListBean> loadLiveList();




}
