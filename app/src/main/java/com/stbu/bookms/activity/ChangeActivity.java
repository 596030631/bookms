package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.ChangeAdapter;
import com.stbu.bookms.databinding.ActivityChangeBinding;
import com.stbu.bookms.databinding.DialogChangeBinding;
import com.stbu.bookms.entity.ChangeInfo;
import com.stbu.bookms.util.db.ChangeDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChangeActivity extends AppCompatActivity {
    private final List<ChangeInfo> data = new ArrayList<>();
    private static final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private com.stbu.bookms.databinding.ActivityChangeBinding binding;
    private final ChangeDao changeDao = new ChangeDao(this);
    private ChangeAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new ChangeAdapter(this, data);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
        binding.btnBack.setOnClickListener(view -> finish());

        thread.execute(() -> {
            List<ChangeInfo> list = changeDao.queryChangeInfo();
            if (list != null && !list.isEmpty()) {
                data.addAll(list);
            }
            handler.postDelayed(() -> adapter.notifyDataSetChanged(), 1000);
        });

        binding.btnAdd.setOnClickListener(view -> {
            DialogChangeBinding dialogBinding = DialogChangeBinding.inflate(getLayoutInflater());
            AlertDialog dialog = new AlertDialog.Builder(ChangeActivity.this)
                    .setView(dialogBinding.getRoot())
                    .create();
            dialog.show();
            dialogBinding.btnSure.setOnClickListener(view1 -> {
                Editable book = dialogBinding.book.getText();
                Editable address = dialogBinding.address.getText();
                Editable buyer = dialogBinding.buyer.getText();
                Editable sale = dialogBinding.saleName.getText();
                Editable price = dialogBinding.price.getText();
                if (book == null || sale == null ||address == null || buyer == null || price == null) {
                    Toast.makeText(ChangeActivity.this, "请录入信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ChangeInfo changeInfo = new ChangeInfo();
                changeInfo.address = address.toString();
                changeInfo.sale_name = sale.toString();
                changeInfo.buyerName = buyer.toString();
                changeInfo.price = price.toString();
                changeInfo.bookName = book.toString();
                changeDao.addChangeInfo(changeInfo);
                data.add(changeInfo);
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            });
        });
    }
}