package com.op.materialdesigndemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ActivityFootprintBinding;

public class FootprintActivity extends AppCompatActivity {

    private ActivityFootprintBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_footprint);
        setSupportActionBar(binding.toolbar);
//        binding.toolbar.setTitle("Your Footprint");
//        binding.toolbar.setTitleTextColor(Color.WHITE);
//        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.calendar.setFirstDayOfWeek(2);// set the order of Sunday to Saturday, 2 means Monday comes first
        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
    }
}
