package com.hpw.mvpframe.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hpw.mvpframe.R;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;
import com.hpw.mvpframe.utils.StatusBarUtil;
import com.hpw.mvpframe.utils.TUtil;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.hpw.mvpframe.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class CoreBaseFragment<T extends CoreBasePresenter> extends SupportFragment {
    protected String TAG;
    protected OnBackToFirstListener _mBackToFirstListener;

    public T mPresenter;
    protected Context mContext;
    protected Activity mActivity;
    Unbinder binder;


    private ProgressDialog loadingDialog;

    public void show(int resStringId) {
        if (loadingDialog == null){
            loadingDialog = ProgressDialog.show(mActivity, null, getString(resStringId));
        }
        else loadingDialog.show();

    }

    public void dismiss() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), null);
        }
    }
    protected boolean isRegistEventBus() {//需要使用eventbus的界面重写并返回true
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultEventRegist(CoreEvent event) {
        if (event != null)
            receiverEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickEventRegist(CoreEvent event) {
        if (event != null)
            receiverStickEvent(event);
    }

    public void receiverEvent(CoreEvent event) {

    }

    public void receiverStickEvent(CoreEvent event) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        setStatusBarColor();
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        binder = ButterKnife.bind(this, view);
        mPresenter = TUtil.getT(this, 0);
//        mModel = TUtil.getT(this, 1);
        initUI(view, savedInstanceState);
        if (this instanceof CoreBaseView) mPresenter.attachVM(this);
        getBundle(getArguments());
        initData();

        if (isRegistEventBus())
            EventBusUtils.regist(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binder != null) binder.unbind();
        if(isRegistEventBus())
            EventBusUtils.unRegist(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = _mActivity.getFragmentAnimator();
        fragmentAnimator.setEnter(0);
        fragmentAnimator.setExit(0);
        return fragmentAnimator;
    }

    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initTitleBar(String title) {
        return new TitleBuilder(mActivity)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> {
                    _mActivity.onBackPressed();
                });
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(getActivity(), tarActivity);
        intent.putExtras(options);
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivity(intent);
    }

    public void showToast(String msg) {
        dismiss();
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {

    }
}
