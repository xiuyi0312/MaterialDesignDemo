package com.op.materialdesigndemo.vh;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ItemNewsGridBinding;
import com.op.materialdesigndemo.db.BehaviorDatabaseHelper;
import com.op.materialdesigndemo.db.DBException;
import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.UserBehavior;
import com.op.materialdesigndemo.ui.DetailActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GridNewsViewHolder extends RecyclerView.ViewHolder {
    private ItemNewsGridBinding binding;

    public GridNewsViewHolder(ItemNewsGridBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateView(final Context context, Object news) {
        final Story story = (Story) news;
        Observable.create(new ObservableOnSubscribe<Story>() {
            @Override
            public void subscribe(ObservableEmitter<Story> emitter) throws Exception {
                Story storyById = BehaviorDatabaseHelper.getInstance(context)
                        .getStoryById(story.getId());
                emitter.onNext(storyById);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Story>() {
                    @Override
                    public void accept(Story story) throws Exception {
                        binding.tvSubtitle.setText(story.getTitle());
                        binding.tvTitle.setText(story.getHint());
                        if (story.getImages() != null && !story.getImages().isEmpty()) {
                            Glide.with(binding.ivCover)
                                    .load(story.getImages().get(0))
                                    .into(binding.ivCover);
                        }
                    }
                });

        Observable.create(new ObservableOnSubscribe<UserBehavior>() {
            @Override
            public void subscribe(ObservableEmitter<UserBehavior> emitter) throws Exception {
                UserBehavior userBehavior = BehaviorDatabaseHelper.getInstance(context)
                        .getUserBehaviorById(story.getId());
                if(userBehavior == null) {
                    emitter.onError(new DBException("null", 404));
                } else {
                    emitter.onNext(userBehavior);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBehavior>() {
                    @Override
                    public void accept(UserBehavior userBehavior) throws Exception {
                        if (userBehavior != null && userBehavior.hasClicked()) {
                            binding.tvSubtitle.setTextColor(context.getResources().getColor(R.color.gray));
                            binding.tvTitle.setTextColor(context.getResources().getColor(R.color.gray));
                        } else {
                            binding.tvSubtitle.setTextColor(context.getResources().getColor(R.color.black));
                            binding.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // do nothing
                    }
                });

    }
}
