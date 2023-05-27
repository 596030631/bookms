package com.stbu.bookms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.R;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;
/**
 * @className UpdatePasswordActivity
 * @description TODO 更新密码的活动的搞
 * @version 1.0
 */
public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText et_user_id, et_new_pwd, et_confirm_pwd;
    private Button btn_save, btn_cancel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 保存
        btn_save.setOnClickListener(v -> {
            String newPassword = et_new_pwd.getEditableText().toString().trim();
            String confirmPassword = et_confirm_pwd.getEditableText().toString().trim();
            System.out.println(newPassword);
            System.out.println(confirmPassword);

            if (newPassword.length() == 0 || confirmPassword.length() == 0) {
                Toast.makeText(UpdatePasswordActivity.this, "密码信息未填写完整", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(UpdatePasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String password = et_new_pwd.getText().toString();
                String id = user.getId();
                User user = new User();
                user.setId(id);
                UserDao userDao = new UserDao(UpdatePasswordActivity.this);
                // 通过id查询用户信息
                User tempUser = userDao.findUserById(user);
                tempUser.setPassword(password);
                // 更新用户信息
                userDao.updateUserInfo(tempUser);
                Toast.makeText(UpdatePasswordActivity.this, "更新密码成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdatePasswordActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 取消修改
        btn_cancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdatePasswordActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initData() {
        user = getUserAccount();
        et_user_id.setText(user.getId());
        et_user_id.setEnabled(false);
    }

    private void initView() {
        et_user_id = findViewById(R.id.et_user_id);
        et_new_pwd = findViewById(R.id.et_new_pwd);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
    }

    /**
     * 将用户信息从SharePreference中取出来，将JSON字符串转换为对象，再实例化该对象。
     *
     * @return 返回获取用户账号信息
     */
    private User getUserAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        // 读取数据
        String strJson = sharedPreferences.getString("user", "");
        // 处理数据 json 字符串 => object
        Gson gson = new Gson();
        User user = gson.fromJson(strJson, new TypeToken<User>() {
        }.getType());
        return user;
    }
}

