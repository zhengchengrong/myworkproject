package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Device;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.JiankangContracts;
import com.eaphone.g08android.mvp.presenter.DevicePresenter;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;

import java.util.List;

import butterknife.BindView;


/**
 * 我的设备
 */
public class DeviceActivity extends CoreBaseActivity<DevicePresenter> implements JiankangContracts.DeviceView {


    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    BaseQuickAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.device_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        initBackTitle("我的设备").setRightImage(R.mipmap.icon_add).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(DeviceAddActivity.class);
            }
        }).build();
        swipe_refresh.setEnabled(false);
        adapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.device_item) {

            @Override
            protected void convert(BaseViewHolder holder, final Device item) {
                holder.setText(R.id.tv_device_name, item.getName());
                if (!TextUtils.isEmpty(item.getThumbnailUrl())) {
                    ImageLoader.displayImage(item.getThumbnailUrl(), (ImageView) holder.getView(R.id.iv_icon));
                }

                holder.getView(R.id.ll_device).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("deviceId", item.getDeviceId());
                        startActivity(DeviceDetailActivity.class, bundle);
                    }
                });
            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter/*, new AlphaInAnimationAdapter(animAdapter)*/);
        show(R.string.loading);
        adapter.setEmptyView(getView());
    }

    private View getView() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("还未绑定任何设备，请点击“+”进行绑定");
        emptyView.setImageResouce(R.mipmap.ic_bind);
        return emptyView.getView();
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event != null && event.getCode() == EventCode.D) {
            mPresenter.onStart();
        }
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
    public void getAnalysis(ResultBase<List<Device>> result) {
        dismiss();
        if (result.isSuccess()) {
            adapter.setNewData(result.getData());
        } else {
            showToast(result.getMessage());
        }
    }
}
