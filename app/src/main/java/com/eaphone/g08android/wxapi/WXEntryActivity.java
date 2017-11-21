package com.eaphone.g08android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eaphone.g08android.R;
import com.eaphone.g08android.ui.MainActivity;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.Const;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        loginWeXin();
    }
    private void loginWeXin(){
        api = WXAPIFactory.createWXAPI(this, LoginActivity.WX_APP_ID);
        api.handleIntent(getIntent(),this);
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
        if(resp instanceof SendAuth.Resp){
           SendAuth.Resp newResp = (SendAuth.Resp) resp;
            if(newResp.errCode == 0){
                //获取微信传回的code
                String code = newResp.code;
                Log.e("code",code+"   fewfew    "+  newResp.url);
                WXEntryActivity.this.sendBroadcast(new Intent(Const.WEIXIN_LOGIN).putExtra("code",code));
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            finish();
        }
    }
}