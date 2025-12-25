package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LostFoundFragment extends Fragment {
    private Button btnPublish;
    private RecyclerView rvLostFound;
    private LostFoundAdapter adapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_found, container, false);
        
        dbHelper = new DatabaseHelper(getContext());
        initViews(view);
        setupList();
        
        return view;
    }

    private void initViews(View view) {
        btnPublish = view.findViewById(R.id.btnPublish);
        rvLostFound = view.findViewById(R.id.rvLostFound);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PublishLostFoundActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void setupList() {
        List<LostFound> list = dbHelper.getAllLostFound();
        adapter = new LostFoundAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLostFound.setLayoutManager(layoutManager);
        rvLostFound.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            // 刷新列表
            List<LostFound> list = dbHelper.getAllLostFound();
            adapter.updateList(list);
        }
    }
}

