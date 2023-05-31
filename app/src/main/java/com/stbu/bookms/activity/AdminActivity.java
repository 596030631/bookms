package com.stbu.bookms.activity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityAdminBinding;

/**
 *  @className AdminActivity
 *  @description TODO 显示管理员主界面
 *  @version 1.0
 */
public class AdminActivity extends BaseActivity {

    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> super.hideKeyBoard(binding.getRoot()));

        initView();
        initEvent();
    }

    private void initEvent() {
        // 用户信息
        binding.btnUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageUserActivity.class);
            startActivity(intent);
        });

        // 促销信息  促销：对于图书进行统计，对价格最低的前十的图书进行罗列，并显示图书名称和图书价格。
        binding.btnShop.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, ShopActivity.class);
            startActivity(intent);
        });

        // 图书信息
        binding.btnBookInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageBookActivity.class);
            startActivity(intent);
        });
        // 图书互换信息
        binding.btnBookBorrowInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ChangeActivity.class);
            startActivity(intent);
        });

        // 交流信息
        binding.btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, FriendsActivity.class);
            startActivity(intent);
        });

        // 退出登录
        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {


    }
}
