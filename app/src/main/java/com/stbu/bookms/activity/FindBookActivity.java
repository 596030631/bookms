package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookms.R;
import com.stbu.bookms.adapter.BookAdapter;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.Borrow;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @className FindBookActivity
 * @description TODO 查找图书的活动
 * @version 1.0
 */
public class FindBookActivity extends AppCompatActivity {
    private ListView lv_find_book;
    private Button btn_return,btn_search;
    private String userId;
    private String bookId;
    private String bookName;
    private EditText bookNameSearch;
    private int bookNumber;
    private BookDao bookDao = new BookDao(FindBookActivity.this);
    private BorrowDao borrowDao = new BorrowDao(FindBookActivity.this);
    private BookAdapter bookAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        initView();
        ArrayList<Book> books = bookDao.showBookInfo();
        initData(books);
        initEvent();
    }



    private void initEvent() {
        lv_find_book.setOnItemClickListener((parent, view, position, id) -> {
            Book book = (Book) parent.getItemAtPosition(position);
            bookId = book.getBookId();
            bookName = book.getBookName();
            bookNumber = book.getBookNumber();
            if (bookNumber <= 0) {
                Toast.makeText(getApplicationContext(), "该图书余量:0，不可互换", Toast.LENGTH_SHORT).show();
            } else {
                boolean flag = false;
                // 查看某学号用户的全部互换信息
                List<Borrow> borrows = borrowDao.showAllBorrowBookForUser(userId);
                for (int i = 0; i < borrows.size(); i++) {
                    if ((borrows.get(i).getBorrowBookId()).equals(bookId)) {
                        flag = true;
                    }
                }
                if (flag) {
                    Toast.makeText(getApplicationContext(), "你已互换，不可重复互换", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindBookActivity.this);
                    builder.setTitle("确认互换？");
                    builder.setPositiveButton("确认", (dialog, whichButton) -> {
                        Book tempBook = bookDao.borrowBookNumberChange(bookId);
                        // 更新图书互换信息
                        bookDao.updateBorrowBookInfo(tempBook);
                        Borrow borrow = new Borrow(userId, bookId, bookName);
                        // 增加借书信息
                        borrowDao.addBorrowBookInfo(borrow);
                        bookDao = new BookDao(FindBookActivity.this);
                        Toast.makeText(getApplicationContext(), "互换成功", Toast.LENGTH_SHORT).show();
                        onStart();
                    });
                    builder.setNegativeButton("取消", (dialog, whichButton) -> dialog.dismiss());
                    builder.show();
                }
            }
        });

        // 返回
        btn_return.setOnClickListener(view -> {
            Intent intent = new Intent(FindBookActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });

        btn_search.setOnClickListener(v -> {
            String temp = bookNameSearch.getText().toString().trim();
            ArrayList<Book> books = (ArrayList<Book>) bookDao.findBookByName(temp);
            bookAdapter = new BookAdapter(FindBookActivity.this, books);
            lv_find_book.setAdapter(bookAdapter);
        });
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        lv_find_book = findViewById(R.id.lv_find_book);
        btn_return = findViewById(R.id.btn_return);
        btn_search = findViewById(R.id.btn_search);
        bookNameSearch = (EditText)findViewById(R.id.et_book_name_search);
    }

    private void initData(ArrayList<Book> books) {
        bookAdapter = new BookAdapter(FindBookActivity.this, books);
        lv_find_book.setAdapter(bookAdapter);
    }
}
