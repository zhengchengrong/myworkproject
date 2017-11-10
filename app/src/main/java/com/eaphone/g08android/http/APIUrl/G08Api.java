package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.G08BindEntity;
import com.hpw.mvpframe.base.ResultBase;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/31 9:59
 * 修改人：Administrator
 * 修改时间：2017/8/31 9:59
 * 修改备注：
 */
public interface G08Api {

    String G08APIBASE = "g08/v1/";

    @POST(G08APIBASE+"bind/app/")
    Observable<ResultBase<String>> loadBindG08(@Body()G08BindEntity entity);


    @GET(G08APIBASE+"bind/status/")
    Observable<ResultBase<String>> loadBindStatus(@Query("bssid")String bssid,@Query("phoneAddress")String phoneAddress ,@Query("deviceAddress")String deviceAddress);
}
