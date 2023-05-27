package com.stbu.bookms.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stbu.bookms.R;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;

/**
 * @className UpdateUserActivity
 * @description TODO 更新用户信息的活动
 * @version 1.0
 */
public class UpdateUserActivity extends BaseActivity {
    private EditText et_id, et_name, et_class_name, et_pwd, et_phone_number;
    private Button btn_save, btn_cancel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("user");

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 保存更新
        btn_save.setOnClickListener(v -> {
            if (et_id.length() == 0 || et_name.length() == 0 || et_class_name.length() == 0 || et_pwd.length() == 0 || et_phone_number.length() == 0) {
                Toast.makeText(UpdateUserActivity.this, "用户信息未填写完整", Toast.LENGTH_SHORT).show();
            } else {
                String id = et_id.getText().toString();
                String name = et_name.getText().toString();
                String className = et_class_name.getText().toString();
                String password = et_pwd.getText().toString();
                String phoneNumber = et_phone_number.getText().toString();

                User user = new User(id, name, className, password, phoneNumber, "0");
                UserDao userDao = new UserDao(UpdateUserActivity.this);
                // 更新用户信息
                userDao.updateUserInfo(user);
                Toast.makeText(UpdateUserActivity.this, "更新用户成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateUserActivity.this, ViewUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 取消更新
        btn_cancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateUserActivity.this, ViewUserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initData() {
        et_id.setText(user.getId());
        et_id.setEnabled(false);
        et_name.setText(user.getName());
        et_class_name.setText(user.getAddress());
        et_pwd.setText(user.getPassword());
        et_phone_number.setText(user.getPhoneNumber());
    }

    private void initView() {
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_class_name = findViewById(R.id.et_address);
        et_pwd = findViewById(R.id.et_pwd);
        et_phone_number = findViewById(R.id.et_phone_number);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
    }
}
