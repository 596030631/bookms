package com.stbu.bookms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.stbu.bookms.databinding.ActivityBookManageBinding;

/**
 * @version 1.0
 * @className ManageBookActivity
 * @description TODO 管理图书信息页面
 */
public class ManageBookActivity extends BaseActivity {//添加或查看书籍

    private ActivityBookManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initEvent() {
        // 增加图书信息
        binding.btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

//        // 修改图书信息
//        binding.btnUpdateBook.setOnClickListener(v -> {
//            Toast.makeText(this, "请在查看列表里修改", Toast.LENGTH_SHORT).show();
//        });

        // 查看图书信息
        binding.btnViewBook.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBookActivity.this, ViewBookActivity.class);
            startActivity(intent);
        });

        // 返回上一级 -> 管理员界面
        binding.btnReturn.setOnClickListener(v -> {
            finish();
        });

    }

    private void initView() {

    }
}
