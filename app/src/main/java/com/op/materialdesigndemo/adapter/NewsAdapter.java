package com.op.materialdesigndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.ItemNewsBinding;
import com.op.materialdesigndemo.databinding.ItemNewsGridBinding;
import com.op.materialdesigndemo.vh.GridNewsViewHolder;
import com.op.materialdesigndemo.vh.NewsViewHolder;

import java.util.List;

public class NewsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ItemOnClickListener<T> {
        void onItemClick(T item);
    }

    private Context context;
    private List<T> newsList;
    private ItemOnClickListener<T> listener;
    private boolean vertical = true;


    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setListener(ItemOnClickListener<T> listener) {
        this.listener = listener;
    }

    public void setData(List<T> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void switchLayout() {
        vertical = !vertical;
        notifyDataSetChanged();
    }

    public boolean isVertical() {
        return vertical;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_news, parent, false);
            return new NewsViewHolder(binding);
        } else {
            ItemNewsGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_news_grid, parent, false);
            return new GridNewsViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return vertical ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (vertical) {
            ((NewsViewHolder) holder).updateView(context, newsList.get(position));
        } else {
            ((GridNewsViewHolder) holder).updateView(context, newsList.get(position));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(newsList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }
}
