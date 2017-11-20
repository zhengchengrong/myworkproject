package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.LiveHome;
import com.eaphone.g08android.mvp.contracts.LiveContracts;
import com.eaphone.g08android.mvp.presenter.LiveHealthPresenter;
import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.base.ResultBase;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:24
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:24
 * 修改备注：
 */
public class LiveHealthFragment extends CoreBaseFragment<LiveHealthPresenter> implements LiveContracts.LiveHealthView, View.OnClickListener {

    private Banner banner;

    private ImageView iv_info_zhibo;
    private ImageView iv_info_ketang;
    private ImageView iv_info_com;
    List<LiveHome.BannersBean> banners;


    private ArrayList<String> images = new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.info_new_fragment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        banner = (Banner) view.findViewById(R.id.banner);
        iv_info_zhibo = (ImageView) view.findViewById(R.id.iv_info_zhibo);
        iv_info_ketang = (ImageView) view.findViewById(R.id.iv_info_ketang);
        iv_info_com = (ImageView) view.findViewById(R.id.iv_info_com);
        iv_info_zhibo.setOnClickListener(this);
        iv_info_ketang.setOnClickListener(this);
        iv_info_com.setOnClickListener(this);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",banners.get(position).getUrl());
                startActivity(intent);

            }
        });
        mPresenter.info();

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_info_zhibo:
                startActivity(HealthZhiBoActivity.class);
                break;
            case R.id.iv_info_ketang:
                startActivity(HealthClassRoomActivity.class);
                break;
            case R.id.iv_info_com:
                startActivity(HealthZhiBoGroupActivity.class);
                break;

        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void getInfo(ResultBase<LiveHome> bean) {
        if(bean.isSuccess()){
            banners  = bean.getData().getBanners();
            for(int i = 0; i< banners.size() ; i++){
                images.add(banners.get(i).getBanner());
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
            }

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

}
