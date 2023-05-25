package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.adapter.BookAdapter;
import com.stbu.bookmanagementsystem.entity.Book;
import com.stbu.bookmanagementsystem.util.db.BookDao;
import com.stbu.bookmanagementsystem.util.db.BorrowDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @className ViewBookActivity
 * @description TODO 查看图书信息活动类
 * @version 1.0
 */
public class ViewBookActivity extends AppCompatActivity {
    private ListView lv_view_book;
    private Button btn_return,btn_search;
    private EditText bookNameSearch;
    private BookDao bookDao = new BookDao(ViewBookActivity.this);
    private BorrowDao borrowDao = new BorrowDao(ViewBookActivity.this);
    private BookAdapter bookAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        initView();
        initEvent();
    }

    private void initEvent() {
        // 返回
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(ViewBookActivity.this, ManageBookActivity.class);
            startActivity(intent);
            finish();
        });
        //搜索
        btn_search.setOnClickListener(v -> {
            String temp = bookNameSearch.getText().toString().trim();
            ArrayList<Book> books = (ArrayList<Book>) bookDao.findBookByName(temp);
            bookAdapter = new BookAdapter(this, books);
            lv_view_book.setAdapter(bookAdapter);
        });
    }

    private void initView() {
        lv_view_book = findViewById(R.id.lv_find_book);
        btn_return = findViewById(R.id.btn_return);
        btn_search = findViewById(R.id.btn_search);
        bookNameSearch = (EditText)findViewById(R.id.et_book_name_search);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Book> books = bookDao.showBookInfo();
        BookAdapter bookAdapter = new BookAdapter(ViewBookActivity.this,  books);
        lv_view_book.setAdapter(bookAdapter);
        // 为每一项数据绑定事件
        lv_view_book.setOnItemClickListener((parent, view, position, id) -> {
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
