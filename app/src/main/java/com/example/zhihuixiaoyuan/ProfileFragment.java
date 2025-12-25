package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView tvNickname;
    private TextView tvStudentId;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        tvNickname = view.findViewById(R.id.tvNickname);
        tvStudentId = view.findViewById(R.id.tvStudentId);
        btnLogout = view.findViewById(R.id.btnLogout);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_prefs", getActivity().MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname", "");
        String studentId = sharedPreferences.getString("student_id", "");
        
        tvNickname.setText("昵称：" + nickname);
        tvStudentId.setText("学号：" + studentId);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}

