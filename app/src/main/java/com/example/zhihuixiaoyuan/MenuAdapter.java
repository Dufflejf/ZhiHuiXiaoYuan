package com.example.zhihuixiaoyuan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<Menu> menuList;
    private OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onMenuClick(Menu menu);
    }

    public MenuAdapter(List<Menu> menuList, OnMenuClickListener listener) {
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        
        holder.tvName.setText(menu.getName());

        // 加载图片
        String image = menu.getImage();
        if (image != null && !image.trim().isEmpty()) {
            try {
                if (image.startsWith("content://")) {
                    Uri uri = Uri.parse(image);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(holder.ivImage);
                } else if (image.startsWith("file://")) {
                    Uri uri = Uri.parse(image);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(holder.ivImage);
                } else if (image.startsWith("http://") || image.startsWith("https://")) {
                    Glide.with(holder.itemView.getContext())
                            .load(image)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(holder.ivImage);
                } else {
                    holder.ivImage.setImageResource(R.mipmap.ic_launcher);
                }
            } catch (Exception e) {
                e.printStackTrace();
                holder.ivImage.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            holder.ivImage.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMenuClick(menu);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList != null ? menuList.size() : 0;
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}

