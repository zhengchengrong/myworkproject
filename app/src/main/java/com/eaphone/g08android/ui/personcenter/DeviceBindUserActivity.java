package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.DeviceBindUserAdapter;
import com.eaphone.g08android.bean.DeviceBindEntity;
import com.eaphone.g08android.bean.DeviceDetail;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.DeviceBindUserPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.widget.CircleImageView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.eaphone.g08android.R.id.tv_bind;

public class DeviceBindUserActivity extends CoreBaseActivity<DeviceBindUserPresenter> implements View.OnClickListener, JiankangContracts.DeviceBindUserView {


    @BindView(tv_bind)
    TextView mTvBind;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private DeviceBindUserAdapter popAdapter;

    private BaseQuickAdapter<DeviceDetail.ChannelsBean, BaseViewHolder> mAdapter;

    private String deviceId;
    private List<Family> popList;

    private List<DeviceDetail.ChannelsBean> dataList;

    private String channelNameDelete = "";//记录解绑用户的channelName

    private List<String> checkedList;//保存成员列表ID

    private DeviceDetail mDevice;

    @Override
    public int getLayoutId() {
        return R.layout.device_bind_user_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        deviceId = getIntent().getExtras().getString("data");
        initBackTitle("绑定成员").build();
        popList = new ArrayList<>();
        dataList = new ArrayList<>();
        checkedList = new ArrayList<>();
        mTvBind.setOnClickListener(this);

        popAdapter = new DeviceBindUserAdapter( popList, DeviceBindUserActivity.this);

        mAdapter = new BaseQuickAdapter<DeviceDetail.ChannelsBean, BaseViewHolder>(R.layout.text_item) {
            @Override
            protected void convert(BaseViewHolder helper, final DeviceDetail.ChannelsBean item) {
                TextView textView = helper.getView(R.id.tv_title);
                TextView tv_chennal = helper.getView(R.id.tv_chennal);
                if ("1".equals(item.getName())) {
                    tv_chennal.setBackgroundResource(R.drawable.shape_logout);
                    tv_chennal.setText("用户1");
                } else {
                    tv_chennal.setBackgroundResource(R.drawable.shape_time_solid5);
                    tv_chennal.setText("用户2");
                }
                textView.setText(item.getDisplayName());

                helper.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initBindDialog(mDevice.getId(), item.getName(), item.getDisplayName());
                    }
                });

                ImageLoader.displayImage(Const.getAvater(item.getUserId()), (CircleImageView) helper.getView(R.id.iv_avager));

            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, mRecyclerView, true, mAdapter);
        mPresenter.detail(deviceId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case tv_bind:
                mPresenter.familyMember();
                initPop();
                break;
        }
    }

    private void initBindDialog(final String deviceId, final String channelName, String name) {
        AlertDialog dialog = initAlertDialog("解绑提示", "【" + name + "】，是否要解除绑定？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        channelNameDelete = channelName;
                        Log.e(TAG, deviceId + "   " + channelName);
                        mPresenter.deviceDelete(deviceId, channelName);
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    PopupWindow popupWindow;

    private void initPop() {


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pop, null);
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(popAdapter.getUserId())) {
                    showToast("请选择绑定用户");
                    return;
                }
                String channelName1 = "";
                String channelName2 = "";
                for (int i = 0; i < mDevice.getChannels().size(); i++) {

                    if (TextUtils.isEmpty(mDevice.getChannels().get(0).getUserId())) {
                        channelName1 = mDevice.getChannels().get(0).getName();
                    }

                    if (TextUtils.isEmpty(mDevice.getChannels().get(1).getUserId())) {
                        channelName2 = mDevice.getChannels().get(1).getName();
                    }
                }

                DeviceBindEntity entity = new DeviceBindEntity();
                entity.setName(popAdapter.getName());
                entity.setUserId(popAdapter.getUserId());
                entity.setProduct_id(mDevice.getProduct().getId());
                if(!TextUtils.isEmpty(channelName1)){
                    entity.setChannel_name(channelName1);
                }else{
                    entity.setChannel_name(channelName2);
                }

                entity.setSerial_number(mDevice.getSerialNumber());
                mPresenter.deviceBind(entity);

            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.setAnimationStyle(R.style.Dialog_Animation);
        RecyclerViewHelper.initRecyclerViewH(this, recyclerview, true, popAdapter);
        popupWindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
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
    protected void onResume() {
        super.onResume();
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void getFamilyMember(ResultBase<List<Family>> result) {
        if (result.isSuccess()) {
            popList.clear();
            Family mySelf = new Family();
            mySelf.setUserId(PreferencesUtils.getSharePreStr(Const.USERID));
            mySelf.setName(PreferencesUtils.getSharePreStr(Const.NAME));
            popList.add(mySelf);
            if (result.getData().size() > 0 && result.getData() != null) {
                popList.addAll(result.getData());
                for (int i = 0; i < popList.size(); i++) {
                    for (int j = 0; j < checkedList.size(); j++) {
                        if (popList.get(i).getUserId().equals(checkedList.get(j))) {
                            popList.remove(i);
                        }
                    }
                }
            }
            Family family = new Family();
            family.setName("添加成员");
            popList.add(family);
            popAdapter.notifyDataSetChanged();
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getBindDevice(ResultBase<DeviceDetail> result) {
        if (result.isSuccess()) {
            mPresenter.detail(deviceId);
        }
        popupWindow.dismiss();

    }

    @Override
    public void getDeviceDelete(ResultBase result) {
        if (result.isSuccess()) {

            mPresenter.detail(deviceId);
        } else {
            showToast(result.getMessage());
        }
    }

    @Override
    public void getDetail(ResultBase<DeviceDetail> result) {

        if(result.isSuccess()){
            mDevice = result.getData();
            dataList.clear();
            checkedList.clear();
            for (int i = 0; i < mDevice.getChannels().size(); i++) {
                if (!TextUtils.isEmpty(mDevice.getChannels().get(i).getUserId())) {
                    dataList.add(mDevice.getChannels().get(i));
                    checkedList.add(mDevice.getChannels().get(i).getUserId());
                }
            }
            mAdapter.setNewData(dataList);
            if (checkedList.size() == 2) {
                mTvBind.setVisibility(View.GONE);
            }else{
                mTvBind.setVisibility(View.VISIBLE);
            }
        }


    }
}
