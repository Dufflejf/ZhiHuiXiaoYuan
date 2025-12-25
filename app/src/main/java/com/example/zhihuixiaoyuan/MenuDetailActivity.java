package com.example.zhihuixiaoyuan;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuDetailActivity extends AppCompatActivity {
    private ImageView ivMenuImage;
    private TextView tvMenuName;
    private RecyclerView rvComments;
    private TextInputEditText etComment;
    private Button btnSendComment;

    private DatabaseHelper dbHelper;
    private Menu menu;
    private MenuCommentAdapter commentAdapter;
    private int menuId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        menuId = getIntent().getIntExtra("menu_id", -1);
        if (menuId == -1) {
            finish();
            return;
        }

        dbHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        initViews();
        loadMenu();
        setupComments();
    }

    private void initViews() {
        ivMenuImage = findViewById(R.id.ivMenuImage);
        tvMenuName = findViewById(R.id.tvMenuName);
        rvComments = findViewById(R.id.rvComments);
        etComment = findViewById(R.id.etComment);
        btnSendComment = findViewById(R.id.btnSendComment);

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void loadMenu() {
        menu = dbHelper.getMenuById(menuId);
        if (menu != null) {
            tvMenuName.setText(menu.getName());

            // 加载图片
            String image = menu.getImage();
            if (image != null && !image.trim().isEmpty()) {
                try {
                    if (image.startsWith("content://")) {
                        Uri uri = Uri.parse(image);
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(ivMenuImage);
                    } else if (image.startsWith("file://")) {
                        Uri uri = Uri.parse(image);
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(ivMenuImage);
                    } else if (image.startsWith("http://") || image.startsWith("https://")) {
                        Glide.with(this)
                                .load(image)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(ivMenuImage);
                    } else {
                        ivMenuImage.setImageResource(R.mipmap.ic_launcher);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ivMenuImage.setImageResource(R.mipmap.ic_launcher);
                }
            } else {
                ivMenuImage.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }

    private void setupComments() {
        List<MenuComment> commentList = dbHelper.getMenuCommentsByMenuId(menuId);
        commentAdapter = new MenuCommentAdapter(commentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(layoutManager);
        rvComments.setAdapter(commentAdapter);
    }

    private void sendComment() {
        String content = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == -1) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("nickname", "用户");
        String userAvatar = sharedPreferences.getString("avatar", "");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());

        long result = dbHelper.insertMenuComment(menuId, userId, userName, userAvatar, content, time);
        if (result > 0) {
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            etComment.setText("");
            setupComments(); // 刷新评论列表
        } else {
            Toast.makeText(this, "评论失败", Toast.LENGTH_SHORT).show();
        }
    }
}

