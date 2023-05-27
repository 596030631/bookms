package com.stbu.bookms.activity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.adapter.BorrowAdapter;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.Borrow;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;

/**
 * @className ViewBorrowActivity
 * @description TODO 查看互换信息的活动
 * @version 1.0
 */
public class ViewBorrowActivity extends AppCompatActivity {
    private ListView lv_view_borrow;
    private Button btn_return;
    private String userId, bookId;
    private BorrowDao borrowDao = new BorrowDao(ViewBorrowActivity.this);
    private BookDao bookDao = new BookDao(ViewBorrowActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrow);

        initView();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Borrow> borrows = borrowDao.showBorrowBookInfo();
        BorrowAdapter borrowAdapter = new BorrowAdapter(ViewBorrowActivity.this, borrows);
        lv_view_borrow.setAdapter(borrowAdapter);
        // 为每一项数据绑定事件
        lv_view_borrow.setOnItemClickListener((parent, view, position, id) -> {
            Borrow borrow = (Borrow) parent.getItemAtPosition(position);
            userId = borrow.getBorrowId();
            bookId = borrow.getBorrowBookId();
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBorrowActivity.this);
            builder.setTitle("是否删除该互换记录？");
            // 确认
            builder.setPositiveButton("确认", (dialog, whichButton) -> {
                bookDao = new BookDao(ViewBorrowActivity.this);
                // 获取图书数量
                Book tempBook = bookDao.returnBookNumberChange(bookId);
                // 更新图书信息
                bookDao.updateBorrowBookInfo(tempBook);
                Borrow tempBorrow = new Borrow(userId, bookId);
                // 删除互换信息
                borrowDao.deleteBorrowBookInfo(tempBorrow);
                onStart();
            });
            // 取消
            builder.setNegativeButton("取消", (dialog, whichButton) -> dialog.dismiss());
            builder.show();
        });
    }

    private void initEvent() {
        // 返回
        btn_return.setOnClickListener(view -> {
            Intent intent = new Intent(ViewBorrowActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        lv_view_borrow = findViewById(R.id.lv_view_borrow);
        btn_return = findViewById(R.id.btn_return);
    }
}
