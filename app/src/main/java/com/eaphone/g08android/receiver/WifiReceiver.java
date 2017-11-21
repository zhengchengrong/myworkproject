package com.eaphone.g08android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.hpw.mvpframe.utils.ToastUtils;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/21 15:00
 * 修改人：Administrator
 * 修改时间：2017/11/21 15:00
 * 修改备注：
 */
public class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)){
                //signal strength changed
            }
            else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){//wifi连接上与否
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
                    ToastUtils.showToast(context,"wifi已断开");

                }
                else if(info.getState().equals(NetworkInfo.State.CONNECTED)){
                    WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    //获取当前wifi名称
                    System.out.println("连接到网络 " + wifiInfo.getSSID());

                }
            }
            else if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){//wifi打开与否
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

                if(wifistate == WifiManager.WIFI_STATE_DISABLED){
                    System.out.println("系统关闭wifi");
                }
                else if(wifistate == WifiManager.WIFI_STATE_ENABLED){
                    System.out.println("系统开启wifi");
                }
            }
        }
    }
