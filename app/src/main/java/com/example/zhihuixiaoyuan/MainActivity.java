package com.example.zhihuixiaoyuan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private Fragment newsFragment;
    private Fragment marketFragment;
    private Fragment lostFoundFragment;
    private Fragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查登录状态
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        if (userId == -1) {
            // 未登录，跳转到登录页面
            finish();
            return;
        }

        initFragments();
        initViews();

        // 默认显示第一个Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, newsFragment)
                .commit();
    }

    private void initFragments() {
        newsFragment = new NewsFragment();
        marketFragment = new MarketFragment();
        lostFoundFragment = new LostFoundFragment();
        menuFragment = new MenuFragment();
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                
                if (itemId == R.id.nav_news) {
                    selectedFragment = newsFragment;
                } else if (itemId == R.id.nav_market) {
                    selectedFragment = marketFragment;
                } else if (itemId == R.id.nav_message) {
                    selectedFragment = lostFoundFragment;
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = menuFragment;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}
