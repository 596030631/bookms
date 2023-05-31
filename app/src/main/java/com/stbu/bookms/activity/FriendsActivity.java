package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.FriendsAdapter;
import com.stbu.bookms.databinding.ActivityFriendsBinding;
import com.stbu.bookms.entity.ExchangeInfo;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.ExchangeDao;
import com.stbu.bookms.util.db.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// 好友列表
public class FriendsActivity extends BaseActivity {//与交流相互跳转

    private ActivityFriendsBinding binding;
    private FriendsAdapter adapter;
    private final List<ExchangeInfo> friendsList = new ArrayList<>();


    private static final Executor thread = Executors.newSingleThreadExecutor();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final ExchangeDao exchangeDao = new ExchangeDao(this);
    private final UserDao userDao = new UserDao(this);

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        binding.btnBack.setOnClickListener(view -> finish());

        // 不做搜索了，直接去聊天
        binding.btnSearch.setOnClickListener(view -> {
            if (binding.inputName.getText() == null) {
                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }

            String account = binding.inputName.getText().toString();

            User user = userDao.findId(new User(account, ""));
            if (user == null) {
                Toast.makeText(this, "该用户不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(FriendsActivity.this, ExchangeActivity.class);
            intent.putExtra("user", user.getId());
            startActivity(intent);

        });

        thread.execute(() -> {
            List<ExchangeInfo> list = exchangeDao.queryFriend();
            Log.d("tag", "查询到的用户纪录为:" + list.size());
            if (!list.isEmpty()) {
                Map<String, ExchangeInfo> names = new HashMap<>();

                ExchangeInfo tmp;
                for (ExchangeInfo i : list) {
                    if (ExchangeActivity.LOGIN_USER.equals( i.loginUser)) continue; // 用户自己的消息过滤不展示
                    if (names.containsKey(i.loginUser) && (tmp = names.get(i.loginUser)) != null) {
                        tmp.message = i.message;
                    } else {
                        names.put(i.loginUser, i);
                    }
                }
                friendsList.addAll(names.values());
                adapter.notifyDataSetChanged();
                handler.postDelayed(() -> onWindowFocusChanged(true), 2_000);
            }
        });


        adapter = new FriendsAdapter(this, friendsList);

        adapter.setOnclickCallback((position, user) -> {
            Intent intent = new Intent(FriendsActivity.this, ExchangeActivity.class);
            intent.putExtra("user", user.loginUser);
            startActivity(intent);
        });

        binding.recyclerview.setAdapter(adapter);


    }
}