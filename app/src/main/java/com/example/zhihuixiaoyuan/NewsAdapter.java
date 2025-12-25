package com.example.zhihuixiaoyuan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<News> newsList;
    private OnNewsClickListener listener;
    private View headerView;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    public NewsAdapter(List<News> newsList, OnNewsClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && headerView != null) {
            return new HeaderViewHolder(headerView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            return;
        }

        int actualPosition = headerView != null ? position - 1 : position;
        News news = newsList.get(actualPosition);
        NewsViewHolder newsHolder = (NewsViewHolder) holder;

        newsHolder.tvTitle.setText(news.getTitle());
        newsHolder.tvDate.setText(news.getDate());

        if (news.getImage() != null && !news.getImage().isEmpty()) {
            Glide.with(newsHolder.itemView.getContext())
                    .load(news.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(newsHolder.ivImage);
        } else {
            newsHolder.ivImage.setImageResource(R.mipmap.ic_launcher);
        }

        newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNewsClick(news);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = newsList != null ? newsList.size() : 0;
        return headerView != null ? count + 1 : count;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvDate;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivNewsImage);
            tvTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvDate = itemView.findViewById(R.id.tvNewsDate);
        }
    }
}

