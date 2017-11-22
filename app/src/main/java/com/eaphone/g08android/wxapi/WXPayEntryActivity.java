package com.eaphone.g08android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.eaphone.g08android.ui.personcenter.WebPayActivity;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this,"wx45904aba60ff29eb");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if(resp.errCode == 0){
            // 支付成功
            ToastUtils.showToast(this,"支付成功");

            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url","http://open.eaphonetech.com/doctor-web/v1/pay/pay_success.htm?out_trade_no="+ PreferencesUtils.getSharePreStr("out_trade_no"));
            startActivity(intent);
        }else if(resp.errCode == -1){
            // 支付失败
            ToastUtils.showToast(this,"支付失败");
            ToastUtils.showToast(this,"取消支付");
            Intent intent = new Intent(this, WebPayActivity.class);
            startActivity(intent);
        }else if(resp.errCode == -2){
            //取消支付
            ToastUtils.showToast(this,"取消支付");
            Intent intent = new Intent(this, WebPayActivity.class);
            startActivity(intent);

        }
        finish();

    }
}