package com.eaphone.g08android.ui.info;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.mvp.contracts.NewsContracts;
import com.eaphone.g08android.mvp.presenter.InfoReplyPresenter;
import com.eaphone.g08android.utils.EventCode;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;

import butterknife.BindView;

public class InfoReplyActivity extends CoreBaseActivity<InfoReplyPresenter> implements NewsContracts.InfoReplyView, View.OnClickListener {

    private String newsId;
    private String commentId;

    @BindView(R.id.tv_sure)
    TextView mTvSure;

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;

    @BindView(R.id.tv_count)
    TextView mTvCount;

    @BindView(R.id.edt_content)
    EditText mEdtContent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_info_reply;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        newsId = getIntent().getExtras().getString("newsId");
        commentId = getIntent().getExtras().getString("commentId");
        if(TextUtils.isEmpty(commentId)){
            initBackTitle("填写评论").setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id",newsId);
                    startActivity(InfoCommentActivity.class,bundle);
                    InfoReplyActivity.this.finish();
                }
            }).build();
        }else{
            initBackTitle("回复"+getIntent().getExtras().getString("name")).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id",newsId);
                    startActivity(InfoCommentActivity.class,bundle);
                    InfoReplyActivity.this.finish();
                }
            }).build();
        }

        mEdtContent.addTextChangedListener(new TextWatcher() {

            private int selectionStart;
            private int selectionEnd;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                selectionStart = mEdtContent.getSelectionStart();
                selectionEnd = mEdtContent.getSelectionEnd();
                if (temp.length() > 140) {
                    showToast("评论只能输入140个字");
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEdtContent.setText(editable);
                    mEdtContent.setSelection(tempSelection);

                    return;
                }

                mTvCount.setText(temp.length() + "");
            }
        });

        mTvCancel.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                if (TextUtils.isEmpty(mEdtContent.getText())) {
                    showToast("请输入内容");
                    return;
                }
                if (mEdtContent.getText().toString().length() < 2) {
                    showToast("评论不能少于2个字");
                    return;
                }
                mPresenter.infoComment(mEdtContent.getText().toString(), newsId, commentId);

                break;

            case R.id.tv_cancel:
                Bundle bundle = new Bundle();
                bundle.putString("id",newsId);
                startActivity(InfoCommentActivity.class,bundle);
                InfoReplyActivity.this.finish();
                break;
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
    protected boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void getInfoCommentToSomeOne(ResultBase result) {
        if (result.isSuccess()) {
            CoreEvent event = new CoreEvent(EventCode.B);
            EventBusUtils.sendEvent(event);
            Bundle bundle = new Bundle();
            bundle.putString("id",newsId);
            startActivity(InfoCommentActivity.class,bundle);
            InfoReplyActivity.this.finish();
        } else {
            showToast(result.getMessage());
        }
    }


}
