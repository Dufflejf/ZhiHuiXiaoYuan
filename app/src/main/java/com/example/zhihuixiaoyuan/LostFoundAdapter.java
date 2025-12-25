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

public class LostFoundAdapter extends RecyclerView.Adapter<LostFoundAdapter.LostFoundViewHolder> {
    private List<LostFound> list;

    public LostFoundAdapter(List<LostFound> list) {
        this.list = list;
    }

    public void updateList(List<LostFound> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LostFoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lost_found, parent, false);
        return new LostFoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostFoundViewHolder holder, int position) {
        LostFound item = list.get(position);
        
        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvPhone.setText("联系电话：" + item.getPhone());

        // 加载图片
        String image = item.getImage();
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
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class LostFoundViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvDescription;
        TextView tvPhone;

        LostFoundViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}

