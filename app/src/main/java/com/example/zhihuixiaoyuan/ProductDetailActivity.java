package com.example.zhihuixiaoyuan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private ViewPager2 viewPagerImages;
    private TextView tvProductPrice;
    private TextView tvProductName;
    private TextView tvPublisherName;
    private TextView tvPhone;
    private RecyclerView rvComments;
    private TextInputEditText etComment;
    private Button btnSendComment;
    
    private DatabaseHelper dbHelper;
    private Product product;
    private CommentAdapter commentAdapter;
    private int productId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productId = getIntent().getIntExtra("product_id", -1);
        if (productId == -1) {
            finish();
            return;
        }

        dbHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        initViews();
        loadProduct();
        setupImagePager();
        setupComments();
    }

    private void initViews() {
        viewPagerImages = findViewById(R.id.viewPagerImages);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductName = findViewById(R.id.tvProductName);
        tvPublisherName = findViewById(R.id.tvPublisherName);
        tvPhone = findViewById(R.id.tvPhone);
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

    private void loadProduct() {
        product = dbHelper.getProductById(productId);
        if (product != null) {
            tvProductPrice.setText("￥" + String.format("%.2f", product.getPrice()));
            tvProductName.setText(product.getName());
            tvPublisherName.setText("发布人：" + product.getPublisherName());
            tvPhone.setText("联系电话：" + product.getPhone());
        }
    }

    private void setupImagePager() {
        if (product != null && product.getImages() != null && !product.getImages().isEmpty()) {
            String[] imageArray = product.getImages().split(",");
            // 过滤空字符串
            int count = 0;
            for (String img : imageArray) {
                if (img != null && !img.trim().isEmpty()) {
                    count++;
                }
            }
            if (count > 0) {
                String[] validImages = new String[count];
                int index = 0;
                for (String img : imageArray) {
                    if (img != null && !img.trim().isEmpty()) {
                        validImages[index++] = img.trim();
                    }
                }
                ImagePagerAdapter adapter = new ImagePagerAdapter(validImages);
                viewPagerImages.setAdapter(adapter);
                return;
            }
        }
        // 如果没有图片，显示默认图片（至少显示一张）
        String[] defaultImages = {""};
        ImagePagerAdapter adapter = new ImagePagerAdapter(defaultImages);
        viewPagerImages.setAdapter(adapter);
    }

    private void setupComments() {
        List<Comment> commentList = dbHelper.getCommentsByProductId(productId);
        commentAdapter = new CommentAdapter(commentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(layoutManager);
        rvComments.setAdapter(commentAdapter);
    }

    private void sendComment() {
        String content = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入留言内容", Toast.LENGTH_SHORT).show();
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

        long result = dbHelper.insertComment(productId, userId, userName, userAvatar, content, time);
        if (result > 0) {
            Toast.makeText(this, "留言成功", Toast.LENGTH_SHORT).show();
            etComment.setText("");
            setupComments(); // 刷新留言列表
        } else {
            Toast.makeText(this, "留言失败", Toast.LENGTH_SHORT).show();
        }
    }
}

