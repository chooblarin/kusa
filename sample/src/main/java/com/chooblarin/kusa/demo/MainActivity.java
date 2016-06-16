package com.chooblarin.kusa.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        final int[] colorResArray
                = {R.color.kusa_001, R.color.kusa_002, R.color.kusa_003, R.color.kusa_004};

        for (int i = 0; i < 100; i++) {
            int colorIndex = (int) (Math.random() * 4);
            int color = ContextCompat.getColor(this, colorResArray[colorIndex]);
            ChartData data = new ChartData(i, color);
            list.add(data);
        }

        KusaView kusaView = (KusaView) findViewById(R.id.kusa_view);
        kusaView.setChartDataList(list);
    }
}
