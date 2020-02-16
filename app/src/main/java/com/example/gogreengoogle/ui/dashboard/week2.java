package com.example.gogreengoogle.ui.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.gogreengoogle.R;

import java.util.ArrayList;
import java.util.List;

public class week2 extends AppCompatActivity {

    double[] week1 = new double[]{4,4.8,6,5,4,3.6,3.1};
    double[] week2 = new double[]{2,5.6,6,7,4.4,4.6,5.2};
    double[] week3 = new double[]{2.4,5.8,6.4,7.6,8.4,4,5};
    double[] week4 = new double[]{3,4.4,5.8,6.4,7.6,5.8,6};
    double[] week5 = new double[]{6,5.6,4.6,5.8,6.4,4,2};
    double[] week6 = new double[]{5,7,4.4,7.6,8,5,6.4};
    double[] week7 = new double[]{2,5.6,8,6.4,7.6,5.2,5};
    double[] week8 = new double[]{3,4.8,5,5.6,7,8,7.8};
    double avg1, avg2, avg3, avg4, avg5, avg6, avg7, avg8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week2);

        for(int i=0; i<7; i++){
            avg1+=week1[i];
            avg2+=week2[i];
            avg3+=week3[i];
            avg4+=week4[i];
            avg5+=week5[i];
            avg6+=week6[i];
            avg7+=week7[i];
            avg8+=week8[i];
        }

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Week: 5", avg5));
        data.add(new ValueDataEntry("Week: 6", avg6));
        data.add(new ValueDataEntry("Week: 7", avg7));
        data.add(new ValueDataEntry("Week: 8", avg8));

        pie.data(data);
        anyChartView.setChart(pie);
    }
}
