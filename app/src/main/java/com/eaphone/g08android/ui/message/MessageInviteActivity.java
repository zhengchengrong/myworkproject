package com.eaphone.g08android.ui.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.InviteEnity;
import com.eaphone.g08android.bean.MessageFirstLevel;
import com.eaphone.g08android.bean.ReadStatusEntity;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.InvitePresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;

public class MessageInviteActivity extends CoreBaseActivity<InvitePresenter>
        implements PassportContracts.InviteView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_news_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private BaseQuickAdapter<MessageFirstLevel, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("成员消息").build();
        initAdapter();
        mPresenter.loadInvite("chengyuanxiaoxi", "1", Const.PAGE_SIZE);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        RecyclerViewHelper.initRecyclerViewV(mContext, mRecyclerView, true, mAdapter);
        show(R.string.loading);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        isErr = true;
        dismiss();
        showToast(msg);
    }

    @Override
    public void getInvite(ResultBase<List<MessageFirstLevel>> result) {

        dismiss();

        if (result.isSuccess()) {
            mCurrentCounter = result.getData().size();
            mAdapter.setNewData(result.getData());
        } else {
            showToast(result.getMessage());
        }

        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void getMoreInvite(ResultBase<List<MessageFirstLevel>> result) {
        if (result.isSuccess()) {
            mCurrentCounter = result.getData().size();
            mAdapter.addData(result.getData());
        } else {
            showToast(result.getMessage());
        }
        mAdapter.loadMoreComplete();
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void getHandler(ResultBase resultBase) {
        dismiss();
        if (resultBase.isSuccess()) {
//            MessageInviteActivity.this.finish();
        } else {
            showToast(resultBase.getMessage());
        }
    }

    @Override
    public void getPatchMessage(ResultBase<MessageFirstLevel> resultBase) {

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
                mPresenter.loadInvite("chengyuanxiaoxi","1",  Const.PAGE_SIZE);
                mRefreshLayout.setRefreshing(false);
            }
        }, Const.DELAYMILLIS);
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnabled(false);
        if (mCurrentCounter < 10) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= 10) {
                if (isErr) {
                    isErr = false;
                    mAdapter.loadMoreFail();
                } else {
                    mAdapter.loadMoreEnd(false);
                    pageIndex++;
                    mPresenter.loadMoreInvite("chengyuanxiaoxi", String.valueOf(pageIndex) ,Const.PAGE_SIZE);
                }
            } else {
                mAdapter.loadMoreEnd(true);
            }
            mRefreshLayout.setEnabled(true);
        }
    }


    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<MessageFirstLevel, BaseViewHolder>(R.layout.message_invite_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageFirstLevel item) {
                helper.setText(R.id.tv_time, TimeUtils.formatDisplayTime(item.getCreateTime(),TimeUtils.TIME_TYPE_1));
                helper.setText(R.id.tv_data, item.getText());
                ImageLoader.displayImage(item.getImage(), (ImageView) helper.getView(R.id.iv_icon));
                TextView tvAgree = helper.getView(R.id.tv_agree);
                TextView tvDisagree = helper.getView(R.id.tv_disagree);
                RelativeLayout rl_status = helper.getView(R.id.rl_status);
                if (TextUtils.isEmpty(item.getUrl())) {
                    rl_status.setVisibility(View.GONE);
                } else {
                    if ("accepted".equals(item.getReadStatus())) {
                        tvAgree.setText("已同意");
                        tvDisagree.setVisibility(View.GONE);
                        tvAgree.setTextColor(getResources().getColor(R.color.gray));
                        tvAgree.setBackgroundColor(getResources().getColor(R.color.white));
                        rl_status.setVisibility(View.VISIBLE);
                    } else if ("expired".equals(item.getReadStatus())) {
                        tvAgree.setText("已过期");
                        tvDisagree.setVisibility(View.GONE);
                        tvAgree.setTextColor(getResources().getColor(R.color.gray));
                        tvAgree.setBackgroundColor(getResources().getColor(R.color.white));
                        rl_status.setVisibility(View.VISIBLE);
                    } else if ("refused".equals(item.getReadStatus())) {
                        tvAgree.setText("未同意");
                        tvDisagree.setVisibility(View.GONE);
                        tvAgree.setTextColor(getResources().getColor(R.color.gray));
                        tvAgree.setBackgroundColor(getResources().getColor(R.color.white));
                        rl_status.setVisibility(View.VISIBLE);
                    } else if ("deleted".equals(item.getReadStatus())) {
                        tvAgree.setVisibility(View.GONE);
                        tvDisagree.setVisibility(View.GONE);
                        tvAgree.setTextColor(getResources().getColor(R.color.gray));
                    } else if ("read".equals(item.getReadStatus())) {
                        rl_status.setVisibility(View.GONE);
                    } else {
                        rl_status.setVisibility(View.VISIBLE);
                        tvAgree.setText("同意");
                        tvAgree.setTextColor(getResources().getColor(R.color.white));
                        tvDisagree.setTextColor(getResources().getColor(R.color.white));
                        tvDisagree.setText("不同意");
                        tvDisagree.setVisibility(View.VISIBLE);
                        tvAgree.setBackgroundResource(R.drawable.app_btn_bg);
                        tvDisagree.setBackgroundResource(R.drawable.app_btn_bg);
                        Uri uri = Uri.parse(item.getUrl());
                        final String id = uri.getQueryParameter("id");
                        tvAgree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                show(R.string.loading_up);
                                mPresenter.loadHandler(id, new InviteEnity("accepted"));
                                item.setReadStatus("accepted");
                                mPresenter.loadPatchMessage(item.getId(), new ReadStatusEntity("accepted"));
                                notifyDataSetChanged();
                            }
                        });

                        tvDisagree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                show(R.string.loading_up);
                                mPresenter.loadHandler(id, new InviteEnity("refused"));
                                item.setReadStatus("refused");
                                mPresenter.loadPatchMessage(item.getId(), new ReadStatusEntity("refused"));
                                notifyDataSetChanged();
                            }
                        });
                    }


                }
            }
        };
    }

}
