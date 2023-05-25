package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.User;
import com.stbu.bookmanagementsystem.util.db.UserDao;

/**
 * @className AddUserActivity
 * @description TODO 添加用户活动类
 * @version 1.0
 */
public class AddUserActivity extends AppCompatActivity {
    private EditText et_id, et_name, et_class_name, et_password, et_phone_number;
    private Button btn_save_info, btn_cancel_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 保存数据
        btn_save_info.setOnClickListener(v -> {
            String tempId = et_id.getText().toString();
            String tempName = et_name.getText().toString();
            String tempClassName = et_class_name.getText().toString();
            String tempPassword = et_password.getText().toString();
            String tempPhoneNumber = et_phone_number.getText().toString();

            User user = new User(tempId, tempName, tempClassName, tempPassword, tempPhoneNumber);
            UserDao userDao = new UserDao(AddUserActivity.this);
            if (tempId.length() == 0 || tempName.length() == 0 || tempClassName.length() == 0 || tempPassword.length() == 0 || tempPhoneNumber.length() == 0) {
                Toast.makeText(AddUserActivity.this, "用户信息未填写完整", Toast.LENGTH_SHORT).show();
            } else {
                // 查询用户是否存在
                if (userDao.checkExist(user)) {
                    Toast.makeText(AddUserActivity.this, "该学号已存在", Toast.LENGTH_SHORT).show();
                } else {
                    // 添加用户信息
                    userDao.addUserInfo(user);
                    Toast.makeText(AddUserActivity.this, "添加用户成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // 取消添加
        btn_cancel_add.setOnClickListener(v -> {
            Intent intent = new Intent(AddUserActivity.this, ManageUserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_class_name = findViewById(R.id.et_class_name);
        et_password = findViewById(R.id.et_pwd);
        et_phone_number = findViewById(R.id.et_phone_number);
        btn_save_info = findViewById(R.id.btn_save_info);
        btn_cancel_add = findViewById(R.id.btn_cancel_add);
    }
}
