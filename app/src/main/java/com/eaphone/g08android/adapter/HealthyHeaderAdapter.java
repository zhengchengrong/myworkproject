package com.eaphone.g08android.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Family;
import com.eaphone.g08android.bean.HealthyDataEnity;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.mvp.presenter.HealthyPresenter;
import com.eaphone.g08android.ui.personcenter.FamilyAddActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.PreferencesUtils;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 16:20
 * 修改人：Administrator
 * 修改时间：2017/8/29 16:20
 * 修改备注：
 */
public class HealthyHeaderAdapter extends BaseQuickAdapter<Family, BaseViewHolder> {

    private Activity activity;
    private HealthyPresenter presenter;
    private HealthyDataEnity dataEnity;

    public HealthyHeaderAdapter(@LayoutRes int layoutResId, List<Family> list,
                                Activity activity, HealthyPresenter presenter, HealthyDataEnity dataEnity) {
        super(layoutResId, list);
        this.presenter = presenter;
        this.dataEnity = dataEnity;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Family item) {

        ImageView imageView = (ImageView) helper.getView(R.id.iv_avater);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        LinearLayout ll_item = helper.getView(R.id.ll_item);

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(item.getName().equals("添加成员")){
                    Intent intent = new Intent(activity, FamilyAddActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.SOURCE, Const.SOURCE_FAMILY);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }else{
                    if(item.getName().equals(PreferencesUtils.getSharePreStr(Const.NAME))){
                        PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER);
                        PreferencesUtils.removeSharePreStr(Const.CURRENT_MEMBER_NAME);
                    }else{
                        PreferencesUtils.putSharePre(Const.CURRENT_MEMBER, item.getUserId());
                        PreferencesUtils.putSharePre(Const.CURRENT_MEMBER_NAME, item.getName());
                    }

                    presenter.analysis(dataEnity, LoginUtil.isFamilyMember());
                    notifyDataSetChanged();
                }


            }
        });


        if (LoginUtil.isFamilyMember().equals(item.getUserId())) {
            params.width = 120;
            params.height = 120;
            imageView.setLayoutParams(params);
            textView.setTextColor(activity.getResources().getColor(R.color.bg_presure));
            textView.setText(item.getName());
            textView.setTextSize(10);
            ImageLoader.displayImage(Const.getAvater(item.getUserId()), imageView);
        } else {
            params.width = 110;
            params.height = 110;
            imageView.setLayoutParams(params);

            textView.setText(item.getName());
            textView.setTextSize(8);
            textView.setTextColor(activity.getResources().getColor(R.color.gray_text));

            if ("添加成员".equals(item.getName())) {
                imageView.setImageResource(R.mipmap.ic_add_family);

            } else {
                if (!TextUtils.isEmpty(item.getUserId())) {

                    ImageLoader.displayImage(Const.getAvater(item.getUserId()), imageView);
                } else {
                    imageView.setImageResource(R.mipmap.ic_default);
                }
            }
        }
    }

}
