package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ZhiBoGroupItemBean;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.eaphone.g08android.mvp.presenter.LiveZhiBoGroupPresenter;
import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.ArrayList;


/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/16 14:04
 * 修改人：Administrator
 * 修改时间：2017/11/16 14:04
 * 修改备注：
 */
public class HealthZhiBoGroupActivity extends CoreBaseActivity<LiveZhiBoGroupPresenter> implements LiveContracts.LiveZhiBoGroupView,View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_refresh;
    BaseQuickAdapter adapter;
    private int mCurrentCounter = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_heath_group_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle(LiveConstats.GROUP);

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        adapter = new BaseQuickAdapter<ZhiBoGroupItemBean, BaseViewHolder>(R.layout.zhibo_group_item) {
            @Override
            protected void convert(final BaseViewHolder holder, final ZhiBoGroupItemBean item) {
                ImageView imageView = holder.getView(R.id.iv_group_item);
                ImageLoader.displayImageOther(item.getBanner(),imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HealthZhiBoGroupActivity.this, WebActivity.class);
                        intent.putExtra("url",item.getUrl());
                        intent.putExtra("title",item.getTitle());
                        startActivity(intent);
                    }
                });
            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter);
        mPresenter.info();
        adapter.setOnLoadMoreListener(this,recyclerView);
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }
    @Override
    public void onLoadMoreRequested() {

    }
    @Override
    public void getInfo(ResultBase<ArrayList<ZhiBoGroupItemBean>> bean) {
        dismiss();
        if (bean.isSuccess()) {
            mCurrentCounter = bean.getData().size();
            adapter.setNewData(bean.getData());
        } else {
            showToast(bean.getMessage());
        }

        swipe_refresh.setEnabled(true);
    }

    @Override
    public void getInfoMore(ResultBase<ArrayList<ZhiBoGroupItemBean>> bean) {

    }

    @Override
    public void onRefresh() {
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
}