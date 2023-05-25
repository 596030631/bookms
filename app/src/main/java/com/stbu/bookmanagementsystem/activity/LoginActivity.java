package com.stbu.bookmanagementsystem.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.User;
import com.stbu.bookmanagementsystem.util.db.UserDao;
/**
 * @className LoginActivity
 * @description TODO 登录活动类
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    private EditText et_account, et_pwd;
    private Button btn_login, btn_reset, btnRegister;
    private RadioButton rb_admin, rb_user;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去掉状态栏, 此段代码应放在关联布局文件代码之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


//        et_pwd.setText(sharedPreferences.getString("password"," "));


        initView();

        sharedPreferences = getSharedPreferences("userInfo", 0);
        String account = sharedPreferences.getString("id","");
        String password = sharedPreferences.getString("password","");
        et_account.setText(account);
        et_pwd.setText(password);
        commonLogin();
        initEvent();

//        commonLogin();
    }

    private void initView() {
        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        rb_admin = findViewById(R.id.rb_admin);
        rb_user = findViewById(R.id.rb_user);
        btn_login = findViewById(R.id.btn_login);
        btn_reset = findViewById(R.id.btn_reset);
        btnRegister = findViewById(R.id.register);

    }

    private void initEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(v -> {
            String account = et_account.getText().toString();
            String password = et_pwd.getText().toString();

            // 管理员登录
            if (rb_admin.isChecked()) {
                if (account.equals("admin") && password.equals("123")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "管理员登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            // 普通用户登录
            if (rb_user.isChecked()) {
                if (account.length() != 0 && password.length() != 0) {
                    User tempUser = new User(account, password);
                    UserDao userDao = new UserDao(LoginActivity.this);
                    // 判断用户是否存在
                    User user = userDao.userIsExist(tempUser);
                    if (user != null) {
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        setUserAccount(user);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 重置页面
        btn_reset.setOnClickListener(v -> {
            et_account.setText("");
            et_pwd.setText("");
        });
    }

    public void commonLogin(){
        String account = et_account.getText().toString();
        String password = et_pwd.getText().toString();
        if (account.length() != 0 && password.length() != 0) {
            User tempUser = new User(account, password);
            UserDao userDao = new UserDao(LoginActivity.this);
            // 判断用户是否存在
            User user = userDao.userIsExist(tempUser);
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
//                setUserAccount(user);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                finish();
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
        editor = sharedPreferences.edit();
        // 处理数据 object => json 字符串
        Gson gson = new Gson();
        String srtJson = gson.toJson(user);
        // 存放数据
        editor.putString("user", srtJson);
//        //
        editor.putString("password",user.getPassword());
        editor.putString("id",user.getId());
        // 完成提交
        editor.apply();
    }
}
