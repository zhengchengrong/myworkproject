package com.eaphone.g08android.http.APIUrl;

import com.eaphone.g08android.bean.Bind3rdInfoEnity;
import com.eaphone.g08android.bean.BindMsgResult;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.FamilyAddInvite;
import com.eaphone.g08android.bean.InviteEnity;
import com.eaphone.g08android.bean.LoginEnity;
import com.eaphone.g08android.bean.MessageInvite;
import com.eaphone.g08android.bean.RegistEnity;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.bean.SelfMsgEnity;
import com.eaphone.g08android.bean.TokenResult;
import com.eaphone.g08android.bean.UpdatePhoneEntity;
import com.eaphone.g08android.bean.UserInfo;
import com.eaphone.g08android.bean.WXMsg;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 11:14
 * 修改人：Administrator
 * 修改时间：2017/8/29 11:14
 * 修改备注：
 */
public interface PassportApi {

    String PASSPORTBASE = "passport/v1/";

    /*------------------------------------------passport-------------------*/
    //登录
    @POST(PASSPORTBASE + "user/login")
    Observable<ResultBase<TokenResult>> userLogin(@Body() LoginEnity enity);

    //微信登录获取token
    @GET
    Observable<WXMsg> loadWXMSG(@Url String url, @Query("appid") String appid,
                                @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    //通过token获取微信用户信息
    @GET
    Observable<ResultQQMsg> loadWXUserMsg(@Url String url, @Query("access_token") String access_token, @Query("openid") String openid);

    //查询是否绑定第三方
    @POST(PASSPORTBASE + "user/3rd-party/login")
    Observable<ResultBase<TokenResult>> loadIsBind3rd(@Query("openId") String openId, @Query("type") String type, @Query("role") String role);

    //获取用户信息
    @GET(PASSPORTBASE + "user/info")
    Observable<ResultBase<UserInfo>> loadUserInfo(@Query("userId") String userId);

    //修改用户信息
    @PATCH(PASSPORTBASE + "user/info")
    Observable<ResultBase> loadUserInfo(@Body() SelfMsgEnity msg);

    //获取验证码
    @POST(PASSPORTBASE + "verifyCode")
    Observable<ResultBase> loadVerifyCode(@Query("phone") String phone);

    //绑定第三方
    @POST(PASSPORTBASE + "user/3rd-party/bind")
    Observable<ResultBase<BindMsgResult>> loadBind3rd(@Query("phone") String phone, @Query("openId") String openId, @Query("type") String type, @Query("authcode") String authcode, @Query("role") String role);

    //完善第三方登录信息
    @POST(PASSPORTBASE + "user/3rd-party/info")
    Observable<ResultBase> load3rdInfo(@Body() Bind3rdInfoEnity enity);

    //用户注册
    @POST(PASSPORTBASE + "user/register")
    Observable<ResultBase> loadRegist(@Body() RegistEnity regist);

    @POST(PASSPORTBASE + "user/resetPassword")
    Observable<ResultBase> resetPwd(@Query("authcode") String authcode, @Query("phone") String phone, @Query("newPassword") String newPassword, @Query("role") String role);

    //家庭成员
    @GET(PASSPORTBASE + "family")
    Observable<ResultBase<List<Family>>> loadFamily();

    //获取邀请列表
    @GET(PASSPORTBASE + "family/application/")
    Observable<ResultBase<List<MessageInvite>>> loadFimalyInvite(@Query("page_size") String page_size, @Query("page_index") String page_index);

    //回应一条邀请
    @PATCH(PASSPORTBASE + "family/application/{id}/")
    Observable<ResultBase> loadHandlerMessage(@Path("id")String id,@Body() InviteEnity invite);

    //发送邀请
    @POST(PASSPORTBASE + "family/application/")
    Observable<ResultBase> loadHandlerMessage(@Body() FamilyAddInvite invite);

    //手机号确认用户
    @GET(PASSPORTBASE + "user/existence")
    Observable<ResultBase<Boolean>> loadIsExist(@Query("username") String phone);

    @GET(PASSPORTBASE + "family/user/{userId}/")
    Observable<ResultBase<Family>> loadMemberDetail(@Path("userId") String userId);

    //添加未注册家庭成员验证码
    @POST(PASSPORTBASE + "family/sendVerifyCode")
    Observable<ResultBase> loadAddCode(@Query("phone") String phone);

    @POST(PASSPORTBASE+"family/addMember")
    Observable<ResultBase> loadAddMember(@Query("phone") String phone,
                                         @Query("relationship") String relationship,
                                         @Query("authcode") String authcode,
                                         @Query("name") String name,
                                         @Query("identity") String identity,
                                         @Query("birthday") String birthday,
                                         @Query("gender") String gender
    );

    //取消关注
    @DELETE(PASSPORTBASE + "family/user/{userId}")
    Observable<ResultBase> loadFamilyDetail(@Path("userId") String userId);

    //上传头像
    @Multipart
    @POST(PASSPORTBASE + "user/{user_id}/avatar")
    Observable<ResultBase> upLoadAvater(@Path("user_id") String user_id, @Part MultipartBody.Part part);

    //退出登录
    @GET(PASSPORTBASE + "user/logout")
    Observable<ResultBase> logout();

    //修改密码
    @POST(PASSPORTBASE + "user/updatePassword")
    Observable<ResultBase> loadResetPassword(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    //更换手机
    @POST(PASSPORTBASE + "user/phone")
    Observable<ResultBase> loadUpdatePhone(@Body()UpdatePhoneEntity entity);


}
