package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuFragment extends Fragment {
    private RecyclerView rvMenu;
    private MenuAdapter adapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        
        dbHelper = new DatabaseHelper(getContext());
        initViews(view);
        setupList();
        
        return view;
    }

    private void initViews(View view) {
        rvMenu = view.findViewById(R.id.rvMenu);
    }

    private void setupList() {
        List<Menu> menuList = dbHelper.getAllMenus();
        adapter = new MenuAdapter(menuList, new MenuAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(Menu menu) {
                Intent intent = new Intent(getContext(), MenuDetailActivity.class);
                intent.putExtra("menu_id", menu.getId());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMenu.setLayoutManager(layoutManager);
        rvMenu.setAdapter(adapter);
    }
}

