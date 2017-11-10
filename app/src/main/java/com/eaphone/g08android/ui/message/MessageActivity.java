package com.eaphone.g08android.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.CommonContracts;
import com.eaphone.g08android.mvp.presenter.MessagePresenter;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;


public class MessageActivity extends CoreBaseActivity<MessagePresenter> implements CommonContracts.MessageView {

    @BindView(R.id.rv_news_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private BaseQuickAdapter<MessageFirstLevel, BaseViewHolder> mAdapter;

    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("消息中心").build();

        mRefreshLayout.setEnabled(false);
        mAdapter = new BaseQuickAdapter<MessageFirstLevel, BaseViewHolder>(R.layout.message_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageFirstLevel item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_data, item.getText());
                helper.setText(R.id.tv_time, TimeUtils.displayTime(item.getUpdateTime()));
                ImageLoader.displayImage(item.getImage(), (ImageView) helper.getView(R.id.iv_icon));
                TextView tvRedPoint = helper.getView(R.id.tv_red_point);
                if ("read".equals(item.getReadStatus())) {
                    tvRedPoint.setVisibility(View.GONE);
                } else {
                    tvRedPoint.setVisibility(View.VISIBLE);
                }

                helper.getView(R.id.relative_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = item.getType();
                        toList();
                        item.setReadStatus("read");
                        notifyDataSetChanged();
                        mPresenter.loadPatchMessage(item.getId(), new ReadStatusEntity("read"));
                    }
                });
            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, mRecyclerView, true, mAdapter);
        show(R.string.loading);

    }

    private View getViewEmpty() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("暂无消息");
        emptyView.setImageResouce(R.mipmap.c45);
        return emptyView.getView();
    }


    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
        dismiss();
    }

    @Override
    public void getMessage(ResultBase<List<MessageFirstLevel>> resultBase) {
        dismiss();
        if (resultBase.isSuccess()) {
            if (resultBase.getData().size() == 0) {
                mAdapter.setEmptyView(getViewEmpty());
            } else
                mAdapter.setNewData(resultBase.getData());

        } else {

        }
    }

    @Override
    public void getPatchMessage(ResultBase<MessageFirstLevel> resultBase) {
        if (resultBase.isSuccess()) {

        }
    }

    private void toList() {
        if ("chengyuanxiaoxi".equals(type)) {
            startActivity(MessageInviteActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            startActivity(MessageSystemActivity.class, bundle);

        }
    }
}
