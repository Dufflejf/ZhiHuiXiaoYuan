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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        
        holder.tvUserName.setText(comment.getUserName());
        holder.tvCommentContent.setText(comment.getContent());
        holder.tvCommentTime.setText(comment.getTime());

        // 加载头像
        String avatar = comment.getUserAvatar();
        if (avatar != null && !avatar.trim().isEmpty()) {
            try {
                if (avatar.startsWith("content://")) {
                    // 处理content:// URI
                    Uri uri = Uri.parse(avatar);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .fallback(R.mipmap.ic_launcher)
                            .circleCrop()
                            .into(holder.ivAvatar);
                } else if (avatar.startsWith("file://")) {
                    // 处理file:// URI
                    Uri uri = Uri.parse(avatar);
                    Glide.with(holder.itemView.getContext())
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .fallback(R.mipmap.ic_launcher)
                            .circleCrop()
                            .into(holder.ivAvatar);
                } else if (avatar.startsWith("http://") || avatar.startsWith("https://")) {
                    // 处理网络图片
                    Glide.with(holder.itemView.getContext())
                            .load(avatar)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .fallback(R.mipmap.ic_launcher)
                            .circleCrop()
                            .into(holder.ivAvatar);
                } else {
                    // 其他情况显示默认头像
                    holder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
                }
            } catch (Exception e) {
                e.printStackTrace();
                holder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            // 没有头像时显示默认头像
            holder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUserName;
        TextView tvCommentContent;
        TextView tvCommentTime;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
            tvCommentTime = itemView.findViewById(R.id.tvCommentTime);
        }
    }
}

