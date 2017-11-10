package com.eaphone.g08android.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.ui.personcenter.FamilyAddActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.widget.CircleImageView;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/7 11:08
 * 修改人：Administrator
 * 修改时间：2017/9/7 11:08
 * 修改备注：
 */
public class DeviceBindUserAdapter extends RecyclerView.Adapter<DeviceBindUserAdapter.UserViewHolder> {

    private Activity activity;
    private String name;
    private String userId;
    private List<Family> data;

    public DeviceBindUserAdapter(@Nullable List<Family> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }


    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bind_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getName());
        if (data.get(position).getName().equals("添加成员")) {
            holder.iv_avater.setImageResource(R.mipmap.ic_add_family);
        } else {
            ImageLoader.displayImage(Const.getAvater(data.get(position).getUserId()), holder.iv_avater);
        }

        if(data.get(position).isCheck()){
            holder.iv_check.setVisibility(View.VISIBLE);
        }else{
            holder.iv_check.setVisibility(View.GONE);
        }

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = "";
                userId = "";
                if (data.get(position).getName().equals("添加成员")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.SOURCE, Const.SOURCE_FAMILY_MEMBER);
                    Intent intent = new Intent(activity, FamilyAddActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                } else
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            if (data.get(position).isCheck()) {
                                data.get(position).setCheck(false);

                                holder.iv_check.setVisibility(View.GONE);
                                name = "";
                                userId = "";

                            } else {
                                data.get(position).setCheck(true);
                                holder.iv_check.setVisibility(View.VISIBLE);
                                name = data.get(position).getName();
                                userId = data.get(position).getUserId();

                            }
                        } else {
                            data.get(i).setCheck(false);
                        }
                    }
                notifyDataSetChanged();
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout ll_item;
        private CircleImageView iv_avater;
        private TextView tv_name;
        private ImageView iv_check;

        public UserViewHolder(View itemView) {
            super(itemView);
            ll_item = (RelativeLayout) itemView.findViewById(R.id.ll_item);
            iv_avater = (CircleImageView) itemView.findViewById(R.id.iv_avater);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_check = (ImageView) itemView.findViewById(R.id.iv_check);
        }
    }
}
