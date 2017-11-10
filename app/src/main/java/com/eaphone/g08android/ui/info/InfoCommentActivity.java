package com.eaphone.g08android.ui.info;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.InfoComment;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.InfoCommentPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TextViewUtils;
import com.eaphone.g08android.utils.TimeUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;

import java.util.List;

import butterknife.BindView;


public class InfoCommentActivity extends CoreBaseActivity<InfoCommentPresenter>
        implements NewsContracts.InfoCommentView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;


    @BindView(R.id.ll_reply)
    LinearLayout ll_reply;


    private String newsId = "";

    private BaseQuickAdapter<InfoComment, BaseViewHolder> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.info_comment_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("评论").build();
        show(R.string.loading);
        newsId = getIntent().getExtras().getString("id");
        mPresenter.infoComment(newsId, "1", Const.PAGE_SIZE);
        adapter = new BaseQuickAdapter<InfoComment, BaseViewHolder>(R.layout.info_comment_item) {
            @Override
            protected void convert(BaseViewHolder holder, final InfoComment item) {
                holder.setText(R.id.tv_name, item.getCommentBy().getName());
                holder.setText(R.id.tv_time, TimeUtils.displayTime(item.getCreateTime()));
                ImageLoader.displayImage(Const.getAvater(item.getCommentBy().getId()), (ImageView) holder.getView(R.id.iv_icon));
                TextView tv_data = (TextView) holder.getView(R.id.tv_data);
                TextView tv_zan = (TextView) holder.getView(R.id.tv_zan);
                TextView tv_reply = (TextView) holder.getView(R.id.tv_reply);
                tv_data.setText(item.getCommentContent());
                if (item.getParentNewsComment() != null) {

                    String name2 = item.getCommentBy().getName();
                    String data1 = "回复" + "@";
                    String name = item.getParentNewsComment().getCommentBy().getName();
                    String last = "，"
                            + item.getParentNewsComment().getCommentContent();
//                    String data = "回复@" + name2 + "：" + item.getCommentContent();
                    SpannableString styledText = new SpannableString(data1 + name + last);
                    //设置有需要修改的样式 //包尾不包头，比如0~3，其中修改了样式不会包括0，第一个位置是1开始
                    styledText.setSpan(new TextAppearanceSpan(mContext, R.style.data_change_color1), data1.length() - 1, data1.length() + name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_reply.setText(styledText, TextView.BufferType.SPANNABLE);
                } else {
                    tv_reply.setVisibility(View.GONE);
                }

                if (item.isZan()) {
                    TextViewUtils.setDrawableRight(mContext, R.mipmap.ic_zan_s, tv_zan);
                } else {
                    TextViewUtils.setDrawableRight(mContext, R.mipmap.ic_zan_n, tv_zan);
                }

                tv_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.isZan()) {

                        } else {
                            mPresenter.zan(newsId, item.getId());
                            item.setZan(true);
                            int count = item.getZanCount();
                            count++;
                            item.setZanCount(count);
                            notifyDataSetChanged();
                        }
                    }
                });

                tv_zan.setText(item.getZanCount() + "");
                holder.getView(R.id.iv_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("newsId", newsId);
                        bundle.putString("commentId", item.getId());
                        bundle.putString("name", item.getCommentBy().getName());
                        startActivity(InfoReplyActivity.class, bundle);
                        InfoCommentActivity.this.finish();

                    }
                });
            }
        };

        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter/*, new AlphaInAnimationAdapter(animAdapter)*/);
        ll_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("newsId", newsId);
                bundle.putString("commentId", "");
                startActivity(InfoReplyActivity.class, bundle);
            }
        });

        swipe_refresh.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyclerView);
    }


    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        isErr = true;
        showToast(msg);
    }

    @Override
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void receiverEvent(CoreEvent event) {
        super.receiverEvent(event);
        if (event != null && event.getCode() == EventCode.B) {
            pageIndex = 1;
            mPresenter.infoComment(newsId, "1", Const.PAGE_SIZE);
        }
    }

    @Override
    public void getInfoComment(ResultBase<List<InfoComment>> result) {
        dismiss();
        if (result.isSuccess()) {
            mCurrentCounter = result.getData().size();
            adapter.setNewData(result.getData());
        } else {
            showToast(result.getMessage());
        }

        adapter.setEnableLoadMore(true);
    }

    @Override
    public void getInfoMoreComment(ResultBase<List<InfoComment>> result) {
        if (result.isSuccess()) {
            mCurrentCounter = result.getData().size();
            adapter.addData(result.getData());
        } else {
            showToast(result.getMessage());
        }
        adapter.loadMoreComplete();
    }


    @Override
    public void getZan(ResultBase result) {
        if (result.isSuccess()) {
        } else {
            showToast(result.getMessage());
        }
    }

    private int mCurrentCounter = 0;
    private boolean isErr;
    private int pageIndex = 1;

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex = 1;
                mPresenter.infoComment(newsId, "1", Const.PAGE_SIZE);
                swipe_refresh.setRefreshing(false);
            }
        }, Const.DELAYMILLIS);
    }

    @Override
    public void onLoadMoreRequested() {
        swipe_refresh.setEnabled(false);
        if (mCurrentCounter < 10) {
            adapter.loadMoreEnd(true);
            swipe_refresh.setEnabled(true);
        } else {
            if (mCurrentCounter >= 10) {
                if (isErr) {
                    isErr = false;
                    adapter.loadMoreFail();
                } else {
                    adapter.loadMoreEnd(false);
                    pageIndex++;
                    mPresenter.infoMoreComment(newsId, String.valueOf(pageIndex), Const.PAGE_SIZE);
                }
            } else {
                adapter.loadMoreEnd(true);
            }
            swipe_refresh.setEnabled(true);
        }
    }

}
