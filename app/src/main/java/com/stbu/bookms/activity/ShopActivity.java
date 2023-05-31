package com.stbu.bookms.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.ShopAdapter;
import com.stbu.bookms.databinding.ActivityShopBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShopActivity extends BaseActivity {

    private final BookDao bookDao = new BookDao(this);

    private Executor thread = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private ActivityShopBinding binding;
    private List<Book> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = bookDao.showTop10BookInfo();

        if (data != null && !data.isEmpty()) {
            Book[] ghj = new Book[data.size()];
            for (int i = 0; i < data.size(); i++) {
                ghj[i] = data.get(i);
            }
            quickSort(ghj, 0, ghj.length - 1);
        } else {
            data = new ArrayList<>();
        }

        List<Book> booksll = new ArrayList<>();
        for (int j = 0; j < Math.min(10, data.size()); j++) {
            booksll.add(data.get(j));
        }

        data.clear();
        data.addAll(booksll);

        ShopAdapter adapter = new ShopAdapter(this, data);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);

        binding.btnBack.setOnClickListener(view -> finish());
    }


    public void quickSort(Book[] prices, int left, int right) {
        if (left < right) {
            // 选择基准值，这里选取第一个元素
            Book pivot = prices[left];
            int i = left;
            int j = right;
            while (i < j) {
                // 找到左边第一个大于基准值的数
                while (i < j && Double.parseDouble(prices[j].getPrice()) >= Double.parseDouble(pivot.getPrice())) {
                    j--;
                }
                if (i < j) {
                    prices[i] = prices[j];
                    i++;
                }
                // 找到右边第一个小于等于基准值的数
                while (i < j && Double.parseDouble(prices[i].getPrice()) <= Double.parseDouble(pivot.getPrice())) {
                    i++;
                }
                if (i < j) {
                    prices[j] = prices[i];
                    j--;
                }
            }
            prices[i] = pivot;
            // 递归排序左右两个子列表
            quickSort(prices, left, i - 1);
            quickSort(prices, i + 1, right);
        }

        data.clear();
        data.addAll(Arrays.asList(prices));
    }


}