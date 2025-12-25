package com.example.zhihuixiaoyuan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {
    private String[] imagePaths;

    public ImagePagerAdapter(String[] imagePaths) {
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        // ViewPager2 要求子视图必须使用 match_parent
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (position >= imagePaths.length) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        
        String imagePath = imagePaths[position].trim();
        if (imagePath.isEmpty()) {
            // 如果图片路径为空，显示默认图片
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else if (imagePath.startsWith("content://")) {
            // 处理content:// URI
            try {
                Uri uri = Uri.parse(imagePath);
                Glide.with(holder.itemView.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .fallback(R.mipmap.ic_launcher)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
            }
        } else if (imagePath.startsWith("file://")) {
            // 处理file:// URI
            try {
                Uri uri = Uri.parse(imagePath);
                Glide.with(holder.itemView.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .fallback(R.mipmap.ic_launcher)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
            }
        } else if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
            // 处理网络图片
            Glide.with(holder.itemView.getContext())
                    .load(imagePath)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .fallback(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        } else {
            // 其他情况显示默认图片
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return imagePaths != null ? imagePaths.length : 0;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}

