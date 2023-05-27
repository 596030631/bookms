package com.stbu.bookms.activity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityAddUserBinding;
import com.stbu.bookms.entity.ExchangeInfo;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.ExchangeDao;
import com.stbu.bookms.util.db.UserDao;

/**
 * @className AddUserActivity
 * @description TODO 添加用户活动类
 * @version 1.0
 */
public class AddUserActivity extends BaseActivity {
    private com.stbu.bookms.databinding.ActivityAddUserBinding binding;
    private final ExchangeDao exchangeDao = new ExchangeDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etAmount));
        initView();
        initEvent();
    }

    private void initEvent() {
        // 保存数据
        binding.btnSaveInfo.setOnClickListener(v -> {
            String tempId =binding.etId.getText().toString();
            String tempName = binding.etName.getText().toString();
            String tempClassName =binding.etAddress.getText().toString();
            String tempPassword = binding.etPwd.getText().toString();
            String tempPhoneNumber = binding.etPhoneNumber.getText().toString();
            String amount = binding.etAmount.getText().toString();

            User user = new User(tempId, tempName, tempClassName, tempPassword, tempPhoneNumber, amount);
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

                    ExchangeInfo exchangeInfo = new ExchangeInfo();
                    exchangeInfo.time = String.valueOf(System.currentTimeMillis());
                    exchangeInfo.message = "Hi, admin";
                    exchangeInfo.chatUser = "admin";
                    exchangeInfo.loginUser = user.getId();
                    exchangeDao.addExchangeInfo(exchangeInfo);

                    Toast.makeText(AddUserActivity.this, "添加用户成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddUserActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }
        });

//        // 取消添加
//       binding.btnCancelAdd.setOnClickListener(v -> {
//            Intent intent = new Intent(AddUserActivity.this, ManageUserActivity.class);
//            startActivity(intent);
//            finish();
//        });


       // 返回按钮事件
       binding.btnBack.setOnClickListener(view -> finish());

    }

    private void initView() {

    }
}
