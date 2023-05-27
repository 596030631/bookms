package com.stbu.bookms.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityBookManageBinding;

/**
 * @className ManageBookActivity
 * @description TODO 管理图书信息页面
 * @version 1.0
 */
public class ManageBookActivity extends BaseActivity {

    private com.stbu.bookms.databinding.ActivityBookManageBinding binding;

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
