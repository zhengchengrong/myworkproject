package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ZhiBoDetailItemBean;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.eaphone.g08android.mvp.presenter.LiveZhiBoDetailPresenter;
import com.eaphone.g08android.service.PlayerService;
import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.DensityUtil;
import com.eaphone.g08android.utils.FileUtils;
import com.eaphone.g08android.utils.NetUtil;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.TimeUtils;
import com.eaphone.g08android.utils.util.Player;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/16 14:04
 * 修改人：Administrator
 * 修改时间：2017/11/16 14:04
 * 修改备注：
 */
public class HealthZhiBoDetailActivity2 extends CoreBaseActivity<LiveZhiBoDetailPresenter> implements LiveContracts.LiveZhiBoDetailView,View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private Banner banner;
    private ArrayList<String> images = new ArrayList<String>();

    private TextView tv_zhibo_detail;
    private TextView tv_zhibo_detail_title;

    // 倒序
    private TextView tv_zhibo_detail_sort;

    private Player player;
    public  static SeekBar musicProgress;
    private ProgressBar progressBar;
    private static final int PROCESSING = 1;
    private static final int FAILURE = -1;

    // 设置本地或者网络播放
    public static final int ON_LOC = 0;
    public static final int ON_LINE = 1;

    private boolean isBackgroudPlay = false;

    private boolean isShowDetai=false;

    private boolean isSort = false;
    private boolean isWifi =false;

    /* 定义在播放列表中的当前选择项 */
    public static int currentListItme = 0;
    public static  int lastListItem = -1;

    public boolean isPlay =true;//默认不播放

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_refresh;
    BaseQuickAdapter adapter;
    public static List<ZhiBoDetailItemBean.ClassesBean> datas;
    public static List<ZhiBoDetailItemBean.ClassesBean> reDatas;
    public String zhiboId = "";
    private int mCurrentCounter = 0;
    private boolean isErr;
    TextView iv_play_small_jindu;
    private static final int IDLE = 0;
    private static final int PAUSE = 1;
    private static final int START = 2;

    boolean  isPause = false;

    private String description_url;

    PopupWindow window;
    View popupView;
    RelativeLayout rl_detail_header;
    private WebView tv_zhibo_detail_gone;
    private final class UIHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROCESSING: // 更新进度
                    progressBar.setProgress(msg.getData().getInt("size"));
                    float num = (float) progressBar.getProgress()
                            / (float) progressBar.getMax();
                    int result = (int) (num * 100); // 计算进度
                    adapter.notifyDataSetChanged();
                    // resultView.setText(result + "%");
                    iv_play_small_jindu.setText(result + "%");
                    if (progressBar.getProgress() == progressBar.getMax()) { // 下载完成
                        Toast.makeText(getApplicationContext(), R.string.music_down_success,
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case FAILURE: // 下载失败
                    Toast.makeText(getApplicationContext(), R.string.music_down_error,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_heath_zhibo_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        zhiboId  =  getIntent().getStringExtra("zhiboId");

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        // 初始化
        popupView =  LayoutInflater.from(this).inflate(R.layout.pop_heath_zhibo_detail, null,false);

        window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,160.0f),true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        tv_zhibo_detail_gone =(WebView) popupView.findViewById(R.id.tv_zhibo_detail_gone);
        tv_zhibo_detail_gone.getSettings().setJavaScriptEnabled(true);

        musicProgress = (SeekBar) findViewById(R.id.music_progress);
        player = new Player(musicProgress);
        musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        adapter = new BaseQuickAdapter<ZhiBoDetailItemBean.ClassesBean, BaseViewHolder>(R.layout.zhibo_detail_item) {
            @Override
            protected void convert(final BaseViewHolder holder, final ZhiBoDetailItemBean.ClassesBean item) {

                iv_play_small_jindu = holder.getView(R.id.iv_play_small_jindu);
                holder.setText(R.id.iv_play_title,item.getTitle());
                holder.setText(R.id.iv_play_small_title,"时长:"+ TimeUtils.formatSeconds(item.getTotal_seconds()));
                iv_play_small_jindu.setText(item.getLocProgress());
                ImageView iv_play_item =holder.getView(R.id.iv_play_item);
                iv_play_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentListItme = holder.getAdapterPosition()-1;
                        if(item.isFouce()){ // 如果当前是播放的，就暂停
                            for(int i=0;i<datas.size();i++){
                                ZhiBoDetailItemBean.ClassesBean classesBean = datas.get(i);
                                classesBean.setFouce(false);
                            }
                            // adapter.setNewData(datas);
                            adapter.notifyDataSetChanged(); // 用notify不会显示加载中，setNewData会显示加载中。
                            if(isBackgroudPlay) {
                                PlayerService.startCommand(HealthZhiBoDetailActivity2.this, LiveConstats.ACTION_MEDIA_PLAY_PAUSE);
                            }else{
                                player.pause();
                            }
                        }else {
                            for(int i=0;i<datas.size();i++){
                                ZhiBoDetailItemBean.ClassesBean classesBean = datas.get(i);
                                classesBean.setFouce(false);
                            }
                            item.setFouce(true);
                            datas.set(currentListItme,item);
                            //adapter.setNewData(datas);
                            adapter.notifyDataSetChanged();

                           if(lastListItem == currentListItme){
                               player.play();
                               return;
                           }
                            if(isBackgroudPlay){
                                // 有背景播放
                                PlayerService.startCommand(HealthZhiBoDetailActivity2.this, LiveConstats.ACTION_MEDIA_PLAY);
                            }else{
                                // 如果文件存在，就本地播放
                                final String path = "/storage/emulated/0/yfjk/"+zhiboId+"/"+item.getTitle()+".mp3";
                                File file = new File(path);
                                if(FileUtils.isFileExits(file)){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.playUrl(ON_LOC,path);
                                        }
                                    }).start();
                                }else {
                                    checkNetwork();

                                }

                            }
                        }

                        lastListItem = currentListItme;

                    }
                });
                if(!item.isFouce()){
                    iv_play_item.setImageResource(R.mipmap.zhibo_play_logo);
                }else{
                    iv_play_item.setImageResource(R.mipmap.zhibo_reading_logo);
                }

                holder.getView(R.id.iv_zhibo_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showToast("点击详情");
                         AlertDialog.Builder builder = new AlertDialog.Builder(HealthZhiBoDetailActivity2.this);
                         LayoutInflater inflater = getLayoutInflater();
                         View layout = inflater.inflate(R.layout.heath_zhibo_webview, null);//获取自定义布局
                         WebView webView = (WebView) layout.findViewById(R.id.wv_heath_zhibo);
                         webView.getSettings().setJavaScriptEnabled(true);
                         webView.loadUrl(datas.get(holder.getAdapterPosition()-1).getDescription_url());
                         builder.setView(layout);
                         AlertDialog dlg = builder.create();
                         dlg.show();
                    }
                });
                holder.getView(R.id.iv_zhibo_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String path = "/storage/emulated/0/yfjk/"+zhiboId+"/"+item.getTitle()+".mp3";
                        File file = new File(path);
                        if(FileUtils.isFileExits(file)){
                            showToast("该文件已下载");
                        }else {
                            showToast("下载中...");
                            FileUtils.fileDown(item.getUrl(),path);
                        }

                    }
                });
            }
        };
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter);
        if(!TextUtils.isEmpty(zhiboId)) {
         mPresenter.info(zhiboId);
        }
        swipe_refresh.setRefreshing(true);
        adapter.setOnLoadMoreListener(this,recyclerView);
        adapter.removeAllFooterView();
        //添加头部
        setHeaderView(recyclerView);
     moni();


    }
    private void moni() {
        datas = new ArrayList<ZhiBoDetailItemBean.ClassesBean>();
        ZhiBoDetailItemBean.ClassesBean classesBean = new ZhiBoDetailItemBean.ClassesBean();
        classesBean.setTitle("方法：一万次试验法则（一）");
        classesBean.setUrl("http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3");
        classesBean.setCreateTime("2017-11-03T16:48:20");
        classesBean.setTotal_seconds(657);
        datas.add(classesBean);
        ZhiBoDetailItemBean.ClassesBean classesBean2 = new ZhiBoDetailItemBean.ClassesBean();
        classesBean2.setTitle("方法：一万次试验法则（二）");
        classesBean2.setUrl("http://sc1.111ttt.com/2017/1/11/11/304112003368.mp3");
        classesBean2.setCreateTime("2017-11-13T16:48:20");
        classesBean2.setTotal_seconds(428);
        datas.add(classesBean2);
        ZhiBoDetailItemBean.ClassesBean classesBean3 = new ZhiBoDetailItemBean.ClassesBean();
        classesBean3.setTitle("方法：一万次试验法则（三）");
        classesBean3.setUrl("http://sc1.111ttt.com/2016/1/12/10/205101900477.mp3");
        classesBean3.setCreateTime("2017-11-23T16:48:20");
        classesBean3.setTotal_seconds(657);
        datas.add(classesBean3);
        adapter.setNewData(datas);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_zhibo_detail:
                if(isShowDetai==false){
                    Drawable drawable = getResources().getDrawable(R.mipmap.zhibo_up);
                    tv_zhibo_detail.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                    isShowDetai =true;
                    window.showAsDropDown(rl_detail_header, 0, 0);
                }else{
                    Drawable drawable = getResources().getDrawable(R.mipmap.zhibo_down);
                    tv_zhibo_detail.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                    isShowDetai =false;
                    window.dismiss();
                }
                break;
            case R.id.tv_zhibo_detail_sort:
                // 倒序
                if(isSort){
                    Drawable drawable = getResources().getDrawable(R.mipmap.zhibo_sort_dwon);
                    tv_zhibo_detail_sort.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                    isSort = false;
                }else{
                    Drawable drawable = getResources().getDrawable(R.mipmap.zhibo_sort_up);
                    tv_zhibo_detail_sort.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                    isSort = true;
                }
                Collections.reverse(datas);
                adapter.setNewData(datas);
                break;
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void getInfo(ResultBase<ZhiBoDetailItemBean> bean) {
        if(bean.isSuccess()){
            mCurrentCounter = bean.getData().getClasses().size();
            if(!TextUtils.isEmpty(bean.getData().getTitle())) {
                tv_zhibo_detail_title.setText(bean.getData().getTitle());
                initBackTitle(bean.getData().getTitle());
            }
            description_url = bean.getData().getDescription_url();
            //设置图片集合
            images.add(bean.getData().getBanner());
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            datas = bean.getData().getClasses();
            adapter.setNewData(datas);
            tv_zhibo_detail_gone.loadUrl(bean.getData().getDescription_url());
        }else {
            showToast(LiveConstats.ERROR);
        }
        swipe_refresh.setEnabled(true);


    }
    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.info(zhiboId);
                swipe_refresh.setRefreshing(false);
            }
        }, Const.DELAYMILLIS);
    }

    @Override
    public void getInfoMore(ResultBase<ZhiBoDetailItemBean> bean) {
        if (bean.isSuccess()) {
            mCurrentCounter = bean.getData().getClasses().size();
            adapter.addData(bean.getData());
        } else {
            showToast(bean.getMessage());
        }
        adapter.loadMoreComplete();
    }


    @Override
    public void onLoadMoreRequested() {
        swipe_refresh.setEnabled(false);
        if (mCurrentCounter < 10) {
            adapter.loadMoreEnd(true);
            swipe_refresh.setEnabled(true);
        } else {
            if (mCurrentCounter >= 10) {
                if (isErr) {
                    isErr = false;
                    adapter.loadMoreFail();
                } else {
                    adapter.loadMoreEnd(false);
                    mPresenter.infoMore(zhiboId);
                }
            } else {
                adapter.loadMoreEnd(true);
            }
            swipe_refresh.setEnabled(true);
        }
    }


    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.activity_heath_zhibo_detail_header, view, false);
        banner = (Banner) header.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(HealthZhiBoDetailActivity2.this, WebActivity.class);
                intent.putExtra("url",description_url);
                startActivity(intent);
            }
        });
        tv_zhibo_detail = (TextView) header.findViewById(R.id.tv_zhibo_detail);
        rl_detail_header = (RelativeLayout) header.findViewById(R.id.rl_detail_header);

        tv_zhibo_detail.setOnClickListener(this);
        // 标题
        tv_zhibo_detail_title = (TextView) header.findViewById(R.id.tv_zhibo_detail_title);
        adapter.setHeaderView(header);

        // 倒叙
        tv_zhibo_detail_sort = (TextView)header.findViewById(R.id.tv_zhibo_detail_sort);

        tv_zhibo_detail_sort.setOnClickListener(this);

    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            Log.d("zcr",(progress+1)+"");
            ZhiBoDetailItemBean.ClassesBean currentItem= datas.get(currentListItme);
            currentItem.setLocProgress("已看"+progress+"%");
            currentItem.setJinduProgress(progress);
            // adapter.setNewData(datas);
            adapter.notifyDataSetChanged();
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
            currentItem.setPauseNum(this.progress);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            player.mediaPlayer.seekTo(datas.get(currentListItme).getPauseNum());
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }

    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player = null;
        }
    }

    private void checkNetwork() {


        if (NetUtil.isWiFi(this)) {
            // 是wifi 环境
            new Thread(new Runnable() {
                @Override
                public void run() {
                player.playUrl(ON_LINE,"");
                }
            }).start();
        } else {

            if(!isWifi){
                isWifi = true;
                new AlertDialog.Builder(HealthZhiBoDetailActivity2.this)
                        .setTitle("播放")
                        .setMessage("当前是移动网络，耗费流量较多，确定吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        player.playUrl(ON_LINE,"");
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        player.playUrl(ON_LINE,"");
                    }
                }).start();
            }


        }
    }


}
