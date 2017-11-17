package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.RecyclerViewHelper;
import com.eaphone.g08android.utils.util.Player;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
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
public class HealthZhiBoDetailActivity extends CoreBaseActivity<LiveZhiBoDetailPresenter> implements LiveContracts.LiveZhiBoDetailView,View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private Banner banner;
    private ArrayList<String> images = new ArrayList<String>();

    private TextView tv_zhibo_detail;
    private TextView tv_zhibo_detail_gone;
    private TextView tv_zhibo_detail_title;

    private Player player;
    private SeekBar musicProgress;
    private ProgressBar progressBar;
    private static final int PROCESSING = 1;
    private static final int FAILURE = -1;


    private boolean isShowDetai=false;
    int lastPosition = 0;
    ZhiBoDetailItemBean.ClassesBean item;;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_refresh;
    BaseQuickAdapter adapter;
    List<ZhiBoDetailItemBean.ClassesBean> datas;
    String zhiboId = "";
    private int mCurrentCounter = 0;
    private boolean isErr;
    TextView iv_play_small_jindu;
    private Handler handler = new UIHandler();

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

        musicProgress = (SeekBar) findViewById(R.id.music_progress);
        player = new Player(musicProgress);
        musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());



        adapter = new BaseQuickAdapter<ZhiBoDetailItemBean.ClassesBean, BaseViewHolder>(R.layout.zhibo_detail_item) {
            @Override
            protected void convert(final BaseViewHolder holder, final ZhiBoDetailItemBean.ClassesBean item) {
                iv_play_small_jindu = holder.getView(R.id.iv_play_small_jindu);
                holder.setText(R.id.iv_play_title,item.getTitle());
                holder.setText(R.id.iv_play_small_title,"时长:"+item.getTotal_seconds());
                iv_play_small_jindu.setText(item.getProgress());
                ImageView iv_play_item =holder.getView(R.id.iv_play_item);
                iv_play_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(lastPosition != 0){
                            item.setFouce(false);
                            datas.set(lastPosition,item);
                        }
                        item.setFouce(true);
                        datas.set(holder.getAdapterPosition()-1,item);
                        // 记录上一个被点击的。
                        lastPosition = holder.getAdapterPosition()-1;
                        adapter.setNewData(datas);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                player.playUrl(item.getUrl());

                            }
                        }).start();
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
                        showToast("点击详情");
                    }
                });

                holder.getView(R.id.iv_zhibo_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击下载");
                    }
                });
            }
        };
        adapter.setEnableLoadMore(false);
        RecyclerViewHelper.initRecyclerViewV(mContext, recyclerView, true, adapter);
        mPresenter.info("5a0b9bc4732d00043f24bfec");
        swipe_refresh.setRefreshing(true);
        adapter.setOnLoadMoreListener(this,recyclerView);
        //添加头部
        setHeaderView(recyclerView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_zhibo_detail:
            if(isShowDetai==false){
                tv_zhibo_detail_gone.setVisibility(View.VISIBLE);
                isShowDetai =true;
            }else{
                tv_zhibo_detail_gone.setVisibility(View.GONE);
                isShowDetai =false;
            }
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
            datas = bean.getData().getClasses();
            adapter.setNewData(datas);

        }else {
            showToast(LiveConstats.ERROR);
        }
        swipe_refresh.setEnabled(true);


    }

    @Override
    public void getInfoMore(ResultBase<ZhiBoDetailItemBean> bean) {
        mPresenter.info(zhiboId);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.info(zhiboId);
                swipe_refresh.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, Const.DELAYMILLIS);
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
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        tv_zhibo_detail = (TextView) header.findViewById(R.id.tv_zhibo_detail);
        tv_zhibo_detail_gone = (TextView) header.findViewById(R.id.tv_zhibo_detail_gone);
        tv_zhibo_detail.setOnClickListener(this);
        // 标题
        tv_zhibo_detail_title = (TextView) header.findViewById(R.id.tv_zhibo_detail_title);
        adapter.setHeaderView(header);
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
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
}
