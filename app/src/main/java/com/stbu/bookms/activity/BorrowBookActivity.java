package com.stbu.bookms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.R;
import com.stbu.bookms.adapter.BorrowAdapter;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.Borrow;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;

/**
 * @Classname BorrowBookActivity
 * @Description TODO 查看已互换图书信息的活动//弃用
 * Version 1.0
 */
public class BorrowBookActivity extends AppCompatActivity {
    private ListView lv_borrow_book;
    private Button btn_return;
    private String book_id;
    private User user;
    private BorrowDao borrowDao = new BorrowDao(BorrowBookActivity.this);
    private BookDao bookDao = new BookDao(BorrowBookActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        user = getUserAccount();
    }

    private void initEvent() {
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(BorrowBookActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        lv_borrow_book = findViewById(R.id.lv_borrow_book);
        btn_return = findViewById(R.id.btn_return);
    }

    @Override
    protected void onStart() {//会执行一些需要在界面可见之前完成的初始化操作
        super.onStart();
        ArrayList<Borrow> borrows = borrowDao.showAllBorrowBookForUser(user.getId());
        BorrowAdapter borrowAdapter = new BorrowAdapter(BorrowBookActivity.this, borrows);
        lv_borrow_book.setAdapter(borrowAdapter);
        lv_borrow_book.setOnItemClickListener((parent, view, position, id) -> {
            Borrow borrow = (Borrow) parent.getItemAtPosition(position);
            book_id = borrow.getBorrowBookId();
            AlertDialog.Builder builder = new AlertDialog.Builder(BorrowBookActivity.this);
            builder.setTitle("确认还书？");
            builder.setPositiveButton("确认", (dialog, whichButton) -> {
                bookDao = new BookDao(BorrowBookActivity.this);
                // 获取待还书籍数量
                Book tempBook = bookDao.returnBookNumberChange(book_id);
                // 更新图书互换信息
                bookDao.updateBorrowBookInfo(tempBook);
                Borrow tempBorrow = new Borrow(user.getId(), book_id);
                // 删除借书信息
                borrowDao.deleteBorrowBookInfo(tempBorrow);
                Toast.makeText(getApplicationContext(), "还书成功", Toast.LENGTH_SHORT).show();
                onStart();
            });
            builder.setNegativeButton("取消", (dialog, whichButton) -> dialog.dismiss());
            builder.show();
        });
    }

    /**
     * 将用户信息从SharePreference中取出来，将JSON字符串转换为对象，再实例化该对象。
     *
     * @return 返回获取用户账号信息
     */
    private User getUserAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        // 读取数据
        String strJson = sharedPreferences.getString("user", "");
        // 处理数据 json 字符串 => object
        Gson gson = new Gson();
        User user = gson.fromJson(strJson, new TypeToken<User>() {
        }.getType());
        return user;
    }
}
