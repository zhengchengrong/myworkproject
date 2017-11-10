package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.Device;
import com.eaphone.g08android.bean.DeviceBindEntity;
import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.DeviceName;
import com.eaphone.g08android.bean.HandInDataEnity;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.bean.HealthyWarn;
import com.eaphone.g08android.bean.HealthyWarnDetail;
import com.eaphone.g08android.bean.JianKang;
import com.eaphone.g08android.bean.LineBean;
import com.eaphone.g08android.bean.MessageWarnDetail;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 11:15
 * 修改人：Administrator
 * 修改时间：2017/8/29 11:15
 * 修改备注：
 */
public interface JianKangApi {

    String JIANKANGBASE = "jiankang/v1/";

    @POST(JIANKANGBASE + "data/analysis")
    Observable<ResultBase<List<Healthy>>> loadAnalysis(@Body() HealthyDataEnity healthy, @Query("userId") String userId);

    @GET(JIANKANGBASE + "user/device")
    Observable<ResultBase<List<Device>>> loadDevice();

    //绑定设备
    @POST(JIANKANGBASE + "user/device")
    Observable<ResultBase<DeviceDetail>> loadDeviceBind(@Body() DeviceBindEntity entity);

    @GET(JIANKANGBASE + "device/{device_id}/")
    Observable<ResultBase<DeviceDetail>> loadDeviceDetail(@Path("device_id") String device_id);

    //用户按传感器类型查询历史数据（日曲线
    @GET(JIANKANGBASE + "data/sensor/{sensor_type}/")
    Observable<ResultBase<List<Healthy>>> loadHistory(@Path("sensor_type") String sensor_type,
                                                      @Query("begin_time") String begin_time,
                                                      @Query("end_time") String end_time,
                                                      @Query("userId") String userId);

    //用户按传感器类型查询曲线数据
    @GET(JIANKANGBASE + "data/sensor/{sensor_type}/{time_type}/")
    Observable<ResultBase<List<LineBean>>> loadHisTimeType(@Path("sensor_type") String sensor_type,
                                                           @Path("time_type") String time_type,
                                                           @Query("firstDay") String firstDay,
                                                           @Query("userId") String userId);

    //用户手动上传数据
    @POST(JIANKANGBASE + "data/sensor/{sensor_type}/")
    Observable<ResultBase> loadHandIdData(@Path("sensor_type") String sensor_type,
                                          @Body HandInDataEnity enity);

    @GET(JIANKANGBASE + "user/alarm/statistics/type/{sensor_type}/")
    Observable<ResultBase<List<HealthyWarn>>> loadHealthyWarn(@Path("sensor_type") String sensor_type, @Query("userId") String userId);

//    @GET(JIANKANGBASE + "user/alarm/message/{message_id}/")
//    Observable<ResultBase<MessageWarnDetail>> loadWarnDetail(@Path("message_id") String message_id);

    @GET(JIANKANGBASE+"user/warning/{id}/")
    Observable<ResultBase<MessageWarnDetail>> loadWarnDetail(@Path("id") String message_id);

    @GET(JIANKANGBASE + "user/alarm/message/")
    Observable<ResultBase<List<HealthyWarnDetail>>> loadWarnList(@Query("sensor_type") String sensor_type,
                                                                 @Query("page_index") String page_index,
                                                                 @Query("page_size") String page_size,
                                                                 @Query("end_time") String end_time,
                                                                 @Query("begin_time") String begin_time,
                                                                 @Query("userId") String userId);

    @PATCH(JIANKANGBASE + "user/device/{device_id}/channel/{channel_name}/")
    Observable<ResultBase> loadDeviceName(@Path("device_id") String device_id,
                                          @Path("channel_name") String channel_name, @Body() DeviceName name);

    @DELETE(JIANKANGBASE + "user/device/{device_id}/channel/{channel_name}/")
    Observable<ResultBase> loadDeleteDevice(@Path("device_id") String device_id,
                                            @Path("channel_name") String channel_name);

    @PATCH(JIANKANGBASE + "device/{device_id}/")
    Observable<ResultBase> loadDeviceRename(@Path("device_id") String device_id, @Body() DeviceName name);

    @DELETE(JIANKANGBASE + "device/{device_id}/")
    Observable<ResultBase> loadDeviceUnBind(@Path("device_id") String device_id);

    @GET(JIANKANGBASE + "archive/user/{user_id}/")
    Observable<ResultBase<JianKang>> loadJianKang(@Path("user_id") String user_id);

    @PATCH(JIANKANGBASE + "archive/user/{user_id}/")
    Observable<ResultBase<JianKang>> loadJianKang(@Path("user_id") String user_id,@Body() JianKang jianKang);


}
