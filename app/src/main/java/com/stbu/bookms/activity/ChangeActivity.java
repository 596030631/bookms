package com.stbu.bookms.activity;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.BarringInfo;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.ChangeAdapter;
import com.stbu.bookms.databinding.ActivityChangeBinding;
import com.stbu.bookms.databinding.DialogChangeBinding;
import com.stbu.bookms.entity.Borrow;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChangeActivity extends AppCompatActivity {//互换信息查询
    private final List<Borrow> data = new ArrayList<>();
    private static final Executor thread = Executors.newSingleThreadExecutor();//只有一个线程在执行异步任务，这可以保证多个任务不会同时运行，从而避免资源冲突
    private final Handler handler = new Handler(Looper.getMainLooper());// 用于消息的传递和处理，它会将消息放入到一个消息队列中，并最终在相应的线程中执行这些消息。
    private ActivityChangeBinding binding;
    private final BorrowDao changeDao = new BorrowDao(this);
    private ChangeAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")//用于告诉 Lint 工具去忽略指定的 lint 检查。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new ChangeAdapter(this, data);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));//RecyclerView 是 Android 系统中的一个控件，它可以实现列表、网格等各种形式的滚动式布局。
        binding.recyclerview.setAdapter(adapter);
        binding.btnBack.setOnClickListener(view -> finish());

        thread.execute(() -> {
            List<Borrow> list = changeDao.showBorrowBookInfo();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if ("admin".equals(LOGIN_USER) || LOGIN_USER.equals(list.get(i).getBuyer_name()) || LOGIN_USER.equals(list.get(i).getOwn())) {
                        data.add(list.get(i));
                    }
                }
            }
            handler.postDelayed(() -> adapter.notifyDataSetChanged(), 1000);
        });


    }
}