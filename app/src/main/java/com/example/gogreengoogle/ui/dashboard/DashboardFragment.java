package com.example.gogreengoogle.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gogreengoogle.R;

public class DashboardFragment extends Fragment {

    double[] week1 = new double[]{4,4.8,6,5,4,3.6,3.1};
    double[] week2 = new double[]{2,5.6,6,7,4.4,4.6,5.2};
    double[] week3 = new double[]{2.4,5.8,6.4,7.6,8.4,4,5};
    double[] week4 = new double[]{3,4.4,5.8,6.4,7.6,5.8,6};
    double[] week5 = new double[]{6,5.6,4.6,5.8,6.4,4,2};
    double[] week6 = new double[]{5,7,4.4,7.6,8,5,6.4};
    double[] week7 = new double[]{2,5.6,8,6.4,7.6,5.2,5};
    double[] week8 = new double[]{3,4.8,5,5.6,7,8,7.8};
    double avg1, avg2, avg3, avg4, avg5, avg6, avg7, avg8;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button Week1 = root.findViewById(R.id.week1);
        Button Week2 = root.findViewById(R.id.week2);

        Week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent week1 = new Intent(getContext(),week1.class);
                startActivity(week1);
            }
        });

        Week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent week1 = new Intent(getContext(),week2.class);
                startActivity(week1);
            }
        });

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

        return root;
    }
}