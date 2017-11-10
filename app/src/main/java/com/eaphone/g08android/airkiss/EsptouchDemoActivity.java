package com.eaphone.g08android.airkiss;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.airkiss.task.__IEsptouchTask;
import com.eaphone.g08android.ui.personcenter.DeviceConnectActivity;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;

public class EsptouchDemoActivity extends CoreBaseActivity implements OnClickListener {

    private static final String TAG = "EsptouchDemoActivity";
    @BindView(R.id.tvApSssidConnected)
    TextView mTvApSsid;

    @BindView(R.id.edtApPassword)
    EditText mEdtApPassword;

    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;

    @BindView(R.id.cb_pass)
    CheckBox cb_pass;

    @BindView(R.id.cb_wifi)
    CheckBox cb_wifi;


    private EspWifiAdminSimple mWifiAdmin;

    @Override
    public int getLayoutId() {
        return R.layout.esptouch_demo_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("配置WIFI").build();
        mWifiAdmin = new EspWifiAdminSimple(this);
        mBtnConfirm.setOnClickListener(this);
        cb_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //为了让光标一直显示在最后
                if (!TextUtils.isEmpty(mEdtApPassword.getText())) {
                    String content = mEdtApPassword.getText().toString();
                    mEdtApPassword.setSelection(content.length());
                }
                if (b) {
                    mEdtApPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mEdtApPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        cb_wifi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new
                        Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            mTvApSsid.setText(apSsid);
            cb_wifi.setChecked(true);
        } else {
            mTvApSsid.setText("");
            cb_wifi.setChecked(false);
        }
        // check whether the wifi is connected
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        mBtnConfirm.setEnabled(!isApSsidEmpty);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnConfirm:
                String apSsid = mTvApSsid.getText().toString();

                if (TextUtils.isEmpty(apSsid)) {
                    showToast("请连接wifi");
                    return;
                }

                String apPassword = mEdtApPassword.getText().toString();

                if (TextUtils.isEmpty(apPassword)) {
                    showToast("请输入wifi密码");
                    return;
                }

                String apBssid = mWifiAdmin.getWifiConnectedBssid(); // 路由器mac地址
                String taskResultCountStr = "1"; /*Integer.toString(mSpinnerTaskCount
                    .getSelectedItemPosition())*/
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                            + ", " + " mEdtApPassword = " + apPassword);
                }
                Bundle bundle = new Bundle();
                bundle.putString("apSsid", apSsid); // wifi账号
                bundle.putString("apPassword", apPassword); // wifi密码
                bundle.putString("apBssid", apBssid); // 路由器的mac地址
                bundle.putString("source", getIntent().getExtras().getString("source")); //  bundle.putString("source","bind"); bundle.putString("source","reBind");
                startActivity(DeviceConnectActivity.class, bundle);
                break;
        }
    }


}
/*
private class EsptouchAsyncTask2 extends AsyncTask<String, Void, IEsptouchResult> {

	private ProgressDialog mProgressDialog;

	private IEsptouchTask mEsptouchTask;
	// without the lock, if the user tap confirm and cancel quickly enough,
	// the bug will arise. the reason is follows:
	// 0. task is starting created, but not finished
	// 1. the task is cancel for the task hasn't been created, it do nothing
	// 2. task is created
	// 3. Oops, the task should be cancelled, but it is running
	private final Object mLock = new Object();

	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(EsptouchDemoActivity.this);
		mProgressDialog
				.setMessage("Esptouch is configuring, please wait for a moment...");
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				synchronized (mLock) {
					if (__IEsptouchTask.DEBUG) {
						Log.i(TAG, "progress dialog is canceled");
					}
					if (mEsptouchTask != null) {
						mEsptouchTask.interrupt();
					}
				}
			}
		});
		mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
				"Waiting...", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		mProgressDialog.show();
		mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setEnabled(false);
	}

	@Override
	protected IEsptouchResult doInBackground(String... params) {
		synchronized (mLock) {
			String apSsid = params[0];
			String apBssid = params[1];
			String apPassword = params[2];
			mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,EsptouchDemoActivity.this);
		}
		IEsptouchResult result = mEsptouchTask.executeForResult();
		return result;
	}

	@Override
	protected void onPostExecute(IEsptouchResult result) {
		mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setEnabled(true);
		mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
				"Confirm");
		// it is unnecessary at the moment, add here just to show how to use isCancelled()
		if (!result.isCancelled()) {
			if (result.isSuc()) {
				mProgressDialog.setMessage("Esptouch success, bssid = "
						+ result.getBssid() + ",InetAddress = "
						+ result.getInetAddress().getHostAddress());
			} else {
				mProgressDialog.setMessage("Esptouch fail");
			}
		}
	}
}*/
