package com.op.materialdesigndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ItemNewsBinding;
import com.op.materialdesigndemo.vh.NewsViewHolder;

import java.util.List;

public class NewsAdapter<T> extends RecyclerView.Adapter<NewsViewHolder> {

    private Context context;
    private List<T> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_news, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.updateView(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }
}
