package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private ViewPager2 viewPagerBanner;
    private RecyclerView rvNews;
    private NewsAdapter newsAdapter;
    private BannerAdapter bannerAdapter;
    private DatabaseHelper dbHelper;
    private Handler handler;
    private Runnable autoScrollRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        
        dbHelper = new DatabaseHelper(getContext());
        initViews(view);
        setupNewsList();
        
        return view;
    }

    private void initViews(View view) {
        rvNews = view.findViewById(R.id.rvNews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvNews.setLayoutManager(layoutManager);
    }

    private void setupNewsList() {
        List<News> newsList = dbHelper.getAllNews();
        
        // 创建包含轮播图的适配器
        newsAdapter = new NewsAdapter(newsList, new NewsAdapter.OnNewsClickListener() {
            @Override
            public void onNewsClick(News news) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("news_url", news.getUrl());
                startActivity(intent);
            }
        });
        
        // 设置header
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_banner, rvNews, false);
        viewPagerBanner = headerView.findViewById(R.id.viewPagerBanner);
        List<String> bannerImages = new ArrayList<>();
        bannerImages.add("https://www.ujs.edu.cn/banner/20251017-huixin.jpg");
        bannerImages.add("https://www.ujs.edu.cn/__local/F/69/97/882603B25AB5AF582C69B9DABF0_8EA883B1_431AB.jpg");
        bannerImages.add("https://www.ujs.edu.cn/__local/B/2D/B4/3733D474DC780922C9D3607F839_6E7F4471_20A93.jpg");
        bannerAdapter = new BannerAdapter(bannerImages);
        viewPagerBanner.setAdapter(bannerAdapter);
        newsAdapter.setHeaderView(headerView);
        
        // 自动轮播
        handler = new Handler();
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPagerBanner.getCurrentItem();
                int totalItems = bannerAdapter.getItemCount();
                if (totalItems > 0) {
                    viewPagerBanner.setCurrentItem((currentItem + 1) % totalItems, true);
                }
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(autoScrollRunnable, 3000);
        

        rvNews.setAdapter(newsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null && autoScrollRunnable != null) {
            handler.removeCallbacks(autoScrollRunnable);
        }
    }
}

