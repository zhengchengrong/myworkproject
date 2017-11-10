package com.eaphone.g08android.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.TimeUtils;

import java.util.List;

import static android.R.attr.data;
import static com.eaphone.g08android.R.id.iv_type;
import static com.eaphone.g08android.R.id.tv_data;


/**
 * Created by Administrator on 2016/4/13.
 */
public class HealthyServiceAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Healthy> dataHomes;

    public HealthyServiceAdapter(List<Healthy> dataHomes, Context context) {
        this.context = context;
        this.dataHomes = dataHomes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataHomes == null ? 0 : dataHomes.size();
    }

    @Override
    public Object getItem(int position) {
        return dataHomes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.healthy_item, null);

            mHolder = new ViewHolder();
            mHolder.tv_data = (TextView) convertView.findViewById(tv_data);
            mHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            mHolder.iv_type = (ImageView) convertView.findViewById(iv_type);
            mHolder.tv_pressure = (TextView) convertView.findViewById(R.id.tv_pressure);
            mHolder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
            mHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            mHolder.cardview = (CardView) convertView.findViewById(R.id.cardview);
            mHolder.rtl_type = (RelativeLayout) convertView.findViewById(R.id.rtl_type);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        Healthy dataHome = dataHomes.get(position);
        if (dataHome.getSource() != null)
            if (TextUtils.isEmpty(dataHome.getSource().getText())) {
                mHolder.tv_source.setText("数据来源：--");
            } else {
                mHolder.tv_source.setText(dataHome.getSource().getText());
            }

        String type = dataHome.getSensorType();
        if (Const.BLODPRESSURE.equals(type)) {
            mHolder.tv_pressure.setText("血压");
            mHolder.tv_unit.setText("mmHg");

            if (TextUtils.isEmpty(dataHome.getText())) {
                mHolder.tv_data.setText("-- / --");
            } else {
                mHolder.tv_data.setText(dataHome.getText());
            }
            mHolder.iv_type.setImageResource(R.mipmap.ic_pressure);
            mHolder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.bg_presure));
            mHolder.rtl_type.setBackgroundColor(context.getResources().getColor(R.color.bg_presure));

        } else if (Const.HEARTRATE.equals(type)) {
            mHolder.tv_pressure.setText("心率");
            mHolder.tv_unit.setText("bpm");

            if (TextUtils.isEmpty(dataHome.getText())) {
                mHolder.tv_data.setText("--");
            } else {
                mHolder.tv_data.setText(dataHome.getText());
            }
            mHolder.iv_type.setImageResource(R.mipmap.ic_bpm);
            mHolder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.bg_heartrate));
            mHolder.rtl_type.setBackgroundColor(context.getResources().getColor(R.color.bg_heartrate));

        } else if (Const.BODYTEMPERATURE.equals(type)) {

            mHolder.tv_pressure.setText("体温");
            mHolder.tv_unit.setText("℃");

            mHolder.iv_type.setImageResource(R.mipmap.ic_t);
            mHolder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.bg_temperature));
            mHolder.rtl_type.setBackgroundColor(context.getResources().getColor(R.color.bg_temperature));
            if (TextUtils.isEmpty(dataHome.getText())) {
                mHolder.tv_data.setText("--");
            } else {
                mHolder.tv_data.setText(dataHome.getText());
            }
        } else if (Const.OXYGENATION.equals(type)) {
            mHolder.tv_pressure.setText("血氧");
            mHolder.tv_unit.setText("%");
            mHolder.iv_type.setImageResource(R.mipmap.ic_blood_o);
            mHolder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.bg_blood_oxygen));
            mHolder.rtl_type.setBackgroundColor(context.getResources().getColor(R.color.bg_blood_oxygen));
            dataHome.setValue(FormatUtil.formatFloat(data, Const.FLOAT_2));
            if (TextUtils.isEmpty(dataHome.getText())) {
                mHolder.tv_data.setText("--");
            } else {
                mHolder.tv_data.setText(dataHome.getText());
            }
        } else if (Const.BLOOD_GLUCOSE.equals(type)) {
            mHolder.tv_pressure.setText("血糖");
            mHolder.tv_unit.setText("mmol/L");

            if (TextUtils.isEmpty(dataHome.getText())) {
                mHolder.tv_data.setText("--");
            } else {
                mHolder.tv_data.setText(dataHome.getText());
            }

        } else if (Const.ELECTROCARDIOGRAM.equals(type)) {
            mHolder.tv_pressure.setText("心电");
            mHolder.tv_unit.setVisibility(View.GONE);
            mHolder.tv_data.setBackgroundResource(R.mipmap.hz);
            mHolder.tv_data.setText("");
            mHolder.iv_type.setImageResource(R.mipmap.ic_ecg);
        }


        if (TextUtils.isEmpty(dataHome.getTimestamp())) {
            mHolder.tv_time.setText("测量时间：--");
        } else {

            mHolder.tv_time.setText(TimeUtils.timeTypeChange(dataHome.getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_2));
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iv_type;
        private TextView tv_pressure;
        private TextView tv_unit;
        private TextView tv_source;
        private TextView tv_time;
        private TextView tv_data;
        private CardView cardview;
        private RelativeLayout rtl_type;
    }
}
