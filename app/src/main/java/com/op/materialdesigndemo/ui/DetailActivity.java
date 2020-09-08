package com.op.materialdesigndemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ActivityDetailBinding;
import com.op.materialdesigndemo.db.BehaviorDatabaseHelper;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.StoryDetail;
import com.op.materialdesigndemo.entity.UserBehavior;
import com.op.materialdesigndemo.util.HtmlGenerator;
import com.op.materialdesigndemo.vm.NewsViewModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private NewsViewModel viewModel;
    private Story story;
    private UserBehavior userBehavior;

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

        initData();
        initViewAndListeners();
    }

    private void initViewAndListeners() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.fabFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userBehavior != null && userBehavior.hasRead()) {
                    CommentFragment.getInstance(story.getId()).show(getSupportFragmentManager(), "COMMENT_FRAGMENT");
                } else {
                    viewModel.insertUserBehavior(UserBehavior.OP_READ, story.getId());
                    Snackbar.make(v, "Congrats and wanna comment now", Snackbar.LENGTH_LONG).setAction("Okay", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("News", "Comment now");
                            CommentFragment.getInstance(story.getId()).show(getSupportFragmentManager(), "COMMENT_FRAGMENT");
                        }
                    }).show();
                }
            }
        });
    }

    private void initData() {
        viewModel.storyDetailMutableLiveData.observe(this, new Observer<StoryDetail>() {
            @Override
            public void onChanged(StoryDetail storyDetail) {
                setContent(storyDetail);
            }
        });
        final int id = getIntent().getIntExtra("news_id", 0);
        viewModel.getNewsDetail(id);

        Observable.create(new ObservableOnSubscribe<Story>() {
            @Override
            public void subscribe(ObservableEmitter<Story> emitter) throws Exception {
                Story storyById = BehaviorDatabaseHelper.getInstance(DetailActivity.this)
                        .getStoryById(id);
                emitter.onNext(storyById);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Story>() {
                    @Override
                    public void accept(Story story) throws Exception {
                        DetailActivity.this.story = story;
                    }
                });

        Observable.create(new ObservableOnSubscribe<UserBehavior>() {
            @Override
            public void subscribe(ObservableEmitter<UserBehavior> emitter) throws Exception {
                UserBehavior userBehaviorById = BehaviorDatabaseHelper.getInstance(DetailActivity.this)
                        .getUserBehaviorById(id);
                if (userBehaviorById == null) {
                    userBehaviorById = new UserBehavior();
                    userBehaviorById.setStoryId(id);
                }
                emitter.onNext(userBehaviorById);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBehavior>() {
                    @Override
                    public void accept(UserBehavior userBehavior) throws Exception {
                        DetailActivity.this.userBehavior = userBehavior;
                        if (userBehavior != null && userBehavior.hasRead()) {
                            binding.fabFinish.setImageResource(R.drawable.ic_baseline_insert_comment_24);
                        } else {
                            binding.fabFinish.setImageResource(R.drawable.ic_baseline_check_24_white);
                        }
                    }
                });
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
