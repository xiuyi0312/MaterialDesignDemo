package com.op.materialdesigndemo.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.op.materialdesigndemo.entity.UserBehavior;
import com.op.materialdesigndemo.vm.NewsViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private LayoutMenuHeaderBinding menuHeaderBinding;
    private NewsAdapter newsAdapter;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        menuHeaderBinding = DataBindingUtil.bind(binding.menu.navigation.getHeaderView(0));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        initViewAndListeners();
        initData();
    }

    private void initViewAndListeners() {
        binding.menu.navigation.setNavigationItemSelectedListener(this);
        binding.drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                updateMenu();
            }
        });

        binding.main.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.main.swipeRefresh.setOnRefreshListener(this);

        newsAdapter = new NewsAdapter<Story>(this);
        binding.main.recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.main.recyclerview.setAdapter(newsAdapter);
        binding.main.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter.setListener(new NewsAdapter.ItemOnClickListener<Story>() {
            @Override
            public void onItemClick(Story item) {
                // TODO: 2020/9/5 add or update User Behavior record
                viewModel.insertUserBehavior(UserBehavior.OP_CLICK, item.getId());
                DetailActivity.startActivity(MainActivity.this, item.getId());
            }
        });
    }

    private void initData() {
        viewModel = new NewsViewModel(getApplication());
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
        binding.main.swipeRefresh.setRefreshing(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.footprint) {
            binding.drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, FootprintActivity.class));
        } else if (item.getItemId() == R.id.settings) {

        } else if (item.getItemId() == R.id.about) {

        }
        binding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("CheckResult")
    private void updateMenu() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(viewModel.getStatistics());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String info) throws Exception {
                        menuHeaderBinding.name.setText(info);
                    }
                });

    }
}
