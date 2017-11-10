package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.FamilyPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.widget.CircleImageView;
import com.eaphone.g08android.widget.EmptyView;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;

/**
 * 家庭成员
 */
public class FamilyActivity extends CoreBaseActivity<FamilyPresenter> implements PassportContracts.FamilyView {


    @BindView(R.id.rv_news_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private BaseQuickAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        show(R.string.loading);
        initBackTitle("家庭成员").setRightImage(R.mipmap.icon_add).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Const.SOURCE, Const.SOURCE_FAMILY);
                startActivity(FamilyAddActivity.class, bundle);
            }
        }).build();

        adapter = new BaseQuickAdapter<Family, BaseViewHolder>(R.layout.item_family) {
            @Override
            protected void convert(BaseViewHolder holder, final Family item) {
                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_relation, "关系：" + item.getRelationship());
                holder.setText(R.id.tv_phone, "电话：" + FormatUtil.getSecretPhone(item.getPhone()));
                holder.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FamilyActivity.this, FamilyDetailActivity.class);
                        intent.putExtra("data", item);
                        startActivityForResult(intent, 1);
                    }
                });
                ImageLoader.displayImage(Const.getAvater(item.getUserId()), (CircleImageView) holder.getView(R.id.iv_avatar));
            }
        };

        RecyclerViewHelper.initRecyclerViewV(this, recyclerView, true, adapter);
        refreshLayout.setEnabled(false);

    }

    private View getView() {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setText("暂无家庭成员");
        emptyView.setImageResouce(R.mipmap.ic_no_family);
        emptyView.setVisibility();
        return emptyView.getView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
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
    public void getFamily(ResultBase<List<Family>> result) {
        dismiss();
        if (result.isSuccess()) {
            if (result.getData().size() == 0) {
                adapter.setEmptyView(getView());
            } else
                adapter.setNewData(result.getData());
        } else {
            showToast(result.getMessage());
        }
    }
}
