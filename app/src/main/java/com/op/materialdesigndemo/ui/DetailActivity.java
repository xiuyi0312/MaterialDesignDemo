package com.op.materialdesigndemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ActivityDetailBinding;
import com.op.materialdesigndemo.entity.StoryDetail;
import com.op.materialdesigndemo.util.HtmlGenerator;
import com.op.materialdesigndemo.vm.NewsViewModel;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private NewsViewModel viewModel;

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("news_id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new NewsViewModel(getApplication());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        viewModel.storyDetailMutableLiveData.observe(this, new Observer<StoryDetail>() {
            @Override
            public void onChanged(StoryDetail storyDetail) {
                setContent(storyDetail);
            }
        });
        int id = getIntent().getIntExtra("news_id", 0);
        viewModel.getNewsDetail(id);
    }

    private void setContent(StoryDetail storyDetail) {
        binding.content.loadData(HtmlGenerator.generateHtml(storyDetail), "text/html; charset=utf-8", "utf-8");
        Glide.with(binding.cover).load(storyDetail.getImage()).into(binding.cover);
        // set tht article title to the CollapsingToolbarLayout not the toolbar
        binding.collapsing.setTitle(storyDetail.getTitle());
        binding.imageSource.setText(storyDetail.getImage_source());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            shareByIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareByIntent() {
        StoryDetail story = viewModel.storyDetailMutableLiveData.getValue();
        if (story == null) {
            Toast.makeText(this, "content doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, story.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, "share from <" + getResources().getString(R.string.app_name) + "> : " + story.getTitle() + "，http://daily.zhihu.com/story/" + story.getId());
        startActivity(Intent.createChooser(intent, "Share"));
    }
}
