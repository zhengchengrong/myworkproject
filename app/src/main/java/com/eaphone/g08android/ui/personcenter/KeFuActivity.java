package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.FAQ;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.FAQPresenter;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import java.util.List;

import butterknife.BindView;

//import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
//import com.alibaba.sdk.android.feedback.util.ErrorCode;
//import com.alibaba.sdk.android.feedback.util.FeedbackErrorCallback;


public class KeFuActivity extends CoreBaseActivity<FAQPresenter> implements NewsContracts.FAQView, View.OnClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.tv_question)
    TextView mTvQuestion;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    private BaseQuickAdapter<FAQ, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ke_fu;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("客服中心").build();
        show(R.string.loading);
        mAdapter = new BaseQuickAdapter<FAQ, BaseViewHolder>(R.layout.kefu_item) {
            @Override
            protected void convert(BaseViewHolder helper, final FAQ item) {
                TextView textView = helper.getView(R.id.tv_title);
                textView.setText(item.getQuestion());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getId());
                        startActivity(KeFuDetailActivity.class, bundle);
                    }
                });

            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, mAdapter);

        mTvQuestion.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
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
    public void getFAQ(ResultBase<List<FAQ>> result) {
        dismiss();
        if (result.isSuccess()) {
            mAdapter.setNewData(result.getData());
        } else {

        }
    }

    @Override
    public void getFAQDetail(ResultBase<FAQ> result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_question:
                setFeedBack();
                break;
            case R.id.tv_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tv_phone.getText().toString()));
                startActivity(intent);


                break;
        }
    }

    private void setFeedBack() {
//        /**
//         * 添加自定义的error handler
//         */
//        FeedbackAPI.addErrorCallback(new FeedbackErrorCallback() {
//            @Override
//            public void onError(Context context, String errorMessage, ErrorCode code) {
//                Toast.makeText(context, "ErrMsg is: " + errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        });
//        FeedbackAPI.addLeaveCallback(new Callable() {
//            @Override
//            public Object call() throws Exception {
//                Log.d("DemoApplication", "custom leave callback");
//                return null;
//            }
//        });
//
//        /**
//         * 建议放在此处做初始化
//         */
//
//
//        FeedbackAPI.openFeedbackActivity();
//        /**
//         * 自定义参数演示
//         */
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("loginTime", "登录时间");
//            jsonObject.put("visitPath", "登陆，关于，反馈");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        FeedbackAPI.setAppExtInfo(jsonObject);
//        /**
//         * 以下是设置UI
//         */
//        //设置默认联系方式
//        //沉浸式任务栏，控制台设置为true之后此方法才能生效
//        FeedbackAPI.setTranslucent(false);
//        //设置返回按钮图标
//        FeedbackAPI.setBackIcon(R.drawable.ali_feedback_common_back_btn_bg);
//        //设置标题栏"历史反馈"的字号，需要将控制台中此字号设置为0
//        FeedbackAPI.setHistoryTextSize(14);
//        //设置标题栏高度，单位为像素
////        FeedbackAPI.setTitleBarHeight(100);

    }

}
