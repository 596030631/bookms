package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;

/**
 *  @className AdminActivity
 *  @description TODO 显示管理员主界面
 *  @version 1.0
 */
public class AdminActivity extends AppCompatActivity {
    private Button btn_user_info, btn_book_info, btn_book_borrow_info, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 用户信息
        btn_user_info.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageUserActivity.class);
            startActivity(intent);
            finish();
        });

        // 图书信息
        btn_book_info.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageBookActivity.class);
            startActivity(intent);
            finish();
        });
        // 图书借阅信息
        btn_book_borrow_info.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ViewBorrowActivity.class);
            startActivity(intent);
            finish();
        });

        // 退出登录
        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        btn_user_info = findViewById(R.id.btn_user_info);
        btn_book_info = findViewById(R.id.btn_book_info);
        btn_book_borrow_info = findViewById(R.id.btn_book_borrow_info);
        btn_logout = findViewById(R.id.btn_logout);
    }
}
