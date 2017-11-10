package com.eaphone.g08android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.HealthyServiceList;

import java.util.List;


/**
 * Created by Administrator on 2016/4/13.
 */
public class HealthyServiceFragmentAdapter extends BaseAdapter {

    private List<HealthyServiceList> list;
    private LayoutInflater inflater;

    public HealthyServiceFragmentAdapter(List<HealthyServiceList> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.healthyservice_fragment_item, null);

            mHolder = new ViewHolder();
            mHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            mHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if(!"体温值".equals(list.get(position).getTitle())){

            mHolder.tv_data.setText(list.get(position).getData().replace(".0",""));
        }else{
            mHolder.tv_data.setText(list.get(position).getData());
        }
        mHolder.tv_title.setText(list.get(position).getTitle());

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_data;
    }
}
