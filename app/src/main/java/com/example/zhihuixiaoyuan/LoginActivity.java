package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etStudentId;
    private TextInputEditText etPassword;
    private CheckBox cbRememberPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        // 加载记住的密码
        loadRememberedPassword();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        etStudentId = findViewById(R.id.etStudentId);
        etPassword = findViewById(R.id.etPassword);
        cbRememberPassword = findViewById(R.id.cbRememberPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void loadRememberedPassword() {
        String savedStudentId = sharedPreferences.getString("student_id", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean isRemembered = sharedPreferences.getBoolean("remember_password", false);

        if (isRemembered && !TextUtils.isEmpty(savedStudentId)) {
            etStudentId.setText(savedStudentId);
            etPassword.setText(savedPassword);
            cbRememberPassword.setChecked(true);
        }
    }

    private void login() {
        String studentId = etStudentId.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(studentId)) {
            Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = dbHelper.login(studentId, password);
        if (user != null) {
            // 保存登录状态
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", user.getId());
            editor.putString("student_id", user.getStudentId());
            editor.putString("nickname", user.getNickname());
            // 确保avatar不为null
            String avatar = user.getAvatar();
            editor.putString("avatar", avatar != null ? avatar : "");

            // 记住密码
            if (cbRememberPassword.isChecked()) {
                editor.putString("password", password);
                editor.putBoolean("remember_password", true);
            } else {
                editor.remove("password");
                editor.putBoolean("remember_password", false);
            }
            editor.apply();

            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "学号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}

