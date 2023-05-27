package com.stbu.bookms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.stbu.bookms.adapter.BookAdapter;
import com.stbu.bookms.databinding.ActivityViewBookBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;

/**
 * @version 1.0
 * @className ViewBookActivity
 * @description TODO 查看图书信息活动类
 */
public class ViewBookActivity extends BaseActivity {
    private BookDao bookDao = new BookDao(ViewBookActivity.this);
    private BorrowDao borrowDao = new BorrowDao(ViewBookActivity.this);
    private BookAdapter bookAdapter;
    private com.stbu.bookms.databinding.ActivityViewBookBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBookBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etBookNameSearch));

        initView();
        initEvent();
    }

    private void initEvent() {
        // 返回
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        //搜索
        binding.btnSearch.setOnClickListener(v -> {
            String temp = binding.etBookNameSearch.getText().toString().trim();
            ArrayList<Book> books = (ArrayList<Book>) bookDao.findBookByName(temp);
            bookAdapter = new BookAdapter(this, books);
            binding.lvFindBook.setAdapter(bookAdapter);
        });
    }

    private void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Book> books = bookDao.showBookInfo();
        BookAdapter bookAdapter = new BookAdapter(ViewBookActivity.this, books);
        binding.lvFindBook.setAdapter(bookAdapter);
        // 为每一项数据绑定事件
        binding.lvFindBook.setOnItemClickListener((parent, view, position, id) -> {
            Book book = (Book) parent.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookActivity.this);
            builder.setTitle("请选择操作？");
            // 修改
            builder.setPositiveButton("修改", (dialog, whichButton) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                intent.setClass(ViewBookActivity.this, UpdateBookActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            });
            // 删除
            builder.setNegativeButton("删除", (dialog, whichButton) -> {
                // 删除图书信息
                bookDao.deleteBookInfo(book);
                onStart();
            });
            builder.show();
        });
    }
}
