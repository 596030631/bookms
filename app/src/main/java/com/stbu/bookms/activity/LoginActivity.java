package com.stbu.bookms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stbu.bookms.databinding.ActivityLoginBinding;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;

/**
 * @version 1.0
 * @className LoginActivity
 * @description TODO 登录活动类
 */
public class LoginActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;
    private com.stbu.bookms.databinding.ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去掉状态栏, 此段代码应放在关联布局文件代码之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.inputPassword));

        binding.btnExit.setOnClickListener(view -> finish());


        initView();

        sharedPreferences = getSharedPreferences("userInfo", 0);
        String account = sharedPreferences.getString("id", "");
        String password = sharedPreferences.getString("password", "");

        binding.inputUser.setText(account);
        binding.inputPassword.setText(password);

        commonLogin();
        initEvent();

    }

    private void initView() {

    }

    private void initEvent() {

        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(v -> {

            Log.d("tag", "login");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.inputUser.getWindowToken(), 0);

            String account = binding.inputUser.getText().toString();
            String password = binding.inputPassword.getText().toString();

            if (account.contains("admin")) {
                if (account.equals("admin") && password.equals("123")) {
                    ExchangeActivity.LOGIN_USER = "admin";
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "管理员登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (account.length() != 0 && password.length() != 0) {
                    User tempUser = new User(account, password);
                    UserDao userDao = new UserDao(LoginActivity.this);
                    // 判断用户是否存在
                    User user = userDao.userIsExist(tempUser);
                    if (user != null) {
                        ExchangeActivity.LOGIN_USER = user.getId();
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        setUserAccount(user);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.d("tag", "gg");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void commonLogin() {
        Editable acc = binding.inputUser.getText();
        Editable pwd = binding.inputPassword.getText();
        if (acc == null || pwd == null) {
            Toast.makeText(getApplicationContext(), "用户名密码不全", Toast.LENGTH_SHORT).show();
            return;
        }
        String account = acc.toString();
        String password = pwd.toString();
        if (account.length() != 0 && password.length() != 0) {
            User tempUser = new User(account, password);
            UserDao userDao = new UserDao(LoginActivity.this);
            // 判断用户是否存在
            User user = userDao.userIsExist(tempUser);
            if (user != null) {
                ExchangeActivity.LOGIN_USER = user.getId();
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "用户名密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 将用户信息暂时存到SharePreference中，因为SharePreference只能存储普通类型数据，
     * 所以才存储用户信息时，将对象转换为JSON字符串后，再将JSON字符串保存。
     *
     * @param user 用户
     */
    private void setUserAccount(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 处理数据 object => json 字符串
        Gson gson = new Gson();
        String srtJson = gson.toJson(user);
        // 存放数据
        editor.putString("user", srtJson);
//        //
        editor.putString("password", user.getPassword());
        editor.putString("id", user.getId());
        // 完成提交
        editor.apply();
    }
}
