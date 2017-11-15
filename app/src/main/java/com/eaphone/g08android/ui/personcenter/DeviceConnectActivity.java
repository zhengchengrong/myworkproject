package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.airkiss.EspWifiAdminSimple;
import com.eaphone.g08android.airkiss.EsptouchTask;
import com.eaphone.g08android.airkiss.IEsptouchListener;
import com.eaphone.g08android.airkiss.IEsptouchResult;
import com.eaphone.g08android.airkiss.IEsptouchTask;
import com.eaphone.g08android.airkiss.task.__IEsptouchTask;
import com.eaphone.g08android.bean.G08BindEntity;
import com.eaphone.g08android.mvp.contracts.G08Contracts;
import com.eaphone.g08android.mvp.presenter.BindG08Presenter;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.MyHandler;
import com.eaphone.g08android.utils.WifiManagerUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;

import java.util.List;

import butterknife.BindView;

public class DeviceConnectActivity extends CoreBaseActivity<BindG08Presenter> implements G08Contracts.BindView {

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_connect)
    TextView tv_connect;

    @BindView(R.id.tv_wifi)
    TextView tv_wifi;

    private EspWifiAdminSimple mWifiAdmin;
    private String apSsid;
    private String apBssid;
    private String apPassword;

    private String phoneIp;


    private String deviceAddress;
    private String source;

    @Override
    public int getLayoutId() {
        return R.layout.device_connect_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("连接设备").setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = initAlertDialog(null, "离开本页面会终止设备连接，确认离开？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        synchronized (mLock) {
                            if (__IEsptouchTask.DEBUG) {
                                Log.i(TAG, "progress dialog is canceled");
                            }
                            if (mEsptouchTask != null) {
                                mEsptouchTask.interrupt();
                            }
                        }
                        dialogInterface.dismiss();
                        DeviceConnectActivity.this.finish();
                    }
                }).create();

                dialog.setCancelable(true);
                dialog.show();
            }
        }).build();
        loadProgress();
        mWifiAdmin = new EspWifiAdminSimple(this);

        Bundle bundle = getIntent().getExtras();
        apSsid = bundle.getString("apSsid");
        apBssid = bundle.getString("apBssid");
        apPassword = bundle.getString("apPassword");
        source = bundle.getString("source");
        new EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword, "1");

        phoneIp = WifiManagerUtils.getIPAddress(this);
        G08BindEntity entity = new G08BindEntity();
        entity.setBssid(apBssid.toUpperCase());
        entity.setSsid(apSsid);
        entity.setPhoneAddress(phoneIp);
        if (source.equals("bind")) {

            mPresenter.bind(entity);
        }
//        showToast("  apSsid= " + apSsid + "  apBssid=" + apBssid.toUpperCase() + "   apPassword=" + apPassword + "   phoneIp=" + phoneIp);

//        mPresenter.bindStatus(apBssid.toUpperCase(),phoneIp,deviceAddress);

        tv_wifi.setText(apSsid);

        tv_connect.setText("正在连接,请耐心等待...");
    }

    private CountDownTimer countDownTimer;

    private void loadProgress() {
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int aa = 60 - (int) millisUntilFinished / 1000;
                mProgressBar.setProgress(aa);
            }

            public void onFinish() {
                mProgressBar.setVisibility(View.GONE);
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }


    private IEsptouchListener myListener = new IEsptouchListener() {

        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };


    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (source.equals("bind")) {
                    tv_connect.setText("设备已连接上wifi，正在配网，请稍后...");
                } else {
                    showToast("重置wifi成功");
                    startActivity(DeviceDetailActivity.class);
                    DeviceConnectActivity.this.finish();
                }

//                String text = result.getBssid() + " is connected to the wifi";
//                Toast.makeText(DeviceConnectActivity.this, text,
//                        Toast.LENGTH_LONG).show();
            }

        });
    }

    private final Object mLock = new Object();
    private IEsptouchTask mEsptouchTask;

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getBind(ResultBase<String> result) {
        if (result.isSuccess()) {
//            showToast(result.getData());
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }


    @Override
    public void getBindStatus(ResultBase<String> result) {
        if (result.isSuccess()) {
            //showToast(result.getData());
            if ("waiting".equals(result.getData())) {
                new MyHandler(this).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.bindStatus(apBssid.toUpperCase(), phoneIp, deviceAddress);
                    }
                }, 1000);
            } else if ("success".equals(result.getData())) {
                showToast("设备绑定成功");
                CoreEvent event = new CoreEvent(EventCode.D);
                EventBusUtils.sendEvent(event);
                startActivity(DeviceActivity.class);
                DeviceConnectActivity.this.finish();
            } else if ("expired".equals(result.getData())) {
                showToast("绑定超时，请重试");
                startActivity(DeviceConnectFailedActivity.class);
                DeviceConnectActivity.this.finish();
            } else if ("already_bond_other_device".equals(result.getData())) {
                showToast("检测到您已绑定该类型设备，请更换用户绑定");
                DeviceConnectActivity.this.finish();
            }
        } else {
            if("already_bond_by_other".equals(result.getData())){
                AlertDialog dialog = initAlertDialog("","该设备已被绑定，如需解绑请联系客服。客服电话：400-621-1136")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:400-621-1136"));
                                startActivity(intent);
                                DeviceConnectActivity.this.finish();
                            }
                        }).create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }else {
                showToast(result.getMessage());
            }
        }

    }


    private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {

//        private ProgressDialog mProgressDialog;

//        private IEsptouchTask mEsptouchTask;
        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
//        private final Object mLock = new Object();

        @Override
        protected void onPreExecute() {
//            mProgressDialog = new ProgressDialog(DeviceConnectActivity.this);
//            mProgressDialog
//                    .setMessage("Esptouch is configuring, please wait for a moment...");
//            mProgressDialog.setCanceledOnTouchOutside(false);
//            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    synchronized (mLock) {
//                        if (__IEsptouchTask.DEBUG) {
//                            Log.i(TAG, "progress dialog is canceled");
//                        }
//                        if (mEsptouchTask != null) {
//                            mEsptouchTask.interrupt();
//                        }
//                    }
//                }
//            });
//            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                    "Waiting...", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//            mProgressDialog.show();
//            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                    .setEnabled(false);
        }

        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            int taskResultCount = -1;
            synchronized (mLock) {
                // !!!NOTICE
                String apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0]);
                String apBssid = params[1];
                String apPassword = params[2];
                String taskResultCountStr = params[3];
                taskResultCount = Integer.parseInt(taskResultCountStr);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, DeviceConnectActivity.this);
                mEsptouchTask.setEsptouchListener(myListener);
            }
            List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
            return resultList;
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
//            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                    .setEnabled(true);
//            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
//                    "Confirm");
            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results
                if (firstResult.isSuc()) {
                    StringBuilder sb = new StringBuilder();
                    boolean success = false;
                    for (IEsptouchResult resultInList : result) {

                        success = resultInList.isSuc();
                        deviceAddress = resultInList.getInetAddress()
                                .getHostAddress();
//                        deviceBssid = resultInList.getBssid();

                        sb.append("Esptouch success, bssid = "
                                + resultInList.getBssid() + " isSuccess  " + resultInList.isSuc() + "   isCancel  " + resultInList.isCancelled()
                                + ",\nInetAddress = "
                                + resultInList.getInetAddress()
                                .getHostAddress());
                        count++;
                        if (count >= maxDisplayCount) {
                            break;
                        }
                    }
                    mPresenter.bindStatus(apBssid.toUpperCase(), phoneIp, deviceAddress);

                    if (count < result.size()) {
                        sb.append("\nthere's " + (result.size() - count)
                                + " more result(s) without showing\n");

                    }
                } else {//失败
                    showToast("设备连接失败");
                    startActivity(DeviceConnectFailedActivity.class);
                    DeviceConnectActivity.this.finish();
                }
            }
        }
    }


}
