package com.eaphone.g08android.mvp.contracts;

import com.eaphone.g08android.bean.BindMsgResult;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.FamilyAddInvite;
import com.eaphone.g08android.bean.FamilyAddMemberEntity;
import com.eaphone.g08android.bean.FileResult;
import com.eaphone.g08android.bean.InviteEnity;
import com.eaphone.g08android.bean.LoginEnity;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.eaphone.g08android.bean.RegistEnity;
import com.eaphone.g08android.bean.ResetPwdEnity;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.bean.SelfMsgEnity;
import com.eaphone.g08android.bean.TokenResult;
import com.eaphone.g08android.bean.UserInfo;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * passport相应
 * Created by zlq on 2017/8/13.
 */

public interface PassportContracts {

    interface StartImageView extends CoreBaseView {
        void getImages(int[] res);
    }

    abstract class StartImagesPresenter extends CoreBasePresenter< StartImageView> {
        public abstract void getImages();
    }

    //登录

    interface LoginView extends CoreBaseView {
        void getLoginResult(ResultBase<TokenResult> result);

        void getIsBindResult(ResultBase<TokenResult> result, ResultQQMsg resultQQMsg);

        void getUserInfo(ResultBase<UserInfo> info);
    }

    abstract class LoginPresenter extends CoreBasePresenter< LoginView> {
        public abstract void login(LoginEnity enity);

        public abstract void wxRes(String appid, String secret, String code, String grant_type);

        public abstract void wxUserInfo(String access_token, String openid);

        public abstract void isBind3rd(String openId, String type, String role, ResultQQMsg resultQQMsg);

        public abstract void userInfo(String userId);
    }

    //绑定第三方

    interface Bind3rdView extends CoreBaseView {
        void getVerifyCode(ResultBase resultBase);

        void getUserInfo(ResultBase<UserInfo> info);

        void getBind3rd(ResultBase<BindMsgResult> resultBase);
    }

    abstract class Bind3rdPresenter extends CoreBasePresenter< Bind3rdView> {
        public abstract void verifyCode(String phone);

        public abstract void bind3rd(String phone, String openId, String type, String authcode, String role);

        public abstract void userInfo(String userId);
    }

    //完善第三方信息

    interface Bind3rdInfoView extends CoreBaseView {
        void getBindInfo(ResultBase result);
    }

    abstract class Bind3rdInfoPresenter extends CoreBasePresenter< Bind3rdInfoView> {
        public abstract void bind3rdInfo(ResultQQMsg qqMsg);
    }

    //注册

    interface RegistView extends CoreBaseView {
        void getVerifyCode(ResultBase resultBase);

        void getRegist(ResultBase result);
    }

    abstract class RegistPresenter extends CoreBasePresenter< RegistView> {
        public abstract void verifyCode(String phone);

        public abstract void regist(RegistEnity regist);
    }

    //重置密码

    interface ResetView extends CoreBaseView {
        void getVerifyCode(ResultBase resultBase);

        void getReset(ResultBase result);
    }

    abstract class ResetPresenter extends CoreBasePresenter< ResetView> {
        public abstract void resetCode(String phone);

        public abstract void reset(ResetPwdEnity reset);
    }

    //家庭成员列表

    interface FamilyView extends CoreBaseView {
        void getFamily(ResultBase<List<Family>> result);
    }

    abstract class FamilyPresenter extends CoreBasePresenter< FamilyView> {
        public abstract void loadFamily();
    }

    //取消关注

    interface FamilyDetailView extends CoreBaseView {
        void getDelete(ResultBase result);
    }

    abstract class FamilyDetailPresenter extends CoreBasePresenter< FamilyDetailView> {
        public abstract void loadDelete(String id);
    }

    //亲人邀请消息

    interface InviteView extends CoreBaseView {
        void getInvite(ResultBase<List<MessageFirstLevel>> result);

        void getMoreInvite(ResultBase<List<MessageFirstLevel>> result);

        void getHandler(ResultBase resultBase);

        void getPatchMessage(ResultBase<MessageFirstLevel> resultBase);
    }

    abstract class InvitePresenter extends CoreBasePresenter< InviteView> {
        public abstract void loadInvite(String level,String size, String index);

        public abstract void loadMoreInvite(String level,String index, String size );

        public abstract void loadHandler(String id, InviteEnity invite);
        public abstract void loadPatchMessage(String id, ReadStatusEntity invite);
    }

    //邀请成员

    interface FamilyAddView extends CoreBaseView {
        void getIsExist(ResultBase<Boolean> result);

        void getSendInvite(ResultBase result);

        void getAddCode(ResultBase result);

        void getAddMember(ResultBase resultBase);

        void getAvaget(ResultBase<FileResult> resultBase);

        void getMsg(ResultBase resultBase);

        void getUserMsg(ResultBase<UserInfo> resultBase);

    }

    abstract class FamilyAddPresenter extends CoreBasePresenter< FamilyAddView> {
        public abstract void isExist(String phone);

        public abstract void sendInvite(FamilyAddInvite invite);

        public abstract void addCode(String phone);

        public abstract void addMember(FamilyAddMemberEntity family);

        public abstract void upLoadAvager(MultipartBody.Part part);

        public abstract void upLoadMsg(SelfMsgEnity enity);

        public abstract void downLoadMsg(String userId);
    }


    //修改个人信息

    interface UpdateMsgView extends CoreBaseView {
        void getMsg(ResultBase result);

        void getUserInfo(ResultBase<UserInfo> result);

        void getAvager(ResultBase result);
    }

    abstract class UpdateMsgPresenter extends CoreBasePresenter< UpdateMsgView> {
        public abstract void loadmsg(SelfMsgEnity enity);

        public abstract void upLoadAvager(MultipartBody.Part part,String userId);

        public abstract void downLoadUserInfo(String userId);
    }


    //退出

    interface SetView extends CoreBaseView {
        void getLogout(ResultBase result);
    }

    abstract class SetPresenter extends CoreBasePresenter< SetView> {
        public abstract void logout();
    }


    interface ResetPasswordView extends CoreBaseView {
        void getReset(ResultBase result);
    }

    abstract class ResetPasswordPresenter extends CoreBasePresenter< ResetPasswordView> {
        public abstract void reset(String old, String last);
    }

    //更换手机

    interface UpdatePhoneView extends CoreBaseView {
        void getReset(ResultBase result);

        void getCode(ResultBase result);
    }

    abstract class UpdatePhonePresenter extends CoreBasePresenter< UpdatePhoneView> {
        public abstract void reset(String password, String phone, String authcode);

        public abstract void verifyCode(String phone);
    }

    //设备绑定成员

    interface BindUserNextView extends CoreBaseView {
        void getIsExits(ResultBase<Boolean> result);

        void getVerCode(ResultBase result);

        void getSendInvite(ResultBase resultBase);
    }

    abstract class BindUserNextPresenter extends CoreBasePresenter< BindUserNextView> {
        public abstract void isExits(String phone);

        public abstract void vercode(String phone);

        public abstract void sendInvite(FamilyAddInvite invite);
    }

    interface CheckPhoneView extends CoreBaseView{
        void getIsExist(ResultBase<Boolean> result);
    }

    abstract class CheckPhonePresenter extends CoreBasePresenter<CheckPhoneView>{
        public abstract void isExist(String phone);
    }
}
