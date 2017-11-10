package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.airkiss.EsptouchDemoActivity;
import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.DeviceName;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.DeviceDetailPresenter;
import com.eaphone.g08android.utils.EventCode;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;

import butterknife.BindView;

public class DeviceDetailActivity extends CoreBaseActivity<DeviceDetailPresenter> implements View.OnClickListener, JiankangContracts.DeviceDetailView {

    @BindView(R.id.btn_unbind)
    Button mBtnUnbind;


    @BindView(R.id.tv_bind)
    TextView mTvBind;
    @BindView(R.id.tv_reset)
    TextView mTvReset;

    @BindView(R.id.iv_rename)
    ImageView mIvRename;

    @BindView(R.id.edt_name)
    EditText mEdtName;

    @BindView(R.id.iv_bg)
    ImageView mIvBg;


    private String deviceId;


    @Override
    public int getLayoutId() {
        return R.layout.device_detail_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("设备详情").build();
        deviceId = getIntent().getExtras().getString("deviceId");
        mBtnUnbind.setOnClickListener(this);
        mTvBind.setOnClickListener(this);
        mTvReset.setOnClickListener(this);
        mIvRename.setOnClickListener(this);

        mPresenter.detail(deviceId);
        show(R.string.loading);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getDetail(ResultBase<DeviceDetail> result) {
        dismiss();
        if (result.isSuccess()) {
            ImageLoader.displayImage(result.getData().getImageUrl(), mIvBg);
            mEdtName.setText(result.getData().getName());
            mEdtName.setEnabled(false);
        }
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void getDeviceUnbind(ResultBase result) {
        if (result.isSuccess()) {
            showToast("设备解绑成功");
            CoreEvent event = new CoreEvent(EventCode.D);
            EventBusUtils.sendEvent(event);
            DeviceDetailActivity.this.finish();
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getDeviceRename(ResultBase result) {
        if (result.isSuccess()) {
            showToast("设备名修改成功");
            CoreEvent event = new CoreEvent(EventCode.D);
            EventBusUtils.sendEvent(event);
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        Bundle bundle ;
        switch (view.getId()) {
            case R.id.btn_unbind:
                initUnBindDialog();
                break;
            case R.id.tv_bind:
                bundle = new Bundle();
                bundle.putString("data", deviceId);
                startActivity(DeviceBindUserActivity.class, bundle);
                break;
            case R.id.tv_reset:
                bundle = new Bundle();
                bundle.putString("source","reBind");
                startActivity(EsptouchDemoActivity.class,bundle);
                break;
            case R.id.iv_rename:
                initRenameDialog();
                break;
        }
    }


    private void initRenameDialog() {
        View view = getLayoutInflater().inflate(R.layout.device_rename_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.edt_name);
        final TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        final TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        final AlertDialog dialog = new AlertDialog.Builder(DeviceDetailActivity.this)
                .setView(view).create();
        dialog.setCanceledOnTouchOutside(false);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText())) {
                    showToast("请输入设备名");
                } else {
                    DeviceName deviceName = new DeviceName();
                    deviceName.setName(editText.getText().toString());
                    mPresenter.deviceRename(deviceId,deviceName);
                    mEdtName.setText(editText.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void initUnBindDialog() {
        AlertDialog dialog = initAlertDialog(null, "删除该数据，测量数据将不能通过手机接收，请确认删除？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mPresenter.deviceUnbind(deviceId);

                    }
                }).create();

        dialog.show();
    }
}
