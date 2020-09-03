package com.op.materialdesigndemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.adapter.NewsAdapter;
import com.op.materialdesigndemo.databinding.ActivityMainBinding;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.vm.NewsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityMainBinding binding;
    private NewsAdapter newsAdapter;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        binding.main.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.main.swipeRefresh.setOnRefreshListener(this);

        newsAdapter = new NewsAdapter<Story>(this);
        binding.main.recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.main.recyclerview.setAdapter(newsAdapter);
        binding.main.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        initData();
    }

    private void initData() {
        viewModel = new NewsViewModel();
        viewModel.newsMutableLiveData.observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> news) {
                newsAdapter.setData(news);
            }
        });
        viewModel.getLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawer.openDrawer(GravityCompat.START | Gravity.LEFT);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Toast.makeText(this, "add pressed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        viewModel.getLatestNews();
        binding.main.swipeRefresh.setRefreshing(false);
    }
}
