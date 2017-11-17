package com.eaphone.g08android.wxapi;

import android.app.Activity;

import com.eaphone.g08android.bean.WXPayBean;
import com.hpw.mvpframe.utils.ToastUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class WXPay {

    public static boolean isShow = false;

    public static void UseWXPay(Activity mContext, WXPayBean wxPayBean) {
        // 将该app注册到微信(通过WXAPIFactory工厂，获取IWXAPI的实例)
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, wxPayBean.appid,false);
        msgApi.registerApp(wxPayBean.appid);

        if (!msgApi.isWXAppInstalled()) {
            ToastUtils.showToast(mContext,"没有安装微信");
            return;
        }
        if (!msgApi.isWXAppSupportAPI()) {
            ToastUtils.showToast(mContext,"当前版本不支持支付功能");
            return;
        }
        PayReq req = new PayReq();
        req.appId = wxPayBean.appid;
        req.partnerId = wxPayBean.partnerid;
        req.prepayId = wxPayBean.prepayid;
        req.nonceStr = wxPayBean.noncestr;
        req.timeStamp = wxPayBean.timestamp;
        req.packageValue = wxPayBean.pack;
        req.sign = wxPayBean.sign;
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        msgApi.sendReq(req);

    }

}
