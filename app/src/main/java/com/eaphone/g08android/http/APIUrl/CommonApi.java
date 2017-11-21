package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.FileResult;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.eaphone.g08android.bean.ShareInfoEntity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.eaphone.g08android.http.APIUrl.CommonApi.BASECOMMOM;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 11:13
 * 修改人：Administrator
 * 修改时间：2017/8/30 11:13
 * 修改备注：
 */
public interface CommonApi {
    String BASECOMMOM = "common/v1/";

    @GET(BASECOMMOM + "user/message/level/{level}")
    Observable<ResultBase<List<MessageFirstLevel>>> loadMessage(@Path("level") String level,
                                                                @Query("page_index") String pageIndex,
                                                                @Query("page_size") String pageSize);

    @GET(BASECOMMOM + "user/message/level/{level}/type/{type}/")
    Observable<ResultBase<List<MessageFirstLevel>>> loadSystemMessage(
            @Path("level") String level,
            @Path("type") String type,
            @Query("page_index") String pageIndex,
            @Query("page_size") String pageSize);

    @Multipart
    @POST(BASECOMMOM + "file/")
    Observable<ResultBase<FileResult>> loadFile(@Part MultipartBody.Part part);

    @PATCH(BASECOMMOM + "user/message/{message_id}/")
    Observable<ResultBase<MessageFirstLevel>> loadPatchMessage(@Path("message_id") String message_id, @Body() ReadStatusEntity entity);

    @GET(BASECOMMOM + "user/message/type/{type}/unreadCount")
    Observable<ResultBase<Integer>> loadMessageCount(@Path("type") String type);


    // app分享
    @GET(BASECOMMOM + "app/shareInfo/")
    Observable<ResultBase<ShareInfoEntity>> loadShareInfo();


}
