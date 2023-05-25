package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;

/**
 * @className ManageBookActivity
 * @description TODO 管理图书信息页面
 * @version 1.0
 */
public class ManageBookActivity extends AppCompatActivity {
    private Button btn_add_book, btn_view_book, btn_return, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manage);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 增加图书信息
        btn_add_book.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, AddBookActivity.class);
            startActivity(intent);
            finish();
        });

        // 查看图书信息
        btn_view_book.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, ViewBookActivity.class);
            startActivity(intent);
            finish();
        });

        // 返回上一级 -> 管理员界面
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });
        // 退出登录
        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        btn_add_book = findViewById(R.id.btn_add_book);
        btn_view_book = findViewById(R.id.btn_view_book);
        btn_return = findViewById(R.id.btn_return);
        btn_logout = findViewById(R.id.btn_logout);
    }
}
