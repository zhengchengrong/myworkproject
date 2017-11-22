package com.eaphone.g08android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.eaphone.g08android.bean.WXPayBean;
import com.eaphone.g08android.wxapi.WXPay;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class WebUtil2 {

    private WebView mWebView;
    private Activity mActivity;

    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;

    public WebUtil2() {
    }

    public WebUtil2(final Activity activity, WebView webView, String url) {
        setAddHtml5(activity, webView, url);
    }


    //    加载html5
    public void setAddHtml5(final Activity activity, WebView webView, String url) {
        mWebView = webView;
        mActivity = activity;
//        mHandler = new Handler();
        //将屏幕设置为全屏
//        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
//        让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
//        支持javaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setSupportZoom(true);

        webSettings.setDatabaseEnabled(false);

//        缓存html5小游戏
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小，我设的是8M
//        String appCacheDir = activity.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
//        webSettings.setAppCachePath(appCacheDir);
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        如果需要存储一些简单的用key/value对即可解决的数据，DOM Storage是非常完美的方案。根据作用范围的不同，有Session Storage和Local Storage两种，分别用于会话级别的存储（页面关闭即消失）和本地化存储（除非主动删除，否则数据永远不会过期）。
//       防止黑屏、加载不了, 此处很重要，必须要
        webSettings.setDomStorageEnabled(true);
//        切换到浏览器
        webView.setWebChromeClient(new WebChromeClient() {
            //配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }


            //扩充缓存的容量
            @Override
            public void onReachedMaxAppCacheSize(long spaceNeeded,
                                                 long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(spaceNeeded * 2);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setMessage(message);
                dialog.setButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        result.confirm();
                    }
                });
                dialog.setButton2("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });
                dialog.show();
                return true;

            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setMessage(message);
                dialog.setButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        result.confirm();
                    }
                });
                dialog.setButton2("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setMessage(message);
                dialog.setButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        result.confirm();
                    }
                });
                dialog.show();
                return true;
            }

            //For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
            }

            // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                activity.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FCR);
            }

            //For Android 4.1+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
            }

            //For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                activity.startActivityForResult(chooserIntent, FCR);
                return true;
            }
        });
        mWebView.setHapticFeedbackEnabled(false);
//        用于与页面的交互
       /* mWebView.addJavascriptInterface(new Object() {
            @SuppressWarnings("unused")
            public void oneClick(final String locX, final String locY) {//此处的参数可传入作为js参数
                mHandler.post(new Runnable() {
                    public void run() {
                        mWebView.loadUrl("javascript:shows(" + locX + "," + locY + ")");
                    }
                });
            }
        }, "demo");*/
//        设置web视图
        webView.setWebViewClient(new webViewClient());
        //加载需要显示的网页
        webView.loadUrl(url);
//        activity.setContentView(webView);
    }

    //图片返回js的时候的回调方法
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    // Create an image file
    private static File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        private ProgressDialog loadingBar;
//
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////           支持选择拓展浏览器
//            if ("www.example.com".equals(Uri.parse(url).getHost())) {
//                return false;
//            }
//            if (url.contains("http") || url.contains(("https"))) {
//                view.loadUrl(url);
//            } else if(url.contains("alipays://platformapi")){
//                boolean visit = checkAliPayInstalled(mActivity);
//                if(visit){
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    mActivity.startActivity(intent);
//                }
//            }
//
//            return true;
//        }
//
@Override
public boolean shouldOverrideUrlLoading(final WebView view, String url) {
    LogUtils.debug("---------------------"+url);
    if (!(url.startsWith("http") || url.startsWith("https"))) {
        return true;
    }


    // 微信支付
    if(url.contains("Sign=WXPay")){
        Map<String, String> ss = StringUtils.urlSplit(url);
        WXPayBean wxPayBean = new WXPayBean();
        wxPayBean.appid = ss.get("appid");
        wxPayBean.timestamp = ss.get("timestamp");
        wxPayBean.sign = ss.get("sign");
        wxPayBean.partnerid = ss.get("partnerid");
        wxPayBean.noncestr = ss.get("noncestr");
        wxPayBean.prepayid = ss.get("prepayid");
        wxPayBean.pack = "Sign=WXPay";
        PreferencesUtils.putSharePre("out_trade_no",ss.get("out_trade_no"));
        WXPay.UseWXPay(mActivity,wxPayBean);
       return true;
    }

    /**
     * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
     */
    final PayTask task = new PayTask(mActivity);
    boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
        @Override
        public void onPayResult(final H5PayResultModel result) {
            final String url=result.getReturnUrl();
            LogUtils.debug(result.getResultCode()+":"+url);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(url == null){
                            mWebView.goBack();
                        }else {
                            view.loadUrl(url);
                        }
                    }
                });

        }
    });
    /**
     * 判断是否成功拦截
     * 若成功拦截，则无需继续加载该URL；否则继续加载
     */
    if(!isIntercepted)
        view.loadUrl(url);
    return true;
}
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if ("www.example.com".equals(request.getUrl().getHost())) {
//                    return false;
//                }
//
//                if (request.getUrl().toString().contains("http")) {
//                    view.loadUrl(request.getUrl().toString());
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
//                    mActivity.startActivity(intent);
//                }
//            }
//
//
//            return true;
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (loadingBar != null) {
                loadingBar.show();
            } else {
                loadingBar = ProgressDialog.show(mActivity, null, "正在加载…");
            }
            loadingBar.setCancelable(true);
        }

        /*
               * 可以看出我们对系统API在19以上的版本作了兼容。因为4.4以上系统在onPageFinished时再恢复图片加载时,
               * 如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
               * */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
            if (loadingBar != null)
                if (loadingBar.isShowing()) {
                    loadingBar.dismiss();
                }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            无法访问到网页
            Toast.makeText(mActivity, "加载出错！", Toast.LENGTH_LONG).show();
            final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage(description);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    //封装的返回方法
    public boolean isCanGoBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        } else {
            mActivity.finish();
            return false;
        }
    }

    //    加载网络视频
    public void setAddVideo(Activity activity, WebView webView, String url) {
        mWebView = webView;
        mActivity = activity;
        webView.loadUrl(url);
        webView.setInitialScale(50);
        WebSettings setting = webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setJavaScriptEnabled(true); //支持 JavaScript
        setting.setPluginState(WebSettings.PluginState.ON); //支持插件,例如就像flash插件。
        setting.setSupportZoom(true); //变焦控制网络 (You don't need this if ROM supports Multi-Touch
        setting.setBuiltInZoomControls(true); //如果由ROM实现多点触控
        webView.setWebViewClient(new webViewClient());
        //加载需要显示的网页
        activity.setContentView(webView);

    }

    /**
     * @param title      判断的字段
     * @param title_name 拼接的名称
     * @return
     */
    public static String getWebTitle(String title, String title_name) {
        String webTitle = title;
        switch (title) {
            case "医生名片":
                webTitle = title_name;
                break;
        }

        return webTitle;
    }

    //判断是否安装支付宝app
    public  boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }
}
