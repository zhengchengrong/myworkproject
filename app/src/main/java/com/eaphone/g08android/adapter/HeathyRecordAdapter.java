package com.eaphone.g08android.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.Healthy;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.DeviceLevelUtils;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.TimeUtils;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/19 17:33
 * 修改人：Administrator
 * 修改时间：2017/9/19 17:33
 * 修改备注：
 */
public class HeathyRecordAdapter extends BaseQuickAdapter<Healthy, BaseViewHolder> {


    private Context context;

    public HeathyRecordAdapter(@LayoutRes int layoutResId,Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Healthy item) {


        helper.setText(R.id.tv_time, TimeUtils.timeTypeChange(item.getTimestamp(), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_17));
        helper.setText(R.id.tv_title, item.getSource().getText());
        String unit = FormatUtil.getUnit(item.getSensorType());
        TextView tv_data = helper.getView(R.id.tv_data);

        helper.setText(R.id.tv_data, item.getText() + unit);
        if(setDataColor(item.getSensorType(),item.getText())){
            tv_data.setTextColor(context.getResources().getColor(R.color.txt_black));
        }else{
            tv_data.setTextColor(context.getResources().getColor(R.color.bg_ecg));
        }
    }

    private boolean setDataColor(String sensorType,String data){
        switch (sensorType){
            case Const.BLODPRESSURE:

                return setBloodPressure(data);
            case Const.OXYGENATION:

                return jugdeOxygenation(data);
            case Const.BODYTEMPERATURE:

                return jugdeBodytemperature(data);
            case Const.HEARTRATE:

                return setBPM(data);
            default:
                return false;
        }
    }

    //血氧
    private boolean jugdeOxygenation(String data) {
        int level = DeviceLevelUtils.getBloodOxygen(Float.parseFloat(data));
        if (level == 1) {
            return true;
        } else {
            return false;
        }
    }

    //体温
    private boolean jugdeBodytemperature(String data) {
        int level = DeviceLevelUtils.getTemperatureLevel(Float.parseFloat(data));
        if (level == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean setBPM(String data) {
        int level = DeviceLevelUtils.getHRVLevel(Float.parseFloat(data));
        if (level == 1) {
            return true;

        } else return false;

    }

    private boolean setBloodPressure(String data) {
            String[] split = data.split("/");
            int value = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[0]), Const.FLOAT_2));
            int value1 = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[1]), Const.FLOAT_2));
            if (DeviceLevelUtils.getBloodPressLevel(value, value1) == 1) {
                return true;
            } else {
                return false;
            }

    }
}
