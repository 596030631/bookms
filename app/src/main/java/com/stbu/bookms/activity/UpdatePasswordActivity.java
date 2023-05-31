package com.stbu.bookms.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.databinding.ActivityUpdatePasswordBinding;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;

/**
 * @version 1.0
 * @className UpdatePasswordActivity
 * @description TODO 更新密码的活动的搞
 */
public class UpdatePasswordActivity extends AppCompatActivity {
    private User user;
    private ActivityUpdatePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 保存
        binding.btnSave.setOnClickListener(v -> {
            String newPassword = binding.etNewPwd.getEditableText().toString().trim();//可以去掉字符串两端的空格或其他指定字符，并返回新的字符串。
            String confirmPassword = binding.etConfirmPwd.getEditableText().toString().trim();
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
                String password = binding.etNewPwd.getText().toString();
                String id = user.getId();
                User user = new User();
                user.setId(id);
                UserDao userDao = new UserDao(UpdatePasswordActivity.this);
                // 通过id查询用户信息
                User tempUser = userDao.findUserById(user);
                tempUser.setPassword(password);

                if (binding.etAddress.getText() != null) {
                    tempUser.setAddress(binding.etAddress.getText().toString());
                }
                if (binding.etTel.getText() != null) {
                    tempUser.setPhoneNumber(binding.etTel.getText().toString());
                }


                // 更新用户信息
                userDao.updateUserInfo(tempUser);
                Toast.makeText(UpdatePasswordActivity.this, "更新密码成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdatePasswordActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 取消修改
        binding.btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdatePasswordActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initData() {
        user = getUserAccount();
        binding.etUserId.setText(user.getId());
        binding.etUserId.setEnabled(false);
        binding.tvAmount.setEnabled(false);
        binding.tvAmount.setText(user.getAmount());
        binding.etAddress.setText(user.getAddress());
        binding.etTel.setText(user.getPhoneNumber());
    }

    private void initView() {

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

        UserDao userDao = new UserDao(this);
        User vvv = userDao.findId(user);
        Log.d("tag", "查询到的用户信息为:"+vvv);
        if (vvv != null) return vvv;
        return user;
    }
}

