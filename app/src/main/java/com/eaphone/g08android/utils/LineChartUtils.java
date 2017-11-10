package com.eaphone.g08android.utils;

import android.graphics.Color;
import android.text.TextUtils;

import com.eaphone.g08android.bean.LineBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zlq on 2017/9/25.
 */

public class LineChartUtils {


    /**
     * 绘制线图，默认最多绘制三种颜色。所有线均依赖左侧y轴显示。
     *
     * @param lineChart
     * @param dateType  周月年曲线的日期格式
     */
    public static void setLinesChart(LineChart lineChart, List<LineBean> dataList, String dateType, String sensorType) {
        lineChart.setDescription("")/*.setEnabled(false)*/;//设置描述
        lineChart.setPinchZoom(true);//设置按比例放缩柱状图


        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);


        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);


        configYData(lineChart);
        setData(lineChart, dataList, dateType, sensorType);
    }

    private static void setData(LineChart lineChart, List<LineBean> dataList, String dateType, String sensorType) {

        List<String> xAxisValue = new ArrayList<>();
        List<Entry> entriesList = new ArrayList<>();
        List<Entry> entriesList2 = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            xAxisValue.add(dataList.get(i).getTimestamp());
            if (!TextUtils.isEmpty(dataList.get(i).getText()))
                if (sensorType.equals(Const.BLODPRESSURE)) {
                    String valueH = dataList.get(i).getText().split("/")[0];
                    String valueL = dataList.get(i).getText().split("/")[1];
                    entriesList.add(new Entry(Float.parseFloat(valueH), i));
                    entriesList2.add(new Entry(Float.parseFloat(valueL), i));
                } else {
                    entriesList.add(new Entry(Float.parseFloat(dataList.get(i).getText()), i));
                }
        }


        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//        Log.e("Line",xxx.toString());
        LineDataSet set;
        LineDataSet set2;
//        if (lineChart.getData() == null) {
        if (sensorType.equals(Const.BLODPRESSURE)) {
            set2 = new LineDataSet(entriesList2, "");
            lineDataSets.add(set2);
            setY2(set2);
        }
        set = new LineDataSet(entriesList, "");

        setY1(set);

        lineDataSets.add(set);
        LineData data = new LineData(XAxisValueFormatter.getXAxisValue(xAxisValue, dateType), lineDataSets);
        lineChart.setData(data);
        lineChart.invalidate();
//        } else {
//
//            lineChart.getData().notifyDataChanged();
//            lineChart.notifyDataSetChanged();
//        }
    }

    private static void configYData(LineChart mChart) {
        mChart.setNoDataText("");
        mChart.setDrawBorders(false); // 是否在折线图上添加边框
        mChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mChart.setNoDataTextDescription("暂无数据");
        mChart.setDoubleTapToZoomEnabled(false);// 双击屏幕放大图表
        mChart.setTouchEnabled(true); // 设置是否可以触摸
        // enable scaling and dragging
        mChart.setDragEnabled(true);// 是否可以拖拽
        mChart.setScaleEnabled(true);// 是否可以缩放
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);//
        mChart.setBackgroundColor(Color.rgb(255, 255, 255));// 设置背景
        mChart.setDrawGridBackground(false);// 设置图表内格子背景是否显示，默认是false
        mChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        // 隐藏左边坐标轴横网格线
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMinValue(0f);

        // 隐藏右边坐标轴横网格线
        mChart.getAxisRight().setDrawGridLines(false);
        // 隐藏X轴竖网格线
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setAvoidFirstLastClipping(true);//设置避免第一个最后的剪辑
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        mChart.setDrawBorders(false); // 设置图表内格子外的边框是否显示

        mChart.animateX(2500); // 立即执行的动画,x轴
    }

    private static void setY1(LineDataSet set1) {
        set1.setDrawValues(false);// 不显示数据
        set1.setCircleSize(3f);// 显示的圆形大小
        set1.setColor(Color.rgb(181, 207, 58));// 显示颜色
        set1.setCircleColor(Color.rgb(181, 207, 58));// 圆形的颜色
        set1.setHighLightColor(Color.rgb(181, 207, 58)); // 高亮的线的颜色
        set1.setDrawCircles(true);// 设置有圆点

        set1.setDrawFilled(true);
        set1.setFillColor(Color.rgb(181, 207, 58));// 填充颜色
        set1.setLineWidth(1.5f);
    }

    private static void setY2(LineDataSet set2) {
        set2.setDrawValues(false);// 不显示数据
        set2.setCircleSize(3f);// 显示的圆形大小
        set2.setColor(Color.rgb(0, 113, 183));// 显示颜色
        set2.setCircleColor(Color.rgb(0, 113, 183));// 圆形的颜色
        set2.setHighLightColor(Color.rgb(0, 113, 183)); // 高亮的线的颜色
        set2.setDrawCircles(true);// 设置有圆点

        set2.setDrawFilled(true);
        set2.setFillColor(Color.rgb(0, 113, 183));// 填充颜色

        set2.setLineWidth(1.5f);
    }

}
