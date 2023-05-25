package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;

/**
 * @className ManageUserActivity
 * @description TODO 管理用户信息页面
 * @version 1.0
 */
public class ManageUserActivity extends AppCompatActivity {
    private Button btn_add_user, btn_view_user, btn_return, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 增加用户
        btn_add_user.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, AddUserActivity.class);
            startActivity(intent);
            finish();
        });

        // 查看用户
        btn_view_user.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, ViewUserActivity.class);
            startActivity(intent);
            finish();
        });

        // 返回上一级
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });

        // 退出登录
        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        btn_add_user = findViewById(R.id.btn_add_user);
        btn_view_user = findViewById(R.id.btn_view_user);
        btn_return = findViewById(R.id.btn_return);
        btn_logout = findViewById(R.id.btn_logout);
    }
}
