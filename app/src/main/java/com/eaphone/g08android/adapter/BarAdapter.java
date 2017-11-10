package com.eaphone.g08android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.presenter.HealthyPresenter;
import com.eaphone.g08android.ui.personcenter.FamilyAddActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.widget.AutoLocateHorizontalView;
import com.eaphone.g08android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙衣 on 17-2-20.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> implements AutoLocateHorizontalView.IAutoLocateHorizontalView {
    private List<Family> list;
    private Context context;
    private View itemView;

    private HealthyPresenter presenter;
    private HealthyDataEnity enity;

    public BarAdapter(Context context,  HealthyPresenter presenter, HealthyDataEnity dataEnity) {
        this.context = context;
        this.enity = dataEnity;
        this.presenter = presenter;
    }


    public void addNewData(List<Family> listData) {
        this.list = listData == null ? new ArrayList<Family>() : listData;
        notifyDataSetChanged();
    }

    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_bar, parent, false);
        this.itemView = itemView;
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(BarAdapter.ViewHolder holder, int position) {
        Family family = list.get(position);
        if (!TextUtils.isEmpty(family.getName()))
            holder.tv_name.setText(family.getName());
        if (position == list.size() - 1) {
            holder.iv_avater.setImageResource(R.mipmap.ic_add_family);
        } else {
            if (!TextUtils.isEmpty(family.getUserId())) {
                ImageLoader.displayImage(Const.getAvater(family.getUserId()), holder.iv_avater);
            } else {
                holder.iv_avater.setImageResource(R.mipmap.ic_default);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public View getItemView() {
        return itemView;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        BarAdapter.ViewHolder holder1 = (BarAdapter.ViewHolder) holder;
        ViewGroup.LayoutParams params = holder1.iv_avater.getLayoutParams();
        if (pos == list.size() - 1) {

            if (isSelected) {
                Intent intent = new Intent(context, FamilyAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Const.SOURCE, Const.SOURCE_FAMILY);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

            params.height = itemWidth - 90;
            params.width = itemWidth - 90;

            holder1.tv_name.setTextColor(context.getResources().getColor(R.color.gray_text));
            holder1.iv_avater.setLayoutParams(params);
            holder1.tv_name.setTextSize(10);

        } else {

            if (isSelected) {
                params.height = itemWidth - 70;
                params.width = itemWidth - 70;
                holder1.iv_avater.setLayoutParams(params);
                holder1.tv_name.setTextSize(12);
                holder1.tv_name.setTextColor(context.getResources().getColor(R.color.main_color));
                if (list.get(pos).getName().equals("我")) {
                    PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER);
                    PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER_NAME);
                } else {
                    PreferencesUtils.putSharePre(Const.CURRENT_MEMBER, list.get(pos).getUserId());
                    PreferencesUtils.putSharePre(Const.CURRENT_MEMBER_NAME, list.get(pos).getName());
                }
                presenter.analysis(enity, LoginUtil.isFamilyMember());
            } else {
                params.height = itemWidth - 90;
                params.width = itemWidth - 90;

                holder1.tv_name.setTextColor(context.getResources().getColor(R.color.gray_text));
                holder1.iv_avater.setLayoutParams(params);
                holder1.tv_name.setTextSize(10);

            }
        }


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView iv_avater;
        public TextView tv_name;

        public LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_avater = (CircleImageView) itemView.findViewById(R.id.iv_avater);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
