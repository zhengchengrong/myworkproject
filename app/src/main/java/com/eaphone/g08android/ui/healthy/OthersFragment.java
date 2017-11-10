package com.eaphone.g08android.ui.healthy;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.InfoTypePagerAdapter;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.widget.NoScrollViewPager;
import com.hpw.mvpframe.base.CoreBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OthersFragment extends CoreBaseFragment {


    @BindView(R.id.rg_tab)
    RadioGroup rg_tab;

    @BindView(R.id.rb_day)
    RadioButton rb_day;

    @BindView(R.id.rb_week)
    RadioButton rb_week;

    @BindView(R.id.rb_month)
    RadioButton rb_month;

    @BindView(R.id.rb_year)
    RadioButton rb_year;


    @BindView(R.id.vp_item)
    NoScrollViewPager mViewpager;

    @BindView(R.id.tv_trend)
    TextView mTvTrend;

    private List<Fragment> viewList;
    private InfoTypePagerAdapter adapter;
    private List<String> titlList;
    private static String[] titles = new String[]{"日", "周", "月", "年"};
    private String sensorType;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_others;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        super.initData();
        titlList = new ArrayList<>();
        viewList = new ArrayList<>();
        sensorType = getArguments().getString("type");
        userId = getArguments().getString("userId");
        for (int i = 0; i < titles.length; i++) {
            titlList.add(titles[i]);
            OthersItemFragment itemFragment = new OthersItemFragment();
            Bundle bun = new Bundle();
            bun.putString("timeType", titles[i]);
            bun.putString("type", sensorType);
            bun.putString("userId", userId);
            itemFragment.setArguments(bun);
            viewList.add(itemFragment);
        }
        mViewpager.setNoScroll(true);
        adapter = new InfoTypePagerAdapter(this.getChildFragmentManager(), viewList, titlList);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(5);
        initTrendText();

        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                switch (i) {
                    case R.id.rb_day:
                        mViewpager.setCurrentItem(0);
                        break;
                    case R.id.rb_week:

                        mViewpager.setCurrentItem(1);
                        break;
                    case R.id.rb_month:
                        mViewpager.setCurrentItem(2);
                        break;
                    case R.id.rb_year:
                        mViewpager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private void initTrendText() {
        mTvTrend.setText(DeviceLevelUtils.getSensorType(sensorType) + "趋势");
        onStartAnimation();
    }

    private void onStartAnimation() {
        AnimationSet animatorSet = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.arrows_down);
        mTvTrend.startAnimation(animatorSet);
    }
}
