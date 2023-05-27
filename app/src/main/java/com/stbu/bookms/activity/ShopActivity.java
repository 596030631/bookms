package com.stbu.bookms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.stbu.bookms.R;
import com.stbu.bookms.adapter.ShopAdapter;
import com.stbu.bookms.databinding.ActivityShopBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShopActivity extends AppCompatActivity {

    private final BookDao bookDao = new BookDao(this);

    private Executor thread = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private com.stbu.bookms.databinding.ActivityShopBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Book> data = bookDao.showTop10BookInfo();


        ShopAdapter adapter = new ShopAdapter(this, data);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);

        binding.btnBack.setOnClickListener(view -> finish());
    }
}