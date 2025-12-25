package com.example.zhihuixiaoyuan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.ImageViewHolder> {
    private List<String> imageList;
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onAddImageClick();
        void onDeleteImageClick(int position);
    }

    public ImagePickerAdapter(List<String> imageList, OnImageClickListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_picker, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (position < imageList.size()) {
            // 显示已选择的图片
            String imageUri = imageList.get(position);
            holder.btnDelete.setVisibility(View.VISIBLE);
            
            try {
                if (imageUri.startsWith("content://")) {
                    Uri uri = Uri.parse(imageUri);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(holder.ivImage);
                } else if (imageUri.startsWith("file://")) {
                    Uri uri = Uri.parse(imageUri);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(holder.ivImage);
                } else if (imageUri.startsWith("http://") || imageUri.startsWith("https://")) {
                    Glide.with(holder.itemView.getContext())
                            .load(imageUri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(holder.ivImage);
                } else {
                    holder.ivImage.setImageResource(R.mipmap.ic_launcher);
                }
            } catch (Exception e) {
                e.printStackTrace();
                holder.ivImage.setImageResource(R.mipmap.ic_launcher);
            }

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDeleteImageClick(position);
                    }
                }
            });
        } else {
            // 显示添加按钮
            holder.ivImage.setImageResource(R.mipmap.ic_launcher);
            holder.btnDelete.setVisibility(View.GONE);
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddImageClick();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // 如果图片数量小于最大值，显示一个添加按钮
        return imageList.size() < 4 ? imageList.size() + 1 : imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageButton btnDelete;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

