package com.stbu.bookms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.stbu.bookms.databinding.ActivityRegisterBinding;
import com.stbu.bookms.entity.ExchangeInfo;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.ExchangeDao;
import com.stbu.bookms.util.db.UserDao;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private final ExchangeDao exchangeDao = new ExchangeDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//输入法管理
            imm.hideSoftInputFromWindow(binding.inputAccount.getWindowToken(), 0);

        });


        binding.btnRegister.setOnClickListener(view -> {

            Editable accountEdit = binding.inputAccount.getText();
            Editable pwdEdit = binding.inputPassword.getText();
            Editable pwdEditRepeat = binding.inputPasswordRepeat.getText();
            if (accountEdit == null) {
                Toast.makeText(RegisterActivity.this, "用户不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwdEdit == null || pwdEditRepeat == null) {
                Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            String pwd = pwdEdit.toString();
            String pwd2 = pwdEditRepeat.toString();
            if (!pwd.equals(pwd2)) {
                Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            String account = accountEdit.toString();
            if (account.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "用户名空", Toast.LENGTH_SHORT).show();
            }

            Editable name = binding.inputName.getText();
            Editable address = binding.inputAddress.getText();
            Editable tel = binding.inputTel.getText();
            String nameStr = name == null ? "未填写" : name.toString();
            String addressStr = address == null ? "未填写" : address.toString();
            String telStr = tel == null ? "未填写" : tel.toString();

            User user = new User(account, nameStr, addressStr, pwd, telStr, "0");
            UserDao userDao = new UserDao(this);

            if (userDao.checkExist(user)) {
                Toast.makeText(this, "该账号已存在", Toast.LENGTH_SHORT).show();
            } else {
                // 添加用户信息
                userDao.addUserInfo(user);

                ExchangeInfo exchangeInfo = new ExchangeInfo();
                exchangeInfo.time = String.valueOf(System.currentTimeMillis());
                exchangeInfo.message = "Hi, admin";
                exchangeInfo.chatUser = "admin";
                exchangeInfo.loginUser = user.getId();
                exchangeDao.addExchangeInfo(exchangeInfo);

                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        });

        // 返回注册
        binding.btnGoSign.setOnClickListener(view -> finish());
    }
}