package com.eaphone.g08android.ui.info;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Info;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.InfoTypePresenter;
import com.eaphone.g08android.ui.live.LiveHealthDetailActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.EventCode;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/26 10:58
 * 修改人：Administrator
 * 修改时间：2017/8/26 10:58
 * 修改备注：
 */
public class InfoTypeFragment extends CoreBaseFragment<InfoTypePresenter>
        implements NewsContracts.InfoTypeView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    BaseQuickAdapter adapter;

    private String newsTypeId;//类型id

    private int mCurrentCounter = 0;
    private boolean isErr;

    @Override
    public int getLayoutId() {
        return R.layout.info_type_fragment;
    }

    @Override
    public View getLayoutView() {
        return recyclerView;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        show(R.string.loading);
        newsTypeId = getArguments().getString("newsTypeId");

        adapter = new BaseQuickAdapter<Info, BaseViewHolder>(R.layout.info_item) {


            @Override
            protected void convert(BaseViewHolder holder, final Info item) {
                holder.setText(R.id.tv_data, item.getTitle());
                if (!TextUtils.isEmpty(item.getCreateTime())) {
                    holder.setText(R.id.tv_time, TimeUtils.displayTime(item.getCreateTime()));
                }
                if (item.getComments() > 99) {
                    holder.setText(R.id.tv_comment, "99+评论");
                } else {
                    holder.setText(R.id.tv_comment, item.getComments() + "评论");
                }
                ImageLoader.displayImage(item.getImage(), (ImageView) holder.getView(R.id.iv_icon));
                holder.getView(R.id.linear_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getId());
                        bundle.putString("newsTypeId", newsTypeId);
                        startActivity(LiveHealthDetailActivity.class, bundle);
                    }
                });
            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter/*, new AlphaInAnimationAdapter(animAdapter)*/);
        mPresenter.info(newsTypeId);
        swipe_refresh.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this,recyclerView);
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
            mPresenter.info(newsTypeId);
        }
    }

    private View getViewEmpty() {
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setText("暂无资讯");
        emptyView.setImageResouce(R.mipmap.c46);
        return emptyView.getView();
    }

    @Override
    public void getInfo(ResultBase<List<Info>> result) {
        dismiss();
        if (result.isSuccess()) {
            mCurrentCounter = result.getData().size();
            adapter.setNewData(result.getData());
        } else {
            showToast(result.getMessage());
        }

        swipe_refresh.setEnabled(true);
    }

    @Override
    public void getInfoMore(ResultBase<List<Info>> result) {
        if (result.isSuccess()) {

            if(result.getData().size() == 0){
                adapter.setEmptyView(getViewEmpty());
                return;
            }

            mCurrentCounter = result.getData().size();
            adapter.addData(result.getData());

        } else {
            showToast(result.getMessage());
        }
        adapter.loadMoreComplete();
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.info(newsTypeId);
                swipe_refresh.setRefreshing(false);
                adapter.setEnableLoadMore(true);
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
                    mPresenter.infoMore(newsTypeId);
                }
            } else {
                adapter.loadMoreEnd(true);
            }
            swipe_refresh.setEnabled(true);
        }
    }
}