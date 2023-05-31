package com.stbu.bookms.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityUserManageBinding;

/**
 * @className ManageUserActivity
 * @description TODO 管理用户信息页面
 * @version 1.0
 */
public class ManageUserActivity extends BaseActivity {

    private ActivityUserManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEvent();
    }

    private void initEvent() {
        // 增加用户
        binding.btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, AddUserActivity.class);
            startActivity(intent);

        });

        // 查看用户
        binding.btnViewUser.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, ViewUserActivity.class);
            startActivity(intent);
            finish();
        });

        // 返回上一级
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        // 退出登录
        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }


}
