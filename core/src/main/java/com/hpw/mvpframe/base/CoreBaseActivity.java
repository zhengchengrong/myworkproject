package com.hpw.mvpframe.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hpw.mvpframe.AppManager;
import com.hpw.mvpframe.R;
import com.hpw.mvpframe.data.entity.CoreEvent;
import com.hpw.mvpframe.utils.EventBusUtils;
import com.hpw.mvpframe.utils.PermissionUtil;
import com.hpw.mvpframe.utils.StatusBarUtil;
import com.hpw.mvpframe.utils.TUtil;
import com.hpw.mvpframe.utils.ThemeUtil;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.hpw.mvpframe.utils.ToastUtils;
import com.hpw.mvpframe.utils.UmengUtil;
import com.hpw.mvpframe.widget.SwipeBackLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


public abstract class CoreBaseActivity<T extends CoreBasePresenter> extends SupportActivity {

    protected String TAG;

    public T mPresenter;
//    public E mModel;
    protected Context mContext;
    Unbinder binder;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    private boolean isOpen = true;

    private ProgressDialog loadingDialog;

    public void show(int resStringId) {
        if (loadingDialog == null){
            loadingDialog = ProgressDialog.show(this, null, getString(resStringId));
            loadingDialog.setCanceledOnTouchOutside(true);
        }
        else loadingDialog.show();

    }

    public void dismiss() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
//        setStatusBarColor();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
//        setTheme(ThemeUtil.themeArr[SpUtil.getThemeIndex(this)][
//                SpUtil.getNightModel(this) ? 1 : 0]);
        setTheme(ThemeUtil.themeArr[6][0]);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
//        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView) mPresenter.attachVM(this);
        this.initView(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (isRegistEventBus())
            EventBusUtils.regist(this);
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
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (binder != null) binder.unbind();
        if (mPresenter != null) mPresenter.detachVM();
        if (isRegistEventBus()) {
            EventBusUtils.unRegist(this);
        }


    }

    /**
     * 统一样式的dialog
     *
     * @param title
     * @param message
     */
    public AlertDialog.Builder initAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengUtil.onResume(this);
        UmengUtil.onPageStart(this.getClass().getName());

    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengUtil.onPause(this);
        UmengUtil.onPageEnd(this.getClass().getName());
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isOpen()) {
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
//            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            swipeBackLayout.addView(view);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }


    public void onCreateCustomToolBar(Toolbar toolbar) {
        //插入toolbar视图的内容的起始点与结束点
        toolbar.setContentInsetsRelative(0, 0);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置无动画
//        return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);
        // 默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
    }

    public void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> {
                    finish();
                });
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtras(options);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> tarActivity, Bundle options, int requestCode) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtras(options);
        startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void showToast(String msg) {
        dismiss();
        Log.e(TAG, "showToast  " + msg);
        if (!TextUtils.isEmpty(msg)) {
            if (msg.equals("invalid token")) {
                ToastUtils.showToast(this, "检测到您的登录已过期，请重新登录", Toast.LENGTH_SHORT);
                sendBroadcast(new Intent("RELOAD_LOGIN"));
            } else
                ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
        } else {

        }
    }

    public void showLog(String msg) {
    }

    protected int REQUEST_CODE_ASK_PERMISSION = 0x00099;

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_ASK_PERMISSION = requestCode;
        if (PermissionUtil.checkPermissions(permissions)) {
            permissionGrant(true, REQUEST_CODE_ASK_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_ASK_PERMISSION);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSION) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                permissionGrant(true, REQUEST_CODE_ASK_PERMISSION);
            } else {
                permissionGrant(false, REQUEST_CODE_ASK_PERMISSION);
                showTipsDialog();
            }
        }
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {

        AlertDialog dialog = initAlertDialog("提示信息", "当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行授权。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        startAppSettings();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 获取权限是否成功
     *
     * @param isGranted   true:成功权限成功 false:获取权限失败
     * @param requestCode 权限请求码
     */
    @CallSuper
    public void permissionGrant(boolean isGranted, int requestCode) {
        Log.i(TAG, "获取权限是否成功——>" + "isGranted：" + isGranted + " requestCode：" + requestCode);
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

}
