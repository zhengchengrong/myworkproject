package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ZhiboInfo;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.eaphone.g08android.mvp.presenter.LiveZhiBoPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;


/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/10 14:43
 * 修改人：Administrator
 * 修改时间：2017/11/10 14:43
 * 修改备注：
 */
public class HealthZhiBoActivity extends CoreBaseActivity<LiveZhiBoPresenter> implements LiveContracts.LiveZhiBoView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    BaseQuickAdapter adapter;
    private boolean isErr;
    private int mCurrentCounter = 0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_zhibo_activity;
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle(LiveConstats.TITLE);
      //  show(R.string.loading);
        adapter = new BaseQuickAdapter<ZhiboInfo, BaseViewHolder>(R.layout.zhibo_item) {
            @Override
            protected void convert(BaseViewHolder holder, final ZhiboInfo item) {
                holder.setText(R.id.tv_zhibo_title,item.getTitle());
                holder.setText(R.id.tv_zhibo_athuo,item.getDescription());
                holder.setText(R.id.tv_zhibo_num,item.getCreateTime());
                holder.getView(R.id.rl_zhibo_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(HealthZhiBoActivity.this,"我被点击了");
                        startActivity(HealthZhiBoDetailActivity.class);

                    }
                });
                ImageLoader.displayImageOther(item.getImage(), (ImageView) holder.getView(R.id.iv_zhibo_pic));


            }
        };
        adapter.setEnableLoadMore(false);
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter);
        mPresenter.info();
        swipe_refresh.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this,recyclerView);
    }


    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(String msg) {
        isErr = true;
        showToast(msg);
    }



    // 刷新界面
    @Override
    public void onRefresh() {
        mPresenter.info();
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.info();
                swipe_refresh.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, Const.DELAYMILLIS);
    }
    // 加载更多
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
                    mPresenter.infoMore();
                }
            } else {
                adapter.loadMoreEnd(true);
            }
            swipe_refresh.setEnabled(true);
        }
    }

    @Override
    public void getInfo(ResultBase<List<ZhiboInfo>> bean) {
        if (bean.isSuccess()) {
            mCurrentCounter = bean.getData().size();
            adapter.setNewData(bean.getData());
        } else {
            showToast(bean.getMessage());
        }
      swipe_refresh.setEnabled(true);

    }

    @Override
    public void getInfoMore(ResultBase<List<ZhiboInfo>> bean) {

    }
}
