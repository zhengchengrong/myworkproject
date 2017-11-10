package com.eaphone.g08android.ui.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.mvp.contracts.CommonContracts;
import com.eaphone.g08android.mvp.presenter.MessageSecondPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;


public class MessageSystemActivity extends CoreBaseActivity<MessageSecondPresenter>
        implements CommonContracts.MessageSystemView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private BaseQuickAdapter<MessageFirstLevel, BaseViewHolder> mAdapter;
    @BindView(R.id.rv_news_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private static final String MESSAGE_SYSTEM = "xitongxiaoxi";
    private static final String MESSAGE_WARN = "yujingxiaoxi";
    private String type;
    private String title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getExtras().getString("type");
        if (MESSAGE_WARN.equals(type)) {
            title = "预警消息";
        } else {
            title = "系统消息";
        }
        initBackTitle(title).build();
        mRefreshLayout.setRefreshing(false);
        mPresenter.loadSystemMessage(type, "1", Const.PAGE_SIZE);

        mAdapter = new BaseQuickAdapter<MessageFirstLevel, BaseViewHolder>(R.layout.message_system_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageFirstLevel item) {
                helper.setText(R.id.tv_time, TimeUtils.displayTime(item.getCreateTime()));
                helper.setText(R.id.tv_data, item.getText());

                TextView tv_detail = helper.getView(R.id.tv_look_detail);

                if (type.equals(MESSAGE_WARN)) {
                    tv_detail.setVisibility(View.VISIBLE);
                } else {
                    tv_detail.setVisibility(View.GONE);
                    helper.getView(R.id.v_divider).setVisibility(View.GONE);
                }

                tv_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = item.getUrl();
                        String id = Uri.parse(url).getQueryParameter("id");
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        startActivity(MessageWarnDetailActivity.class, bundle);
                    }
                });
            }
        };

        RecyclerViewHelper.initRecyclerViewV(mContext, mRecyclerView, true, mAdapter);

        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        show(R.string.loading);

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        dismiss();
        showToast(msg);
    }

    @Override
    public void getSystemMessage(ResultBase<List<MessageFirstLevel>> resultBase) {
        dismiss();
        if (resultBase.isSuccess()) {
            mCurrentCounter = resultBase.getData().size();
            mAdapter.setNewData(resultBase.getData());
        } else {
            showToast(resultBase.getMessage());
        }

        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void getMoreSystemMessage(ResultBase<List<MessageFirstLevel>> resultBase) {
        if (resultBase.isSuccess()) {
            mCurrentCounter = resultBase.getData().size();
            mAdapter.addData(resultBase.getData());

        } else {
            showToast(resultBase.getMessage());
        }
        mAdapter.loadMoreComplete();

    }


    private int mCurrentCounter = 0;
    private boolean isErr;
    private int pageIndex = 1;

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadSystemMessage(type, "1", Const.PAGE_SIZE);
                mRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            }
        }, Const.DELAYMILLIS);
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnabled(false);
        if (mCurrentCounter < 10) {
            mAdapter.loadMoreEnd(true);
            mRefreshLayout.setEnabled(true);
        } else {
            if (mCurrentCounter >= 10) {
                if (isErr) {
                    isErr = false;
                    mAdapter.loadMoreFail();
                } else {
                    mAdapter.loadMoreEnd(false);
                    pageIndex++;
                    mPresenter.loadMoreSystemMessage(type, String.valueOf(pageIndex), Const.PAGE_SIZE);
                }
            } else {
                mAdapter.loadMoreEnd(true);
            }
            mRefreshLayout.setEnabled(true);
        }
    }
}
