package com.eaphone.g08android.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/14 10:05
 * 修改人：Administrator
 * 修改时间：2017/8/14 10:05
 * 修改备注：
 */
public class StartActivity extends CoreBaseActivity {

    @BindView(R.id.vp_pictrue)
    ViewPager mViewPager;

    @BindView(R.id.btn_into)
    Button mBtnInto;

    @BindView(R.id.linear_dot)
    LinearLayout mLinearDot;

    private List<ImageView> imageLists;
    private ImageView[] dotImages;
    private int[] ints =  new int[]{R.mipmap.start_1, R.mipmap.start_2,R.mipmap.start_3};


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.start_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBtnInto.setVisibility(View.GONE);
        PreferencesUtils.putSharePre(Const.ISFIRST, true);
        mBtnInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        });

        init(ints);
    }

    private void init(final int[] images) {
        imageLists = new ArrayList<>();
        mLinearDot.removeAllViews();
        dotImages = new ImageView[images.length];
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
            imageLists.add(imageView);

            ImageView dot = new ImageView(this);
            dotImages[i] = dot;
            dot.setImageResource(R.drawable.dot_n);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dot.setLayoutParams(layoutParams);
            if (i > 0) {
                layoutParams.leftMargin = 20;
            }
            mLinearDot.addView(dot);
        }
        dotImages[0].setImageResource(R.drawable.dot_s);
        mViewPager.setAdapter(new DotPagerAdapter(images));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //让滑倒最后一页显示按钮
                if (position == images.length - 1) {
                    mBtnInto.setVisibility(View.VISIBLE);
                } else {
                    mBtnInto.setVisibility(View.GONE);
                }
                for (int i = 0; i < dotImages.length; i++) {
                    if (i == position) {
                        dotImages[i].setImageResource(R.drawable.dot_s);
                    } else {
                        dotImages[i].setImageResource(R.drawable.dot_n);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private class DotPagerAdapter extends PagerAdapter {

        private int[] images;

        public DotPagerAdapter(int[] images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageLists.get(position);
            imageView.setImageResource(images[position]);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
