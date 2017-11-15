package com.eaphone.g08android.ui.live;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eaphone.g08android.G08Application;
import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.InfoTypePagerAdapter;
import com.eaphone.g08android.entity.NewsType;
import com.eaphone.g08android.entity.NewsTypeDao;
import com.eaphone.g08android.mvp.presenter.LiveHealthPresenter;
import com.eaphone.g08android.ui.info.InfoTypeFragment;
import com.hpw.mvpframe.base.CoreBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/10 14:43
 * 修改人：Administrator
 * 修改时间：2017/11/10 14:43
 * 修改备注：
 */
public class HealthClassRoomActivity extends CoreBaseActivity<LiveHealthPresenter> implements View.OnClickListener {

    private TabLayout tabs;
    private ViewPager vp_view;
    private TextView titlebar_tv;
    private ImageView titlebar_iv_left;

    private List<Fragment> viewList;
    private InfoTypePagerAdapter adapter;
    private List<String> titlList;

    private List<NewsType> newsTypeList;
    private NewsTypeDao mNewsTypeDao;


    @Override
    public int getLayoutId() {
        return R.layout.activity_live_health_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tabs = (TabLayout) findViewById(R.id.tabs);
        vp_view = (ViewPager) findViewById(R.id.vp_view);
        titlebar_tv = (TextView) findViewById(R.id.titlebar_tv);
        titlebar_iv_left = (ImageView) findViewById(R.id.titlebar_iv_left);
        titlebar_iv_left.setVisibility(View.VISIBLE);
        titlebar_iv_left.setImageResource(R.mipmap.ic_back);
        titlebar_iv_left.setOnClickListener(this);

        titlebar_tv.setText("健康课堂");
        viewList = new ArrayList<>();
        titlList = new ArrayList<>();
        newsTypeList = new ArrayList<>();

        getNewsTypeList();

    }

    private void getNewsTypeList() {
        mNewsTypeDao = G08Application.getInstances().getDaoSession().getNewsTypeDao();
        newsTypeList = mNewsTypeDao.queryBuilder().list();

        for (int i = 0; i < newsTypeList.size(); i++) {
            titlList.add(newsTypeList.get(i).getName());
            InfoTypeFragment info = new InfoTypeFragment();
            Bundle bun = new Bundle();
            bun.putString("newsTypeId", newsTypeList.get(i).getId());
            info.setArguments(bun);
            viewList.add(info);
        }

        adapter = new InfoTypePagerAdapter(this.getSupportFragmentManager(), viewList, titlList);
        vp_view.setAdapter(adapter);
        tabs.setupWithViewPager(vp_view);//将TabLayout和ViewPager关联起来。
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }
}
