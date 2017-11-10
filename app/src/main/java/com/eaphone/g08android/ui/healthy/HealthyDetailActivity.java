package com.eaphone.g08android.ui.healthy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.LoginUtil;
import com.eaphone.g08android.utils.StatusBarUtils;
import com.eaphone.g08android.widget.verviewpager.YFragmentPagerAdapter;
import com.eaphone.g08android.widget.verviewpager.YViewPager;
import com.hpw.mvpframe.base.CoreBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HealthyDetailActivity extends CoreBaseActivity {


    @BindView(R.id.viewpager)
    YViewPager viewPager;

    @BindView(R.id.ll_record)
    LinearLayout ll_record;

    @BindView(R.id.ll_warn)
    LinearLayout ll_warn;

    @BindView(R.id.rl_titlebar)
    RelativeLayout rl_titlebar;

    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    private List<Fragment> list;
    private FragmentAdapter pagerAdapter;
    private String sensorType;
    private String userId;
    private String name;

    private String title;

    @Override
    public int getLayoutId() {
        return R.layout.healthy_detail_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.setColor(HealthyDetailActivity.this, getResources().getColor(R.color.main_color), 0);
        sensorType = getIntent().getExtras().getString("type");


        list = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("type", sensorType);

        try{
            userId = getIntent().getExtras().getString("userId");
            name = getIntent().getExtras().getString("name");
        }catch (Exception e){
            userId = "";
            name = "";
        }

        if(TextUtils.isEmpty(userId)){
            initTitle(LoginUtil.isFamilyName()+ DeviceLevelUtils.getSensorType(sensorType)+"数据",R.mipmap.ic_custom_data);
        }else{
            initTitle(name+ DeviceLevelUtils.getSensorType(sensorType)+"数据",0);

        }

        bundle.putString("userId",userId);

        NewDataFragment fragment = new NewDataFragment();
        OthersFragment fragment1 = new OthersFragment();
        fragment.setArguments(bundle);
        fragment1.setArguments(bundle);
        list.add(fragment);
        list.add(fragment1);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        ll_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("sensorType", sensorType);
                bundle.putString("userId", userId);
                startActivity(HealthyRecordActivity.class,bundle);
            }
        });

        ll_warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                bundle.putString("sensorType", sensorType);
                startActivity(WarnActivity.class,bundle);

            }
        });
    }


    private void initTitle(String title,int resImage){

        initBackTitle(title).setRightImage(resImage).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", sensorType);
                startActivity(HandInDataActivity.class, bundle);
            }
        }).build();
    }


    class FragmentAdapter extends YFragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }


    }
}
