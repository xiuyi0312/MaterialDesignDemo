package com.op.materialdesigndemo.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.adapter.NewsAdapter;
import com.op.materialdesigndemo.databinding.ActivityMainBinding;
import com.op.materialdesigndemo.databinding.LayoutMenuHeaderBinding;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.vm.NewsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener {
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

        binding.menu.navigation.setNavigationItemSelectedListener(this);

        binding.main.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.main.swipeRefresh.setOnRefreshListener(this);

        newsAdapter = new NewsAdapter<Story>(this);
        newsAdapter.setListener(new NewsAdapter.ItemOnClickListener<Story>() {
            @Override
            public void onItemClick(Story item) {
                DetailActivity.startActivity(MainActivity.this, item.getId());
            }
        });
        binding.main.recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.main.recyclerview.setAdapter(newsAdapter);
        binding.main.recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        binding.main.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));

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
        viewModel.bitmapMutableLiveData.observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                LayoutMenuHeaderBinding headerBinding = DataBindingUtil.bind(binding.menu.navigation.getHeaderView(0));
                if (headerBinding != null) {
                    headerBinding.avatar.setImageBitmap(bitmap);
                }
            }
        });
        viewModel.getLatestNews();
//        viewModel.getAvatar("");
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
        } else if (item.getItemId() == R.id.sort) {
            newsAdapter.switchLayout();
            binding.main.recyclerview.setLayoutManager(newsAdapter.isVertical() ?
                    new LinearLayoutManager(this) : new GridLayoutManager(this, 2));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        viewModel.getLatestNews();
        viewModel.getAvatar("");
        binding.main.swipeRefresh.setRefreshing(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.footprint) {

        } else if (item.getItemId() == R.id.settings) {

        } else if (item.getItemId() == R.id.about) {

        }
        binding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
