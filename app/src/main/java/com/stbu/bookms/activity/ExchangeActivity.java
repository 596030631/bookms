package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.ExchangeAdapter;
import com.stbu.bookms.databinding.ActivityExchangeBinding;
import com.stbu.bookms.entity.ExchangeInfo;
import com.stbu.bookms.util.db.ExchangeDao;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// 交流  用户给管理员发消息
public class ExchangeActivity extends BaseActivity {

    public static String LOGIN_USER;

    private static final Executor thread = Executors.newSingleThreadExecutor();
    private final ExchangeDao exchangeDao = new ExchangeDao(this);
    private ActivityExchangeBinding binding;
    private List<ExchangeInfo> mDataList;
    private LinearLayoutManager layoutManager;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExchangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDataList = new ArrayList<>();
        ExchangeAdapter mChatListAdapter = new ExchangeAdapter(this, mDataList);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(mChatListAdapter);

        String chatUser = getIntent().getStringExtra("user");

        thread.execute(() -> {
            // 查询两人的对话
            List<ExchangeInfo> list = exchangeDao.queryChatMessage(chatUser, LOGIN_USER);
            Log.d("tag", "查询到的纪录为:"+list.size());
            if (!list.isEmpty()) {
                mDataList.addAll(list);
                mChatListAdapter.notifyDataSetChanged();
                handler.postDelayed(() -> onWindowFocusChanged(true), 2_000);
            }
        });

        mChatListAdapter.notifyDataSetChanged();

        // 监听键盘打开和关闭事件
        KeyboardVisibilityEvent.setEventListener(
                this,
                isOpen -> {
                    if (isOpen) {
                        binding.recyclerview.scrollToPosition(mDataList.size() - 1);
                    }
                });

        binding.send.setOnClickListener(view -> {
            Editable editable = binding.editText.getText();
            if (editable == null) {
                Toast.makeText(ExchangeActivity.this, "内容为空", Toast.LENGTH_SHORT).show();
                return;
            }
            final ExchangeInfo info = new ExchangeInfo();
            info.loginUser = LOGIN_USER;
            info.chatUser = chatUser;
            info.time = String.valueOf(System.currentTimeMillis());
            info.message = editable.toString();
            binding.editText.setText("");
            mDataList.add(info);
            mChatListAdapter.notifyDataSetChanged();
            layoutManager.scrollToPosition(mDataList.size() - 1);//滚动到最后一条

            thread.execute(() -> {
                String msg = exchangeDao.addExchangeInfo(info);
                runOnUiThread(() -> Toast.makeText(ExchangeActivity.this, msg, Toast.LENGTH_SHORT).show());
            });
        });

        binding.btnBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//是否滚动到随后一条信息
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int i = layoutManager.findLastVisibleItemPosition();
            Log.d("tag", "当前条目:" + i);
            if (i != mDataList.size() - 1) {
                binding.recyclerview.scrollToPosition(mDataList.size() - 1);
            }
        }
    }
}