package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.R;
import com.stbu.bookms.adapter.OnItemClickListener;
import com.stbu.bookms.adapter.UserAdapter;
import com.stbu.bookms.databinding.ActivityViewUserBinding;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @className ViewUserActivity
 * @description TODO 查看用户信息活动类
 * @version 1.0
 */
public class ViewUserActivity extends AppCompatActivity {
    private final UserDao userDao = new UserDao(ViewUserActivity.this);
    private com.stbu.bookms.databinding.ActivityViewUserBinding binding;
    private static final Executor threads = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<User> users = new ArrayList<>();
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initEvent() {
        // 返回
       binding.btnReturn.setOnClickListener(v -> {
            finish();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();

        threads.execute(() -> {
           final ArrayList<User> us = userDao.showUserInfo();
            handler.post(() -> {
                users.clear();
                users.addAll(us);
                Log.d("tag", "用户数据:"+users.size());
                userAdapter.notifyDataSetChanged();
            });
        });


        userAdapter = new UserAdapter(ViewUserActivity.this, users);

        binding.recyclerview.setAdapter(userAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        userAdapter.setOnclickCallback((position, user) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewUserActivity.this);
            builder.setTitle("选择操作？");
            // 修改
            builder.setPositiveButton("修改", (dialog, whichButton) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.setClass(ViewUserActivity.this, UpdateUserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            });
            // 删除
            builder.setNegativeButton("删除", (dialog, whichButton) -> {
                // 删除用户
                userDao.deleteUserInfo(user);
                Toast.makeText(ViewUserActivity.this, "删除用户成功", Toast.LENGTH_SHORT).show();
                // 刷新页面信息
                onStart();
            });
            builder.show();
        });


    }

    private void initView() {

    }
}

