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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public void updateProducts(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("￥" + String.format("%.2f", product.getPrice()));

        // 加载第一张图片
        String images = product.getImages();
        if (images != null && !images.isEmpty()) {
            String[] imageArray = images.split(",");
            if (imageArray.length > 0) {
                String firstImage = imageArray[0].trim();
                try {
                    if (firstImage.startsWith("content://")) {
                        // 处理content:// URI
                        Uri uri = Uri.parse(firstImage);
                        Glide.with(holder.itemView.getContext())
                                .load(uri)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(holder.ivImage);
                    } else if (firstImage.startsWith("file://")) {
                        // 处理file:// URI
                        Uri uri = Uri.parse(firstImage);
                        Glide.with(holder.itemView.getContext())
                                .load(uri)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(holder.ivImage);
                    } else if (firstImage.startsWith("http://") || firstImage.startsWith("https://")) {
                        // 处理网络图片
                        Glide.with(holder.itemView.getContext())
                                .load(firstImage)
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
        } else {
            holder.ivImage.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivProductImage);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}

