package com.java.yanlu;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.java.yanlu.dataprocess.ChartData;
import com.java.yanlu.dataprocess.Manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    private LineChart lineChart;
    private String area="";
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private ChartData chartData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        lineChart = findViewById(R.id.linechart);
        Bundle bundle = getIntent().getExtras();
        area = bundle.getString("area");
        initSetChartAction();
        initLineChartStyle();
    }
    private void initSetChartAction() {
        lineChart.setTouchEnabled(false);//设置是否可以触碰
        lineChart.setDragEnabled(true);//设置不可拖拽
        lineChart.setScaleEnabled(false);

        lineChart.setPinchZoom(true); ////设置是否能扩大扩小
        lineChart.getAxisRight().setEnabled(false);


        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);



    }

    private String formatDate(int days,String date){
        Date beginDate = null;
        try {
            beginDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DATE,days);
        Date resultDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String z_time = sdf.format(resultDate);
        return z_time;
    }

    private void initLineChartStyle() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                chartData = Manager.I.fetchChartData(area);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int size = chartData.confirmed.size();
        Description description = new Description();
        description.setText(area);
        description.setTextSize(Manager.point.y/120);
        description.setPosition(Manager.point.x*2/3,Manager.point.y/13);
        lineChart.setDescription(description);



        //first line
        List<Entry> entries = new ArrayList<Entry>();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String date = chartData.begin;
                return formatDate((int)value,date);
            }
        });

        for (Integer i = 0; i < size; i++) {
            float valueY = chartData.confirmed.get(i);
            float valueX = i ;
            entries.add(new Entry(valueX, valueY));
        }
        //线的数据集
        LineDataSet dataSet = new LineDataSet(entries, "确诊"); // add entries to dataset


        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawCircles(false);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
//        lineChart.getLineData().addDataSet(dataSet);


        //second line
        entries = new ArrayList<Entry>();
        for (Integer i = 0; i < size; i++) {
            float valueY = chartData.cured.get(i);
            float valueX = i ;
            entries.add(new Entry(valueX, valueY));
        }
        //线的数据集

        dataSet = new LineDataSet(entries, "治愈");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawCircles(false);
        lineChart.getLineData().addDataSet(dataSet);

        //third line
        entries = new ArrayList<Entry>();
        for (Integer i = 0; i < size; i++) {
            float valueY = chartData.dead.get(i);
            float valueX = i ;
            entries.add(new Entry(valueX, valueY));
        }
        //线的数据集

        dataSet = new LineDataSet(entries, "死亡");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawCircles(false);
        lineChart.getLineData().addDataSet(dataSet);

        lineChart.invalidate();//更新图表

    }
}
