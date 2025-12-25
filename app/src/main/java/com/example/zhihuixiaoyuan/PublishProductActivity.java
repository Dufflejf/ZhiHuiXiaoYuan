package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PublishProductActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private static final int MAX_IMAGES = 4;

    private RecyclerView rvImages;
    private TextInputEditText etProductName;
    private TextInputEditText etPrice;
    private TextInputEditText etPhone;
    private Button btnPublish;

    private ImagePickerAdapter imageAdapter;
    private List<String> selectedImages;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_product);

        dbHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);
        String publisherName = sharedPreferences.getString("nickname", "用户");

        if (userId == -1) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        selectedImages = new ArrayList<>();
        initViews();
        setupImagePicker();
    }

    private void initViews() {
        rvImages = findViewById(R.id.rvImages);
        etProductName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etPhone = findViewById(R.id.etPhone);
        btnPublish = findViewById(R.id.btnPublish);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishProduct();
            }
        });
    }

    private void setupImagePicker() {
        imageAdapter = new ImagePickerAdapter(selectedImages, new ImagePickerAdapter.OnImageClickListener() {
            @Override
            public void onAddImageClick() {
                if (selectedImages.size() < MAX_IMAGES) {
                    pickImage();
                } else {
                    Toast.makeText(PublishProductActivity.this, "最多只能选择4张图片", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDeleteImageClick(int position) {
                selectedImages.remove(position);
                imageAdapter.notifyDataSetChanged();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvImages.setLayoutManager(layoutManager);
        rvImages.setAdapter(imageAdapter);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null && selectedImages.size() < MAX_IMAGES) {
                // 获取持久化URI权限（Android 10+需要）
                try {
                    getContentResolver().takePersistableUriPermission(imageUri, 
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                selectedImages.add(imageUri.toString());
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    private void publishProduct() {
        String productName = etProductName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(productName)) {
            Toast.makeText(this, "请输入商品名称", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "请输入价格", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                Toast.makeText(this, "价格必须大于0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入有效的价格", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String publisherName = sharedPreferences.getString("nickname", "用户");

        // 将图片URI列表转换为逗号分隔的字符串
        StringBuilder imagesBuilder = new StringBuilder();
        for (int i = 0; i < selectedImages.size(); i++) {
            if (i > 0) {
                imagesBuilder.append(",");
            }
            imagesBuilder.append(selectedImages.get(i));
        }
        String images = imagesBuilder.toString();

        long result = dbHelper.insertProduct(productName, price, images, userId, publisherName, phone, "");
        if (result > 0) {
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "发布失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}

