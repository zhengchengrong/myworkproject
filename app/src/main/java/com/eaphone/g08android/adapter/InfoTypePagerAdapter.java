package com.eaphone.g08android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/26 11:53
 * 修改人：Administrator
 * 修改时间：2017/8/26 11:53
 * 修改备注：
 */
public class InfoTypePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mViewList;
    private List<String> titleList;

    public InfoTypePagerAdapter(FragmentManager fm,List<Fragment> mViewList,List<String> titleList) {
        super(fm);
        this.mViewList = mViewList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
