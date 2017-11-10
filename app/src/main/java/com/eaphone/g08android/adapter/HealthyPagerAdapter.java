package com.eaphone.g08android.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.eaphone.g08android.bean.Family;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/12 10:30
 * 修改人：Administrator
 * 修改时间：2017/9/12 10:30
 * 修改备注：
 */
public class HealthyPagerAdapter extends PagerAdapter {

    private List<View> viewList;
    private List<Family> families;

    public HealthyPagerAdapter(List<Family> families,List<View> viewList) {
        this.viewList = viewList;
        this.families = families;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        Family family = families.get(position);
//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.healthy_family_item,container,false);
//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_avater);
//        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//
//        if(TextUtils.isEmpty(family.getUserId())){
//            imageView.setImageResource(R.mipmap.ic_default);
//        }else{
//            ImageLoader.displayImage(Const.getAvater(family.getUserId()),imageView);
//        }
//        tv_name.setText(family.getName());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.3f;
    }
}
