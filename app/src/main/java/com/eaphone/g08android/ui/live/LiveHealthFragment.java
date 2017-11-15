package com.eaphone.g08android.ui.live;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eaphone.g08android.R;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:24
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:24
 * 修改备注：
 */
public class LiveHealthFragment extends CoreBaseFragment implements View.OnClickListener {

    private Banner banner;

    private ImageView iv_info_zhibo;
    private ImageView iv_info_ketang;
    private ImageView iv_info_com;



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
        images.add("http://img.taopic.com/uploads/allimg/140107/234764-14010F0310582.jpg");
        images.add("http://img.zcool.cn/community/01638059302785a8012193a36096b8.jpg@2o.jpg");
        images.add("http://tupian.enterdesk.com/2015/xll/10/5/Sanji3.jpg");
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_info_zhibo:
                startActivity(HealthClassRoomActivity.class);
                break;
            case R.id.iv_info_ketang:
                startActivity(HealthZhiBoActivity.class);
                break;
            case R.id.iv_info_com:
                ToastUtils.showToast(mContext,"交流群");
                break;

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
