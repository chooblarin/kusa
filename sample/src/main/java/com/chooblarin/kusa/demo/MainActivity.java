package com.chooblarin.kusa.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chooblarin.kusa.ChartData;
import com.chooblarin.kusa.KusaView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ChartData> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChartData data = new ChartData(i, Color.BLUE);
            list.add(data);
        }

        KusaView kusaView = (KusaView) findViewById(R.id.kusa_view);
        kusaView.setChartDataList(list);
    }
}
