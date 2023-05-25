package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.adapter.UserAdapter;
import com.stbu.bookmanagementsystem.entity.User;
import com.stbu.bookmanagementsystem.util.db.UserDao;

import java.util.ArrayList;

/**
 * @className ViewUserActivity
 * @description TODO 查看用户信息活动类
 * @version 1.0
 */
public class ViewUserActivity extends AppCompatActivity {
    private ListView lv_view_user;
    private Button btn_return;
    private UserDao userDao = new UserDao(ViewUserActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 返回
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(ViewUserActivity.this, ManageUserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<User> users = userDao.showUserInfo();
        UserAdapter userAdapter = new UserAdapter(ViewUserActivity.this, users);
        lv_view_user.setAdapter(userAdapter);
        lv_view_user.setOnItemClickListener((parent, view, position, id) -> {
            User user = (User) parent.getItemAtPosition(position);
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
        lv_view_user = findViewById(R.id.lv_view_user);
        btn_return = findViewById(R.id.btn_return);
    }
}

