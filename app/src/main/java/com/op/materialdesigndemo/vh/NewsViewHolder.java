package com.op.materialdesigndemo.vh;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.op.materialdesigndemo.databinding.ItemNewsBinding;
import com.op.materialdesigndemo.entity.Story;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private ItemNewsBinding binding;

    public NewsViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateView(Object news) {
        Story story = (Story) news;
        binding.tvSubtitle.setText(story.getTitle());
        binding.tvTitle.setText(((Story) news).getHint());
        if (story.getImages() != null && !story.getImages().isEmpty()) {
            Glide.with(binding.ivCover)
                    .load(story.getImages().get(0))
                    .into(binding.ivCover);
        }
    }
}
