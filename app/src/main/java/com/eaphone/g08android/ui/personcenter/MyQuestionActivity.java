package com.eaphone.g08android.ui.personcenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;

import butterknife.BindView;

public class MyQuestionActivity extends CoreBaseActivity {

    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout layout;

    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("我的反馈").build();

        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.my_question_item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        };

        RecyclerViewHelper.initRecyclerViewV(this, recyclerView, true, adapter);
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("您还没留言反馈");
        emptyView.setImageResouce(R.mipmap.ic_no_answer);
        adapter.setEmptyView(emptyView.getView());
    }
}
