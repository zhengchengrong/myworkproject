package com.eaphone.g08android.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eaphone.g08android.G08Application;
import com.eaphone.g08android.R;
import com.eaphone.g08android.adapter.InfoTypePagerAdapter;
import com.eaphone.g08android.entity.NewsType;
import com.eaphone.g08android.entity.NewsTypeDao;
import com.hpw.mvpframe.base.CoreBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:24
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:24
 * 修改备注：
 */
public class InfoFragment extends CoreBaseFragment/*<InfoPresenter, InfoModel> implements NewsContracts.InfoView */ {

    @BindView(R.id.tabs)
    TabLayout layout;

    @BindView(R.id.vp_view)
    ViewPager vp_view;

    private List<Fragment> viewList;
    private InfoTypePagerAdapter adapter;
    private List<String> titlList;

    private List<NewsType> newsTypeList;
    private NewsTypeDao mNewsTypeDao;

    @Override
    public int getLayoutId() {
        return R.layout.info_fragment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        super.initData();
        viewList = new ArrayList<>();
        titlList = new ArrayList<>();
        newsTypeList = new ArrayList<>();

        getNewsTypeList();
    }

//    @Override
//    public void showError(String msg) {
//        showToast(msg);
//    }

//    @Override
//    public void getInfo(ResultBase<List<NewsType>> result) {
//        if (result.isSuccess()) {
//
//        }
//
//    }


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

        adapter = new InfoTypePagerAdapter(this.getChildFragmentManager(), viewList, titlList);
        vp_view.setAdapter(adapter);
        layout.setupWithViewPager(vp_view);//将TabLayout和ViewPager关联起来。
    }


}
